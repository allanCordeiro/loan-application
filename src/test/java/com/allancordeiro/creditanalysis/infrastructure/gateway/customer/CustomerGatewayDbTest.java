package com.allancordeiro.creditanalysis.infrastructure.gateway.customer;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.Cpf;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.model.AddressModel;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.model.CustomerModel;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerGatewayDbTest {
    @InjectMocks
    private CustomerGatewayDb customerGatewayDb;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    private Cpf cpf;
    private Address address;
    private Customer customer;

    private CustomerModel customerModel;
    private AddressModel addressModel;

    @BeforeEach
    public void init() throws Exception {
        this.customerGatewayDb = new CustomerGatewayDb(customerRepository, addressRepository);
        this.cpf = new Cpf("841.676.580-46");
        this.address = new Address(
                "Street name",
                "123",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "SP",
                "Some complement"
        );

        this.customer = new Customer(
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        this.customerModel = new CustomerModel(
                this.customer.getId(),
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                this.cpf.GetMaskedCpf(),
                1L,
                7000.00,
                "1234",
                Boolean.TRUE
        );

        this.addressModel = new AddressModel(
                1L,
                "Street name",
                "123",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "SP",
                "Some complement"
        );

        when(addressRepository.save(Mockito.any(AddressModel.class))).thenReturn(this.addressModel);
        when(addressRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(this.addressModel));

        when(customerRepository.findByEmail(this.customer.getEmail())).thenReturn(Optional.ofNullable(this.customerModel));
    }

    @Test
    public void should_create_a_customer() throws Exception {
        this.customer.AddCpf(this.cpf);
        this.customer.ChangeAddress(this.address);
        this.customer.Activate();

        this.customerGatewayDb.create(this.customer);

        Customer newCustomer = this.customerGatewayDb.
            findByEmail(this.customer.getEmail())
            .orElseThrow(CustomerNotFoundException::new);

        assertNotNull(newCustomer.getId());
        assertEquals(this.customer.getId(), newCustomer.getId());
        assertEquals(this.customer.getName(), newCustomer.getName());
        assertEquals(this.customer.getCpf().GetMaskedCpf(), newCustomer.getCpf().GetMaskedCpf());
        assertEquals(this.customer.getRg(), newCustomer.getRg());
        assertEquals(this.customer.getEmail(), newCustomer.getEmail());
        assertEquals(this.customer.getAddress().getStreet(), newCustomer.getAddress().getStreet());
        assertEquals(this.customer.getStatus(), newCustomer.getStatus());

    }

    @Test
    public void should_update_a_customer() throws Exception {

        this.customer.AddCpf(this.cpf);
        this.customer.ChangeAddress(this.address);
        this.customer.Activate();

        customerGatewayDb.create(this.customer);


        this.customer.ChangeName("Afonso Solano");
        this.customer.ChangePassword("Batatinha 123");

        this.customerModel = new CustomerModel(
                this.customer.getId(),
                "Afonso Solano",
                "customer@email.com",
                "11321132-7",
                this.cpf.GetMaskedCpf(),
                1L,
                7000.00,
                "Batatinha 123",
                Boolean.TRUE
        );


        when(customerRepository.findByEmail(this.customer.getEmail())).thenReturn(Optional.ofNullable(this.customerModel));
        when(customerRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(this.customerModel));

        customerGatewayDb.update(this.customer);

        Customer updatedCustomer = customerGatewayDb
            .findByEmail(this.customer.getEmail())
            .orElseThrow(CustomerNotFoundException::new);

        assertEquals(updatedCustomer.getId(), this.customer.getId());
        assertEquals(updatedCustomer.getName(), this.customer.getName());
        assertEquals(updatedCustomer.getPassword(), this.customer.getPassword());
    }

    @Test
    public void should_update_a_customer_address() throws Exception {
        this.customer.AddCpf(this.cpf);
        this.customer.ChangeAddress(this.address);
        this.customer.Activate();

        customerGatewayDb.create(this.customer);

        Address updatedAddress = new Address(
                "Avenue name",
                "1025",
                "Some neighborhood",
                "01211-150",
                "São Paulo",
                "SP"
        );

        this.customer.ChangeAddress(updatedAddress);

        this.addressModel = new AddressModel(
                2L,
                "Avenue name",
                "1025",
                "Some neighborhood",
                "01211-150",
                "São Paulo",
                "SP"
        );

        this.customerModel = new CustomerModel(
                this.customer.getId(),
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                this.cpf.GetMaskedCpf(),
                2L,
                7000.00,
                "1234",
                Boolean.TRUE
        );

        when(customerRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(this.customerModel));
        when(addressRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(this.addressModel));
        when(addressRepository.save(Mockito.any(AddressModel.class))).thenReturn(this.addressModel);


        customerGatewayDb.update(this.customer);

        Customer updatedCustomer = customerGatewayDb
                .findByEmail(this.customer.getEmail())
                .orElseThrow(CustomerNotFoundException::new);

        assertEquals(updatedCustomer.getId(), this.customer.getId());
        assertEquals(updatedCustomer.getAddress().getStreet(), this.customer.getAddress().getStreet());
        assertEquals(updatedCustomer.getAddress().getNumber(),this.customer.getAddress().getNumber());
        assertEquals(updatedCustomer.getAddress().getNeighborhood(), this.customer.getAddress().getNeighborhood());
        assertEquals(updatedCustomer.getAddress().getCep(), this.customer.getAddress().getCep());
        assertEquals(updatedCustomer.getAddress().getCity(), this.customer.getAddress().getCity());
        assertEquals(updatedCustomer.getAddress().getState(), this.customer.getAddress().getState());
        assertEquals(updatedCustomer.getAddress().getComplement(), this.customer.getAddress().getComplement());
    }

    @Test
    public void should_find_a_customer_by_email() throws Exception {
        this.customer.AddCpf(this.cpf);
        this.customer.ChangeAddress(this.address);
        this.customer.Activate();

        customerGatewayDb.create(this.customer);

        Customer newCustomer = customerGatewayDb
                .findByEmail(this.customer.getEmail())
                .orElseThrow(CustomerNotFoundException::new);

        assertEquals(this.customer.getId(), newCustomer.getId());

    }

}