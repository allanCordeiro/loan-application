package com.allancordeiro.creditanalysis.domain.customer.exceptions.name;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class NameIsMandatoryException extends CustomerGeneralException {
    public NameIsMandatoryException() {
        super("name is mandatory");
    }
}
