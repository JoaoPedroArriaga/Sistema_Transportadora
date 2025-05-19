package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import com.mycompany.sistema_transportadora.model.enums.TipoCarga;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Carga extends Entidade {
    private static final List<Carga> cargas = new ArrayList<>();
    private final TipoCarga tipo;
    private final String descricao;
    private final float peso;
    private final float volume;
    private final int quantidade;
    private StatusCarga status;
    private final Calendar dataCadastro;

    private Carga(int codigo, TipoCarga tipo, String descricao, float peso, float volume, int quantidade) {
        super(codigo);
        this.tipo = Objects.requireNonNull(tipo, "Tipo de carga não pode ser nulo");
        this.descricao = validarDescricao(descricao);
        this.peso = validarPeso(peso);
        this.volume = validarVolume(volume);
        this.quantidade = validarQuantidade(quantidade);
        this.status = StatusCarga.ARMAZENADO;
        this.dataCadastro = Calendar.getInstance();
    }

    // Getters
    public TipoCarga getTipo() { 
        return tipo; 
    }
    public String getDescricao() { 
        return descricao; 
    }
    public float getPeso() { 
        return peso; 
    }
    public float getVolume() { 
        return volume; 
    }
    public int getQuantidade() { 
        return quantidade; 
    }
    public StatusCarga getStatus() { 
        return status; 
    }
    public Calendar getDataCadastro() { 
        return dataCadastro; 
    }

    public static void adicionarCarga(TipoCarga tipo, String descricao, float peso, float volume, int quantidade) {
        cargas.add(new Carga(cargas.size() + 1, tipo, descricao, peso, volume, quantidade));
    }

    public static Carga buscarCarga(int codigo) {
        validarCodigo(codigo, cargas.size());
        return cargas.get(codigo - 1);
    }

    public static List<Carga> listarAtivas() {
        return cargas.stream()
            .filter(Carga::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarCarga(int codigo) {
        Carga carga = buscarCarga(codigo);
        if (!carga.isAtivo()) {
            throw new IllegalStateException("Carga já está desativada");
        }
        carga.desativar();
    }

    public void atualizarStatus(StatusCarga novoStatus) {
        this.status = Objects.requireNonNull(novoStatus, "Status não pode ser nulo");
    }

    private static String validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        return descricao.trim();
    }

    private static float validarPeso(float peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero");
        }
        return peso;
    }

    private static float validarVolume(float volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume deve ser maior que zero");
        }
        return volume;
    }

    private static int validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        return quantidade;
    }

    @Override
    public String toString() {
        return String.format("Carga [%d] - %s | %s | Peso: %.2fkg | Vol: %.2fm³ | Qtd: %d | Status: %s | Cadastro: %s | %s",
            getCodigo(),
            tipo,
            descricao,
            peso,
            volume,
            quantidade,
            status,
            DateUtils.formatDateTime(dataCadastro),
            isAtivo() ? "Ativa" : "Inativa");
    }
}