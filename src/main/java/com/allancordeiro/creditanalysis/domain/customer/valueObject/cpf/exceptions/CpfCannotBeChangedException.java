package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions;

public class CpfCannotBeChangedException extends Exception {
    public CpfCannotBeChangedException() {
        super("cpf can not be changed");
    }
}
