package com.allancordeiro.creditanalysis.infrastructure.gateway.loanApplication;

import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.infrastructure.db.model.LoanApplicationModel;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.loanApplication.LoanApplicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationGatewayDbTest {

    @InjectMocks
    private LoanApplicationGatewayDb loanApplicationGatewayDb;
    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    private LoanApplication loanApplication;

    private UUID customerId;
    private LocalDate loanDate = LocalDate.now().plusMonths(2);
    private LoanApplicationModel loanApplicationModel;
    private LoanApplicationModel loanApplicationModel2;
    private ArrayList<LoanApplicationModel> loanApplicationModelList = new ArrayList<>();

    @BeforeEach
    public void init() throws Exception {
        this.loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanApplicationRepository);
        this.customerId = UUID.fromString("a06a5cb7-2638-451b-b1a1-3776b1807511");
        this.loanApplication = new LoanApplication(
                1L,
                this.customerId,
                700.0F,
                loanDate,
                56
        );

        this.loanApplicationModel = new LoanApplicationModel(
                1L,
                this.customerId,
                700.0F,
                Date.from(loanDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                56
        );

        this.loanApplicationModel2 = new LoanApplicationModel(
                2L,
                this.customerId,
                700F,
                Date.from(loanDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                56
        );

        this.loanApplicationModelList.add(loanApplicationModel);
        this.loanApplicationModelList.add(loanApplicationModel2);
    }

    @Test
    public void should_create_a_loan() throws Exception {
        when(loanApplicationRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(this.loanApplicationModel));

        this.loanApplicationGatewayDb.create(this.loanApplication);
        Optional<LoanApplication> loanOutput = this.loanApplicationGatewayDb.findById(1L);

        assertEquals(this.loanApplication.getId(), loanOutput.get().getId());
        assertEquals(this.loanApplication.getCustomerId(), loanOutput.get().getCustomerId());
        assertEquals(this.loanApplication.getValue(), loanOutput.get().getValue());
        assertEquals(this.loanApplication.getInstallmentQty(), loanOutput.get().getInstallmentQty());
        assertEquals(this.loanApplication.getFirstInstallmentDate(), loanOutput.get().getFirstInstallmentDate());

    }

    @Test
    public void should_get_a_loan_list() {

        when(loanApplicationRepository.findByCustomerId(Mockito.any(UUID.class)))
                .thenReturn(this.loanApplicationModelList);

        ArrayList<LoanApplication> loanOutput = this.loanApplicationGatewayDb.findByCustomerId(this.customerId);
        assertEquals(2, loanOutput.size());
    }
}