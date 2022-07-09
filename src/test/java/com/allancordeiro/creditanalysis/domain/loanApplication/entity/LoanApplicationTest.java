package com.allancordeiro.creditanalysis.domain.loanApplication.entity;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.customer.CustomerIdIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.value.ValueIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsNotAllowedException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsNotAllowedException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class LoanApplicationTest {

    @Test
    public void should_create_a_loan_application() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
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

    @Test(expected = CustomerIdIsMandatoryException.class)
    public void should_throw_exception_when_customer_is_empty() throws Exception {
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
                null,
                5000.00,
                firstInstallmentDate,
                36
        );
    }

    @Test(expected = ValueIsMandatoryException.class)
    public void should_throw_exception_when_value_is_empty() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
                customerId,
                0,
                firstInstallmentDate,
                36
        );
    }

    @Test(expected = FirstInstallmentDateIsMandatoryException.class)
    public void should_throw_exception_when_first_installment_date_is_empty() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
                customerId,
                5000.00,
                null,
                36
        );
    }

    @Test(expected = InstallmentQtyIsMandatoryException.class)
    public void should_throw_exception_when_installment_qty_is_empty() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
                customerId,
                5000.00,
                firstInstallmentDate,
                0
        );
    }

    @Test(expected = InstallmentQtyIsNotAllowedException.class)
    public void should_throw_exception_when_installment_qty_greater_than_allowed() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(2);
        LoanApplication loanApplication = new LoanApplication(
                customerId,
                5000.00,
                firstInstallmentDate,
                63
        );
    }

    @Test(expected = FirstInstallmentDateIsNotAllowedException.class)
    public void should_throw_exception_when_first_installment_date_is_greater_than_allowed() throws Exception {
        UUID customerId = UUID.randomUUID();
        LocalDate firstInstallmentDate = LocalDate.now().plusMonths(4);
        LoanApplication loanApplication = new LoanApplication(
                customerId,
                5000.00,
                firstInstallmentDate,
                36
        );
    }
}
