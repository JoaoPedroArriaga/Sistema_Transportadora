package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Veiculo extends Entidade {
    private static final List<Veiculo> veiculos = new ArrayList<>();
    
    private final TipoVeiculo tipo;
    private final String placa;
    private StatusVeiculo status;
    private final float pesoMaximoTransportavel;
    private final float volumeMaximoTransportavel;
    private final int anoFabricacao;
    private float kmRodados;
    private Calendar dataUltimaManutencao;

    private Veiculo(int codigo, TipoVeiculo tipo, String placa, float pesoMaximo, 
                   float volumeMaximo, int anoFabricacao) {
        super(codigo);
        this.tipo = Objects.requireNonNull(tipo);
        this.placa = validarPlaca(placa);
        this.pesoMaximoTransportavel = validarPesoMaximo(pesoMaximo);
        this.volumeMaximoTransportavel = validarVolumeMaximo(volumeMaximo);
        this.anoFabricacao = validarAnoFabricacao(anoFabricacao);
        this.kmRodados = 0;
        this.status = StatusVeiculo.DISPONIVEL;
        this.dataUltimaManutencao = null;
    }

    public static void cadastrar(TipoVeiculo tipo, String placa, float pesoMaximo, 
                               float volumeMaximo, int anoFabricacao) {
        validarPlacaUnica(placa);
        veiculos.add(new Veiculo(
            veiculos.size() + 1,
            tipo,
            placa,
            pesoMaximo,
            volumeMaximo,
            anoFabricacao
        ));
    }

    public static Veiculo buscarPorCodigo(int codigo) {
        validarCodigo(codigo, veiculos.size());
        return veiculos.get(codigo - 1);
    }

    public static List<Veiculo> listarAtivos() {
        return veiculos.stream()
            .filter(Veiculo::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static List<Veiculo> listarPorStatus(StatusVeiculo status) {
        return veiculos.stream()
            .filter(v -> v.getStatus() == status)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarVeiculo(int codigo) {
        Veiculo veiculo = buscarPorCodigo(codigo);
        
        if (!veiculo.isAtivo()) {
            throw new IllegalStateException("Veículo já está desativado");
        }
        
        veiculo.desativar();
        veiculo.status = StatusVeiculo.DESATIVADO;
    }

    public void atualizarStatus(StatusVeiculo novoStatus) {
        this.status = Objects.requireNonNull(novoStatus);
    }

    public void registrarManutencao(float kmAtual) {
        this.kmRodados = validarKmRodados(kmAtual);
        this.dataUltimaManutencao = Calendar.getInstance();
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public void atualizarQuilometragem(float kmAtual) {
        this.kmRodados = validarKmRodados(kmAtual);
    }

    // Métodos de validação
    private static String validarPlaca(String placa) {
        if (placa == null || !placa.matches("[A-Z]{3}-?\\d[A-Z0-9]\\d{2}")) {
            throw new IllegalArgumentException("Placa inválida");
        }
        return placa.toUpperCase().replace("-", "");
    }

    private static float validarPesoMaximo(float peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso máximo deve ser positivo");
        }
        return peso;
    }

    private static float validarVolumeMaximo(float volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume máximo deve ser positivo");
        }
        return volume;
    }

    private static int validarAnoFabricacao(int ano) {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        if (ano < 1950 || ano > anoAtual + 1) {
            throw new IllegalArgumentException("Ano de fabricação inválido");
        }
        return ano;
    }

    private static float validarKmRodados(float km) {
        if (km < 0) {
            throw new IllegalArgumentException("Quilometragem não pode ser negativa");
        }
        return km;
    }

    private static void validarPlacaUnica(String placa) {
        if (veiculos.stream().anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa))) {
            throw new IllegalArgumentException("Placa já cadastrada");
        }
    }

    // Getters
    public TipoVeiculo getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getPlacaFormatada() {
        return placa.substring(0, 3) + "-" + placa.substring(3);
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public float getPesoMaximoTransportavel() {
        return pesoMaximoTransportavel;
    }

    public float getVolumeMaximoTransportavel() {
        return volumeMaximoTransportavel;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public float getKmRodados() {
        return kmRodados;
    }

    public Calendar getDataUltimaManutencao() {
        return dataUltimaManutencao;
    }

    public String getDataUltimaManutencaoFormatada() {
        return dataUltimaManutencao != null 
            ? DateUtils.formatDate(dataUltimaManutencao)
            : "Nunca";
    }

    @Override
    public String toString() {
        return String.format("Veículo [%d] - %s | Placa: %s | Capacidade: %.1fkg/%.1fm³ | Ano: %d | KM: %.1f | Última manutenção: %s | Status: %s | %s",
            getCodigo(),
            tipo,
            getPlacaFormatada(),
            pesoMaximoTransportavel,
            volumeMaximoTransportavel,
            anoFabricacao,
            kmRodados,
            getDataUltimaManutencaoFormatada(),
            status,
            isAtivo() ? "Ativo" : "Inativo");
    }
}