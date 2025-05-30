package com.mycompany.sistema_transportadora.model.interfaces;

import java.util.Collection;

// Interface para entidades que possuem um nome.
// Define métodos para obter o nome, validar o nome, e verificar se um nome já existe em uma coleção de objetos Nomeavel.

public interface Nomeavel {
    String getNome();
    
    // Valida se o nome fornecido é válido (não nulo e não vazio).
    // Caso inválido, lança IllegalArgumentException.
   
    default void validarNome(String nome) { 
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }
    
   // Verifica se um nome já existe dentro de uma coleção de objetos que implementam a interface Nomeavel.
   
    static <T extends Nomeavel> boolean nomeExiste(String nome, Collection<T> itens) {
        return itens.stream()
            .anyMatch(item -> item.getNome().equalsIgnoreCase(nome.trim()));
    }
}
