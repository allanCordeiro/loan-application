package com.allancordeiro.creditanalysis.domain.customer.exceptions.rg;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class RgIsMandatoryException extends CustomerGeneralException {
    public RgIsMandatoryException() {
        super("rg is mandatory");
    }
}
