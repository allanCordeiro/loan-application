package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions;

public class CpfInvalidFormatException extends Exception {
    public CpfInvalidFormatException() {
        super("cpf is invalid");
    }
}
