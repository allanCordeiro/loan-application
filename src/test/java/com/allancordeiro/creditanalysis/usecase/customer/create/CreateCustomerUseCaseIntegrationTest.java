package com.allancordeiro.creditanalysis.usecase.customer.create;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CreateCustomerUseCaseIntegrationTest {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private AddressRepository addressRepository;

    @Test
    public void should_create_a_customer() throws Exception {
        CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
        CreateCustomerUseCase useCase = new CreateCustomerUseCase(customerGatewayDb);
        CreateCustomerInputDto inputDto = new CreateCustomerInputDto(
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
        CreateCustomerOutputDto outputDto = useCase.execute(inputDto);
        assertNotNull(outputDto.id());
        assertEquals(inputDto.name(), outputDto.name());
        assertEquals(inputDto.email(), outputDto.email());
        assertEquals(inputDto.rg(), outputDto.rg());
        assertEquals(inputDto.cpf(), outputDto.cpf());
        assertEquals(inputDto.IncomeValue(), outputDto.IncomeValue());
        assertEquals(inputDto.address().getStreet(), outputDto.address().getStreet());
        assertEquals(inputDto.address().getNumber(), outputDto.address().getNumber());
        assertEquals(inputDto.address().getCity(), outputDto.address().getCity());
        assertEquals(inputDto.address().getNeighborhood(), outputDto.address().getNeighborhood());
        assertEquals(inputDto.address().getState(), outputDto.address().getState());
        assertEquals(inputDto.address().getComplement(), outputDto.address().getComplement());
    }
}