package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.customer;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;

public class CustomerIdIsMandatoryException extends LoanApplicationGeneralException {
    public CustomerIdIsMandatoryException() {
        super("customer id is mandatory");
    }
}
