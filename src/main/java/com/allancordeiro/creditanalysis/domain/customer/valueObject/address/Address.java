package com.allancordeiro.creditanalysis.domain.customer.valueObject.address;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.validator.AddressValidator;

public class Address {
    private String street;
    private String number;
    private String neighborhood;
    private String cep;
    private String city;
    private String state;
    private String complement;

    public Address(String street, String number, String neighborhood, String cep,
                   String city, String state, String complement) throws Exception {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.complement = complement;

        this.Validate();
    }

    public Address(String street, String number, String neighborhood, String cep,
                   String city, String state) throws Exception {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.cep = cep;
        this.city = city;
        this.state = state;

        this.Validate();
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCep() {
        return cep;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getComplement() {
        return complement;
    }

    private void Validate() throws Exception {
        AddressValidator validator = new AddressValidator();
        validator.Validate(this);
    }
}
