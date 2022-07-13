package com.allancordeiro.creditanalysis.domain.customer.exceptions.email;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class EmailIsMandatoryException extends CustomerGeneralException {
    public EmailIsMandatoryException() {
        super("email is mandatory");
    }
}
