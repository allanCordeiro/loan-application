package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.city;

public class CityIsMandatoryException extends Exception {
    public CityIsMandatoryException() {
        super("city is mandatory");
    }
}
