package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CpfInvalidFormatException extends CustomerGeneralException {
    public CpfInvalidFormatException() {
        super("cpf is invalid");
    }
}
