package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Manutencao extends Entidade {
    private static final List<Manutencao> manutencoes = new ArrayList<>();
    
    private final int codVeiculo;
    private final Calendar data;
    private final String tipoServico;
    private final float custo;

    private Manutencao(int codigo, int codVeiculo, Calendar data, String tipoServico, float custo) {
        super(codigo);
        this.codVeiculo = codVeiculo;
        this.data = data;
        this.tipoServico = tipoServico;
        this.custo = custo;
    }

    public static void registrarManutencao(int codVeiculo, Calendar data, String tipoServico, float custo) {
        validarDados(codVeiculo, data, tipoServico, custo);
        manutencoes.add(new Manutencao(manutencoes.size() + 1, codVeiculo, data, tipoServico, custo));
    }

    private static void validarDados(int codVeiculo, Calendar data, String tipoServico, float custo) {
        if (codVeiculo < 1) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
        if (data == null || data.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("Data inválida");
        }
        if (tipoServico == null || tipoServico.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de serviço não pode ser vazio");
        }
        if (custo <= 0) {
            throw new IllegalArgumentException("Custo deve ser positivo");
        }
    }

    public static List<Manutencao> listarPorVeiculo(int codVeiculo) {
        return manutencoes.stream()
            .filter(m -> m.codVeiculo == codVeiculo)
            .collect(Collectors.toUnmodifiableList());
    }

    // Getters
    public int getCodVeiculo() {
        return codVeiculo;
    }

    public Calendar getData() {
        return data;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public float getCusto() {
        return custo;
    }

    @Override
    public String toString() {
        return String.format("Manutenção [%d] - Veículo: %d | Data: %s | Serviço: %s | Custo: R$%.2f | %s",
            getCodigo(), codVeiculo, DateUtils.formatDate(data), tipoServico, custo,
            isAtivo() ? "Ativa" : "Inativa");
    }
}