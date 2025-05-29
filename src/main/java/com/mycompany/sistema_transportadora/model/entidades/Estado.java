// Representa um estado (unidade federativa) no sistema.
// Cada estado possui um nome único e um código identificador.
// A classe mantém uma lista estática de todos os estados registrados.
// Permite operações como registro, busca, listagem e desativação.


package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.interfaces.Nomeavel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Estado extends Entidade implements Nomeavel {
    private static final List<Estado> estados = new ArrayList<>(); // Lista estática contendo todos os estados registrados no sistema
    private final String nome; //  Nome do estado

    private Estado(int codigo, String nome) { // Construtor privado, utilizado internamente para criação de novos estados.
        super(codigo);
        this.nome = nome;
    }

    @Override
    public String getNome() { // Retorna o nome do estado.
        return nome;
    }

    public static void registrarEstado(String nome) { // Registra um novo estado no sistema, desde que o nome não esteja duplicado.
        new Estado(0,nome).validarNome(nome);
        
        if (Nomeavel.nomeExiste(nome, estados)) {
            throw new IllegalArgumentException("O estado " + nome + " já existe");
        }
        estados.add(new Estado(estados.size() + 1, nome));
    }

    public static Estado buscarEstado(int codigo) { // Busca um estado pelo seu código identificador.
        validarCodigo(codigo, estados.size());
        return estados.get(codigo - 1);
    }

    public static List<Estado> listarAtivos() { // Lista todos os estados ativos registrados no sistema.
        return estados.stream()
            .filter(Estado::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarEstado(int codigo) { // Desativa o estado correspondente ao código fornecido.
        validarCodigo(codigo, estados.size());
        Estado estado = estados.get(codigo - 1);
        
        if (!estado.isAtivo()) {
            throw new IllegalStateException("O estado " + estado.getNome() + " já está desativado");
        }
        estado.desativar();
    }

    @Override
    public String toString() { // Retorna uma representação textual do estado.
        return String.format("Estado [%d] %s - %s", 
            getCodigo(), nome, isAtivo() ? "Ativo" : "Inativo");
    }
}
