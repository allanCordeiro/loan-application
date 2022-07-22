package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;

public class FirstInstallmentDateIsMandatoryException extends LoanApplicationGeneralException {
    public FirstInstallmentDateIsMandatoryException() {
        super("first installment date is mandatory");
    }
}
