package com.allancordeiro.creditanalysis.usecase.customer.create;

import javax.validation.constraints.NotEmpty;

public record AddressInputDto(
        @NotEmpty
        String street,
        @NotEmpty
        String number,
        @NotEmpty
        String neighborhood,
        @NotEmpty
        String cep,
        @NotEmpty
        String city,
        @NotEmpty
        String state,
        String complement
) {
}
