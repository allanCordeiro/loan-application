package com.allancordeiro.creditanalysis.usecase.customer.login;

import java.util.UUID;

public record LoginCustomerOutputDto(
        UUID customerId
) { }
