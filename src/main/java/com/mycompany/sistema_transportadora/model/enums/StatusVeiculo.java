package com.mycompany.sistema_transportadora.model.enums;

public enum StatusVeiculo {
    DISPONIVEL("Disponível para uso"),
    EM_MANUTENCAO("Em manutenção"),
    EM_ROTA("Em rota de entrega"),
    EM_ROTA_RETORNO("Retornando da entrega"),
    DESATIVADO("Desativado da frota"),
    RESERVADO("Reservado para manutenção");

    private final String descricao;

    StatusVeiculo(String descricao) {
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