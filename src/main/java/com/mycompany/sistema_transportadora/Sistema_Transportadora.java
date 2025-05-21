package com.mycompany.sistema_transportadora;

import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.view.MenuPrincipal;

public class Sistema_Transportadora {
    public static void main(String[] args) {
        cadastrarDadosIniciais();

        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.exibir();
    }

    private static void cadastrarDadosIniciais() {
        // Cadastra alguns estados para teste
        Estado.registrarEstado("São Paulo");
        Estado.registrarEstado("Rio de Janeiro");
        Estado.registrarEstado("Minas Gerais");
        
        // Cadastra algumas cidades para teste
        Cidade.adicionarCidade(1, "São Paulo");
        Cidade.adicionarCidade(1, "Campinas");
        Cidade.adicionarCidade(2, "Rio de Janeiro");
        Cidade.adicionarCidade(2, "Niterói");
        Cidade.adicionarCidade(3, "Belo Horizonte");
    }
}