package com.fcs.customers.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document {

    private String code;

    public boolean isValidDocument() {

        if (code == null || code.isEmpty())
            return false;

        code = code.replaceAll("\\D", "");

        if (code.length() != 11 || code.matches("(\\d)\\1{10}"))
            return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (code.charAt(i) - '0') * (10 - i);
        }
        int firstCheckDigit = 11 - (sum % 11);
        if (firstCheckDigit >= 10) {
            firstCheckDigit = 0;
        }

        if (firstCheckDigit != (code.charAt(9) - '0'))
            return false;

        sum = 0;
        for (int i = 0; i < 10; i++)
            sum += (code.charAt(i) - '0') * (11 - i);

        int secondCheckDigit = 11 - (sum % 11);
        if (secondCheckDigit >= 10)
            secondCheckDigit = 0;

        return secondCheckDigit == (code.charAt(10) - '0');
    }
}
