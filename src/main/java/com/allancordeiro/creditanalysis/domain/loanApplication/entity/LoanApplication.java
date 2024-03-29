package com.allancordeiro.creditanalysis.domain.loanApplication.entity;

import com.allancordeiro.creditanalysis.domain.loanApplication.validator.LoanApplicationValidator;

import java.time.LocalDate;
import java.util.UUID;

public class LoanApplication {

    private Long id;
    private UUID customerId;
    private Number value;
    private LocalDate firstInstallmentDate;
    private Integer installmentQty;

    public LoanApplication(UUID customerId,
                           Number value,
                           LocalDate firstInstallmentDate,
                           Integer installmentQty) throws Exception {
        this.customerId = customerId;
        this.value = value;
        this.firstInstallmentDate = firstInstallmentDate;
        this.installmentQty = installmentQty;

        this.Validate();
    }

    public LoanApplication(Long id,
                           UUID customerId,
                           Number value,
                           LocalDate firstInstallmentDate,
                           Integer installmentQty) throws Exception {
        this.id = id;
        this.customerId = customerId;
        this.value = value;
        this.firstInstallmentDate = firstInstallmentDate;
        this.installmentQty = installmentQty;

        this.Validate();
    }

    public Long getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Number getValue() {
        return value;
    }

    public LocalDate getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public Integer getInstallmentQty() {
        return installmentQty;
    }


    private void Validate() throws Exception {
        LoanApplicationValidator loanApplicationValidator = new LoanApplicationValidator();
        loanApplicationValidator.Validate(this);
    }
}
