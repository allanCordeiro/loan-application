package com.allancordeiro.creditanalysis.infrastructure.api.customer;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.email.EmailInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.incomeValue.IncomeValueIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.city.CityIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.neighborhood.NeighborhoodIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.number.NumberIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.state.StateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.street.StreetIsMandatory;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfInvalidFormatException;
import com.allancordeiro.creditanalysis.usecase.customer.create.*;
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
                new AddressInputDto(
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
                new AddressOutputDto(
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

        this.mockMvc.perform(post("/customers")
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
                .andExpect(jsonPath("$.incomeValue").value(outputDto.incomeValue()))
                .andExpect(jsonPath("$.address.street").value(outputDto.address().street()))
                .andExpect(jsonPath("$.address.number").value(outputDto.address().number()))
                .andExpect(jsonPath("$.address.neighborhood").value(outputDto.address().neighborhood()))
                .andExpect(jsonPath("$.address.cep").value(outputDto.address().cep()))
                .andExpect(jsonPath("$.address.city").value(outputDto.address().city()))
                .andExpect(jsonPath("$.address.state").value(outputDto.address().state()))
                .andExpect(jsonPath("$.address.complement").value(outputDto.address().complement()));
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
                new AddressInputDto(
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
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_email_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
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
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_rg_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
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
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_cpf_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "",
                "1234",
                7000.0F,
                new AddressInputDto(
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
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_income_value_is_lower_than_one() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                0.0F,
                new AddressInputDto(
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
        when(this.useCase.execute(inputDto)).thenThrow(IncomeValueIsMandatoryException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_password_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "",
                7000.00F,
                new AddressInputDto(
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
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_street_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(StreetIsMandatory.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_address_number_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(NumberIsMandatoryException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_neighborhood_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(NeighborhoodIsMandatoryException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_cep_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "Some neighborhood",
                        "",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(CepIsMandatoryException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_city_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(CityIsMandatoryException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_address_state_is_empty() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(StateIsMandatoryException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_add_an_invalid_email() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer#emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        //when(this.useCase.execute(inputDto)).thenThrow(EmailInvalidFormatException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_add_an_invalid_cpf() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "111.111.111-11",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(CpfInvalidFormatException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_send_an_exception_when_customer_add_an_invalid_cep() throws Exception {
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new AddressInputDto(
                        "Street",
                        "123",
                        "Some neighborhood",
                        "01211-1500",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        when(this.useCase.execute(inputDto)).thenThrow(CepInvalidFormatException.class);

        this.mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }
}