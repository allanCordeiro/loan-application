package com.allancordeiro.creditanalysis.infrastructure.db.repositories.loanApplication;

import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.infrastructure.db.model.LoanApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationModel, Long> {
    ArrayList<LoanApplicationModel> findByCustomerId(UUID customerId);
}
