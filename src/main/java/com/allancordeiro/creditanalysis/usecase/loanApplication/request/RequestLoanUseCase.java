package com.allancordeiro.creditanalysis.usecase.loanApplication.request;

import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.domain.loanApplication.gateway.LoanApplicationGateway;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestLoanUseCase {
    @Autowired
    private LoanApplicationGateway loanApplicationGateway;

    public RequestLoanUseCase(LoanApplicationGateway loanApplicationGateway) {
        this.loanApplicationGateway = loanApplicationGateway;
    }

    public RequestLoanOutputDto execute(RequestLoanInputDto input) throws Exception {
        LoanApplication loanApp = new LoanApplication(
                input.customerId(),
                input.value(),
                input.firstInstallmentDate(),
                input.installmentQty()
        );

        LoanApplication loanAppReturn = this.loanApplicationGateway
                .createAndReturn(loanApp);

        return new RequestLoanOutputDto(
                loanAppReturn.getId(),
                loanAppReturn.getCustomerId(),
                loanAppReturn.getValue().floatValue(),
                loanAppReturn.getFirstInstallmentDate(),
                loanAppReturn.getInstallmentQty()
        );
    }
}
