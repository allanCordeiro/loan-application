package com.allancordeiro.creditanalysis.domain.customer.exceptions;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException() {
        super("customer not found");
    }
}
