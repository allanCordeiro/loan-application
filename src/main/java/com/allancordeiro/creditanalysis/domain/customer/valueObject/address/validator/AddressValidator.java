package com.allancordeiro.creditanalysis.domain.customer.valueObject.address.validator;

import com.allancordeiro.creditanalysis.domain._shared.validator.ValidatorInterface;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.state.StateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.street.StreetIsMandatory;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.city.CityIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.neighborhood.NeighborhoodIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.number.NumberIsMandatoryException;

public class AddressValidator implements ValidatorInterface<Address> {

    private Address address;
    @Override
    public void Validate(Address entity) throws Exception {
        this.address = entity;

        if(this.address.getStreet().equals("")) {
            throw new StreetIsMandatory();
        }

        if(this.address.getNumber().equals("")) {
            throw new NumberIsMandatoryException();
        }

        if(this.address.getNeighborhood().equals("")) {
            throw new NeighborhoodIsMandatoryException();
        }

        if(this.address.getCity().equals("")) {
            throw new CityIsMandatoryException();
        }

        //TODO: create state enumerators
        if(this.address.getState().equals("")) {
            throw new StateIsMandatoryException();
        }

        if(this.address.getCep().equals("")) {
            throw new CepIsMandatoryException();
        }

        if(!this.address.getCep().matches("[0-9]{5}-[0-9]{3}")) {
            throw new CepInvalidFormatException();
        }
    }

}
