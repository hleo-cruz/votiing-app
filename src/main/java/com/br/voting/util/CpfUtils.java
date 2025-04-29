package com.br.voting.util;

public class CpfUtils {

    public static boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int d1 = 0, d2 = 0;
            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cpf.charAt(i));
                d1 += digito * (10 - i);
                d2 += digito * (11 - i);
            }
            int restoD1 = d1 % 11;
            int primeiroDigito = (restoD1 < 2) ? 0 : 11 - restoD1;
            d2 += primeiroDigito * 2;
            int restoD2 = d2 % 11;
            int segundoDigito = (restoD2 < 2) ? 0 : 11 - restoD2;

            return cpf.charAt(9) == Character.forDigit(primeiroDigito, 10)
                    && cpf.charAt(10) == Character.forDigit(segundoDigito, 10);
        } catch (Exception e) {
            return false;
        }
    }
}
