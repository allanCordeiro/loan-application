package com.allancordeiro.creditanalysis.usecase.customer.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record LoginCustomerInputDto (
        @NotNull
        @Email
        String email,
        @NotNull
        String password
){ }
