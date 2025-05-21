package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.DateUtils;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manutencao {
    private static final List<Manutencao> manutencoes = new ArrayList<>();
    
    private final int codigo;
    private final int codVeiculo;
    private final Calendar data;
    private final String tipoServico;
    private final float custo;
    private boolean ativa;

    private Manutencao(int codigo, int codVeiculo, Calendar data, String tipoServico, float custo) {
        this.codigo = codigo;
        this.codVeiculo = codVeiculo;
        this.data = data;
        this.tipoServico = tipoServico;
        this.custo = custo;
        this.ativa = true;
    }

    public static void registrarManutencao(int codVeiculo, Calendar data, String tipoServico, float custo) {
        validarDados(codVeiculo, data, tipoServico, custo);
        manutencoes.add(new Manutencao(manutencoes.size() + 1, codVeiculo, data, tipoServico, custo));
    }

    public static Manutencao buscarPorCodigo(int codigo) {
        if (codigo < 1 || codigo > manutencoes.size()) {
            throw new IllegalArgumentException("Código de manutenção inválido");
        }
        return manutencoes.get(codigo - 1);
    }

    public static List<Manutencao> listarAtivas() {
        return manutencoes.stream()
            .filter(Manutencao::isAtiva)
            .collect(Collectors.toUnmodifiableList());
    }

    public static List<Manutencao> listarPorVeiculo(int codVeiculo) {
        return manutencoes.stream()
            .filter(m -> m.getCodVeiculo() == codVeiculo && m.isAtiva())
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarManutencao(int codigo) {
        Manutencao manutencao = buscarPorCodigo(codigo);
        if (!manutencao.isAtiva()) {
            throw new IllegalStateException("Manutenção já está desativada");
        }
        manutencao.ativa = false;
    }

    private static void validarDados(int codVeiculo, Calendar data, String tipoServico, float custo) {
        if (codVeiculo < 1) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
        if (data == null || data.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("Data da manutenção inválida");
        }
        if (tipoServico == null || tipoServico.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de serviço não pode ser vazio");
        }
        if (custo <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero");
        }
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }

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

    public boolean isAtiva() {
        return ativa;
    }

    public String getDataFormatada() {
        return DateUtils.formatDate(data);
    }

    @Override
    public String toString() {
        return String.format("Manutenção [%d] - Veículo: %d | Data: %s | Serviço: %s | Custo: R$%.2f | %s",
            codigo,
            codVeiculo,
            getDataFormatada(),
            tipoServico,
            custo,
            ativa ? "Ativa" : "Inativa");
    }
}