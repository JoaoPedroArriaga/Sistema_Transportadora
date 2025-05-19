package com.mycompany.sistema_transportadora.model.enums;

public enum TipoVeiculo {
    CAMINHAO_BAU("Caminhão Baú"),
    CAMINHAO_REFRIGERADO("Caminhão Refrigerado"),
    BITREM("Bitrem"),
    RODOTREM("Rodotrem"),
    VAN("Van"),
    UTILITARIO("Veículo Utilitário");

    private final String descricao;

    TipoVeiculo(String descricao) {
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