package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;
import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuEndereco extends MenuBase {
    
    public MenuEndereco(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR ENDEREÇOS");
            System.out.println("1. Listar endereços ativos");
            System.out.println("2. Adicionar novo endereço");
            System.out.println("3. Atualizar endereço");
            System.out.println("4. Desativar endereço");
            System.out.println("5. Exportar para CSV");
            System.out.println("6. Importar de CSV");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarEnderecos();
                    break;
                case 2:
                    adicionarEndereco();
                    break;
                case 3:
                    atualizarEndereco();
                    break;
                case 4:
                    desativarEndereco();
                    break;
                case 5:
                    exportarParaCSV();
                    break;
                case 6:
                    importarDeCSV();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarEnderecos() {
        System.out.println("\nEndereços ativos:");
        Endereco.listarAtivos().forEach(System.out::println);
        aguardarEntrada();
    }

    private void adicionarEndereco() {
        System.out.println("\nEstados disponíveis:");
        Estado.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getNome()));
        
        System.out.print("Selecione o código do estado: ");
        int codEstado = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Cidades disponíveis neste estado:");
        Cidade.listarAtivas().stream()
            .filter(c -> c.getCodEstado() == codEstado)
            .forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNome()));
        
        System.out.print("Selecione o código da cidade: ");
        int codCidade = scanner.nextInt();
        limparBuffer();
        
        System.out.print("Informe o logradouro: ");
        String logradouro = scanner.nextLine();
        
        try {
            Estado estado = Estado.buscarEstado(codEstado);
            Cidade cidade = Cidade.buscarCidade(codCidade);
            Endereco.adicionarEndereco(logradouro, estado, cidade);
            System.out.println("Endereço adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void atualizarEndereco() {
        System.out.println("\nEndereços disponíveis:");
        Endereco.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Estados disponíveis:");
        Estado.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getNome()));
        
        System.out.print("Selecione o novo código do estado: ");
        int codEstado = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Cidades disponíveis neste estado:");
        Cidade.listarAtivas().stream()
            .filter(c -> c.getCodEstado() == codEstado)
            .forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNome()));
        
        System.out.print("Selecione o novo código da cidade: ");
        int codCidade = scanner.nextInt();
        limparBuffer();
        
        System.out.print("Informe o novo logradouro: ");
        String logradouro = scanner.nextLine();
        
        try {
            Estado estado = Estado.buscarEstado(codEstado);
            Cidade cidade = Cidade.buscarCidade(codCidade);
            Endereco endereco = Endereco.buscarEndereco(codEndereco);
            endereco.atualizar(logradouro, estado, cidade);
            System.out.println("Endereço atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void desativarEndereco() {
        System.out.println("\nEndereços disponíveis:");
        Endereco.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        limparBuffer();
        
        try {
            Endereco.desativarEndereco(codEndereco);
            System.out.println("Endereço desativado com sucesso!");
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