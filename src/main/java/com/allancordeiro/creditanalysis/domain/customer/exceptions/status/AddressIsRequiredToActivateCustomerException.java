package com.allancordeiro.creditanalysis.domain.customer.exceptions.status;

public class AddressIsRequiredToActivateCustomerException extends Exception{
    public AddressIsRequiredToActivateCustomerException() {
        super("address is required to activate a customer");
    }
}
