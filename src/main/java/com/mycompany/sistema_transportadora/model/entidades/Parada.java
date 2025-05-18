package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.DateUtils;
import com.mycompany.sistema_transportadora.utils.DateValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Parada extends Entidade {
    private static final List<Parada> paradas = new ArrayList<>();
    private Endereco local;
    private Calendar dataHoraPrevista;
    private Calendar dataHoraReal;

    private Parada(int codigo, Endereco local, Calendar dataHoraPrevista) {
        super(codigo);
        this.local = local;
        this.dataHoraPrevista = dataHoraPrevista;
        this.dataHoraReal = null;
    }

    public Endereco getLocal() {
        return local;
    }

    public Calendar getDataHoraPrevista() {
        return dataHoraPrevista;
    }

    public Calendar getDataHoraReal() {
        return dataHoraReal;
    }

    public void setDataHoraReal(Calendar dataHoraReal) {
        DateValidator.validarDataNaoNula(dataHoraReal, "Data/hora real não pode ser nula");
        this.dataHoraReal = dataHoraReal;
    }

    public static Parada adicionarParada(Endereco local, Calendar dataHoraPrevista) {
        validarEndereco(local);
        DateValidator.validarDataNaoNula(dataHoraPrevista, "Data/hora prevista não pode ser nula");
        
        Parada novaParada = new Parada(paradas.size() + 1, local, dataHoraPrevista);
        paradas.add(novaParada);
        return novaParada;
    }

    public static void registrarChegada(int codigo, Calendar dataHoraReal) {
        DateValidator.validarDataNaoNula(dataHoraReal, "Data/hora real não pode ser nula");
        Parada parada = buscarParada(codigo);
        parada.setDataHoraReal(dataHoraReal);
    }

    private static void validarEndereco(Endereco local) {
        if (local == null || !local.isAtivo()) {
            throw new IllegalArgumentException("Endereço inválido ou inativo");
        }
    }

    public static Parada buscarParada(int codigo) {
        validarCodigo(codigo, paradas.size());
        return paradas.get(codigo - 1);
    }

    public static List<Parada> listarAtivas() {
        return paradas.stream()
            .filter(Parada::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarParada(int codigo) {
        validarCodigo(codigo, paradas.size());
        Parada parada = paradas.get(codigo - 1);
        
        if (!parada.isAtivo()) {
            throw new IllegalStateException("Parada já está desativada");
        }
        parada.desativar();
    }

    @Override
    public String toString() {
        return String.format("Parada [%d] - Local: %s | Prevista: %s | Real: %s | %s",
            getCodigo(),
            local.toString(),
            DateUtils.formatDateTime(dataHoraPrevista),
            DateUtils.formatDateTime(dataHoraReal),
            isAtivo() ? "Ativa" : "Inativa");
    }
}