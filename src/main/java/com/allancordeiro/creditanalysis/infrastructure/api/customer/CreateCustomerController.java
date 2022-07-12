package com.allancordeiro.creditanalysis.infrastructure.api.customer;

import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCustomerController {
    @Autowired
    private final CreateCustomerUseCase createCustomerUseCase;

    public CreateCustomerController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping("/customers")
    public CreateCustomerOutputDto createCustomer(@RequestBody CreateCustomerInputDto request) throws Exception {
        return this.createCustomerUseCase.execute(request);
    }
}
