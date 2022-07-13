package com.allancordeiro.creditanalysis.infrastructure.api.customer;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.name.NameIsMandatoryException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateCustomerController {
    @Autowired
    private final CreateCustomerUseCase createCustomerUseCase;

    public CreateCustomerController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCustomerOutputDto createCustomer(@RequestBody CreateCustomerInputDto request) throws Exception {
        try {
            return this.createCustomerUseCase.execute(request);
        } catch (NameIsMandatoryException ex) {
            throw new BadRequestException(ex);
        }
    }
}
