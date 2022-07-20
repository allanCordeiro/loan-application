package com.allancordeiro.creditanalysis.infrastructure.gateway.loanApplication;

import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanNotFoundException;
import com.allancordeiro.creditanalysis.domain.loanApplication.gateway.LoanApplicationGateway;
import com.allancordeiro.creditanalysis.infrastructure.db.model.LoanApplicationModel;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.loanApplication.LoanApplicationRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LoanApplicationGatewayDb implements LoanApplicationGateway {
    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationGatewayDb(LoanApplicationRepository loanApplicationRepository)  {
        this.loanApplicationRepository = loanApplicationRepository;
    }


    @Override
    public void create(LoanApplication entity) {
        Date installmentDate = Date.from(entity.getFirstInstallmentDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        LoanApplicationModel loanApplicationModel = new LoanApplicationModel(
                entity.getCustomerId(),
                entity.getValue().floatValue(),
                installmentDate,
                entity.getInstallmentQty()
        );

        this.loanApplicationRepository.save(loanApplicationModel);
    }


    @Override
    public ArrayList<LoanApplication> findByCustomerId(UUID customerId) {
        ArrayList<LoanApplicationModel> model = this.loanApplicationRepository.findByCustomerId(customerId);

        return model.stream()
                .map((loan) -> {
                    try {
                        return new LoanApplication(
                                loan.getId(),
                                loan.getCustomerId(),
                                loan.getValue(),
                                loan.getFirstInstallmentDate()
                                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                loan.getInstallmentQty()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<LoanApplication> findById(Long id) throws Exception {
        LoanApplicationModel model = this.loanApplicationRepository.findById(id)
                .orElseThrow(LoanNotFoundException::new);
        LoanApplication loan = new LoanApplication(
                model.getId(),
                model.getCustomerId(),
                model.getValue(),
                model.getFirstInstallmentDate()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                model.getInstallmentQty()
        );
        return Optional.of(loan);
    }

    @Override
    public void update(LoanApplication entity) throws Exception {}

    @Override
    public Optional<LoanApplication> findById(UUID id) throws Exception {
        return Optional.empty();
    }
}
