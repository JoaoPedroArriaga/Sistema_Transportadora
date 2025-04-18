package com.mycompany.sistema_transportadora;

import java.util.ArrayList;
import java.util.List;

public class Cidade {
    private static final List<Cidade> cidades = new ArrayList<>();

    private int codCidade;
    private String nome;

    public Cidade(int codCidade, String nome) {
        this.codCidade = codCidade;
        this.nome = nome;
    }

    //Getters
    public int getCodCidade(){
        return codCidade;
    }

    public String getNome(){
        return nome;
    }

    public static List<Cidade> getcidades() {
        return new ArrayList<>(cidades);
    }

    private static void VerificarNomeCidade(String nome){
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da cidade não pode ser vazio!");
        }
    }

    private static void VerificarCodigo(int codigo){
        if (codigo < 0 || codigo > cidades.size()) {
            throw new IllegalArgumentException("Codigo inválido!" + codigo);
        }
    }

    public static void AdicionarCidade(String nome) {
        VerificarNomeCidade(nome);
        int novoCodigo = cidades.size()+1;
        cidades.add(new Cidade(novoCodigo,nome));
    }

    public static Cidade getCidadePorCodigo(int codigo) {
        VerificarCodigo(codigo);
        return cidades.get(codigo - 1);
    }

    public void ExcluirCidade(int codigo) {
        VerificarCodigo(codigo);
        
        cidades.remove(codigo - 1);

        for (int i = 0; i < cidades.size(); i++) {
            cidades.get(i).codCidade = i + 1;
        }
    }
}