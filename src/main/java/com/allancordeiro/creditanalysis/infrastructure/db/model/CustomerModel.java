package com.allancordeiro.creditanalysis.infrastructure.db.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="customer")
public class CustomerModel  {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;
    private String email;
    private String rg;
    private String cpf;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name="income_value")
    private Number incomeValue;
    private String password;
    private Boolean status;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name="updated_at", insertable = false)
    private Timestamp updated_At;

    public CustomerModel() {}

    public CustomerModel(UUID id, String name, String email, String rg, String cpf, Long addressId,
                         Number incomeValue, String password, Boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.addressId = addressId;
        this.incomeValue = incomeValue;
        this.password = password;
        this.status = status;
    }

    public UUID getId() {
        return id;
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

    public String getCpf() {
        return cpf;
    }

    public Long getAddress() {
        return addressId;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setAddress(Long addressId) {
        this.addressId = addressId;
    }

    public void setIncomeValue(Number incomeValue) {
        this.incomeValue = incomeValue;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
