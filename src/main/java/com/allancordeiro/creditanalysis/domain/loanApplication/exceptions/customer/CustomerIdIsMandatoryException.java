package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.customer;

public class CustomerIdIsMandatoryException extends Exception {
    public CustomerIdIsMandatoryException() {
        super("customer id is mandatory");
    }
}
