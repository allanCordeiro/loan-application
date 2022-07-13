package com.allancordeiro.creditanalysis.domain.customer.exceptions.email;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class EmailInvalidFormatException extends CustomerGeneralException {
    public EmailInvalidFormatException() {
        super("email is in invalid format");
    }
}
