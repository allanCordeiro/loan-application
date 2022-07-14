package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.number;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class NumberIsMandatoryException extends CustomerGeneralException {
    public NumberIsMandatoryException() {
        super("number is mandatory");
    }
}
