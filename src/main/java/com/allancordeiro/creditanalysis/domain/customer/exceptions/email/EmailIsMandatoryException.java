package com.allancordeiro.creditanalysis.domain.customer.exceptions.email;

public class EmailIsMandatoryException extends Exception {
    public EmailIsMandatoryException() {
        super("email is mandatory");
    }
}
