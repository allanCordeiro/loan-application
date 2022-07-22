package com.allancordeiro.creditanalysis.usecase.loanApplication.list;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.domain.loanApplication.gateway.LoanApplicationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListLoanUseCase {

    @Autowired
    private final LoanApplicationGateway loanApplicationGateway;

    public ListLoanUseCase(LoanApplicationGateway loanApplicationGateway) {
        this.loanApplicationGateway = loanApplicationGateway;
    }

    public ArrayList<ListLoanOutputDto> execute(ListLoanInputDto input) throws Exception {
        ArrayList<LoanApplication> loans = this.loanApplicationGateway.findByCustomerId(
                input.customerId()
        );

        return loans.stream()
                .map((loan) -> {
                    try {
                        return new ListLoanOutputDto(
                                loan.getId(),
                                loan.getValue().floatValue(),
                                loan.getInstallmentQty()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
