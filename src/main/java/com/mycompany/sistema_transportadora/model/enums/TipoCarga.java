package com.mycompany.sistema_transportadora.model.enums;

public enum TipoCarga {
    PERECIVEL,
    FRAGIL,
    PERIGOSA,
    REFRIGERADA,
    GRANEL,
    NORMAL;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}