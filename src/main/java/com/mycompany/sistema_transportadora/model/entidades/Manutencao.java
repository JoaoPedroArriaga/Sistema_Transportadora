// Representa uma manutenção realizada em um veículo da transportadora.
// Cada manutenção possui uma data, um tipo de serviço, um custo e está associada a um veículo.
// As manutenções são armazenadas em uma lista estática.
// A classe permite registrar novas manutenções, validar dados e listar manutenções de um veículo específico.

package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Manutencao extends Entidade { 
    private static final List<Manutencao> manutencoes = new ArrayList<>(); // Lista de todas as manutenções registradas.
    
    private final int codVeiculo; // Código do veículo ao qual a manutenção está vinculada.
    private final Calendar data; // Data em que a manutenção foi realizada.
    private final String tipoServico; // Tipo de serviço realizado na manutenção.
    private final float custo; // Custo da manutenção.
 
    private Manutencao(int codigo, int codVeiculo, Calendar data, String tipoServico, float custo) { // Construtor privado para criar uma instância de manutenção.
        super(codigo);
        this.codVeiculo = codVeiculo;
        this.data = data;
        this.tipoServico = tipoServico;
        this.custo = custo;
    }

    public static void registrarManutencao(int codVeiculo, Calendar data, String tipoServico, float custo) { // Registra uma nova manutenção no sistema.
        validarDados(codVeiculo, data, tipoServico, custo);
        manutencoes.add(new Manutencao(manutencoes.size() + 1, codVeiculo, data, tipoServico, custo));
    }

    private static void validarDados(int codVeiculo, Calendar data, String tipoServico, float custo) { // Valida os dados fornecidos para o registro de manutenção.
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

    public static List<Manutencao> listarPorVeiculo(int codVeiculo) { // Lista todas as manutenções associadas a um determinado veículo.
        return manutencoes.stream()
            .filter(m -> m.codVeiculo == codVeiculo)
            .collect(Collectors.toUnmodifiableList());
    }

    // Getters
    public int getCodVeiculo() { // Retorna o código do veículo relacionado à manutenção.
        return codVeiculo;
    }

    public Calendar getData() {  // Retorna a data da manutenção.
        return data;
    }

    public String getTipoServico() { // Retorna o tipo de serviço realizado.
        return tipoServico;
    }

    public float getCusto() { // Retorna o custo da manutenção.
        return custo;
    }

    @Override
    public String toString() { 
        return String.format("Manutenção [%d] - Veículo: %d | Data: %s | Serviço: %s | Custo: R$%.2f | %s", // Retorna uma representação textual da manutenção.
            getCodigo(), codVeiculo, DateUtils.formatDate(data), tipoServico, custo,
            isAtivo() ? "Ativa" : "Inativa");
    }
}
