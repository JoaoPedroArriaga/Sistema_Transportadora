package com.mycompany.sistema_transportadora;

import java.util.ArrayList;
import java.util.List;

public class Cidade {
    private static final List<Cidade> cidades = new ArrayList<>();
    private int cod_estado = new Estado().cod_estado; 

    private int cod_cidade;
    private String nome;

    public Cidade(int cod_cidade, String nome) {
        this.cod_cidade = cod_cidade;
        this.nome = nome;
    }

    //Getters
    public int getcod_cidade(){
        return cod_cidade;
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
            cidades.get(i).cod_cidade = i + 1;
        }
    }
}