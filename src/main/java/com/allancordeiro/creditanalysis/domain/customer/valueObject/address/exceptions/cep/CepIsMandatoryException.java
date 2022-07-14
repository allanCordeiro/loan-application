package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CepIsMandatoryException extends CustomerGeneralException {
    public CepIsMandatoryException() {
        super("cep is mandatory");
    }
}
