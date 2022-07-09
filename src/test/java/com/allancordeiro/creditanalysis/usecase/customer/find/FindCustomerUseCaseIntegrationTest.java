package com.allancordeiro.creditanalysis.usecase.customer.find;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
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
                new Address(
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
        assertEquals(customerInputDto.IncomeValue(), outputDto.get().IncomeValue());
        assertEquals(customerInputDto.address().getStreet(), outputDto.get().address().getStreet());
        assertEquals(customerInputDto.address().getNumber(), outputDto.get().address().getNumber());
        assertEquals(customerInputDto.address().getCity(), outputDto.get().address().getCity());
        assertEquals(customerInputDto.address().getNeighborhood(), outputDto.get().address().getNeighborhood());
        assertEquals(customerInputDto.address().getState(), outputDto.get().address().getState());
        assertEquals(customerInputDto.address().getComplement(), outputDto.get().address().getComplement());
    }

}