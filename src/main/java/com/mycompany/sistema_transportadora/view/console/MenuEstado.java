package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;

import com.mycompany.sistema_transportadora.model.entidades.Estado;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuEstado extends MenuBase{
    
    public MenuEstado(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR ESTADOS");
            System.out.println("1. Listar estados ativos");
            System.out.println("2. Adicionar novo estado");
            System.out.println("3. Desativar estado");
            System.out.println("4. Exportar para CSV");
            System.out.println("5. Importar de CSV");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarEstados();
                    break;
                case 2:
                    adicionarEstado();
                    break;
                case 3:
                    desativarEstado();
                    break;
                case 4:
                    exportarParaCSV();
                    break;
                case 5:
                    importarDeCSV();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarEstados() {
        System.out.println("\nEstados ativos:");
        Estado.listarAtivos().forEach(System.out::println);
        aguardarEntrada();
    }

    private void adicionarEstado() {
        System.out.print("Informe o nome do novo estado: ");
        String nomeEstado = scanner.nextLine();
        try {
            Estado.registrarEstado(nomeEstado);
            System.out.println("Estado cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void desativarEstado() {
        System.out.println("\nEstados ativos:");
        Estado.listarAtivos().forEach(System.out::println);
        System.out.print("Informe o código do estado a desativar: ");
        int codEstado = scanner.nextInt();
        limparBuffer();
        
        try {
            Estado.desativarEstado(codEstado);
            System.out.println("Estado desativado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

     private void exportarParaCSV() {
        System.out.print("Informe o caminho do arquivo para exportação: ");
        String caminho = scanner.nextLine();
        CSVUtils.exportarVeiculos(caminho);
        System.out.println("Veículos exportados com sucesso para: " + caminho);
        aguardarEntrada();
    }

    private void importarDeCSV() {
        System.out.print("Informe o caminho do arquivo para importação: ");
        String caminho = scanner.nextLine();
        System.out.println("ATENÇÃO: Esta operação adicionará aos dados existentes!");
        System.out.print("Confirmar importação? (S/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            CSVUtils.importarVeiculos(caminho);
            System.out.println("Veículos importados com sucesso!");
        } else {
            System.out.println("Importação cancelada.");
        }
        aguardarEntrada();
    }
}