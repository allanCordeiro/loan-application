package com.allancordeiro.creditanalysis.usecase.customer.create;

public record AddressInputDto(
        String street,
        String number,
        String neighborhood,
        String cep,
        String city,
        String state,
        String complement
) {
}
