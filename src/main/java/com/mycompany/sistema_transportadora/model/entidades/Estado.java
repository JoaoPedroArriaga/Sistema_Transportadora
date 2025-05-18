package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.interfaces.Nomeavel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Estado extends Entidade implements Nomeavel {
    private static final List<Estado> estados = new ArrayList<>();
    private final String nome;

    private Estado(int codigo, String nome) {
        super(codigo);
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public static void registrarEstado(String nome) {
        new Estado(0,nome).validarNome(nome);
        
        if (Nomeavel.nomeExiste(nome, estados)) {
            throw new IllegalArgumentException("O estado " + nome + " já existe");
        }
        estados.add(new Estado(estados.size() + 1, nome));
    }

    public static Estado buscarEstado(int codigo) {
        validarCodigo(codigo, estados.size());
        return estados.get(codigo - 1);
    }

    public static List<Estado> listarAtivos() {
        return estados.stream()
            .filter(Estado::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarEstado(int codigo) {
        validarCodigo(codigo, estados.size());
        Estado estado = estados.get(codigo - 1);
        
        if (!estado.isAtivo()) {
            throw new IllegalStateException("O estado " + estado.getNome() + " já está desativado");
        }
        estado.desativar();
    }

    @Override
    public String toString() {
        return String.format("Estado [%d] %s - %s", 
            getCodigo(), nome, isAtivo() ? "Ativo" : "Inativo");
    }
}