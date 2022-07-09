package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date;

public class FirstInstallmentDateIsNotAllowedException extends Exception{
    public FirstInstallmentDateIsNotAllowedException() {
        super("first installment date is not allowed");
    }
}
