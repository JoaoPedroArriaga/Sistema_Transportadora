package com.mycompany.sistema_transportadora.model.enums;

public enum StatusCarga {
    PENDENTE,
    ARMAZENADA,
    EM_TRANSPORTE,
    ENTREGUE,
    EXTRAVIADA,
    CANCELADA;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}