package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.TextFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Endereco extends Entidade {
    private static final List<Endereco> enderecos = new ArrayList<>();
    private String logradouro;
    private Estado estado;
    private Cidade cidade;

    private Endereco(int codigo, String logradouro, Estado estado, Cidade cidade) {
        super(codigo);
        this.logradouro = logradouro;
        this.estado = estado;
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public static void adicionarEndereco(String logradouro, Estado estado, Cidade cidade) {
        validarLogradouro(logradouro);
        validarRelacaoCidadeEstado(cidade, estado);
        
        enderecos.add(new Endereco(enderecos.size() + 1, logradouro, estado, cidade));
    }

    public void atualizar(String novoLogradouro, Estado novoEstado, Cidade novaCidade) {
        validarLogradouro(novoLogradouro);
        validarRelacaoCidadeEstado(novaCidade, novoEstado);

        this.logradouro = novoLogradouro;
        this.estado = novoEstado;
        this.cidade = novaCidade;
    }

    private static void validarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }
    }

    private static void validarRelacaoCidadeEstado(Cidade cidade, Estado estado) {
        if (cidade == null || estado == null) {
            throw new IllegalArgumentException("Cidade e estado não podem ser nulos");
        }
        if (cidade.getCodEstado() != estado.getCodigo()) {
            throw new IllegalArgumentException(
                String.format("A cidade %s não pertence ao estado %s", 
                    cidade.getNome(), estado.getNome()));
        }
    }

    public static Endereco buscarEndereco(int codigo) {
        validarCodigo(codigo, enderecos.size());
        return enderecos.get(codigo - 1);
    }

    public static List<Endereco> listarAtivos() {
        return enderecos.stream()
            .filter(Endereco::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarEndereco(int codigo) {
        validarCodigo(codigo, enderecos.size());
        Endereco endereco = enderecos.get(codigo - 1);
        
        if (!endereco.isAtivo()) {
            throw new IllegalStateException("Endereço " + endereco.getLogradouro() + " já está desativado");
        }
        endereco.desativar();
    }

    @Override
    public String toString() {
        return String.format("Endereço [%d] - %s",
            getCodigo(),
            TextFormatter.formatarEnderecoCompleto(logradouro,cidade.getNome(),estado.getNome())
            ) + " - " + (isAtivo() ? "Ativo" : "Inativo");
    }
}