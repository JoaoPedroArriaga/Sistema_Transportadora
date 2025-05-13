package com.mycompany.sistema_transportadora;

import java.util.*;

import javax.print.DocFlavor.STRING;

public class Endereco {
    private static final List<Endereco> enderecos = new ArrayList<>();

    private final int cod_endereco;
    private String logradouro;
    private Estado estado;
    private Cidade cidade;
    private boolean ativo;

    public Endereco(int cod_endereco, String logradouro, Estado estado, Cidade cidade) {
        this.cod_endereco = cod_endereco;
        this.logradouro = logradouro;
        this.estado = estado;
        this.cidade = cidade;
        this.ativo = true;
    }

    //Getters

    public int getCodEndereco() {
        return cod_endereco;
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

    public boolean isAtivo() {
        return ativo;
    }

    //Validação

    private static void ValidarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.trim().isEmpty()){
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }
    }

    private static void ValidarEstado(Estado estado) {
        if (estado == null || !estado.isAtivo()){
            throw new IllegalArgumentException("Estado inválido ou inativo");
        }
    }

    private static void ValidarCidade(Cidade cidade) {
        if (cidade == null || !cidade.isAtiva()){
            throw new IllegalArgumentException("Cidade inválida ou inativa");
        }
    }

    private static void ValidarRelacaoCidadeEstado(Cidade cidade, Estado estado) {
        if (cidade.getCodEstado() != estado.getCodEstado()){
            throw new IllegalArgumentException("A cidade não pertence ao estado selecionado");
        }
    }

    private static void ValidarCodEndereco(int cod_endereco) {
        if(cod_endereco < 1 || cod_endereco > enderecos.size()){
            throw new IllegalArgumentException("Código de endereço inválido");
        }
    }

    //Métodos CRUD

    public static void Adicionar_Endereco(String logradouro, Estado estado, Cidade cidade) {
        ValidarLogradouro(logradouro);
        ValidarCidade(cidade);
        ValidarEstado(estado);
        ValidarRelacaoCidadeEstado(cidade, estado);

        int novoCodigo = enderecos.size() + 1;
        enderecos.add(new Endereco(novoCodigo, logradouro, estado, cidade));
    }

    public void Atualizar_Endereco(int cod_endereco, String novo_logradouro, Estado novo_estado, Cidade nova_cidade) {
        ValidarCodEndereco(cod_endereco);
        ValidarLogradouro(novo_logradouro);
        ValidarCidade(nova_cidade);
        ValidarEstado(novo_estado);
        ValidarRelacaoCidadeEstado(nova_cidade, novo_estado);

        Endereco endereco = enderecos.get(cod_endereco - 1);
        endereco.logradouro = novo_logradouro;
        endereco.cidade = nova_cidade;
        endereco.estado = novo_estado; 
    }

    public void Excluir_Endereco(int cod_endereco) {
        ValidarCodEndereco(cod_endereco);
        enderecos.get(cod_endereco - 1).ativo = false;
    }

    //Métodos de consulta

    public static Endereco BuscarPorCodigo(int cod_endereco) {
        ValidarCodEndereco(cod_endereco);
        return enderecos.get(cod_endereco - 1);
    }

    public static List<Endereco> ListarAtivos() {
        List<Endereco> ativos = new ArrayList<>();
        for (Endereco e : enderecos){
            if (e.ativo) {
                ativos.add(e);
            }
        }

        return Collections.unmodifiableList(ativos);
    }

    @Override
    public String toString() {
        return String.format("Endereço [%d] - %s, %s/%s - %s",
                cod_endereco,
                logradouro,
                cidade.getNome(),
                estado.getNome(),
                ativo ? "Ativo" : "Inativo");
    }

}