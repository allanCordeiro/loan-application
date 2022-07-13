package com.allancordeiro.creditanalysis.domain.customer.exceptions.password;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class PasswordIsMandatoryException extends CustomerGeneralException {
    public PasswordIsMandatoryException() {
        super("password is mandatory");
    }

}
