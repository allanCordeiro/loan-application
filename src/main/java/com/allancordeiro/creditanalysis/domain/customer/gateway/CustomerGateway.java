package com.allancordeiro.creditanalysis.domain.customer.gateway;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain._shared.gateway.DomainGateway;

import java.util.Optional;


public interface CustomerGateway extends DomainGateway<Customer> {
    Optional<Customer> findByEmail(String email) throws Exception;
}
