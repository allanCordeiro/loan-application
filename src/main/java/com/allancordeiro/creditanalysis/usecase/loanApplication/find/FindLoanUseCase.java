package com.allancordeiro.creditanalysis.usecase.loanApplication.find;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.domain.loanApplication.entity.LoanApplication;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanNotFoundException;
import com.allancordeiro.creditanalysis.domain.loanApplication.gateway.LoanApplicationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindLoanUseCase {
    @Autowired
    private final LoanApplicationGateway loanApplicationGateway;
    @Autowired
    private final CustomerGateway customerGateway;
    public FindLoanUseCase(LoanApplicationGateway loanApplicationGateway, CustomerGateway customerGateway) {
        this.loanApplicationGateway = loanApplicationGateway;
        this.customerGateway = customerGateway;
    }

    public FindLoanOutputDto execute(FindLoanInputDto inputDto) throws Exception {
        LoanApplication loan = this.loanApplicationGateway.findById(inputDto.id())
                .orElseThrow(LoanNotFoundException::new);

        customerDetail customerDetail = this.getCustomerDetail(loan.getCustomerId());
        return new FindLoanOutputDto(
                loan.getId(),
                loan.getCustomerId(),
                loan.getValue().floatValue(),
                loan.getFirstInstallmentDate(),
                loan.getInstallmentQty(),
                customerDetail.customerEmail(),
                customerDetail.customerIncomeValue()
        );
    }

    private customerDetail getCustomerDetail(UUID customerId) throws Exception {
        Customer customer = this.customerGateway.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return new customerDetail(customer.getEmail(), customer.getIncomeValue().floatValue());
    }

    public record customerDetail(String customerEmail, Float customerIncomeValue) {}
}
