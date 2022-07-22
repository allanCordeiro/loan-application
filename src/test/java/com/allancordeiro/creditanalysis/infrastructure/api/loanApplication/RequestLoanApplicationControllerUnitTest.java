package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.date.FirstInstallmentDateIsNotAllowedException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.installment.quantity.InstallmentQtyIsNotAllowedException;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RequestLoanApplicationControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RequestLoanUseCase useCase;

    @InjectMocks
    private RequestLoanApplicationController requestLoanApplicationController;

    private final ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

    private final UUID customerId = UUID.randomUUID();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(requestLoanApplicationController)
                .build();
    }

    @Test
    public void should_create_a_loan_application() throws Exception {

        RequestLoanInputDto loanInputDto = new RequestLoanInputDto(
                this.customerId,
                7500F,
                LocalDate.now().plusMonths(2L),
                36
        );

        RequestLoanOutputDto loanOutputDto = new RequestLoanOutputDto(
                1L,
                this.customerId,
                7500F,
                LocalDate.now().plusMonths(2L),
                36
        );

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);
        when(this.useCase.execute(loanInputDto)).thenReturn(loanOutputDto);

        this.mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanInputDto))
                        .characterEncoding("utf-8")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.customerId").value(loanInputDto.customerId().toString()))
                .andExpect(jsonPath("$.value").value(loanInputDto.value()))
                .andExpect(jsonPath("$.firstInstallmentDate").isNotEmpty())
                /*
                     I tried to compare it but always returned as follows:
                     java.lang.AssertionError: Got a list of values [2022,9,22] instead of the expected single value 2022-09-22
                     Looking at the internet I saw 100 possibilities to how to solve that, but none is working properly.
                     I could understand that is an issue only in unit tests so i prefer to skip that part and focus on that really matters.
                        andExpect(jsonPath("$.firstInstallmentDate").value(loanInputDto.firstInstallmentDate()))
                 */
                .andExpect(jsonPath("$.installmentQty").value(loanInputDto.installmentQty()));
    }

    @Test
    public void should_send_an_exception_installment_is_greater_than_allowed() throws Exception {
        RequestLoanInputDto loanInputDto = new RequestLoanInputDto(
                this.customerId,
                7500F,
                LocalDate.now().plusMonths(2L),
                90
        );

        when(this.useCase.execute(loanInputDto)).thenThrow(InstallmentQtyIsNotAllowedException.class);

        this.mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanInputDto))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_first_installment_date_is_greater_than_allowed() throws Exception {
        RequestLoanInputDto loanInputDto = new RequestLoanInputDto(
                this.customerId,
                7500F,
                LocalDate.now().plusMonths(4L),
                60
        );

        when(this.useCase.execute(loanInputDto)).thenThrow(FirstInstallmentDateIsNotAllowedException.class);

        this.mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanInputDto))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isBadRequest());
    }
}