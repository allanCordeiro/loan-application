package com.allancordeiro.creditanalysis.domain.customer.exceptions.password;

public class PasswordIsMandatoryException extends Exception {
    public PasswordIsMandatoryException() {
        super("password is mandatory");
    }

}
