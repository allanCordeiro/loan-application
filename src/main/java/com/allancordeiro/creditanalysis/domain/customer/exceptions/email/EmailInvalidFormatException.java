package com.allancordeiro.creditanalysis.domain.customer.exceptions.email;

public class EmailInvalidFormatException extends Exception {
    public EmailInvalidFormatException() {
        super("email is in invalid format");
    }
}
