// Enumeração que representa os possíveis status de um motorista no sistema.
// Cada status possui uma descrição textual associada que pode ser utilizada para exibição mais amigável ao usuário.

package com.mycompany.sistema_transportadora.model.enums;

public enum StatusMotorista {
    DISPONIVEL("Disponível para viagens"),
    EM_VIAGEM("Em viagem no momento"), 
    AUSENTE("Ausente temporariamente"),
    FERIAS("Em período de férias"),
    ATESTADO("De atestado médico"),
    DESLIGADO("Não faz mais parte da equipe");

    private final String descricao; // Descrição legível do status do motorista.

    StatusMotorista(String descricao) { // Construtor da enumeração que associa uma descrição a cada status.
        this.descricao = descricao;
    }

    public String getDescricao() { // Obtém a descrição do status do motorista.
        return descricao;
    }

    @Override
    public String toString() { // Retorna a descrição textual do status.
        return descricao;
    }
}
