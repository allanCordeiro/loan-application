package com.allancordeiro.creditanalysis.domain.loanApplication.gateway;

import com.allancordeiro.creditanalysis.domain._shared.gateway.DomainGateway;
import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanNotFoundException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface LoanApplicationGateway extends DomainGateway<LoanApplication> {
    ArrayList<LoanApplication> findByCustomerId(UUID customerId);
    Optional<LoanApplication> findById(Long id) throws Exception;
}
