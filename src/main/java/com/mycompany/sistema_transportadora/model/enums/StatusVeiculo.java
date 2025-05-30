// Enumeração que representa os possíveis status de um veículo na frota.
// Cada status possui uma descrição amigável para facilitar a exibição em interfaces e relatórios.

package com.mycompany.sistema_transportadora.model.enums;

public enum StatusVeiculo {
    DISPONIVEL("Disponível para uso"),
    EM_MANUTENCAO("Em manutenção"),
    EM_ROTA("Em rota de entrega"),
    EM_ROTA_RETORNO("Retornando da entrega"),
    DESATIVADO("Desativado da frota"),
    RESERVADO("Reservado para manutenção");

    private final String descricao; 

    StatusVeiculo(String descricao) { // Construtor do enum que define a descrição do status do veículo.
        this.descricao = descricao;
    }

    public String getDescricao() { // Obtém a descrição do status do veículo.
        return descricao;
    }

    @Override
    public String toString() { // Retorna a descrição amigável ao invés do nome da constante.
        return descricao;
    }
}
