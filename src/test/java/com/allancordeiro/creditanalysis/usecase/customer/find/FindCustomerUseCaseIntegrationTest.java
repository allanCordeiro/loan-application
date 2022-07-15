package com.allancordeiro.creditanalysis.usecase.customer.find;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateAddressInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class FindCustomerUseCaseIntegrationTest {
    @Autowired private CustomerRepository customerRepository;
    @Autowired private AddressRepository addressRepository;


    @Test
    public void should_find_customer_by_email() throws Exception {
        CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
        CreateCustomerUseCase inputUseCase = new CreateCustomerUseCase(customerGatewayDb);
        CreateCustomerInputDto customerInputDto = new CreateCustomerInputDto(
                "Customer Name",
                "customer@emaiil.com",
                "11321132-7",
                "841.676.580-46",
                "1234",
                7000.0F,
                new CreateAddressInputDto(
                        "Street name",
                        "123",
                        "Some neighborhood",
                        "01211-100",
                        "São Paulo",
                        "SP",
                        "Some complement"
                )
        );
        inputUseCase.execute(customerInputDto);

        FindCustomerInputDto inputDto = new FindCustomerInputDto("customer@emaiil.com");
        FindCustomerUseCase useCase = new FindCustomerUseCase(customerGatewayDb);
        Optional<FindCustomerOutputDto> outputDto = useCase.execute(inputDto);

        if(outputDto.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        assertNotNull(outputDto.get().id());
        assertEquals(customerInputDto.name(), outputDto.get().name());
        assertEquals(customerInputDto.email(), outputDto.get().email());
        assertEquals(customerInputDto.rg(), outputDto.get().rg());
        assertEquals(customerInputDto.cpf(), outputDto.get().cpf());
        assertEquals(customerInputDto.incomeValue(), outputDto.get().IncomeValue());
        assertEquals(customerInputDto.address().street(), outputDto.get().address().getStreet());
        assertEquals(customerInputDto.address().number(), outputDto.get().address().getNumber());
        assertEquals(customerInputDto.address().city(), outputDto.get().address().getCity());
        assertEquals(customerInputDto.address().neighborhood(), outputDto.get().address().getNeighborhood());
        assertEquals(customerInputDto.address().state(), outputDto.get().address().getState());
        assertEquals(customerInputDto.address().complement(), outputDto.get().address().getComplement());
    }

}