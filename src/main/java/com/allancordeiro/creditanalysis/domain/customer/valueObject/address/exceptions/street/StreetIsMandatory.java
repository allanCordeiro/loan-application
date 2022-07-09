package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.street;

public class StreetIsMandatory extends Exception {
    public StreetIsMandatory() {
        super("street is mandatory");
    }
}
