package br.dev.onepiece.webpiece.utils;

public class ValidadorCpfCnpj {

    // Método para validar CPF
    public static boolean isCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return calcularDigitosVerificadores(cpf, 9) == cpf.charAt(9) &&
               calcularDigitosVerificadores(cpf, 10) == cpf.charAt(10);
    }

    // Método para validar CNPJ
    public static boolean isCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        char dig13 = calcularDigitosVerificadoresCnpj(cnpj, 12);
        char dig14 = calcularDigitosVerificadoresCnpj(cnpj, 13);
        return dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13);
    }

    // Método para calcular dígitos verificadores de CPF
    private static char calcularDigitosVerificadores(String str, int pesoMaximo) {
        int soma = 0;
        int peso = pesoMaximo + 1;

        for (int i = 0; i < pesoMaximo; i++) {
            soma += (str.charAt(i) - '0') * peso--;
        }

        int resto = 11 - (soma % 11);
        return (resto < 2) ? '0' : (char) (resto + '0');
    }

    // Método específico para calcular dígitos verificadores de CNPJ
    private static char calcularDigitosVerificadoresCnpj(String cnpj, int pesoMaximo) {
        int[] multiplicador1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] multiplicador2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma = 0;
        int[] multiplicador = (pesoMaximo == 12) ? multiplicador1 : multiplicador2;

        for (int i = 0; i < pesoMaximo; i++) {
            soma += (cnpj.charAt(i) - '0') * multiplicador[i];
        }

        int resto = soma % 11;
        return (resto < 2) ? '0' : (char) (11 - resto + '0');
    }
}
