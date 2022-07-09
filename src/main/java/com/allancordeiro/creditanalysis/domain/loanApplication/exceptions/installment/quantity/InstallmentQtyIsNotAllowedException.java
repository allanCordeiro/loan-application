package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity;

public class InstallmentQtyIsNotAllowedException extends Exception{
    public InstallmentQtyIsNotAllowedException() {
        super("installment quantity is not allowed");
    }
}
