package com.mycompany.sistema_transportadora.utils;

import com.mycompany.sistema_transportadora.model.entidades.Endereco;
public class TextFormatter {
    // Método para formatar endereço completo
    public static String formatarEnderecoCompleto(String logradouro, String cidade, String estado) {
        if (logradouro == null || cidade == null || estado == null) {
            return "Endereço incompleto";
        }
        return String.format("%s, %s/%s", logradouro, cidade, estado);
    }

    // Versão sobrecarregada que recebe um objeto Endereco
    public static String formatarEndereco(Endereco endereco) {
        if (endereco == null) return "Endereço não especificado";
        return formatarEnderecoCompleto(
            endereco.getLogradouro(),
            endereco.getCidade().getNome(),
            endereco.getEstado().getNome()
        );
    }

    public static String formatarNomeEEmail(String nome, String email) {
        return String.format("%s <%s>", nome, email);
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null) return "";
        // Remove todos os caracteres não numéricos
        String numeros = telefone.replaceAll("[^0-9]", "");
        
        if (numeros.length() == 11) {
            return String.format("(%s) %s-%s", 
                numeros.substring(0, 2),
                numeros.substring(2, 7),
                numeros.substring(7));
        } else if (numeros.length() == 10) {
            return String.format("(%s) %s-%s", 
                numeros.substring(0, 2),
                numeros.substring(2, 6),
                numeros.substring(6));
        }
        return telefone; // Retorna original se não puder formatar
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