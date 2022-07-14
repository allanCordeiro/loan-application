package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.state;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class StateIsMandatoryException extends CustomerGeneralException {
    public StateIsMandatoryException() {
        super("state is mandatory");
    }
}
