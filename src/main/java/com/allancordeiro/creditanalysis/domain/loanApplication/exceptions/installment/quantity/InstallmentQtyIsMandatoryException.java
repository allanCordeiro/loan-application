package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;

public class InstallmentQtyIsMandatoryException extends LoanApplicationGeneralException {
    public InstallmentQtyIsMandatoryException() {
        super("installment quantity is mandatory");
    }
}
