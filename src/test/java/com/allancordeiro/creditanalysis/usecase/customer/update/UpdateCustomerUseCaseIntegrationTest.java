package com.allancordeiro.creditanalysis.usecase.customer.update;


import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.gateway.customer.CustomerGatewayDb;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateAddressInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.create.CreateCustomerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UpdateCustomerUseCaseIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;


    @Test
    public void should_update_a_customer() throws Exception {
        CustomerGatewayDb customerGatewayDb = new CustomerGatewayDb(this.customerRepository, this.addressRepository);
        CreateCustomerUseCase createUseCase = new CreateCustomerUseCase(customerGatewayDb);
        CreateCustomerInputDto inputPrepareData = new CreateCustomerInputDto(
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
        CreateCustomerOutputDto outputData = createUseCase.execute(inputPrepareData);

        UpdateCustomerUseCase useCase = new UpdateCustomerUseCase(customerGatewayDb);
        Address updatedAddress = new Address(
                "Street name updated",
                "1234",
                "New neighborhood",
                "01211-400",
                "São Paulo",
                "SP",
                "Some complement"
        );

        UpdateCustomerOutputDto customerUpdated = useCase.execute(new UpdateCustomerInputDto(
                outputData.id(),
                "Novo nome",
                outputData.email(),
                outputData.rg(),
                outputData.cpf(),
                "nova senha",
                8500.0F,
                new UpdateAddressInputDto(
                        updatedAddress.getStreet(),
                        updatedAddress.getNumber(),
                        updatedAddress.getNeighborhood(),
                        updatedAddress.getCep(),
                        updatedAddress.getCity(),
                        updatedAddress.getState(),
                        updatedAddress.getComplement()
                )
        ));

        assertNotNull(customerUpdated.id());
        assertEquals(customerUpdated.id(), outputData.id());
        assertEquals(customerUpdated.name(), "Novo nome");
        assertEquals(customerUpdated.email(), outputData.email());
        assertEquals(customerUpdated.rg(), outputData.rg());
        assertEquals(customerUpdated.cpf(), outputData.cpf());
        assertEquals(customerUpdated.incomeValue(), 8500.0F);
        assertEquals(customerUpdated.address().street(), updatedAddress.getStreet());
        assertEquals(customerUpdated.address().number(), updatedAddress.getNumber());
        assertEquals(customerUpdated.address().neighborhood(), updatedAddress.getNeighborhood());
        assertEquals(customerUpdated.address().cep(), updatedAddress.getCep());
        assertEquals(customerUpdated.address().city(), updatedAddress.getCity());
        assertEquals(customerUpdated.address().state(), updatedAddress.getState());
        assertEquals(customerUpdated.address().complement(), updatedAddress.getComplement());
    }

}