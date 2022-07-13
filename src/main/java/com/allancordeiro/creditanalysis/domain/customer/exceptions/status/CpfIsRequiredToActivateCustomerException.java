package com.allancordeiro.creditanalysis.domain.customer.exceptions.status;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;

public class CpfIsRequiredToActivateCustomerException extends CustomerGeneralException  {
    public CpfIsRequiredToActivateCustomerException() {
        super("cpf is required to activate a customer");
    }
}
