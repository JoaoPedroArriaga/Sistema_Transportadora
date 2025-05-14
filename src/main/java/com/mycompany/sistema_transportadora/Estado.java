package com.mycompany.sistema_transportadora;

import java.util.*;

public class Estado {
    private static final List<Estado> estados = new ArrayList<>();

    private final int cod_estado;
    private final String nome;
    private boolean ativo;

    public Estado(int cod_estado, String nome) {
        this.cod_estado = cod_estado;
        this.nome = nome;
        this.ativo = true;
    }

    //Getters

    public int getCodEstado() {
        return cod_estado;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    //Validações

    private static void ValidarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do estado não pode ser vazio");
        }
    }

    private static void ValidarCodEstado(int cod_estado) {
        if (cod_estado < 1 || cod_estado > estados.size()) {
            throw new IllegalArgumentException("Código inválido: " + cod_estado);
        }
    }

    private static void ValidarEstado(String nome){
        if (EstadoExiste(nome)){
            throw new IllegalArgumentException("O estado " + nome + " já existe");
        }
    }

    private static boolean EstadoExiste(String nome){
        return estados.stream().anyMatch(e -> e.getNome().equalsIgnoreCase(nome.trim()));
    }

     //Métodos CRUD

    public static void RegistrarEstado(String nome) {
        ValidarNome(nome);
        ValidarEstado(nome);
        estados.add(new Estado(estados.size()+1, nome));
    }

    public static void DesativarEstado(int cod_estado) {
        ValidarCodEstado(cod_estado);
        estados.get(cod_estado - 1).ativo = false;
    }

    //Métodos de consulta

    public static Estado BuscarPorCodEstado(int cod_estado) {
        ValidarCodEstado(cod_estado);
        return estados.get(cod_estado - 1);
    }

    public static List<Estado> ListarAtivos() {
        List<Estado> ativos = new ArrayList<>();
        for (Estado e : estados) {
            if (e.ativo) ativos.add(e);
        }
        return Collections.unmodifiableList(ativos);
    }

    @Override
    public String toString() {
        return String.format("Estado [%d] %s - %s", 
               cod_estado, nome, ativo ? "Ativo" : "Inativo");
    }

}