package com.allancordeiro.creditanalysis.domain.customer.exceptions.incomeValue;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class IncomeValueIsMandatoryException extends CustomerGeneralException {
    public IncomeValueIsMandatoryException() {
        super("income value is mandatory");
    }
}
