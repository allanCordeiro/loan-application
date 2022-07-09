package com.allancordeiro.creditanalysis.usecase.customer.create;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.factory.CustomerFactory;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.infrastructure.security.login.PasswordManager;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerUseCase {

    private final CustomerGateway customerGateway;
    private final PasswordManager passwordManager;

    public CreateCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
        this.passwordManager = new PasswordManager();
    }

    public CreateCustomerOutputDto execute(CreateCustomerInputDto customer) throws Exception {
        Address address = customer.address();
        Customer newCustomer = new CustomerFactory().Create(
                customer.name(),
                customer.email(),
                customer.rg(),
                customer.IncomeValue(),
                passwordManager.encodePassword(customer.password()),
                customer.cpf(),
                address
        );
        this.customerGateway.create(newCustomer);

        return new CreateCustomerOutputDto(
                newCustomer.getId().toString(),
                newCustomer.getName(),
                newCustomer.getEmail(),
                newCustomer.getRg(),
                newCustomer.getCpf().GetMaskedCpf(),
                newCustomer.getPassword(),
                newCustomer.getIncomeValue().floatValue(),
                newCustomer.getAddress()
        );
    }
}