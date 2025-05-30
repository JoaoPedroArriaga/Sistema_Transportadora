// Enumeração que representa os tipos de carga transportados pela transportadora.
// Cada tipo de carga possui uma descrição, requisitos específicos para transporte, e o tipo de veículo recomendado para o transporte seguro e adequado.

package com.mycompany.sistema_transportadora.model.enums;

public enum TipoCarga {
    PERECIVEL("Perecível", "Requer refrigeração e prazo de entrega reduzido", "Caminhão refrigerado"),
    FRAGIL("Frágil", "Necessita manuseio especial e embalagem reforçada", "Veículo com suspensão especial"),
    PERIGOSA("Perigosa", "Materiais inflamáveis, tóxicos ou radioativos", "Veículo especializado com certificação"),
    GRANEL("Granel", "Transportada a granel sem embalagem", "Caminhão basculante ou tanque"),
    NORMAL("Normal", "Carga geral sem requisitos especiais", "Qualquer veículo");

    private final String descricao; // Descrição do tipo de carga.
    private final String requisitos; // Requisitos específicos para transporte.
    private final String tipoVeiculoRecomendado; // Tipo de veículo recomendado para transporte.

    TipoCarga(String descricao, String requisitos, String tipoVeiculoRecomendado) { // Construtor do enum que inicializa as propriedades do tipo de carga.
        this.descricao = descricao; 
        this.requisitos = requisitos;
        this.tipoVeiculoRecomendado = tipoVeiculoRecomendado;
    }

    public String getDescricao() { // Retorna a descrição do tipo de carga.
        return descricao;
    }

    public String getRequisitos() { // Retorna os requisitos específicos para o transporte deste tipo de carga.
        return requisitos;
    }

    public String getTipoVeiculoRecomendado() { // Retorna o tipo de veículo recomendado para transportar este tipo de carga.
        return tipoVeiculoRecomendado;
    }

    // Retorna uma string formatada listando todas as opções de tipo de carga,
    // com suas descrições, requisitos e tipo de veículo recomendado.
   
    public static String listarOpcoes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tipos de Carga Disponíveis:\n");
        for (TipoCarga tipo : values()) {
            sb.append(tipo.ordinal())
              .append(" - ").append(tipo.descricao)
              .append(" (Requisitos: ").append(tipo.requisitos)
              .append(", Veículo: ").append(tipo.tipoVeiculoRecomendado)
              .append(")\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() { // Retorna a descrição do tipo de carga ao invés do nome da constante.
        return descricao;
    }
}
