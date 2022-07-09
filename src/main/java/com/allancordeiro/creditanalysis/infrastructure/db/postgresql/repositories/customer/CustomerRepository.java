package com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer;

import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    Optional<CustomerModel> findByEmail(String email);
}
