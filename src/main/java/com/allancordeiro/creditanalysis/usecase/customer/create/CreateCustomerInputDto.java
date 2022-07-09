package com.allancordeiro.creditanalysis.usecase.customer.create;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;

public record CreateCustomerInputDto(
        String name,
        String email,
        String rg,
        String cpf,
        String password,
        Float IncomeValue,
        Address address
) {}
