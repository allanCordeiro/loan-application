package com.allancordeiro.creditanalysis.domain.loanApplication.exceptions;

public class LoanNotFoundException extends Exception{
    public LoanNotFoundException() {
        super("Loan not found");
    }
}
