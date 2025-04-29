package com.br.voting.util;

public class LogUtils {

    public static String mascararCpf(String cpf) {
        if (cpf == null || cpf.length() < 11) {
            return "CPF_INVALIDO";
        }
        return cpf.substring(0, 3) + ".***.***-" + cpf.substring(cpf.length() - 2);
    }
}
