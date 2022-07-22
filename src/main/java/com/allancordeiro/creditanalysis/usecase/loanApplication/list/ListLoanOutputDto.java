package com.allancordeiro.creditanalysis.usecase.loanApplication.list;

import java.time.LocalDate;
import java.util.UUID;

public record ListLoanOutputDto(
        Long id,
        Float value,
        Integer installmentQty
) { }
