package com.allancordeiro.creditanalysis.infrastructure.api.customer;


import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CreateCustomerUseCase useCase;
    @InjectMocks
    private CreateCustomerController createCustomerController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(createCustomerController)
                .build();
    }

    @Test
    public void should_create_a_customer() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new Address(
                        "Street name",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        CreateCustomerOutputDto outputDto = new CreateCustomerOutputDto(
                "62270647-5304-455c-9d43-a66fa3ed08eb",
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new Address(
                        "Street name",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );
        ObjectMapper objectMapper = new ObjectMapper();

        when(this.useCase.execute(inputDto)).thenReturn(outputDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(outputDto.id()))
                .andExpect(jsonPath("$.name").value(outputDto.name()))
                .andExpect(jsonPath("$.email").value(outputDto.email()))
                .andExpect(jsonPath("$.rg").value(outputDto.rg()))
                .andExpect(jsonPath("$.cpf").value(outputDto.cpf()))
                .andExpect(jsonPath("$.incomeValue").value(outputDto.IncomeValue()))
                .andExpect(jsonPath("$.address.street").value(outputDto.address().getStreet()))
                .andExpect(jsonPath("$.address.number").value(outputDto.address().getNumber()))
                .andExpect(jsonPath("$.address.neighborhood").value(outputDto.address().getNeighborhood()))
                .andExpect(jsonPath("$.address.cep").value(outputDto.address().getCep()))
                .andExpect(jsonPath("$.address.city").value(outputDto.address().getCity()))
                .andExpect(jsonPath("$.address.state").value(outputDto.address().getState()))
                .andExpect(jsonPath("$.address.complement").value(outputDto.address().getComplement()));
    }

    @Test
    public void should_send_an_exception_when_customer_name_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new Address(
                        "Street name",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
                //TODO:: get error message
    }
    //campos obrigatórios
    /*
        - email
        - income value
        - name
        - password
        - rg
        - cpf
        - endereço

     */
    // regras de "negócio"
    /*
        - email inválido
        - cep formato invalido
        - cpf formato inválido
        - tentativa de trocar o cpf

     */
}