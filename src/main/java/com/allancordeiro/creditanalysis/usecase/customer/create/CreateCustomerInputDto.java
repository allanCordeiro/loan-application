package com.allancordeiro.creditanalysis.usecase.customer.create;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateCustomerInputDto(
        @NotEmpty
        String name,
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String rg,
        @NotEmpty
        String cpf,
        @NotEmpty
        String password,
        @NotNull
        Float incomeValue,
        AddressInputDto address
) {}

