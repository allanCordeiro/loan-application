package com.allancordeiro.creditanalysis.domain.customer.valueObject.address;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.name.NameIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.city.CityIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.neighborhood.NeighborhoodIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.number.NumberIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.state.StateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.street.StreetIsMandatory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AddressTest {
    @Test
    public void should_create_and_address() throws Exception {
        Address address = new Address(
                "Street name",
                "123",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "SP",
                "Some complement"
        );

        Assertions.assertNotNull(address);
        Assertions.assertEquals(address.getStreet(), "Street name");
        Assertions.assertEquals(address.getNumber(), "123");
        Assertions.assertEquals(address.getNeighborhood(), "Some neighborhood");
        Assertions.assertEquals(address.getCep(), "01211-100");
        Assertions.assertEquals(address.getCity(), "São Paulo");
        Assertions.assertEquals(address.getState(), "SP");
        Assertions.assertEquals(address.getComplement(), "Some complement");
    }

    @Test
    public void should_create_and_address_without_a_complement() throws Exception {
        Address address = new Address(
                "Street name",
                "123",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "SP"
        );

        Assertions.assertNotNull(address);
        Assertions.assertEquals(address.getStreet(), "Street name");
        Assertions.assertEquals(address.getNumber(), "123");
        Assertions.assertEquals(address.getNeighborhood(), "Some neighborhood");
        Assertions.assertEquals(address.getCep(), "01211-100");
        Assertions.assertEquals(address.getCity(), "São Paulo");
        Assertions.assertEquals(address.getState(), "SP");
    }

    @Test
    public void should_throw_an_error_when_street_is_missing() throws Exception {
        Exception exception = assertThrows(StreetIsMandatory.class, () -> {
            Address address = new Address(
                    "",
                    "123",
                    "Some neighborhood",
                    "01211-100",
                    "São Paulo",
                    "SP",
                    "Some complement"
            );
        });
        assertEquals(StreetIsMandatory.class, exception.getClass());
    }

    @Test
    public void should_throw_an_error_when_number_is_missing() throws Exception {
        Exception exception = assertThrows(NumberIsMandatoryException.class, () -> {
            Address address = new Address(
                    "Street Name",
                    "",
                    "Some neighborhood",
                    "01211-100",
                    "São Paulo",
                    "SP",
                    "Some complement"
            );
        });
        assertEquals(NumberIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_error_when_neighborhood_is_missing() throws Exception {
        Exception exception = assertThrows(NeighborhoodIsMandatoryException.class, () -> {
            Address address = new Address(
                    "Street Name",
                    "123",
                    "",
                    "01211-100",
                    "São Paulo",
                    "SP",
                    "Some complement"
            );
        });
        assertEquals(NeighborhoodIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_error_when_cep_is_missing() throws Exception {
        Exception exception = assertThrows(CepIsMandatoryException.class, () -> {
            Address address = new Address(
                    "Street Name",
                    "123",
                    "Some neighborhood",
                    "",
                    "São Paulo",
                    "SP",
                    "Some complement"
            );
        });
        assertEquals(CepIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_error_when_city_is_missing() throws Exception {
        Exception exception = assertThrows(CityIsMandatoryException.class, () -> {
            Address address = new Address(
                    "Street Name",
                    "123",
                    "Some neighborhood",
                    "01211-100",
                    "",
                    "SP",
                    "Some complement"
            );
        });
        assertEquals(CityIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_error_when_state_is_missing() throws Exception {
        Exception exception = assertThrows(StateIsMandatoryException.class, () -> {
            Address address = new Address(
                    "Street Name",
                    "123",
                    "Some neighborhood",
                    "01211-100",
                    "São Paulo",
                    "",
                    "Some complement"
            );
        });
        assertEquals(StateIsMandatoryException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_error_when_cep_is_invalid() throws Exception {
        Exception exception = assertThrows(CepInvalidFormatException.class, () -> {
            Address address = new Address(
                    "Street Name",
                    "123",
                    "Some neighborhood",
                    "01211100",
                    "São Paulo",
                    "SP",
                    "Some complement"
            );
        });
        assertEquals(CepInvalidFormatException.class, exception.getClass());
    }
}
