package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.street;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class StreetIsMandatory extends CustomerGeneralException {
    public StreetIsMandatory() {
        super("street is mandatory");
    }
}
