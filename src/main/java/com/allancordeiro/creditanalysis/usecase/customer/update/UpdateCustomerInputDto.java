package com.allancordeiro.creditanalysis.usecase.customer.update;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;

public record UpdateCustomerInputDto(
        String id,
        String name,
        String email,
        String rg,
        String cpf,
        String password,
        Float IncomeValue,
        Address address
) {}
