package com.allancordeiro.creditanalysis.infrastructure.api.customer;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerGeneralException;
import com.allancordeiro.creditanalysis.infrastructure.api.exception.BadRequestException;
import com.allancordeiro.creditanalysis.usecase.customer.update.UpdateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.update.UpdateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.update.UpdateCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/customers")
public class UpdateCustomerController {
    @Autowired
    private UpdateCustomerUseCase updateCustomerUseCase;

    public UpdateCustomerController(UpdateCustomerUseCase updateCustomerUseCase) {
        this.updateCustomerUseCase = updateCustomerUseCase;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UpdateCustomerOutputDto update(@PathVariable String id,
                                          @Valid @RequestBody UpdateCustomerInputDto request) throws Exception {
        UpdateCustomerInputDto customerInputDto = new UpdateCustomerInputDto(
                id,
                request.name(),
                request.email(),
                request.rg(),
                request.cpf(),
                request.password(),
                request.incomeValue(),
                request.address()
        );

        try {
            return this.updateCustomerUseCase.execute(customerInputDto);
        } catch (CustomerGeneralException ex) {
            throw new BadRequestException(ex);
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

}
