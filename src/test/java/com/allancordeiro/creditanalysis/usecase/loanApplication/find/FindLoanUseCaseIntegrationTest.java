package com.allancordeiro.creditanalysis.usecase.loanApplication.find;

import com.allancordeiro.creditanalysis.infrastructure.db.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.loanApplication.LoanApplicationRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.infrastructure.gateway.loanApplication.LoanApplicationGatewayDb;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateAddressInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class FindLoanUseCaseIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private LoanApplicationRepository loanAppRepository;

    private RequestLoanUseCase requestUseCase;
    private UUID customerId = UUID.randomUUID();
    private CreateCustomerOutputDto customerOutputDto;
    private RequestLoanInputDto inputDto;

    @BeforeEach
    public void init() throws Exception {
        CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
        CreateCustomerUseCase useCase = new CreateCustomerUseCase(customerGatewayDb);
        CreateCustomerInputDto customerInputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new CreateAddressInputDto(
                        "Street name",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );
        this.customerOutputDto = useCase.execute(customerInputDto);

        LoanApplicationGatewayDb loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanAppRepository);
        this.requestUseCase = new RequestLoanUseCase(loanApplicationGatewayDb);
        this.inputDto = new RequestLoanInputDto(
                UUID.fromString(this.customerOutputDto.id()),
                1000F,
                LocalDate.now().plusMonths(2L),
                36
        );
        this.requestUseCase.execute(inputDto);
    }

    @Test
    public void should_get_a_loan_list() throws Exception {
        CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
        LoanApplicationGatewayDb loanApplicationGatewayDb = new LoanApplicationGatewayDb(this.loanAppRepository);
        FindLoanUseCase listUseCase = new FindLoanUseCase(loanApplicationGatewayDb, customerGatewayDb);
        FindLoanOutputDto loanOutputDto = listUseCase.execute(new FindLoanInputDto(1L));

        assertNotNull(loanOutputDto.id());
        assertEquals(this.inputDto.customerId(), loanOutputDto.customerId());
        assertEquals(this.inputDto.value(), loanOutputDto.value());
        assertEquals(this.inputDto.firstInstallmentDate(), loanOutputDto.firstInstallmentDate());
        assertEquals(this.inputDto.installmentQty(), loanOutputDto.installmentQty());
        assertEquals(this.customerOutputDto.email(), loanOutputDto.customerEmail());
        assertEquals(this.customerOutputDto.incomeValue(), loanOutputDto.customerIncomeValue());
    }
}