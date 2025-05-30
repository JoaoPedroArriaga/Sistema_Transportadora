package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Veiculo extends Entidade {
    
    private static final List<Veiculo> veiculos = new ArrayList<>();
    
    private final TipoVeiculo tipo;
    private final String placa;
    private final double capacidadeCarga;
    private final int anoFabricacao;
    private double volumeMaximoTransportavel;
    private double kmRodados;
    private Calendar ultimaManutencao;
    private String tipoUltimaManutencao;
    private double custoUltimaManutencao;
    private StatusVeiculo status;

    public Veiculo(int codigo, TipoVeiculo tipo, String placa, double capacidadeCarga, double volumeMaximo, int anoFabricacao) {
        super(codigo);
        this.tipo = Objects.requireNonNull(tipo, "Tipo do veículo não pode ser nulo");
        this.placa = validarPlaca(placa);
        this.capacidadeCarga = validarCapacidade(capacidadeCarga);
        this.anoFabricacao = validarAnoFabricacao(anoFabricacao);
        this.volumeMaximoTransportavel = validarVolume(volumeMaximo);
        this.status = StatusVeiculo.DISPONIVEL;
        this.kmRodados = 0;
        this.ultimaManutencao = null;
        this.tipoUltimaManutencao = null;
        this.custoUltimaManutencao = 0;
    }

    public static void cadastrar(TipoVeiculo tipo, String placa, double capacidadeCarga, double volumeMaximo, int anoFabricacao) {
        int novoCodigo = veiculos.size() + 1;
        veiculos.add(new Veiculo(novoCodigo, tipo, placa, capacidadeCarga, volumeMaximo, anoFabricacao));
    }

    public static void registrarManutencao(int codVeiculo, Calendar data, String tipoServico, double custo, double kmRodados) {
        Veiculo veiculo = buscarPorCodigo(codVeiculo);
        veiculo.registrarManutencao(kmRodados, data, tipoServico, custo);
        Manutencao.registrarManutencao(codVeiculo, data, tipoServico, custo);
    }

    public void atualizarStatus(StatusVeiculo novoStatus) {
        Objects.requireNonNull(novoStatus, "Status do veículo não pode ser nulo");
        
        if (this.status == StatusVeiculo.DESATIVADO && novoStatus != StatusVeiculo.DESATIVADO) {
            throw new IllegalStateException("Veículo desativado não pode ter seu status alterado");
        }
        
        if (this.status == StatusVeiculo.EM_MANUTENCAO && 
            (novoStatus == StatusVeiculo.EM_ROTA || novoStatus == StatusVeiculo.EM_ROTA_RETORNO)) {
            throw new IllegalStateException("Veículo em manutenção não pode ser colocado em rota");
        }
        
        if (novoStatus == StatusVeiculo.EM_ROTA && this.status != StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Só é possível colocar em rota veículos disponíveis");
        }
        
        this.status = novoStatus;
    }

    public static Veiculo buscarPorCodigo(int codigo) {
        return veiculos.stream()
                .filter(v -> v.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de veículo inválido: " + codigo));
    }

    public static void desativarVeiculo(int codigo) {
        Veiculo veiculo = buscarPorCodigo(codigo);
        veiculo.setStatus(StatusVeiculo.DESATIVADO);
    }

    public static List<Veiculo> listarAtivos() {
        return veiculos.stream()
                .filter(Veiculo::isAtivo)
                .collect(Collectors.toList());
    }

    public static List<Veiculo> listarPorStatus(StatusVeiculo status) {
        return veiculos.stream()
                .filter(v -> v.getStatus() == status)
                .collect(Collectors.toList());
    }

    public static List<Veiculo> getVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    private String validarPlaca(String placa) {
        String placaNormalizada = placa.trim().toUpperCase().replace("-", "");
        
        if (placaNormalizada.length() != 7) {
            throw new IllegalArgumentException("Placa deve ter 7 caracteres");
        }
        
        if (!placaNormalizada.matches("[A-Z]{3}[0-9]{4}") && 
            !placaNormalizada.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}")) {
            throw new IllegalArgumentException("Formato de placa inválido! Utilize AAA9999 ou AAA9A99");
        }
        
        return placaNormalizada;
    }

    public String getPlacaFormatada() {
        if (placa.matches("[A-Z]{3}[0-9]{4}")) {
            return placa.substring(0, 3) + "-" + placa.substring(3);
        }
        return placa;
    }

    private double validarCapacidade(double capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade de carga deve ser maior que zero");
        }
        return capacidade;
    }

    private int validarAnoFabricacao(int ano) {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        if (ano < 1900 || ano > anoAtual) {
            throw new IllegalArgumentException("Ano de fabricação inválido. Deve ser entre 1900 e " + anoAtual);
        }
        return ano;
    }

    private double validarVolume(double volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume transportável deve ser maior que zero");
        }
        return volume;
    }

    public void registrarManutencao(double kmRodados, Calendar data, String tipoServico, double custo) {
        if (kmRodados < this.kmRodados) {
            throw new IllegalArgumentException("Quilometragem não pode ser inferior à atual");
        }
        if (data == null || data.after(Calendar.getInstance())) {
            throw new IllegalArgumentException("Data de manutenção inválida");
        }
        if (tipoServico == null || tipoServico.isBlank()) {
            throw new IllegalArgumentException("Tipo de serviço não pode ser vazio");
        }
        if (custo <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero");
        }
        
        this.kmRodados = kmRodados;
        this.ultimaManutencao = data;
        this.tipoUltimaManutencao = tipoServico;
        this.custoUltimaManutencao = custo;
    }

    public void atualizarQuilometragem(double novaQuilometragem) {
        if (novaQuilometragem < kmRodados) {
            throw new IllegalArgumentException("Quilometragem não pode ser reduzida");
        }
        this.kmRodados = novaQuilometragem;
    }

    public boolean isAtivo() {
        return status != StatusVeiculo.DESATIVADO;
    }

    // Getters
    public TipoVeiculo getTipo() { return tipo; }
    public String getPlaca() { return placa; }
    public double getCapacidadeCarga() { return capacidadeCarga; }
    public int getAnoFabricacao() { return anoFabricacao; }
    public double getVolumeMaximoTransportavel() { return volumeMaximoTransportavel; }
    public StatusVeiculo getStatus() { return status; }
    public double getKmRodados() { return kmRodados; }
    public Calendar getUltimaManutencao() { return ultimaManutencao; }
    public String getTipoUltimaManutencao() { return tipoUltimaManutencao; }
    public double getCustoUltimaManutencao() { return custoUltimaManutencao; }
    
    public boolean isDisponivel() {
        return status == StatusVeiculo.DISPONIVEL;
    }

    public boolean isEmRota() {
        return status == StatusVeiculo.EM_ROTA || status == StatusVeiculo.EM_ROTA_RETORNO;
    }

    public boolean isEmManutencao() {
        return status == StatusVeiculo.EM_MANUTENCAO;
    }

    public boolean isDesativado() {
        return status == StatusVeiculo.DESATIVADO;
    }

    // Setters
    public void setVolumeMaximoTransportavel(double volume) {
        this.volumeMaximoTransportavel = validarVolume(volume);
    }

    public void setStatus(StatusVeiculo status) {
        this.status = Objects.requireNonNull(status, "Status do veículo não pode ser nulo");
    }

    @Override
    public String toString() {
        return String.format("Veículo [%d] %s - %s | %d | Capacidade: %.2f kg | Volume: %.2f m³ | Status: %s",
                getCodigo(), tipo, getPlacaFormatada(), anoFabricacao, capacidadeCarga, volumeMaximoTransportavel, status);
    }
}