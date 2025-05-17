package com.mycompany.sistema_transportadora.model.entidades;

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
        this.dataHoraReal = dataHoraReal;
    }

    public static Parada adicionarParada(Endereco local, Calendar dataHoraPrevista) {
        validarEndereco(local);
        validarData(dataHoraPrevista);
        
        Parada novaParada = new Parada(paradas.size() + 1, local, dataHoraPrevista);
        paradas.add(novaParada);
        return novaParada;
    }

    public static void registrarChegada(int codigo, Calendar dataHoraReal) {
        validarData(dataHoraReal);
        Parada parada = buscarParada(codigo);
        parada.setDataHoraReal(dataHoraReal);
    }

    private static void validarEndereco(Endereco local) {
        if (local == null || !local.isAtivo()) {
            throw new IllegalArgumentException("Endereço inválido ou inativo");
        }
    }

    private static void validarData(Calendar data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
    }

    public static Parada buscarParada(int codigo) {
        return buscarPorCodigo(codigo, paradas);
    }

    public static List<Parada> listarAtivas() {
        return paradas.stream()
            .filter(Parada::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarParada(int codigo) {
        desativarEntidade(codigo, paradas,
            p -> String.format("Parada no endereço %s já está desativada", 
                p.getLocal().getLogradouro()));
    }

    private String formatarData(Calendar data) {
        if (data == null) return "Não registrada";
        return String.format("%02d/%02d/%04d %02d:%02d", 
            data.get(Calendar.DAY_OF_MONTH),
            data.get(Calendar.MONTH) + 1,
            data.get(Calendar.YEAR),
            data.get(Calendar.HOUR_OF_DAY),
            data.get(Calendar.MINUTE));
    }

    @Override
    public String toString() {
        return String.format("Parada [%d] - Local: %s | Prevista: %s | Real: %s | %s",
            getCodigo(),
            local.toString(),
            formatarData(dataHoraPrevista),
            formatarData(dataHoraReal),
            isAtivo() ? "Ativa" : "Inativa");
    }
}