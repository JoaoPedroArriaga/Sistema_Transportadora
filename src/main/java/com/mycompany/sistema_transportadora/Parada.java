package com.mycompany.sistema_transportadora;

import java.util.*;

public class Parada {
    private static final List<Parada> paradas = new ArrayList<>();
    
    private final int cod_parada;
    private Endereco local;
    private Calendar data_hora_prevista;
    private Calendar data_hora_real;
    private boolean ativa;

    public Parada(int cod_parada, Endereco local, Calendar data_hora_prevista) {
        this.cod_parada = cod_parada;
        this.local = local;
        this.data_hora_prevista = data_hora_prevista;
        this.data_hora_real = null; 
        this.ativa = true;
    }

    //Getters

    public int getCodParada() { 
        return cod_parada;
    }

    public Endereco getLocal() {
        return local;
    }

    public Calendar getDataHoraPrevista() {
        return data_hora_prevista;
    }

    public Calendar getDataHoraReal() {
        return data_hora_real;
    }

    public boolean isAtiva() {
        return ativa;
    }

    //Setters
    public void setDataHoraReal(Calendar data_hora_real) {
        this.data_hora_real = data_hora_real;
    }

    //Validações
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

    private static void validarCodParada(int cod_parada) {
        if (cod_parada < 1 || cod_parada > paradas.size()) {
            throw new IllegalArgumentException("Código de parada inválido");
        }
    }

    public static void AdicionarParada(Endereco local, Calendar data_hora_prevista) {
        validarEndereco(local);
        validarData(data_hora_prevista);
        
        int novoCodigo = paradas.size() + 1;
        paradas.add(new Parada(novoCodigo, local, data_hora_prevista));
    }

    public static void RegistrarChegada(int cod_parada, Calendar data_hora_real) {
        validarCodParada(cod_parada);
        validarData(data_hora_real);

        Parada parada = paradas.get(cod_parada - 1);
        parada.setDataHoraReal(data_hora_real);
    }

    public static void DesativarParada(int cod_parada) {
        validarCodParada(cod_parada);
        paradas.get(cod_parada - 1).ativa = false;
    }

    //Métodos de consulta
    public static Parada buscarPorCodigo(int cod_parada) {
        validarCodParada(cod_parada);
        return paradas.get(cod_parada - 1);
    }

    public static List<Parada> listarAtivas() {
        List<Parada> ativas = new ArrayList<>();
        for (Parada p : paradas) {
            if (p.ativa) {
                ativas.add(p);
            }
        }
        return Collections.unmodifiableList(ativas);
    }

    //Método para formatar data
    private String FormatarData(Calendar data){
        if(data == null) 
        return "Não registrada";
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
            cod_parada,
            local.toString(),
            FormatarData(data_hora_prevista),
            FormatarData(data_hora_real),
            ativa ? "Ativa" : "Inativa");
    }
}