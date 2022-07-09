package com.allancordeiro.creditanalysis.domain.loanApplication.validator;

import com.allancordeiro.creditanalysis.domain._shared.validator.ValidatorInterface;
import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.customer.CustomerIdIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsNotAllowedException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsNotAllowedException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.value.ValueIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsMandatoryException;

import java.time.LocalDate;
import java.util.Objects;

public class LoanApplicationValidator implements ValidatorInterface<LoanApplication> {
    @Override
    public void Validate(LoanApplication entity) throws Exception {
        if(Objects.isNull(entity.getCustomerId())) {
            throw new CustomerIdIsMandatoryException();
        }

        if(entity.getValue().floatValue() <= 0.0) {
            throw new ValueIsMandatoryException();
        }

        if(Objects.isNull(entity.getFirstInstallmentDate())) {
            throw new FirstInstallmentDateIsMandatoryException();
        }

        if(entity.getInstallmentQty() <= 0) {
            throw new InstallmentQtyIsMandatoryException();
        }

        if(entity.getInstallmentQty() > 60) {
            throw new InstallmentQtyIsNotAllowedException();
        }

        LocalDate dateLimit = LocalDate.now().plusMonths(3);
        if(entity.getFirstInstallmentDate().compareTo(dateLimit) > 0) {
            throw new FirstInstallmentDateIsNotAllowedException();
        }
    }
}
