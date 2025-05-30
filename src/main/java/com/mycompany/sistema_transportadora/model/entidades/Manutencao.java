// Representa uma manutenção realizada em um veículo da transportadora.
// Cada manutenção possui uma data, um tipo de serviço, um custo e está associada a um veículo.
// As manutenções são armazenadas em uma lista estática.
// A classe permite registrar novas manutenções, validar dados e listar manutenções de um veículo específico.

package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Manutencao extends Entidade { 
    private static final List<Manutencao> manutencoes = new ArrayList<>(); 
    
    private final int codVeiculo;
    private final Calendar data;
    private final String tipoServico;
    private final double custo;
    private boolean ativa = true;

    private Manutencao(int codigo, int codVeiculo, Calendar data, String tipoServico, double custo) {
        super(codigo);
        this.codVeiculo = codVeiculo;
        this.data = Objects.requireNonNull(data, "Data não pode ser nula");
        this.tipoServico = validarTipoServico(tipoServico);
        this.custo = validarCusto(custo);
    }

    public static void registrarManutencao(int codVeiculo, Calendar data, String tipoServico, double custo) {
        validarDados(codVeiculo, data, tipoServico, custo);
        
        // Atualiza o veículo com os dados da manutenção
        Veiculo veiculo = Veiculo.buscarPorCodigo(codVeiculo);
        veiculo.registrarManutencao(
            veiculo.getKmRodados(), // Mantém a quilometragem atual
            data,
            tipoServico,
            custo
        );
        
        // Registra a manutenção na lista global
        manutencoes.add(new Manutencao(manutencoes.size() + 1, codVeiculo, data, tipoServico, custo));
    }

    private static void validarDados(int codVeiculo, Calendar data, String tipoServico, double custo) {
        if (codVeiculo < 1) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
        if (data.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("Data não pode ser futura");
        }
        if (tipoServico == null || tipoServico.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de serviço não pode ser vazio");
        }
        if (custo <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero");
        }
    }

    public static List<Manutencao> listarPorVeiculo(int codVeiculo) {
        return manutencoes.stream()
            .filter(m -> m.codVeiculo == codVeiculo)
            .collect(Collectors.toUnmodifiableList());
    }
    
    public static List<Manutencao> listarTodas() {
        return Collections.unmodifiableList(manutencoes);
    }
    
    public static void cancelarManutencao(int codManutencao) {
        Manutencao manutencao = manutencoes.stream()
            .filter(m -> m.getCodigo() == codManutencao)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada"));
        
        manutencao.ativa = false;
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

    public double getCusto() { 
        return custo;
    }
    
    public boolean isAtiva() {
        return ativa;
    }
    
    private String validarTipoServico(String tipoServico) {
        if (tipoServico == null || tipoServico.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de serviço não pode ser vazio");
        }
        return tipoServico.trim();
    }
    
    private double validarCusto(double custo) {
        if (custo <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero");
        }
        return custo;
    }

    @Override
    public String toString() { 
        return String.format("Manutenção [%d] - Veículo: %d | Data: %s | Serviço: %s | Custo: R$%.2f | %s", 
            getCodigo(), codVeiculo, DateUtils.formatDate(data), tipoServico, custo,
            isAtiva() ? "Ativa" : "Inativa");
    }
}
