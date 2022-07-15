package com.allancordeiro.creditanalysis.usecase.customer.create;

public record CreateCustomerOutputDto(
        String id,
        String name,
        String email,
        String rg,
        String cpf,
        Float incomeValue,
        CreateAddressOutputDto address
) {}
