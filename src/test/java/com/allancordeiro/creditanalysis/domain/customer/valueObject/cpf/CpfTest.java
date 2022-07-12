package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf;

import com.allancordeiro.creditanalysis.domain.customer.exceptions.name.NameIsMandatoryException;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfInvalidFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CpfTest {
    @Test
    public void should_add_a_valid_masked_cpf_returns_masked_cpf() throws  Exception{
        Cpf cpf = new Cpf("248.063.950-99");

        Assertions.assertNotNull(cpf);
        Assertions.assertEquals(cpf.GetMaskedCpf(), "248.063.950-99");
    }

    @Test
    public void should_add_a_valid_masked_cpf_returns_unmasked_cpf() throws  Exception{
        Cpf cpf = new Cpf("248.063.950-99");

        Assertions.assertNotNull(cpf);
        Assertions.assertEquals(cpf.GetUnmaskedCpf(), "24806395099");
    }


    @Test
    public void should_add_a_valid_unmasked_cpf_returns_masked_cpf() throws  Exception{
        Cpf cpf = new Cpf("24806395099");

        Assertions.assertNotNull(cpf);
        Assertions.assertEquals(cpf.GetMaskedCpf(), "248.063.950-99");
    }

    @Test
    public void should_add_a_valid_unmasked_cpf_returns_unmasked_cpf() throws  Exception{
        Cpf cpf = new Cpf("24806395099");

        Assertions.assertNotNull(cpf);
        Assertions.assertEquals(cpf.GetUnmaskedCpf(), "24806395099");
    }

    @Test
    public void should_throw_an_invalid_format_exception() {
        Exception exception = assertThrows(CpfInvalidFormatException.class, () -> {
            Cpf cpf = new Cpf("248.063.950-44");
        });
        assertEquals(CpfInvalidFormatException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_invalid_format_exception_when_char_length_is_invalid() {
        Exception exception = assertThrows(CpfInvalidFormatException.class, () -> {
            Cpf cpf = new Cpf("248.063.950");
        });
        assertEquals(CpfInvalidFormatException.class, exception.getClass());
    }

    @Test
    public void should_throw_an_invalid_format_exception_when_receive_same_char_sequence() {
        Exception exception = assertThrows(CpfInvalidFormatException.class, () -> {
            Cpf cpf = new Cpf("111.111.111-11");
        });
        assertEquals(CpfInvalidFormatException.class, exception.getClass());
    }
}
