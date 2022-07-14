package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CpfCannotBeChangedException extends CustomerGeneralException {
    public CpfCannotBeChangedException() {
        super("cpf can not be changed");
    }
}
