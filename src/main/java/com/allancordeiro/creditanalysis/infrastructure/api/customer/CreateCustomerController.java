package com.allancordeiro.creditanalysis.infrastructure.api.customer;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CreateCustomerController {
    @Autowired
    private final CreateCustomerUseCase createCustomerUseCase;

    public CreateCustomerController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCustomerOutputDto createCustomer(@Valid @RequestBody CreateCustomerInputDto request) throws Exception {
        try {
            return this.createCustomerUseCase.execute(request);
        } catch (CustomerGeneralException ex) {
            throw new BadRequestException(ex);
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }
}
