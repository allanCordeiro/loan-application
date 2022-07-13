package com.allancordeiro.creditanalysis.domain.customer.exceptions.status;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class AddressIsRequiredToActivateCustomerException extends CustomerGeneralException {
    public AddressIsRequiredToActivateCustomerException() {
        super("address is required to activate a customer");
    }
}
