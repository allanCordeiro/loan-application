package com.allancordeiro.creditanalysis.domain.customer.factory;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.Cpf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerFactoryTest {
    @Test
    public void should_create_a_complete_customer_entity() throws Exception {
        Address address = new Address(
                "Street number five",
                "123",
                "Bairro Teste",
                "07511-000",
                "Cidade dos Testes",
                "RJ",
                "Casa do fundo"
        );

        Customer customer = new CustomerFactory().Create(
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234",
                "931.920.480-26",
                address
        );


        Assertions.assertNotNull(customer);
        assertNotNull(customer.getId());
        Assertions.assertEquals(customer.getCpf().GetMaskedCpf(), "931.920.480-26");
        Assertions.assertEquals(customer.getAddress(), address);
        Assertions.assertEquals(customer.getStatus(), Boolean.TRUE);
    }
}
