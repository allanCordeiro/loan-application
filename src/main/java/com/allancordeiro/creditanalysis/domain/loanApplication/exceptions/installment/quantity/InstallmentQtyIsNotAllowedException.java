package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;

public class InstallmentQtyIsNotAllowedException extends LoanApplicationGeneralException {
    public InstallmentQtyIsNotAllowedException() {
        super("installment quantity is not allowed");
    }
}
