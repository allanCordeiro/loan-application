package com.allancordeiro.creditanalysis.domain._shared.gateway;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;

import java.util.Optional;
import java.util.UUID;

public interface DomainGateway<T> {
    void create(T entity);
    void update(T entity) throws Exception;
    Optional<T> findById(UUID id) throws Exception;
}
