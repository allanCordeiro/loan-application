package com.allancordeiro.creditanalysis.domain.customer.entity;

import java.util.Objects;
import java.util.UUID;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfCannotBeChangedException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.status.AddressIsRequiredToActivateCustomerException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.status.CpfIsRequiredToActivateCustomerException;
import com.allancordeiro.creditanalysis.domain.customer.validator.CustomerValidator;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.Cpf;


public class Customer {
    private UUID id;
    private String name;
    private String email;
    private String rg;
    private Number incomeValue;
    private String password;
    private Cpf cpf;
    private Address address;
    private Boolean status = false;

    public Customer() {}

    public Customer(UUID id,
                    String name,
                    String email,
                    String rg,
                    Number incomeValue,
                    String password) throws Exception {

        this.id = id;
        this.name = name;
        this.email = email;
        this.rg = rg;
        this.incomeValue = incomeValue;
        this.password = password;

        this.Validate();
    }

    public Customer(String name,
                    String email,
                    String rg,
                    Number incomeValue,
                    String password) throws Exception {

        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.rg = rg;
        this.incomeValue = incomeValue;
        this.password = password;

        this.Validate();
    }


    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getRg() {
        return rg;
    }

    public Number getIncomeValue() {
        return incomeValue;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getStatus() {
        return status;
    }

    public Cpf getCpf() { return this.cpf; }

    public Address getAddress() { return this.address; }

    public void AddCpf(Cpf cpf) throws Exception {
        if(Objects.nonNull(this.cpf)) {
            throw new CpfCannotBeChangedException();
        }
        this.cpf = cpf;
        this.Validate();
    }

    public void ChangeAddress(Address address) {
        this.address = address;
    }

    public void ChangeName(String name) throws Exception {
        this.name = name;
        this.Validate();
    }

    public void ChangePassword(String password) throws Exception {
        this.password = password;
        this.Validate();
    }

    public void ChangeIncomeValue(Float incomeValue) throws Exception {
        this.incomeValue = incomeValue;
        this.Validate();
    }

    public void Activate() throws Exception {
        if(Objects.isNull(this.address)) {
            throw new AddressIsRequiredToActivateCustomerException();
        }

        if(Objects.isNull((this.cpf))) {
            throw new CpfIsRequiredToActivateCustomerException();
        }

        this.status = Boolean.TRUE;
    }

    public void Validate() throws Exception {
        CustomerValidator customerValidator = new CustomerValidator();
        customerValidator.Validate(this);
    }
}

