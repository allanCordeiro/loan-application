package com.allancordeiro.creditanalysis.usecase.customer.login;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.customer.CustomerIdIsMandatoryException;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class LoginCustomerUseCaseIntegrationTest {
    @Autowired private CustomerRepository customerRepository;
    @Autowired private AddressRepository addressRepository;

    @Test
    public void should_receive_a_successful_login_attempt() throws Exception {
        CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
        CreateCustomerUseCase inputUseCase = new CreateCustomerUseCase(customerGatewayDb);
        CreateCustomerInputDto customerInputDto = new CreateCustomerInputDto(
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
        inputUseCase.execute(customerInputDto);

        LoginCustomerInputDto loginInputDto = new LoginCustomerInputDto(customerInputDto.email(), customerInputDto.password());
        LoginCustomerUseCase useCase = new LoginCustomerUseCase(customerGatewayDb);
        LoginCustomerOutputDto loginOutputDto = useCase.execute(loginInputDto);

        Assertions.assertNotNull(loginOutputDto);

    }

    @Test
    public void should_receive_an_unsuccessful_login_attempt() {
        Exception exception = assertThrows(UnauthorizedException.class, () -> {
            CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
            CreateCustomerUseCase inputUseCase = new CreateCustomerUseCase(customerGatewayDb);
            CreateCustomerInputDto customerInputDto = new CreateCustomerInputDto(
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
            inputUseCase.execute(customerInputDto);

            LoginCustomerInputDto loginInputDto = new LoginCustomerInputDto(customerInputDto.email(), "a_different_password");
            LoginCustomerUseCase useCase = new LoginCustomerUseCase(customerGatewayDb);
            useCase.execute(loginInputDto);
        });
        assertEquals(UnauthorizedException.class, exception.getClass());
    }

    @Test
    public void should_receive_an_unsuccessful_login_attempt_in_enumeration_mode() {
        Exception exception = assertThrows(UnauthorizedException.class, () -> {
            CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);

            LoginCustomerInputDto loginInputDto = new LoginCustomerInputDto("enumeration_test@email.com", "enumeration_defense");
            LoginCustomerUseCase useCase = new LoginCustomerUseCase(customerGatewayDb);
            useCase.execute(loginInputDto);
        });
        assertEquals(UnauthorizedException.class, exception.getClass());
    }
}