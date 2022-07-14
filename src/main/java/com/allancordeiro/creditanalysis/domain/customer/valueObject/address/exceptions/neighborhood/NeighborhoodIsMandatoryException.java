package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.neighborhood;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class NeighborhoodIsMandatoryException extends CustomerGeneralException {
    public NeighborhoodIsMandatoryException() {
        super("neighborhood is mandatory");
    }
}
