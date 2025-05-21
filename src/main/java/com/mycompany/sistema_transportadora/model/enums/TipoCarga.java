package com.mycompany.sistema_transportadora.model.enums;

public enum TipoCarga {
    PERECIVEL("Perecível", "Requer refrigeração e prazo de entrega reduzido", "Caminhão refrigerado"),
    FRAGIL("Frágil", "Necessita manuseio especial e embalagem reforçada", "Veículo com suspensão especial"),
    PERIGOSA("Perigosa", "Materiais inflamáveis, tóxicos ou radioativos", "Veículo especializado com certificação"),
    GRANEL("Granel", "Transportada a granel sem embalagem", "Caminhão basculante ou tanque"),
    NORMAL("Normal", "Carga geral sem requisitos especiais", "Qualquer veículo");

    private final String descricao;
    private final String requisitos;
    private final String tipoVeiculoRecomendado;

    TipoCarga(String descricao, String requisitos, String tipoVeiculoRecomendado) {
        this.descricao = descricao;
        this.requisitos = requisitos;
        this.tipoVeiculoRecomendado = tipoVeiculoRecomendado;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public String getTipoVeiculoRecomendado() {
        return tipoVeiculoRecomendado;
    }

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
    public String toString() {
        return descricao;
    }
}