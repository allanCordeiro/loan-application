package com.allancordeiro.creditanalysis.usecase.customer.update;

import java.util.Optional;

public record UpdateAddressOutputDto(
        String street,
        String number,
        String neighborhood,
        String cep,
        String city,
        String state,
        String complement
) {
}
