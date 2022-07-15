package com.allancordeiro.creditanalysis.usecase.customer.update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record UpdateCustomerInputDto(
        String id,
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
        @NotNull
        UpdateAddressInputDto address
) {}
