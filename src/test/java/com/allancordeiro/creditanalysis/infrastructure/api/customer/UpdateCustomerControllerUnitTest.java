package com.allancordeiro.creditanalysis.infrastructure.api.customer;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfCannotBeChangedException;
import com.allancordeiro.creditanalysis.usecase.customer.create.*;
import com.allancordeiro.creditanalysis.usecase.customer.update.*;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class UpdateCustomerControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CreateCustomerUseCase createCustomerUseCase;
    @Mock
    private UpdateCustomerUseCase useCase;
    @InjectMocks
    private CreateCustomerController createCustomerController;

    @InjectMocks
    private UpdateCustomerController updateCustomerController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(createCustomerController)
                .build();

        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
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

        CreateCustomerOutputDto outputDto = new CreateCustomerOutputDto(
                "62270647-5304-455c-9d43-a66fa3ed08eb",
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                7000.0F,
                new CreateAddressOutputDto(
                        "Street name",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );


        when(this.createCustomerUseCase.execute(inputDto)).thenReturn(outputDto);

        this.mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(inputDto))
                .characterEncoding("utf-8")
        );
    }

    @Test
    public void should_update_a_customer() throws Exception {
        UpdateCustomerInputDto updateInputDto = new UpdateCustomerInputDto(
                "62270647-5304-455c-9d43-a66fa3ed08eb",
                "New Customer name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "NewPassword",
                7000.0F,
                new UpdateAddressInputDto(
                        "New street name",
                        "21A",
                        "New Getho",
                        "01211-150",
                        "Rio de Janeiro",
                        "RJ",
                        ""
                )
        );

        UpdateCustomerOutputDto updateOutputDto = new UpdateCustomerOutputDto(
                "62270647-5304-455c-9d43-a66fa3ed08eb",
                "New Customer name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "NewPassword",
                7000.0F,
                new UpdateAddressOutputDto(
                        "New street name",
                        "21A",
                        "New Getho",
                        "01211-150",
                        "Rio de Janeiro",
                        "RJ",
                        ""
                )
        );

        when(this.useCase.execute(updateInputDto)).thenReturn(updateOutputDto);
        mockMvc = MockMvcBuilders.standaloneSetup(updateCustomerController)
                .build();

        this.mockMvc.perform(put("/customers/" + updateInputDto.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateInputDto))
                .characterEncoding("utf-8")
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updateOutputDto.id()))
                .andExpect(jsonPath("$.name").value(updateOutputDto.name()))
                .andExpect(jsonPath("$.email").value(updateOutputDto.email()))
                .andExpect(jsonPath("$.rg").value(updateOutputDto.rg()))
                .andExpect(jsonPath("$.cpf").value(updateOutputDto.cpf()))
                .andExpect(jsonPath("$.incomeValue").value(updateOutputDto.incomeValue()))
                .andExpect(jsonPath("$.address.street").value(updateOutputDto.address().street()))
                .andExpect(jsonPath("$.address.number").value(updateOutputDto.address().number()))
                .andExpect(jsonPath("$.address.neighborhood").value(updateOutputDto.address().neighborhood()))
                .andExpect(jsonPath("$.address.cep").value(updateOutputDto.address().cep()))
                .andExpect(jsonPath("$.address.city").value(updateOutputDto.address().city()))
                .andExpect(jsonPath("$.address.state").value(updateOutputDto.address().state()))
                .andExpect(jsonPath("$.address.complement").value(updateOutputDto.address().complement()));
    }

    @Test
    public void should_send_an_exception_when_tries_to_change_cpf() throws Exception {
        UpdateCustomerInputDto updateInputDto = new UpdateCustomerInputDto(
                "62270647-5304-455c-9d43-a66fa3ed08eb",
                "New Customer name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "NewPassword",
                7000.0F,
                new UpdateAddressInputDto(
                        "New street name",
                        "21A",
                        "New Getho",
                        "01211-150",
                        "Rio de Janeiro",
                        "RJ",
                        ""
                )
        );

        when(this.useCase.execute(updateInputDto)).thenThrow(Can.class);
        mockMvc = MockMvcBuilders.standaloneSetup(updateCustomerController)
                .build();

        this.mockMvc.perform(put("/customers/" + updateInputDto.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateInputDto))
                .characterEncoding("utf-8")
        );
    }
}
