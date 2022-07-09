package com.allancordeiro.creditanalysis.domain.customer.exceptions.incomeValue;

public class IncomeValueIsMandatoryException extends Exception {
    public IncomeValueIsMandatoryException() {
        super("income value is mandatory");
    }
}
