package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;

import com.allancordeiro.creditanalysis.infrastructure.security.login.userdetails.UserDetailData;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.find.FindLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.find.FindLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.find.FindLoanUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FindLoanApplicationControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FindLoanUseCase useCase;

    @Mock
    private LoginCustomerUseCase loginCustomerUseCase;

    @InjectMocks
    private FindLoanApplicationController findLoanApplicationController;
    private final UUID customerId = UUID.randomUUID();

    @BeforeEach
    public void init() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(findLoanApplicationController)
                .build();

        when(this.loginCustomerUseCase.execute(Mockito.any(LoginCustomerInputDto.class))).thenReturn(
                new LoginCustomerOutputDto(customerId)
        );

        UserDetailData userDetails = new UserDetailData(
                new LoginCustomerInputDto("user@customer.com", "password")
        );
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void should_return_an_specific_loan() throws Exception {
        FindLoanOutputDto findLoanOutputDto = new FindLoanOutputDto(
                1L,
                customerId,
                600.0F,
                LocalDate.now().plusMonths(1L),
                56,
                "user@customer.com",
                7500.0F
        );
        when(this.useCase.execute(Mockito.any(FindLoanInputDto.class))).thenReturn(findLoanOutputDto);

        this.mockMvc.perform(get("/loans/" + customerId.toString() + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(findLoanOutputDto.id()))
                .andExpect(jsonPath("$.customerId").value(findLoanOutputDto.customerId().toString()))
                .andExpect(jsonPath("$.value").value(findLoanOutputDto.value()))
                .andExpect(jsonPath("$.firstInstallmentDate").isNotEmpty())
                .andExpect(jsonPath("$.customerEmail").value(findLoanOutputDto.customerEmail()))
                .andExpect(jsonPath("$.customerIncomeValue").value(findLoanOutputDto.customerIncomeValue()));
    }
}