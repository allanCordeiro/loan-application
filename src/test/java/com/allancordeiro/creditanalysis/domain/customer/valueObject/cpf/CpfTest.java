package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfInvalidFormatException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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

    @Test(expected = CpfInvalidFormatException.class)
    public void should_throw_an_invalid_format_exception() throws  Exception{
        Cpf cpf = new Cpf("248.063.950-44");

    }

    @Test(expected = CpfInvalidFormatException.class)
    public void should_throw_an_invalid_format_exception_when_char_length_is_invalid()
            throws  Exception {
        Cpf cpf = new Cpf("248.063.950");

    }

    @Test(expected = CpfInvalidFormatException.class)
    public void should_throw_an_invalid_format_exception_when_receive_same_char_sequence()
            throws  Exception{
        Cpf cpf = new Cpf("111.111.111-11");
    }
}
