package com.allancordeiro.creditanalysis.infrastructure.api.login;

import com.allancordeiro.creditanalysis.infrastructure.api.exception.ForbidenException;
import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    @Autowired
    private LoginCustomerUseCase loginCustomerUseCase;

    public LoginController(LoginCustomerUseCase loginCustomerUseCase) {
        this.loginCustomerUseCase = loginCustomerUseCase;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginCustomerOutputDto login(@Valid @RequestBody LoginCustomerInputDto request) throws Exception {
        try {
            return this.loginCustomerUseCase.execute(request);
        } catch (UnauthorizedException uEx) {
            throw new ForbidenException(uEx);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new Exception("Internal server error. Reach out sysadmin.");
        }
    }
}
