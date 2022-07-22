package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;

import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanUseCase;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class ListLoanApplicationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ListLoanUseCase useCase;

    @InjectMocks
    private ListLoanApplicationController listLoanApplicationController;

    private final ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

    private ArrayList<ListLoanOutputDto> loanList = new ArrayList<>();

    @BeforeEach
    public void init() {
        ListLoanOutputDto loanInputDto = new ListLoanOutputDto(
                1L,
                7500F,
                36
        );

        ListLoanOutputDto loanInputDto2 = new ListLoanOutputDto(
                2L,
                7500F,
                54
        );

        loanList.add(loanInputDto);
        loanList.add(loanInputDto2);


        mockMvc = MockMvcBuilders.standaloneSetup(listLoanApplicationController)
                .build();
    }

    @Test
    public void should_get_a_loan_app_list() throws Exception {
        UUID customerId = UUID.randomUUID();
        when(this.useCase.execute(Mockito.any(ListLoanInputDto.class))).thenReturn(this.loanList);
        ArrayList<ListLoanOutputDto> resultList = this.useCase.execute(new ListLoanInputDto(customerId));

        this.mockMvc.perform(get("/loans/1aab11a1-d4db-46c0-aced-e021f9339b79")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}