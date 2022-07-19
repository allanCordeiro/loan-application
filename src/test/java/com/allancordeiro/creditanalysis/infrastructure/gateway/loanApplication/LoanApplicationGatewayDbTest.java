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
    private LoanApplicationModel loanApplicationModel;
    private LoanApplicationModel loanApplicationModel2;
    private ArrayList<LoanApplicationModel> loanApplicationModelList;

    @BeforeEach
    public void init() throws Exception {
        this.loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanApplicationRepository);
        this.customerId = UUID.fromString("a06a5cb7-2638-451b-b1a1-3776b1807511");
        this.loanApplication = new LoanApplication(
                this.customerId,
                700F,
                LocalDate.now().plusMonths(2),
                56
        );

        this.loanApplicationModel = new LoanApplicationModel(
                1L,
                this.customerId,
                700F,
                Date.from(Instant.from(LocalDate.now().plusMonths(2))),
                56
        );

        this.loanApplicationModel2 = new LoanApplicationModel(
                1L,
                this.customerId,
                700F,
                Date.from(Instant.from(LocalDate.now().plusMonths(2))),
                56
        );

        this.loanApplicationModelList.add(loanApplicationModel);
        this.loanApplicationModelList.add(loanApplicationModel2);

        when(loanApplicationRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(this.loanApplicationModel));

        when(loanApplicationRepository.findByCustomerId(Mockito.any(UUID.class)))
                .thenReturn(this.loanApplicationModelList);
    }

    @Test
    public void should_create_a_loan() {
        Long loanId = this.loanApplicationGatewayDb.create(this.loanApplication);
        LoanApplication loanOutput = this.loanApplicationGatewayDb.findById(loanId);

        assertEquals(this.loanApplication.getId(), loanOutput.getId());
        assertEquals(this.loanApplication.getCustomerId(), loanOutput.getCustomerId());
        assertEquals(this.loanApplication.getValue(), loanOutput.getValue());
        assertEquals(this.loanApplication.getInstallmentQty(), loanOutput.getInstallmentQty());
        assertEquals(this.loanApplication.getFirstInstallmentDate(), loanOutput.getFirstInstallmentDate());

    }

    @Test
    public void should_get_a_loan_list() {
        ArrayList<LoanApplication> loanOutput = this.loanApplicationGatewayDb.findByCustomerId(this.customerId);
        assertEquals(2, loanOutput.size());
    }
}