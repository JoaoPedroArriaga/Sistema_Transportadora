package com.mycompany.sistema_transportadora;

import java.util.*;

public final class Cidade {
    private static final List<Cidade> cidades = new ArrayList<>();
    
    private final int cod_cidade;
    private final String nome;
    private final int cod_estado;  // Relação com Estado
    private boolean ativa;

    private Cidade(int cod_cidade, int cod_estado, String nome) {
        this.cod_cidade = cod_cidade;
        this.cod_estado = cod_estado;
        this.nome = nome;
        this.ativa = true;
    }

    // Getters
    public int getCodCidade() {
        return cod_cidade;
    }

    public String getNome() {
        return nome;
    }

    public int getCodEstado(){
        return cod_estado;
    }

    public boolean isAtiva() {
        return ativa;
    }

    // Validações
    private static void ValidarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da cidade não pode ser vazio");
        }
    }

    private static void ValidarCodCidade(int cod_cidade) {
        if (cod_cidade < 1 || cod_cidade > cidades.size()) {
            throw new IllegalArgumentException("Código inválido: " + cod_cidade);
        }
    }

    private static void ValidarCidade(int cod_estado, String nome){
        if (CidadeExiste(cod_estado, nome)){
            throw new IllegalArgumentException("A cidade " + nome + " já existe");
        }
    }

    private static boolean CidadeExiste(int cod_estado, String nome){
        return cidades.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(nome.trim()) 
                                         && c.getCodEstado() == cod_estado);
    }

    //Métodos CRUD

    public static void AdicionarCidade(int cod_estado, String nome) {
        ValidarNome(nome);
        ValidarCidade(cod_estado, nome);
        cidades.add(new Cidade(cidades.size() + 1, cod_estado, nome));
    }

    public static void DesativarCidade(int cod_cidade) {
        ValidarCodCidade(cod_cidade);
        cidades.get(cod_cidade - 1).ativa = false;
    }

    //Métodos de consulta

    public static Cidade BuscarPorCodCidade(int cod_cidade) {
        ValidarCodCidade(cod_cidade);
        return cidades.get(cod_cidade - 1);
    }

    public static List<Cidade> ListarAtivas() {
        List<Cidade> ativas = new ArrayList<>();
        for (Cidade c : cidades) {
            if (c.ativa) ativas.add(c);
        }
        return Collections.unmodifiableList(ativas);
    }

    @Override
    public String toString() {
        return String.format("Cidade [%d] %s (Estado: %d) - %s", 
               cod_cidade, nome, cod_estado, ativa ? "Ativa" : "Inativa");
    }
}