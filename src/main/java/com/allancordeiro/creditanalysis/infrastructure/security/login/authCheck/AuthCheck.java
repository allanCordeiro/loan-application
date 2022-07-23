package com.allancordeiro.creditanalysis.infrastructure.security.login.authCheck;

import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthCheck {

    @Autowired
    private LoginCustomerUseCase loginCustomerUseCase;
    @Autowired
    private final Authentication auth;
    private final UUID customerId;

    public AuthCheck(LoginCustomerUseCase loginCustomerUseCase,Authentication auth, UUID customerId) {
        this.loginCustomerUseCase = loginCustomerUseCase;
        this.auth = auth;
        this.customerId = customerId;
    }

    public boolean isAuthenticated() {
        if((this.auth instanceof AnonymousAuthenticationToken)) {
            return false;
        }
        return true;
    }

    public boolean isAuthorized() throws Exception {
        if (!isAuthenticated()) {
            return false;
        }

        LoginCustomerInputDto loginCustomerInputDto = new LoginCustomerInputDto(
                this.auth.getName(),
                "password"
        );
        if(this.customerId.equals(this.getCustomerIdFromDb(loginCustomerInputDto))) {
            return true;
        }
        return false;
    }

    private UUID getCustomerIdFromDb(LoginCustomerInputDto loginCustomerInputDto) throws Exception {
        LoginCustomerOutputDto outputDto = this.loginCustomerUseCase.execute(loginCustomerInputDto);
        return outputDto.customerId();
    }
}
