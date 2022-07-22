package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.value;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;

public class ValueIsMandatoryException extends LoanApplicationGeneralException {
    public ValueIsMandatoryException() {
        super("value is mandatory");
    }
}
