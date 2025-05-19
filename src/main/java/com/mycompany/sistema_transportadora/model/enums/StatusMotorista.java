package com.mycompany.sistema_transportadora.model.enums;

public enum StatusMotorista {
    DISPONIVEL("Disponível para viagens"),
    EM_VIAGEM("Em viagem no momento"), 
    AUSENTE("Ausente temporariamente"),
    FERIAS("Em período de férias"),
    ATESTADO("De atestado médico"),
    DESLIGADO("Não faz mais parte da equipe");

    private final String descricao;

    StatusMotorista(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}