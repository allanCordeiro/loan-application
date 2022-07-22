package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;

public class FirstInstallmentDateIsNotAllowedException extends LoanApplicationGeneralException {
    public FirstInstallmentDateIsNotAllowedException() {
        super("first installment date is not allowed");
    }
}
