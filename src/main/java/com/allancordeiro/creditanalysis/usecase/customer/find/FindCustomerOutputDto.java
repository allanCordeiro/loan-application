package com.allancordeiro.creditanalysis.usecase.customer.find;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;

public record FindCustomerOutputDto(
        String id,
        String name,
        String email,
        String rg,
        String cpf,
        String password,
        Float IncomeValue,
        Address address
) {}
