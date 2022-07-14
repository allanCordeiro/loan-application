package com.allancordeiro.creditanalysis.domain.customer.validator;

import com.allancordeiro.creditanalysis.domain._shared.validator.ValidatorInterface;
import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.email.EmailInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.email.EmailIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.incomeValue.IncomeValueIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.name.NameIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.password.PasswordIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.rg.RgIsMandatoryException;

public class CustomerValidator implements ValidatorInterface<Customer> {
    @Override
    public void Validate(Customer entity) throws Exception {
        if(entity.getName().equals("")) {
            throw new NameIsMandatoryException();
        }

        if(entity.getEmail().equals("")) {
            throw new EmailIsMandatoryException();
        }

        if(!entity.getEmail().matches("\\S+@\\S+\\.\\S+")) {
            throw new EmailInvalidFormatException();
        }

        if(entity.getRg().equals("")) {
            throw new RgIsMandatoryException();
        }

        if(entity.getIncomeValue().floatValue() <= 0.0F) {
            throw new IncomeValueIsMandatoryException();
        }

        if(entity.getPassword().equals("")) {
            throw new PasswordIsMandatoryException();
        }
    }
}
