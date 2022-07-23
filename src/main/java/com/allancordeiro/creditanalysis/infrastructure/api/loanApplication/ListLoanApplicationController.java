package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;


import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.ForbidenException;
import com.allancordeiro.creditanalysis.infrastructure.security.login.authCheck.AuthCheck;
import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.infrastructure.security.login.userdetails.UserDetailData;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerUseCase;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class ListLoanApplicationController {

    @Autowired
    private final ListLoanUseCase listLoanUseCase;
    @Autowired
    private final LoginCustomerUseCase loginCustomerUseCase;

    public ListLoanApplicationController(ListLoanUseCase listLoanUseCase, LoginCustomerUseCase loginCustomerUseCase) {
        this.listLoanUseCase = listLoanUseCase;
        this.loginCustomerUseCase = loginCustomerUseCase;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<ListLoanOutputDto> listLoan(@PathVariable String id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UUID customerId = UUID.fromString(id);
            AuthCheck authCheck = new AuthCheck(this.loginCustomerUseCase, authentication, customerId);
            if(authCheck.isAuthorized()) {
                return this.listLoanUseCase.execute(new ListLoanInputDto(customerId));
            }
            throw new UnauthorizedException();
        } catch (LoanApplicationGeneralException ex) {
            throw new BadRequestException(ex);
        } catch (UnauthorizedException ex) {
            throw new ForbidenException(ex);
        }
//        catch (Exception ex) {
//            System.out.println(ex);
//            throw new Exception("Internal server error. Reach out sysadmin.");
//        }
    }
}
