package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CepInvalidFormatException extends CustomerGeneralException {
    public CepInvalidFormatException() {
        super("cep is in invalid format");
    }
}
