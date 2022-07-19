package com.allancordeiro.creditanalysis.infrastructure.db.repositories.customer;

import com.allancordeiro.creditanalysis.infrastructure.db.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
}
