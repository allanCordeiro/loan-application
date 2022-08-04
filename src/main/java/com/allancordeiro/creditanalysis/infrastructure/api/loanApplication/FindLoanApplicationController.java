package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;

import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.ForbidenException;
import com.allancordeiro.creditanalysis.infrastructure.security.login.authCheck.AuthCheck;
import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.find.FindLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.find.FindLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.find.FindLoanUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class FindLoanApplicationController {
    @Autowired
    private final FindLoanUseCase findLoanUseCase;
    @Autowired
    private final LoginCustomerUseCase loginCustomerUseCase;

    public FindLoanApplicationController(FindLoanUseCase findLoanUseCase, LoginCustomerUseCase loginCustomerUseCase) {
        this.findLoanUseCase = findLoanUseCase;
        this.loginCustomerUseCase = loginCustomerUseCase;
    }

    @GetMapping("/{customer}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FindLoanOutputDto findLoan(@PathVariable String customer, @PathVariable String id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UUID customerId = UUID.fromString(customer);
            AuthCheck authCheck = new AuthCheck(this.loginCustomerUseCase, authentication, customerId);
            if(authCheck.isAuthorized()) {
                return this.findLoanUseCase.execute(new FindLoanInputDto(Long.valueOf(id)));
            }
            throw new UnauthorizedException();
        } catch (LoanApplicationGeneralException ex) {
            throw new BadRequestException(ex);
        } catch (UnauthorizedException ex) {
            throw new ForbidenException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
