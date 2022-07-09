package com.allancordeiro.creditanalysis.usecase.customer.update;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCustomerUseCase {
    private final CustomerGateway customerGateway;

    public UpdateCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public UpdateCustomerOutputDto execute(UpdateCustomerInputDto customer) throws Exception {
        Customer customerTobeUpdated = this.customerGateway
                .findById(UUID.fromString(customer.id()))
                .orElseThrow(CustomerNotFoundException::new);

        customerTobeUpdated.ChangeAddress(customer.address());
        customerTobeUpdated.ChangeName(customer.name());
        customerTobeUpdated.ChangePassword(customer.password());
        customerTobeUpdated.ChangeIncomeValue(customer.IncomeValue());

        this.customerGateway.update(customerTobeUpdated);

        return new UpdateCustomerOutputDto(
                customerTobeUpdated.getId().toString(),
                customerTobeUpdated.getName(),
                customerTobeUpdated.getEmail(),
                customerTobeUpdated.getRg(),
                customerTobeUpdated.getCpf().GetMaskedCpf(),
                customerTobeUpdated.getPassword(),
                customerTobeUpdated.getIncomeValue().floatValue(),
                customerTobeUpdated.getAddress()
        );
    }
}
