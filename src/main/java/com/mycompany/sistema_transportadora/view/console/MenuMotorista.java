package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;
import java.util.List;
import com.mycompany.sistema_transportadora.model.entidades.Motorista;
import com.mycompany.sistema_transportadora.model.enums.StatusMotorista;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuMotorista extends MenuBase {
    
    public MenuMotorista(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR MOTORISTAS");
            System.out.println("1. Listar motoristas ativos");
            System.out.println("2. Cadastrar novo motorista");
            System.out.println("3. Atualizar motorista");
            System.out.println("4. Desativar motorista");
            System.out.println("5. Listar por status");
            System.out.println("6. Exportar para CSV");
            System.out.println("7. Importar de CSV");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarMotoristas();
                    break;
                case 2:
                    cadastrarMotorista();
                    break;
                case 3:
                    atualizarMotorista();
                    break;
                case 4:
                    desativarMotorista();
                    break;
                case 5:
                    listarPorStatus();
                    break;
                case 6:
                    exportarParaCSV();
                    break;
                case 7:
                    importarDeCSV();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarMotoristas() {
        System.out.println("\nMotoristas ativos:");
        Motorista.listarAtivos().forEach(System.out::println);
        aguardarEntrada();
    }

    private void cadastrarMotorista() {
        System.out.println("\n--- CADASTRAR MOTORISTA ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("CNH: ");
        String cnh = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        try {
            Motorista.cadastrar(nome, cpf, cnh, telefone);
            System.out.println("Motorista cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void atualizarMotorista() {
        System.out.println("\nMotoristas ativos:");
        Motorista.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do motorista: ");
        int codigo = scanner.nextInt();
        limparBuffer();
        
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.println("Status disponíveis:");
        for (StatusMotorista status : StatusMotorista.values()) {
            System.out.println(status.ordinal() + " - " + status.getDescricao());
        }
        System.out.print("Novo status: ");
        int statusOpcao = scanner.nextInt();
        limparBuffer();
        
        try {
            Motorista motorista = Motorista.buscarPorCodigo(codigo);
            motorista.atualizarTelefone(telefone);
            motorista.atualizarStatus(StatusMotorista.values()[statusOpcao]);
            System.out.println("Motorista atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void desativarMotorista() {
        System.out.println("\nMotoristas ativos:");
        Motorista.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do motorista: ");
        int codigo = scanner.nextInt();
        limparBuffer();
        
        try {
            Motorista.desativarMotorista(codigo);
            System.out.println("Motorista desativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void listarPorStatus() {
        System.out.println("\nStatus disponíveis:");
        for (StatusMotorista status : StatusMotorista.values()) {
            System.out.println(status.ordinal() + " - " + status.getDescricao());
        }
        System.out.print("Selecione o status: ");
        int statusOpcao = scanner.nextInt();
        limparBuffer();
        
        List<Motorista> motoristas = Motorista.listarPorStatus(StatusMotorista.values()[statusOpcao]);
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista encontrado com este status.");
        } else {
            motoristas.forEach(System.out::println);
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