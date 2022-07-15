package com.allancordeiro.creditanalysis.usecase.customer.update;

import javax.validation.constraints.NotEmpty;

public record UpdateAddressInputDto(
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
