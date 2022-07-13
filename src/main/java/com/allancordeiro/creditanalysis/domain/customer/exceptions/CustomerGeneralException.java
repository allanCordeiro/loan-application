package com.allancordeiro.creditanalysis.domain.customer.exceptions;

public class CustomerGeneralException extends Exception{
    public CustomerGeneralException() {
        super();
    }

    public CustomerGeneralException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
