package com.allancordeiro.creditanalysis.usecase.loanApplication.request;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsNotAllowedException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsNotAllowedException;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.loanApplication.LoanApplicationRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.loanApplication.LoanApplicationGatewayDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class RequestLoanUseCaseIntegrationTest {
    @Autowired
    private LoanApplicationRepository loanAppRepository;
    @Test
    public void should_create_a_loan() throws Exception {
        LoanApplicationGatewayDb loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanAppRepository);
        UUID customerId = UUID.randomUUID();
        RequestLoanUseCase useCase = new RequestLoanUseCase(loanApplicationGatewayDb);
        RequestLoanInputDto inputDto = new RequestLoanInputDto(
                customerId,
                5000F,
                LocalDate.now().plusMonths(2L),
                36
        );

        RequestLoanOutputDto outputDto = useCase.execute(inputDto);

        assertNotNull(outputDto.id());
        assertEquals(inputDto.customerId(), outputDto.customerId());
        assertEquals(inputDto.value(), outputDto.value());
        assertEquals(inputDto.firstInstallmentDate(), outputDto.firstInstallmentDate());
        assertEquals(inputDto.installmentQty(), outputDto.installmentQty());
    }

    @Test
    public void should_throw_exception_when_installment_qty_greater_than_allowed() throws Exception {
        Exception exception = assertThrows(InstallmentQtyIsNotAllowedException.class, () -> {
            LoanApplicationGatewayDb loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanAppRepository);
            UUID customerId = UUID.randomUUID();
            RequestLoanUseCase useCase = new RequestLoanUseCase(loanApplicationGatewayDb);
            RequestLoanInputDto inputDto = new RequestLoanInputDto(
                    customerId,
                    5000F,
                    LocalDate.now().plusMonths(2L),
                    67
            );
            RequestLoanOutputDto outputDto = useCase.execute(inputDto);
        });


        assertEquals(InstallmentQtyIsNotAllowedException.class, exception.getClass());
    }

    @Test
    public void should_throw_exception_when_first_installment_date_is_greater_than_allowed() throws Exception {
        Exception exception = assertThrows(FirstInstallmentDateIsNotAllowedException.class, () -> {
            LoanApplicationGatewayDb loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanAppRepository);
            UUID customerId = UUID.randomUUID();
            RequestLoanUseCase useCase = new RequestLoanUseCase(loanApplicationGatewayDb);
            RequestLoanInputDto inputDto = new RequestLoanInputDto(
                    customerId,
                    5000F,
                    LocalDate.now().plusMonths(4L),
                    36
            );
            RequestLoanOutputDto outputDto = useCase.execute(inputDto);
        });


        assertEquals(FirstInstallmentDateIsNotAllowedException.class, exception.getClass());
    }
}