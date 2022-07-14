package com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.validator;

import com.allancordeiro.creditanalysis.domain._shared.validator.ValidatorInterface;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.exceptions.CpfInvalidFormatException;


public class CpfValidator implements ValidatorInterface<String> {
    private String entity;
    private String[] firstBlockCpf;
    private String[] secondBlockCpf;

    @Override
    public void Validate(String entity) throws Exception {
        this.entity = entity;
        if (this.entity.length() != 11) {
            throw new CpfInvalidFormatException();
        }

        if (this.entity.equals("0".repeat(11)) ||
                this.entity.equals("1".repeat(11)) ||
                this.entity.equals("2".repeat(11)) ||
                this.entity.equals("3".repeat(11)) ||
                this.entity.equals("4".repeat(11)) ||
                this.entity.equals("5".repeat(11)) ||
                this.entity.equals("6".repeat(11)) ||
                this.entity.equals("7".repeat(11)) ||
                this.entity.equals("8".repeat(11)) ||
                this.entity.equals("9".repeat(11))
        ) {
            throw new CpfInvalidFormatException();
        }

        this.firstBlockCpf = this.entity.substring(0, 9).split("");
        this.secondBlockCpf = this.entity.substring(9).split("");

        if (!this.CheckVerificationNumbers()) {
            throw new CpfInvalidFormatException();
        }
        ;
    }

    private Boolean CheckVerificationNumbers() {


        Integer firstDigit = this.GetDigitCheck(this.firstBlockCpf, 10);
        if (firstDigit != Integer.parseInt(this.secondBlockCpf[0])) {
            return Boolean.FALSE;
        }
        this.firstBlockCpf = this.entity.substring(0, 10).split("");
        Integer secondDigit = this.GetDigitCheck(this.firstBlockCpf, 11);
        if (secondDigit != Integer.parseInt(this.secondBlockCpf[1])) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private Integer GetDigitCheck(String[] sequence, Integer startFrom) {
        Integer decreaseCalc = startFrom;
        int total = 0;
        for (String digit : sequence) {
            total += Integer.parseInt(digit) * decreaseCalc;
            decreaseCalc--;
        }
        int mod = total % 11;
        if (mod >= 2) {
            return 11 - mod;
        }
        return 0;
    }
}
