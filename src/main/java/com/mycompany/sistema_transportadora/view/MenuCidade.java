package com.mycompany.sistema_transportadora.view;

import java.util.Scanner;
import com.mycompany.sistema_transportadora.model.entidades.Cidade;
import com.mycompany.sistema_transportadora.model.entidades.Estado;

public class MenuCidade extends MenuBase {
    
    public MenuCidade(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR CIDADES");
            System.out.println("1. Listar cidades ativas");
            System.out.println("2. Adicionar nova cidade");
            System.out.println("3. Desativar cidade");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarCidades();
                    break;
                case 2:
                    adicionarCidade();
                    break;
                case 3:
                    desativarCidade();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarCidades() {
        System.out.println("\nCidades ativas:");
        Cidade.listarAtivas().forEach(System.out::println);
        aguardarEntrada();
    }

    private void adicionarCidade() {
        System.out.println("\nEstados disponíveis:");
        Estado.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getNome()));
        
        System.out.print("Informe o código do estado: ");
        int codEstado = scanner.nextInt();
        limparBuffer();
        
        System.out.print("Informe o nome da nova cidade: ");
        String nomeCidade = scanner.nextLine();
        
        try {
            Cidade.adicionarCidade(codEstado, nomeCidade);
            System.out.println("Cidade cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void desativarCidade() {
        System.out.println("\nCidades ativas:");
        Cidade.listarAtivas().forEach(System.out::println);
        
        System.out.print("Informe o código da cidade a desativar: ");
        int codCidade = scanner.nextInt();
        limparBuffer();
        
        try {
            Cidade.desativarCidade(codCidade);
            System.out.println("Cidade desativada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }
}