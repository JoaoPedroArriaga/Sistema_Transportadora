// Enumeração que representa os diferentes tipos de veículos disponíveis na frota.
//  Cada tipo possui uma descrição amigável para facilitar a exibição em interfaces e relatórios.

package com.mycompany.sistema_transportadora.model.enums;

public enum TipoVeiculo {
    CAMINHAO_BAU("Caminhão Baú"),
    CAMINHAO_REFRIGERADO("Caminhão Refrigerado"),
    BITREM("Bitrem"),
    RODOTREM("Rodotrem"),
    VAN("Van"),
    UTILITARIO("Veículo Utilitário");

    private final String descricao;

    TipoVeiculo(String descricao) { // Construtor do enum que define a descrição do tipo de veículo.
        this.descricao = descricao;
    }

    public String getDescricao() { // Obtém a descrição amigável do tipo de veículo.
        return descricao;
    }

    @Override
    public String toString() { //  Retorna a descrição amigável ao invés do nome da constante.
        return descricao;
    }
}
