package com.mycompany.sistema_transportadora.utils;

import com.mycompany.sistema_transportadora.model.entidades.Endereco;
public class TextFormatter {

    public static String formatarEndereco(Endereco endereco) {
        if (endereco == null) return "Endereço não especificado";
        return String.format("%s, %s/%s", 
               endereco.getLogradouro(),
               endereco.getCidade().getNome(),
               endereco.getEstado().getNome());
    }

    public static String formatarNomeEEmail(String nome, String email) {
        return String.format("%s <%s>", nome, email);
    }

    public static String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    public static String formatarCPF(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String truncar(String texto, int tamanhoMaximo) {
        if (texto == null) return null;
        return texto.length() <= tamanhoMaximo ? texto : 
               texto.substring(0, tamanhoMaximo - 3) + "...";
    }
}