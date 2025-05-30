//  Representa um veículo da transportadora, com atributos como tipo, placa, capacidade de carga,
// status atual, quilometragem rodada e última manutenção.

package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Veiculo extends Entidade {
    
    private static final List<Veiculo> veiculos = new ArrayList<>(); // Lista estática que armazena todos os veículos cadastrados
    
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
        this.volumeMaximo = volumeMaximo;
        this.anoFabricacao = anoFabricacao;
        this.status = StatusVeiculo.DISPONIVEL;
        this.kmRodados = 0;
        this.ultimaManutencao = null;
    }

    public static void cadastrar(TipoVeiculo tipo, String placa, double capacidadeCarga) {
        veiculos.add(new Veiculo(veiculos.size() + 1, tipo, placa, capacidadeCarga));
    }

    public static Veiculo buscarPorCodigo(int codigo) { // Busca um veículo pelo código.
        if (codigo < 1 || codigo > veiculos.size()) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
        return veiculos.get(codigo - 1);
    }

    public static void desativarVeiculo(int codigo) throws Exception {
        Veiculo veiculo = buscarPorCodigo(codigo);
        if (veiculo == null) {
            throw new Exception("Veículo não encontrado.");
        }
        veiculo.status(StatusVeiculo.DESATIVADO);
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

    public static List<Veiculo> listarPorStatus(StatusVeiculo status) {
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : listarAtivos()) {
            if (v.getStatus() == status) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public void registrarManutencao(float kmRodados, Calendar data) {
        // Atualiza a quilometragem
        this.kmRodados = kmRodados;
        this.ultimaManutencao = data;
    }

    public void atualizarQuilometragem(double novaQuilometragem) {
        this.kmRodados = novaQuilometragem;
    }

    public void status(StatusVeiculo status) {
        this.status = status;
    }

    // Getters
    public TipoVeiculo getTipo() { return tipo; }
    public String getPlaca() { return placa; }
    public double getCapacidadeCarga() { return capacidadeCarga; }
    public StatusVeiculo getStatus() { return status; }
    public double getKmRodados() { return kmRodados; }
    public Calendar getUltimaManutencao() { return ultimaManutencao; }
    public float getVolumeMaximoTransportavel() {
        return volumeMaximoTransportavel;
    }
    
    public int getAnoFabricacao() {
        return anoFabricacao;
    }
    
    public Calendar getDataUltimaManutencao() {
        return dataUltimaManutencao;
    }

    public void atualizarStatus(StatusVeiculo novoStatus) {
        this.status = novoStatus;
    }

    @Override
    public String toString() {
        return String.format("Veículo [%d] %s - %s | Capacidade: %.2f kg | Status: %s",
            getCodigo(), tipo, placa, capacidadeCarga, status);
    }
}
