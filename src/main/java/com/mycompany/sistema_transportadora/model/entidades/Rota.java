package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusRota;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import com.mycompany.sistema_transportadora.utils.DateValidator;
import com.mycompany.sistema_transportadora.utils.TextFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Rota extends Entidade {
    private static final List<Rota> rotas = new ArrayList<>();
    private final int codVeiculo;
    private final int codCarga;
    private final Endereco origem;
    private final Endereco destino;
    private final List<Parada> paradas;
    private Calendar dataPartida;
    private Calendar dataChegada;
    private StatusRota status;

    private Rota(int codigo, int codVeiculo, int codCarga, Endereco origem, Endereco destino) {
        super(codigo);
        this.codVeiculo = codVeiculo;
        this.codCarga = codCarga;
        this.origem = origem;
        this.destino = destino;
        this.paradas = new ArrayList<>();
        this.status = StatusRota.PLANEJADA;
    }

    public int getCodVeiculo() {
        return codVeiculo;
    }

    public int getCodCarga() {
        return codCarga;
    }

    public Endereco getOrigem() {
        return origem;
    }

    public Endereco getDestino() {
        return destino;
    }

    public List<Parada> getParadas() {
        return Collections.unmodifiableList(paradas);
    }

    public Calendar getDataPartida() {
        return dataPartida;
    }

    public Calendar getDataChegada() {
        return dataChegada;
    }

    public StatusRota getStatus() {
        return status;
    }

    @Override
    public boolean isAtivo() {
        return status != StatusRota.CANCELADA;
    }

    public static void adicionarRota(int codVeiculo, int codCarga, Endereco origem, Endereco destino) {
        validarVeiculo(codVeiculo);
        validarCarga(codCarga);
        validarEnderecos(origem, destino);
        
        rotas.add(new Rota(rotas.size() + 1, codVeiculo, codCarga, origem, destino));
    }

    public static void adicionarParada(int codRota, Parada parada) {
        Rota rota = buscarRota(codRota);
        
        if (!rota.podeAdicionarParadas()) {
            throw new IllegalStateException("Só é possível adicionar paradas em rotas planejadas");
        }
        
        rota.paradas.add(parada);
    }

    public static void iniciarRota(int codRota, Calendar dataPartida) {
        Rota rota = buscarRota(codRota);
        
        if (rota.status != StatusRota.PLANEJADA) {
            throw new IllegalStateException("Só é possível iniciar rotas planejadas");
        }
        
        DateValidator.validarDataNaoNula(dataPartida, "Data de partida não pode ser nula");
        rota.dataPartida = dataPartida;
        rota.status = StatusRota.EM_ANDAMENTO;
    }

    public static void finalizarRota(int codRota, Calendar dataChegada) {
        Rota rota = buscarRota(codRota);
        
        if (rota.status != StatusRota.EM_ANDAMENTO) {
            throw new IllegalStateException("Só é possível finalizar rotas em andamento");
        }
        
        rota.dataChegada = dataChegada;
        rota.status = StatusRota.CONCLUIDA;
    }

    public static void cancelarRota(int codRota) {
        Rota rota = buscarRota(codRota);
        if(rota.status != StatusRota.CONCLUIDA){
            if (rota.status == StatusRota.CANCELADA) {
                throw new IllegalStateException("Rota já está cancelada");
            }
            rota.status = StatusRota.CANCELADA;
        }else{
            throw new IllegalStateException("Não é possival cancelar rotas concluídas");
        }
    }

    private static void validarVeiculo(int codVeiculo) {
        if (codVeiculo < 1) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
    }

    private static void validarCarga(int codCarga) {
        if (codCarga < 1) {
            throw new IllegalArgumentException("Código de carga inválido");
        }
    }

    private static void validarEnderecos(Endereco origem, Endereco destino) {
        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Endereços não podem ser nulos");
        }
        if (!origem.isAtivo() || !destino.isAtivo()) {
            throw new IllegalArgumentException("Endereços devem estar ativos");
        }
        if (origem.equals(destino)) {
            throw new IllegalArgumentException("Origem e destino não podem ser iguais");
        }
    }

    public static Rota buscarRota(int codigo) {
        return buscarPorCodigo(codigo, rotas);
    }

    public static List<Rota> listarAtivas() {
        return rotas.stream()
            .filter(Rota::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static List<Rota> listarPorStatus(StatusRota status) {
        return rotas.stream()
            .filter(r -> r.getStatus() == status)
            .collect(Collectors.toUnmodifiableList());
    }

    public boolean podeAdicionarParadas() {
        return status == StatusRota.PLANEJADA;
    }

    public String resumo() {
        return String.format("[%d] %s → %s - %s",
            getCodigo(),
            origem.getCidade().getNome(),
            destino.getCidade().getNome(),
            status);
    }

    @Override
    public String toString() {
        return String.format("Rota [%d] - %s → %s | Status: %s | Partida: %s | Chegada: %s | %s",
            getCodigo(),
            TextFormatter.formatarEnderecoCompleto(
                origem.getLogradouro(),
                origem.getCidade().getNome(),
                origem.getEstado().getNome()
            ),
            TextFormatter.formatarEnderecoCompleto(
                destino.getLogradouro(),
                destino.getCidade().getNome(),
                destino.getEstado().getNome()
            ),
            status,
            DateUtils.formatDateTime(dataPartida),
            DateUtils.formatDateTime(dataChegada),
            isAtivo() ? "Ativa" : "Inativa");
    }
}