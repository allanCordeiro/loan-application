package com.allancordeiro.creditanalysis.usecase.loanApplication.list;

import java.time.LocalDate;
import java.util.UUID;

public record ListLoanOutputDto(
        Long id,
        UUID customerId,
        Float value,
        LocalDate firstInstallmentDate,
        Integer installmentQty,
        String customerEmail,
        Float customerIncomeValue
) {
}
