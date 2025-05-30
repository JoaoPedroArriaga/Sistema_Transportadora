package com.mycompany.sistema_transportadora.model.interfaces;

// Interface que define o comportamento de ativação e desativação de uma entidade.
// Classes que implementam essa interface devem informar se estão ativas e permitir a desativação.

public interface Ativavel {
    boolean isAtivo(); // Verifica se a entidade está ativa.
    void desativar(); // Desativa a entidade.
}
