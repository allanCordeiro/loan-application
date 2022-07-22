package com.allancordeiro.creditanalysis.infrastructure.api.loanApplication;


import com.allancordeiro.creditanalysis.domain.loanApplication.exceptions.LoanApplicationGeneralException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanInputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanOutputDto;
import com.allancordeiro.creditanalysis.usecase.loanApplication.list.ListLoanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class ListLoanApplicationController {

    @Autowired
    private final ListLoanUseCase listLoanUseCase;

    public ListLoanApplicationController(ListLoanUseCase listLoanUseCase) {
        this.listLoanUseCase = listLoanUseCase;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<ListLoanOutputDto> listLoan(@PathVariable String id) throws Exception {
        try {
            //temporary
            return this.listLoanUseCase.execute(new ListLoanInputDto(UUID.fromString(id)));
        } catch (LoanApplicationGeneralException ex) {
            throw new BadRequestException(ex);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new Exception("Internal server error. Reach out sysadmin.");
        }
    }
}
