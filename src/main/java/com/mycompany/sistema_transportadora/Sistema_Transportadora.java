package com.mycompany.sistema_transportadora;

import java.util.Scanner;

public class Sistema_Transportadora {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Cadastro inicial de estados e cidades para teste
        cadastrarDadosIniciais();
        
        // Menu principal
        int opcao;
        do {
            System.out.println("\n=== SISTEMA TRANSPORTADORA ===");
            System.out.println("1. Gerenciar Estados");
            System.out.println("2. Gerenciar Cidades");
            System.out.println("3. Gerenciar Endereços");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    menuEstados(scanner);
                    break;
                case 2:
                    menuCidades(scanner);
                    break;
                case 3:
                    menuEnderecos(scanner);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void cadastrarDadosIniciais() {
        // Cadastra alguns estados para teste
        Estado.RegistrarEstado("São Paulo");
        Estado.RegistrarEstado("Rio de Janeiro");
        Estado.RegistrarEstado("Minas Gerais");
        
        // Cadastra algumas cidades para teste
        Cidade.AdicionarCidade(1, "São Paulo");
        Cidade.AdicionarCidade(1, "Campinas");
        Cidade.AdicionarCidade(2, "Rio de Janeiro");
        Cidade.AdicionarCidade(2, "Niterói");
        Cidade.AdicionarCidade(3, "Belo Horizonte");
    }

    private static void menuEstados(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR ESTADOS ---");
            System.out.println("1. Listar estados ativos");
            System.out.println("2. Adicionar novo estado");
            System.out.println("3. Desativar estado");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    System.out.println("\nEstados ativos:");
                    Estado.ListarAtivos().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Informe o nome do novo estado: ");
                    String nomeEstado = scanner.nextLine();
                    try {
                        Estado.RegistrarEstado(nomeEstado);
                        System.out.println("Estado cadastrado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nEstados ativos:");
                    Estado.ListarAtivos().forEach(System.out::println);
                    System.out.print("Informe o código do estado a desativar: ");
                    int codEstado = scanner.nextInt();
                    try {
                        Estado.DesativarEstado(codEstado);
                        System.out.println("Estado desativado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void menuCidades(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR CIDADES ---");
            System.out.println("1. Listar cidades ativas");
            System.out.println("2. Adicionar nova cidade");
            System.out.println("3. Desativar cidade");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    System.out.println("\nCidades ativas:");
                    Cidade.ListarAtivas().forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("\nEstados disponíveis:");
                    Estado.ListarAtivos().forEach(e -> System.out.println(e.getCodEstado() + " - " + e.getNome()));
                    System.out.print("Informe o código do estado: ");
                    int codEstado = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Informe o nome da nova cidade: ");
                    String nomeCidade = scanner.nextLine();
                    try {
                        Cidade.AdicionarCidade(codEstado, nomeCidade);
                        System.out.println("Cidade cadastrada com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nCidades ativas:");
                    Cidade.ListarAtivas().forEach(System.out::println);
                    System.out.print("Informe o código da cidade a desativar: ");
                    int codCidade = scanner.nextInt();
                    try {
                        Cidade.DesativarCidade(codCidade);
                        System.out.println("Cidade desativada com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void menuEnderecos(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR ENDEREÇOS ---");
            System.out.println("1. Listar endereços ativos");
            System.out.println("2. Adicionar novo endereço");
            System.out.println("3. Atualizar endereço");
            System.out.println("4. Excluir endereço");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    System.out.println("\nEndereços ativos:");
                    Endereco.ListarAtivos().forEach(System.out::println);
                    break;
                case 2:
                    adicionarEndereco(scanner);
                    break;
                case 3:
                    atualizarEndereco(scanner);
                    break;
                case 4:
                    excluirEndereco(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void adicionarEndereco(Scanner scanner) {
        System.out.println("\n--- ADICIONAR ENDEREÇO ---");
        
        System.out.println("Estados disponíveis:");
        Estado.ListarAtivos().forEach(e -> System.out.println(e.getCodEstado() + " - " + e.getNome()));
        
        System.out.print("Selecione o código do estado: ");
        int codEstado = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Cidades disponíveis neste estado:");
        Cidade.ListarAtivas().stream()
            .filter(c -> c.getCodEstado() == codEstado)
            .forEach(c -> System.out.println(c.getCodCidade() + " - " + c.getNome()));
        
        System.out.print("Selecione o código da cidade: ");
        int codCidade = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Informe o logradouro: ");
        String logradouro = scanner.nextLine();
        
        try {
            Estado estado = Estado.BuscarPorCodEstado(codEstado);
            Cidade cidade = Cidade.BuscarPorCodCidade(codCidade);
            Endereco.Adicionar_Endereco(logradouro, estado, cidade);
            System.out.println("Endereço adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void atualizarEndereco(Scanner scanner) {
        System.out.println("\n--- ATUALIZAR ENDEREÇO ---");
        
        System.out.println("Endereços disponíveis:");
        Endereco.ListarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Estados disponíveis:");
        Estado.ListarAtivos().forEach(e -> System.out.println(e.getCodEstado() + " - " + e.getNome()));
        
        System.out.print("Selecione o novo código do estado: ");
        int codEstado = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Cidades disponíveis neste estado:");
        Cidade.ListarAtivas().stream()
            .filter(c -> c.getCodEstado() == codEstado)
            .forEach(c -> System.out.println(c.getCodCidade() + " - " + c.getNome()));
        
        System.out.print("Selecione o novo código da cidade: ");
        int codCidade = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Informe o novo logradouro: ");
        String logradouro = scanner.nextLine();
        
        try {
            Estado estado = Estado.BuscarPorCodEstado(codEstado);
            Cidade cidade = Cidade.BuscarPorCodCidade(codCidade);
            Endereco endereco = Endereco.BuscarPorCodigo(codEndereco);
            endereco.Atualizar_Endereco(codEndereco, logradouro, estado, cidade);
            System.out.println("Endereço atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void excluirEndereco(Scanner scanner) {
        System.out.println("\n--- EXCLUIR ENDEREÇO ---");
        
        System.out.println("Endereços disponíveis:");
        Endereco.ListarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Endereco endereco = Endereco.BuscarPorCodigo(codEndereco);
            endereco.Excluir_Endereco(codEndereco);
            System.out.println("Endereço excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}