package com.allancordeiro.creditanalysis.domain.customer.factory;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.Cpf;

public class CustomerFactory {
    public Customer Create(String name, String email, String rg, Number incomeValue, String password,
                                            String cpf, Address address) throws Exception {

        Customer customer = new Customer(name, email, rg, incomeValue, password);
        Cpf cpfEntity = new Cpf(cpf);
        customer.AddCpf(cpfEntity);
        customer.ChangeAddress(address);
        customer.Activate();

        return customer;
    }

}
