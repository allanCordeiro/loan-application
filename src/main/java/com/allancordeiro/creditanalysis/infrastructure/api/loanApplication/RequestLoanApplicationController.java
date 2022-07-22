package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;
import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.request.RequestLoanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RequestLoanApplicationController {
    @Autowired
    private final RequestLoanUseCase requestLoanUseCase;

    public RequestLoanApplicationController(RequestLoanUseCase requestLoanUseCase) {
        this.requestLoanUseCase = requestLoanUseCase;
    }

    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestLoanOutputDto createLoanApplicationRequest(@Valid @RequestBody RequestLoanInputDto request)
            throws Exception {
        try {
            return this.requestLoanUseCase.execute(request);
        } catch (LoanApplicationGeneralException ex) {
            throw new BadRequestException(ex);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new Exception("Internal server error. Reach out sysadmin.");
        }
    }

}
