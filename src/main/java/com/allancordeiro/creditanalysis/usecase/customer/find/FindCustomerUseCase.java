package com.allancordeiro.creditanalysis.usecase.customer.find;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindCustomerUseCase {
    private final CustomerGateway customerGateway;

    public FindCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Optional<FindCustomerOutputDto> execute(FindCustomerInputDto input) throws Exception {
        Optional<Customer> customer = this.customerGateway.findByEmail(input.email());

        if(customer.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FindCustomerOutputDto(
                customer.get().getId().toString(),
                customer.get().getName(),
                customer.get().getEmail(),
                customer.get().getRg(),
                customer.get().getCpf().GetMaskedCpf(),
                customer.get().getPassword(),
                customer.get().getIncomeValue().floatValue(),
                customer.get().getAddress()
        ));

    }

}
