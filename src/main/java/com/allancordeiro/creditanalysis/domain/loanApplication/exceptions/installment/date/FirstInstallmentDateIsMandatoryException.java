package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date;

public class FirstInstallmentDateIsMandatoryException extends Exception{
    public FirstInstallmentDateIsMandatoryException() {
        super("first installment date is mandatory");
    }
}
