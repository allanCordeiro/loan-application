package com.allancordeiro.creditanalysis.domain.customer.exceptions.rg;

public class RgIsMandatoryException extends Exception{
    public RgIsMandatoryException() {
        super("rg is mandatory");
    }
}
