package com.allancordeiro.creditanalysis.usecase.loanApplication.find;

import java.time.LocalDate;
import java.util.UUID;

public record FindLoanOutputDto(
        Long id,
        UUID customerId,
        Float value,
        LocalDate firstInstallmentDate,
        Integer installmentQty,
        String customerEmail,
        Float customerIncomeValue
) { }
