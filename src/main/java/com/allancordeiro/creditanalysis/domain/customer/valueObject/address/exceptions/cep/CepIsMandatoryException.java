package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep;

public class CepIsMandatoryException extends Exception {
    public CepIsMandatoryException() {
        super("cep is mandatory");
    }
}
