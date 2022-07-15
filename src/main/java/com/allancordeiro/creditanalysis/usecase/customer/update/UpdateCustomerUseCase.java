package com.allancordeiro.creditanalysis.usecase.customer.update;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateAddressOutputDto;
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

        Address address = new Address(
                customer.address().street(),
                customer.address().number(),
                customer.address().neighborhood(),
                customer.address().cep(),
                customer.address().city(),
                customer.address().state(),
                customer.address().complement()
        );

        customerTobeUpdated.ChangeAddress(address);
        customerTobeUpdated.ChangeName(customer.name());
        customerTobeUpdated.ChangePassword(customer.password());
        customerTobeUpdated.ChangeIncomeValue(customer.incomeValue());

        this.customerGateway.update(customerTobeUpdated);

        return new UpdateCustomerOutputDto(
                customerTobeUpdated.getId().toString(),
                customerTobeUpdated.getName(),
                customerTobeUpdated.getEmail(),
                customerTobeUpdated.getRg(),
                customerTobeUpdated.getCpf().GetMaskedCpf(),
                customerTobeUpdated.getPassword(),
                customerTobeUpdated.getIncomeValue().floatValue(),
                new UpdateAddressOutputDto(
                        customerTobeUpdated.getAddress().getStreet(),
                        customerTobeUpdated.getAddress().getNumber(),
                        customerTobeUpdated.getAddress().getNeighborhood(),
                        customerTobeUpdated.getAddress().getCep(),
                        customerTobeUpdated.getAddress().getCity(),
                        customerTobeUpdated.getAddress().getState(),
                        customerTobeUpdated.getAddress().getComplement()
                )
        );
    }
}
