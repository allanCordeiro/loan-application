package com.allancordeiro.creditanalysis.infrastructure.api.login;

import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerUseCase;
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
public class LoginControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private LoginCustomerUseCase useCase;

    @InjectMocks
    private LoginController loginController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private LoginCustomerInputDto inputDto;
    private LoginCustomerOutputDto outputDto;

    @BeforeEach
    public void init() {
        this.inputDto = new LoginCustomerInputDto(
                "customer@emaiil.com",
                "1234"
        );


        this.outputDto = new LoginCustomerOutputDto(
                "valid-token"
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .build();

    }

    @Test
    public void should_login_with_success() throws Exception {
        when(this.useCase.execute(this.inputDto)).thenReturn(this.outputDto);

        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(inputDto))
                .characterEncoding("utf-8")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("valid-token"))
        ;
    }

    @Test
    public void should_receive_an_exception_when_tries_to_put_an_invalid_password() throws Exception {
        this.inputDto = new LoginCustomerInputDto(
                "customer@emaiil.com",
                "1234-invalid"
        );
        when(this.useCase.execute(this.inputDto)).thenThrow(UnauthorizedException.class);

        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    public void should_receive_an_exception_when_tries_to_put_an_unexistent_user() throws Exception {
        this.inputDto = new LoginCustomerInputDto(
                "customer@emaiil-invalid.com",
                "1234"
        );
        when(this.useCase.execute(this.inputDto)).thenThrow(UnauthorizedException.class);

        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(inputDto))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isForbidden())
        ;
    }
}