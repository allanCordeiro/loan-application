package com.allancordeiro.creditanalysis.usecase.customer.create;

public record CreateAddressOutputDto(
        String street,
        String number,
        String neighborhood,
        String cep,
        String city,
        String state,
        String complement
) {
}
