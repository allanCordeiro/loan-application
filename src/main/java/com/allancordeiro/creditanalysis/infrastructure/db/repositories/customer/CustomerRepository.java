package com.allancordeiro.creditanalysis.infrastructure.db.repositories.customer;

import com.allancordeiro.creditanalysis.infrastructure.db.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    Optional<CustomerModel> findByEmail(String email);
}
