package com.allancordeiro.creditanalysis.usecase.customer.update;

public record UpdateCustomerOutputDto(
        String id,
        String name,
        String email,
        String rg,
        String cpf,
        String password,
        Float incomeValue,
        UpdateAddressOutputDto address
) {}
