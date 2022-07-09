package com.allancordeiro.creditanalysis.domain.customer.exceptions.status;

public class CpfIsRequiredToActivateCustomerException extends Exception{
    public CpfIsRequiredToActivateCustomerException() {
        super("cpf is required to activate a customer");
    }
}
