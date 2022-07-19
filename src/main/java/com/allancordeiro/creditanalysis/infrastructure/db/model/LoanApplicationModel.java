package com.allancordeiro.creditanalysis.infrastructure.db.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "loan_application")
public class LoanApplicationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "income_value")
    private Float incomeValue;

    @Column(name="first_installment_date")
    private Date firstInstallmentDate;

    @Column(name="installment_quantity")
    private Integer installmentQty;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name="updated_at", insertable = false)
    private Timestamp updated_At;

    public LoanApplicationModel() {}

    public LoanApplicationModel(Long id, UUID customerId, Float incomeValue, Date firstInstallmentDate, Integer installmentQty) {
        this.id = id;
        this.customerId = customerId;
        this.incomeValue = incomeValue;
        this.firstInstallmentDate = firstInstallmentDate;
        this.installmentQty = installmentQty;
    }

    public Long getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Float getValue() {
        return 00F;
    }

    public Date getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public Integer getInstallmentQty() {
        return installmentQty;
    }

    public void setId(Long id) { this.id = id; }
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setValue(Float incomeValue) {
        this.incomeValue = incomeValue;
    }

    public void setFirstInstallmentDate(Date firstInstallmentDate) {
        this.firstInstallmentDate = firstInstallmentDate;
    }

    public void setInstallmentQty(Integer installmentQty) {
        this.installmentQty = installmentQty;
    }
}
