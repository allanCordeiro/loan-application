package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep;

public class CepInvalidFormatException extends Exception {
    public CepInvalidFormatException() {
        super("cep is in invalid format");
    }
}
