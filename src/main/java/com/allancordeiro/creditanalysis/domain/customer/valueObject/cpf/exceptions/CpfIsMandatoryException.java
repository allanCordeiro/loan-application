package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CpfIsMandatoryException extends CustomerGeneralException {
    public CpfIsMandatoryException() {
        super("cpf is mandatory");
    }
}
