package com.allancordeiro.creditanalysis.usecase.customer.login;

import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.infrastructure.security.login.PasswordManager;
import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final PasswordManager passwordManager;

    public LoginCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
        this.passwordManager = new PasswordManager();
    }

    public LoginCustomerOutputDto execute(LoginCustomerInputDto input) throws Exception {
        FindCustomerUseCase findUseCase = new FindCustomerUseCase(this.customerGateway);

        Optional<FindCustomerOutputDto> customer = findUseCase.execute(
                new FindCustomerInputDto(input.email())
        );
        String password = "enumeration_defense";
        if(customer.isPresent()) {
            password = customer.get().password();
        }

        if(!passwordManager.match(password, input.password()) || customer.isEmpty()) {
            throw new UnauthorizedException();
        }

        return new LoginCustomerOutputDto(this.generateToken(input.email()));
    }

    private String generateToken(String customerEmail) {
        //AuthenticationManager authenticationManager = new AuthenticationManager();
        //return authenticationManager.authenticate(customerEmail);
        return "hello";
    }


}
