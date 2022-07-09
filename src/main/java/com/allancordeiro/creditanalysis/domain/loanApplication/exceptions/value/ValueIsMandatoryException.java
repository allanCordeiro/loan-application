package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.value;

public class ValueIsMandatoryException extends Exception{
    public ValueIsMandatoryException() {
        super("value is mandatory");
    }
}
