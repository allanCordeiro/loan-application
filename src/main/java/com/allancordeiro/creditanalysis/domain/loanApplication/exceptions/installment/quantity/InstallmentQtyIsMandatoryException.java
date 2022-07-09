package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity;

public class InstallmentQtyIsMandatoryException extends Exception{
    public InstallmentQtyIsMandatoryException() {
        super("installment quantity is mandatory");
    }
}
