package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions;

public class LoanApplicationGeneralException extends Exception {
    public LoanApplicationGeneralException() {
        super();
    }

    public LoanApplicationGeneralException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
