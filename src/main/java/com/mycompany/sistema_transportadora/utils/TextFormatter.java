package com.mycompany.sistema_transportadora.utils;

public class TextFormatter {
    public static String formatarEnderecoCompleto(String logradouro, String cidade, String estado) {
        return String.format("%s, %s/%s", logradouro, cidade, estado);
    }

    public static String formatarNomeEEmail(String nome, String email) {
        return String.format("%s <%s>", nome, email);
    }

    public static String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    public static String formatarCPF(String cpf) {
        // Formatação padrão de CPF: 000.000.000-00
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String truncar(String texto, int tamanhoMaximo) {
        if (texto == null) return null;
        if (texto.length() <= tamanhoMaximo) return texto;
        return texto.substring(0, tamanhoMaximo - 3) + "...";
    }
}