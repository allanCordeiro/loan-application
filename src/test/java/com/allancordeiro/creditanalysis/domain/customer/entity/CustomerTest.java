package com.allancordeiro.creditanalysis.domain.customer.entity;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.email.EmailInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.email.EmailIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.incomeValue.IncomeValueIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.name.NameIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.password.PasswordIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.rg.RgIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.status.AddressIsRequiredToActivateCustomerException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.status.CpfIsRequiredToActivateCustomerException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.Cpf;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfCannotBeChangedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {

    @Test
    public void should_create_a_customer() throws Exception {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId,
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        assertNotNull(customer);
        assertEquals(customer.getId(), customerId);
        assertEquals(customer.getName(), "Customer Name");
        assertEquals(customer.getEmail(), "customer@email.com");
        assertEquals(customer.getRg(), "11321132-7");
        assertEquals(customer.getIncomeValue(), 7000.00);
        assertEquals(customer.getPassword(), "1234");
    }

    @Test
    public void should_create_a_customer_without_an_id() throws Exception {
        Customer customer = new Customer(
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals(customer.getName(), "Customer Name");
        assertEquals(customer.getEmail(), "customer@email.com");
        assertEquals(customer.getRg(), "11321132-7");
        assertEquals(customer.getIncomeValue(), 7000.00);
        assertEquals(customer.getPassword(), "1234");
    }

    @Test
    public void should_fail_when_customer_has_no_name() {
        Exception exception = assertThrows(NameIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    "1234");
        });

        assertEquals(NameIsMandatoryException.class, exception.getClass());

    }

    @Test
    public void should_fail_when_customer_has_no_email() {
        Exception exception = assertThrows(EmailIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer name",
                    "",
                    "11321132-7",
                    7000.00,
                    "1234"
            );
        });

        assertEquals(EmailIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_fail_when_customer_has_an_incorrect_email_format() {
        Exception exception = assertThrows(EmailInvalidFormatException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer name",
                    "este é um email inválido",
                    "11321132-7",
                    7000.00,
                    "1234"
            );
        });

        assertEquals(EmailInvalidFormatException.class, exception.getClass());

    }

    @Test
    public void should_fail_when_customer_has_no_rg() {
        Exception exception = assertThrows(RgIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer name",
                    "customer@email.com",
                    "",
                    7000.00,
                    "1234"
            );
        });
        assertEquals(RgIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_fail_when_customer_has_no_income_value() {
        Exception exception = assertThrows(IncomeValueIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer name",
                    "customer@email.com",
                    "11321132-7",
                    0.0,
                    "1234"
            );
        });
        assertEquals(IncomeValueIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_fail_when_customer_has_no_password() {
        Exception exception = assertThrows(PasswordIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer name",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    ""
            );
        });
        assertEquals(PasswordIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_activate_a_customer() throws Exception {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId,
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        Cpf cpf = new Cpf("171.125.060-00");
        Address address = new Address(
                "Test street",
                "123",
                "My Neighborhood",
                "04255-055",
                "São Paulo",
                "Lapa"
        );
        customer.AddCpf(cpf);
        customer.ChangeAddress(address);
        customer.Activate();

        assertNotNull(customer);
        assertEquals(customer.getStatus(), Boolean.TRUE);
    }

    @Test
    public void throw_exception_when_try_to_activate_customer_without_address() {
        Exception exception = assertThrows(AddressIsRequiredToActivateCustomerException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer Name",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    "1234"
            );
            Cpf cpf = new Cpf("171.125.060-00");
            customer.AddCpf(cpf);

            customer.Activate();
        });
        assertEquals(AddressIsRequiredToActivateCustomerException.class, exception.getClass());
    }

    @Test
    public void throw_exception_when_try_to_activate_customer_without_cpf() {
        Exception exception = assertThrows(CpfIsRequiredToActivateCustomerException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer Name",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    "1234"
            );

            Address address = new Address(
                    "Test street",
                    "123",
                    "My Neighborhood",
                    "04255-055",
                    "São Paulo",
                    "Lapa"
            );

            customer.ChangeAddress(address);
            customer.Activate();
        });
        assertEquals(CpfIsRequiredToActivateCustomerException.class, exception.getClass());
    }

    @Test
    public void should_change_customers_name() throws Exception {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId,
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        customer.ChangeName("Customer Name Changed");
        assertEquals(customer.getName(), "Customer Name Changed");
    }

    @Test
    public void should_throw_exception_when_try_to_erase_customer_name() {
        Exception exception = assertThrows(NameIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer Name",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    "1234"
            );

            customer.ChangeName("");
        });
        assertEquals(NameIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_change_a_customer_password() throws Exception {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId,
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        assertNotNull(customer);
        customer.ChangePassword("5678");
        assertEquals(customer.getPassword(), "5678");
    }

    @Test
    public void throw_exception_when_try_to_erase_customer_password() {
        Exception exception = assertThrows(PasswordIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer Name",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    "1234"
            );

            assertNotNull(customer);
            customer.ChangePassword("");
        });
        assertEquals(PasswordIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_change_customer_address() throws Exception {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId,
                "Customer Name",
                "customer@email.com",
                "11321132-7",
                7000.00,
                "1234"
        );

        Cpf cpf = new Cpf("171.125.060-00");
        Address address = new Address(
                "Test street",
                "123",
                "My Neighborhood",
                "04255-055",
                "São Paulo",
                "Lapa"
        );
        customer.AddCpf(cpf);
        customer.ChangeAddress(address);
        customer.Activate();

        assertNotNull(customer);
        assertEquals(customer.getStatus(), Boolean.TRUE);

        Address addressUpdated = new Address(
                "Test street II",
                "1566",
                "My Neighborhood",
                "04255-055",
                "Rio de Janeiro",
                "Leblon"
        );
        customer.ChangeAddress(addressUpdated);
        assertEquals(customer.getAddress(), addressUpdated);
    }

    @Test
    public void should_throw_exception_when_try_to_change_a_customers_cpf() throws Exception {
        Exception exception = assertThrows(CpfCannotBeChangedException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(
                    customerId,
                    "Customer Name",
                    "customer@email.com",
                    "11321132-7",
                    7000.00,
                    "1234"
            );

            Cpf cpf = new Cpf("171.125.060-00");
            Address address = new Address(
                    "Test street",
                    "123",
                    "My Neighborhood",
                    "04255-055",
                    "São Paulo",
                    "Lapa"
            );
            customer.AddCpf(cpf);
            customer.ChangeAddress(address);
            customer.Activate();

            assertNotNull(customer);
            assertEquals(customer.getStatus(), Boolean.TRUE);
            assertEquals(customer.getCpf().GetMaskedCpf(), cpf.GetMaskedCpf());
            Cpf cpfUpdated = new Cpf("915.606.120-02");
            customer.AddCpf(cpfUpdated);
        });
        assertEquals(CpfCannotBeChangedException.class, exception.getClass());
    }
}