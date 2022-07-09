package com.allancordeiro.creditanalysis.domain.customer.valueObject.address;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepInvalidFormatException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.cep.CepIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.city.CityIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.neighborhood.NeighborhoodIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.number.NumberIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.state.StateIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.exceptions.street.StreetIsMandatory;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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

    @Test(expected = StreetIsMandatory.class)
    public void should_throw_an_error_when_street_is_missing() throws Exception {
        Address address = new Address(
                "",
                "123",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "SP",
                "Some complement"
        );
    }

    @Test(expected = NumberIsMandatoryException.class)
    public void should_throw_an_error_when_number_is_missing() throws Exception {
        Address address = new Address(
                "Street Name",
                "",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "SP",
                "Some complement"
        );
    }

    @Test(expected = NeighborhoodIsMandatoryException.class)
    public void should_throw_an_error_when_neighborhood_is_missing() throws Exception {
        Address address = new Address(
                "Street Name",
                "123",
                "",
                "01211-100",
                "São Paulo",
                "SP",
                "Some complement"
        );
    }

    @Test(expected = CepIsMandatoryException.class)
    public void should_throw_an_error_when_cep_is_missing() throws Exception {
        Address address = new Address(
                "Street Name",
                "123",
                "Some neighborhood",
                "",
                "São Paulo",
                "SP",
                "Some complement"
        );
    }

    @Test(expected = CityIsMandatoryException.class)
    public void should_throw_an_error_when_city_is_missing() throws Exception {
        Address address = new Address(
                "Street Name",
                "123",
                "Some neighborhood",
                "01211-100",
                "",
                "SP",
                "Some complement"
        );
    }

    @Test(expected = StateIsMandatoryException.class)
    public void should_throw_an_error_when_state_is_missing() throws Exception {
        Address address = new Address(
                "Street Name",
                "123",
                "Some neighborhood",
                "01211-100",
                "São Paulo",
                "",
                "Some complement"
        );
    }

    @Test(expected = CepInvalidFormatException.class)
    public void should_throw_an_error_when_cep_is_invalid() throws Exception {
        Address address = new Address(
                "Street Name",
                "123",
                "Some neighborhood",
                "01211100",
                "São Paulo",
                "SP",
                "Some complement"
        );
    }
}
