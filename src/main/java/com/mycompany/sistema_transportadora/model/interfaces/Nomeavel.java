package com.mycompany.sistema_transportadora.model.interfaces;

import java.util.Collection;

public interface Nomeavel {
    String getNome();
    
    default void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }
    
    static <T extends Nomeavel> boolean nomeExiste(String nome, Collection<T> itens) {
        return itens.stream()
            .anyMatch(item -> item.getNome().equalsIgnoreCase(nome.trim()));
    }
}