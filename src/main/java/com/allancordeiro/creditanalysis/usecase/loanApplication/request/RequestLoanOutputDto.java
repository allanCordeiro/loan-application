package com.allancordeiro.creditanalysis.usecase.loanApplication.request;

import java.time.LocalDate;
import java.util.UUID;

public record RequestLoanOutputDto(
        Long id,
        UUID customerId,
        Float value,
        LocalDate firstInstallmentDate,
        Integer installmentQty
) {
}
