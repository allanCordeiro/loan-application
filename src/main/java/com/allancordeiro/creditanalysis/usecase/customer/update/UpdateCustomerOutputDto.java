package com.allancordeiro.creditanalysis.usecase.customer.update;

public record UpdateCustomerOutputDto(
        String id,
        String name,
        String email,
        String rg,
        String cpf,
        Float incomeValue,
        UpdateAddressOutputDto address
) {}
