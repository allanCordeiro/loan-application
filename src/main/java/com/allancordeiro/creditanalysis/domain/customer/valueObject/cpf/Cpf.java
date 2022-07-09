package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.validator.CpfValidator;

public class Cpf {
    private String cpf;

    public Cpf(String cpf) throws Exception {
        this.cpf = cpf;

        this.ClearMask();
        this.Validate();

    }

    public String GetUnmaskedCpf() {
        return this.cpf;
    }

    public String GetMaskedCpf() {
        return (this.cpf.substring(0, 3) + "." +
                this.cpf.substring(3, 6) + "." +
                this.cpf.substring(6, 9) + "-" +
                this.cpf.substring(9, 11)
        );
    }

    private void ClearMask() {
        this.cpf = this.cpf.replaceAll("\\D+", "");
    }

    private void Validate() throws Exception {
        CpfValidator cpfValidator = new CpfValidator();
        cpfValidator.Validate(this.cpf);
    }
}
