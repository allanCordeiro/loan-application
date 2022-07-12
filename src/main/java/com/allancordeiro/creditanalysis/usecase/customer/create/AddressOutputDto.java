package com.allancordeiro.creditanalysis.usecase.customer.create;

public record AddressOutputDto(
        String street,
        String number,
        String neighborhood,
        String cep,
        String city,
        String state,
        String complement
) {
}
