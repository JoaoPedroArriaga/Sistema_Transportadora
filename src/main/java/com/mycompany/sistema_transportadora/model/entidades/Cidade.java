// Representa uma cidade pertencente a um estado específico.
// A classe gerencia uma lista estática de cidades.


package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.interfaces.Nomeavel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cidade extends Entidade implements Nomeavel { 
    private static final List<Cidade> cidades = new ArrayList<>(); // Lista de todas as cidades cadastradas.
    private final String nome; // Nome da cidade.
    private final int codEstado; // Código do estado ao qual a cidade pertence.

    private Cidade(int codigo, int codEstado, String nome) {
        super(codigo);
        this.codEstado = codEstado;
        this.nome = nome;
    }

    @Override
    public String getNome() { // Retorna o nome da cidade.
        return nome;
    }

    public int getCodEstado() { // Retorna o código do estado ao qual a cidade pertence.
        return codEstado;
    }

    public static void adicionarCidade(int codEstado, String nome) { // Adiciona uma nova cidade ao sistema.
        new Cidade(0, codEstado, nome).validarNome(nome);
        
        if (cidades.stream().anyMatch(c -> 
            c.getNome().equalsIgnoreCase(nome.trim()) && 
            c.getCodEstado() == codEstado)) {
            throw new IllegalArgumentException("A cidade " + nome + " já existe neste estado");
        }
        cidades.add(new Cidade(cidades.size() + 1, codEstado, nome));
    }

    public static Cidade buscarCidade(int codigo) { // Busca uma cidade pelo seu código.
        validarCodigo(codigo, cidades.size());
        return cidades.get(codigo - 1);
    }

    public static List<Cidade> listarAtivas() { // Lista todas as cidades ativas.
        return cidades.stream()
            .filter(Cidade::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarCidade(int codigo) { // Desativa uma cidade pelo código.
        validarCodigo(codigo, cidades.size());
        Cidade cidade = cidades.get(codigo - 1);
        
        if (!cidade.isAtivo()) {
            throw new IllegalStateException("A cidade " + cidade.getNome() + " já está desativada");
        }
        cidade.desativar();
    }

    @Override
    public String toString() {
        return String.format("Cidade [%d] %s (Estado: %d) - %s", 
            getCodigo(), nome, codEstado, isAtivo() ? "Ativa" : "Inativa");
    }
}
