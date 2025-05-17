package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.interfaces.Nomeavel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cidade extends Entidade implements Nomeavel {
    private static final List<Cidade> cidades = new ArrayList<>();
    private final String nome;
    private final int codEstado;

    private Cidade(int codigo, int codEstado, String nome) {
        super(codigo);
        this.codEstado = codEstado;
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getCodEstado() {
        return codEstado;
    }

    public static void adicionarCidade(int codEstado, String nome) {
        new Cidade(0, codEstado, nome).validarNome(nome);
        
        if (cidades.stream().anyMatch(c -> 
            c.getNome().equalsIgnoreCase(nome.trim()) && 
            c.getCodEstado() == codEstado)) {
            throw new IllegalArgumentException("A cidade " + nome + " já existe neste estado");
        }
        cidades.add(new Cidade(cidades.size() + 1, codEstado, nome));
    }

    public static Cidade buscarCidade(int codigo) {
        return buscarPorCodigo(codigo, cidades);
    }

    public static List<Cidade> listarAtivas() {
        return cidades.stream()
            .filter(Cidade::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarCidade(int codigo) {
        desativarEntidade(codigo, cidades, c -> "A cidade " + c.getNome() + " já está desativada");
    }

    @Override
    public String toString() {
        return String.format("Cidade [%d] %s (Estado: %d) - %s", 
            getCodigo(), nome, codEstado, isAtivo() ? "Ativa" : "Inativa");
    }
}