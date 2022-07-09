package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.number;

public class NumberIsMandatoryException extends Exception {
    public NumberIsMandatoryException() {
        super("number is mandatory");
    }
}
