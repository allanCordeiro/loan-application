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
    @Autowired
    private final CustomerGateway customerGateway;
    public ListLoanUseCase(LoanApplicationGateway loanApplicationGateway, CustomerGateway customerGateway) {
        this.loanApplicationGateway = loanApplicationGateway;
        this.customerGateway = customerGateway;
    }

    public ArrayList<ListLoanOutputDto> execute(ListLoanInputDto input) throws Exception {
        ArrayList<LoanApplication> loans = this.loanApplicationGateway.findByCustomerId(
                input.customerId()
        );

        return loans.stream()
                .map((loan) -> {
                    try {
                        customerDetail customerDetail = this.getCustomerDetail(loan.getCustomerId());
                        return new ListLoanOutputDto(
                                loan.getId(),
                                loan.getCustomerId(),
                                loan.getValue().floatValue(),
                                loan.getFirstInstallmentDate(),
                                loan.getInstallmentQty(),
                                customerDetail.customerEmail(),
                                customerDetail.customerIncomeValue()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private customerDetail getCustomerDetail(UUID customerId) throws Exception {
        Customer customer = this.customerGateway.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return new customerDetail(customer.getEmail(), customer.getIncomeValue().floatValue());
    }

    public record customerDetail(String customerEmail, Float customerIncomeValue) {}
}
