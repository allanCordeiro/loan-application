package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.state;

public class StateIsMandatoryException extends Exception{
    public StateIsMandatoryException() {
        super("state is mandatory");
    }
}
