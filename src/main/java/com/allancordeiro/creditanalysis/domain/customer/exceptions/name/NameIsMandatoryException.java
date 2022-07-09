package com.allancordeiro.creditanalysis.domain.customer.exceptions.name;

public class NameIsMandatoryException extends Exception {
    public NameIsMandatoryException() {
        super("name is mandatory");
    }
}
