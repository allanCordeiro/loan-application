package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.neighborhood;

public class NeighborhoodIsMandatoryException extends  Exception{
    public NeighborhoodIsMandatoryException() {
        super("neighborhood is mandatory");
    }
}
