package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.city;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CityIsMandatoryException extends CustomerGeneralException {
    public CityIsMandatoryException() {
        super("city is mandatory");
    }
}
