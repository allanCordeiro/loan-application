package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions;

public class CpfIsMandatoryException extends Exception{
    public CpfIsMandatoryException() {
        super("cpf is mandatory");
    }
}
