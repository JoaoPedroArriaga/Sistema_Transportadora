package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Veiculo extends Entidade {
    
    private static final List<Veiculo> veiculos = new ArrayList<>();
    
    private final TipoVeiculo tipo;
    private final String placa;
    private final double capacidadeCarga;
    private StatusVeiculo status;
    private double kmRodados;
    private Calendar ultimaManutencao;

    private Veiculo(int codigo, TipoVeiculo tipo, String placa, double capacidadeCarga) {
        super(codigo);
        this.tipo = tipo;
        this.placa = validarPlaca(placa);
        this.capacidadeCarga = validarCapacidade(capacidadeCarga);
        this.status = StatusVeiculo.DISPONIVEL;
        this.kmRodados = 0;
        this.ultimaManutencao = null;
    }

    public static void cadastrar(TipoVeiculo tipo, String placa, double capacidadeCarga) {
        veiculos.add(new Veiculo(veiculos.size() + 1, tipo, placa, capacidadeCarga));
    }

    public static Veiculo buscarPorCodigo(int codigo) {
        if (codigo < 1 || codigo > veiculos.size()) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
        return veiculos.get(codigo - 1);
    }

    public static List<Veiculo> listarAtivos() {
        return veiculos.stream()
            .filter(Veiculo::isAtivo)
            .toList();
    }

    private String validarPlaca(String placa) {
        String placaFormatada = placa.trim().toUpperCase();
        if (!placaFormatada.matches("[A-Z]{3}-\\d[A-Z0-9]\\d{2}")) {
            throw new IllegalArgumentException("Formato de placa inválido! Use AAA-9999");
        }
        return placaFormatada;
    }

    private double validarCapacidade(double capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        return capacidade;
    }

    // Getters
    public TipoVeiculo getTipo() { return tipo; }
    public String getPlaca() { return placa; }
    public double getCapacidadeCarga() { return capacidadeCarga; }
    public StatusVeiculo getStatus() { return status; }
    public double getKmRodados() { return kmRodados; }
    public Calendar getUltimaManutencao() { return ultimaManutencao; }

    public void atualizarStatus(StatusVeiculo novoStatus) {
        this.status = novoStatus;
    }

    @Override
    public String toString() {
        return String.format("Veículo [%d] %s - %s | Capacidade: %.2f kg | Status: %s",
            getCodigo(), tipo, placa, capacidadeCarga, status);
    }
}