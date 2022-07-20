package com.allancordeiro.creditanalysis.domain.loanApplication.entity;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.name.NameIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.customer.CustomerIdIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.value.ValueIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsNotAllowedException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsNotAllowedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationTest {

    @Test
    public void should_create_a_loan_application() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
                1L,
                customerId,
                5000.00,
                firstInstallmentDate,
                36
        );

        assertNotNull(loanApplication);
        assertNotNull(loanApplication.getId());
        assertEquals(loanApplication.getCustomerId(), customerId);
        assertEquals(loanApplication.getValue(), 5000.00);
        assertEquals(loanApplication.getFirstInstallmentDate(), firstInstallmentDate);
        assertEquals(loanApplication.getInstallmentQty(), 36);
    }

    @Test
    public void should_throw_exception_when_customer_is_empty() {
        Exception exception = assertThrows(CustomerIdIsMandatoryException.class, () -> {
            LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
            LoanApplication loanApplication = new LoanApplication(
                    null,
                    5000.00,
                    firstInstallmentDate,
                    36
            );
        });
        assertEquals(CustomerIdIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_value_is_empty() {
        Exception exception = assertThrows(ValueIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
            LoanApplication loanApplication = new LoanApplication(
                    customerId,
                    0,
                    firstInstallmentDate,
                    36
            );
        });
        assertEquals(ValueIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_first_installment_date_is_empty() {
        Exception exception = assertThrows(FirstInstallmentDateIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
            LoanApplication loanApplication = new LoanApplication(
                    customerId,
                    5000.00,
                    null,
                    36
            );
        });
        assertEquals(FirstInstallmentDateIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_installment_qty_is_empty() {
        Exception exception = assertThrows(InstallmentQtyIsMandatoryException.class, () -> {
            UUID customerId = UUID.randomUUID();
            LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
            LoanApplication loanApplication = new LoanApplication(
                    customerId,
                    5000.00,
                    firstInstallmentDate,
                    0
            );
        });
        assertEquals(InstallmentQtyIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_installment_qty_greater_than_allowed() {
        Exception exception = assertThrows(InstallmentQtyIsNotAllowedException.class, () -> {
            UUID customerId = UUID.randomUUID();
            LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
            LoanApplication loanApplication = new LoanApplication(
                    customerId,
                    5000.00,
                    firstInstallmentDate,
                    63
            );
        });
        assertEquals(InstallmentQtyIsNotAllowedException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_first_installment_date_is_greater_than_allowed() {
        Exception exception = assertThrows(FirstInstallmentDateIsNotAllowedException.class, () -> {
            UUID customerId = UUID.randomUUID();
            LocalDate firstInstallmentDate = LocalDate.now().plusMonths(4);
            LoanApplication loanApplication = new LoanApplication(
                    customerId,
                    5000.00,
                    firstInstallmentDate,
                    36
            );
        });
        assertEquals(FirstInstallmentDateIsNotAllowedException.class, exception.getClass());
    }
}
