package com.allancordeiro.creditanalysis.usecase.customer.create;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.factory.CustomerFactory;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.infrastructure.security.login.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateCustomerUseCase {

    @Autowired
    private final CustomerGateway customerGateway;
    private final PasswordManager passwordManager;

    public CreateCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
        this.passwordManager = new PasswordManager();
    }

    public CreateCustomerOutputDto execute(CreateCustomerInputDto customer) throws Exception {
        Address address = new Address(
                customer.address().street(),
                customer.address().number(),
                customer.address().neighborhood(),
                customer.address().cep(),
                customer.address().city(),
                customer.address().state(),
                customer.address().complement()
        );
        Customer newCustomer = new CustomerFactory().Create(
                customer.name(),
                customer.email(),
                customer.rg(),
                customer.incomeValue(),
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
                new AddressOutputDto(
                        newCustomer.getAddress().getStreet(),
                        newCustomer.getAddress().getNumber(),
                        newCustomer.getAddress().getNeighborhood(),
                        newCustomer.getAddress().getCep(),
                        newCustomer.getAddress().getCity(),
                        newCustomer.getAddress().getState(),
                        newCustomer.getAddress().getComplement()
                )
        );
    }
}