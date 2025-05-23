package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuBackup extends MenuBase {
    
    public MenuBackup(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("BACKUP E RESTAURAÇÃO");
            System.out.println("1. Exportar todos os dados");
            System.out.println("2. Importar todos os dados");
            System.out.println("3. Exportar estados");
            System.out.println("4. Importar estados");
            System.out.println("5. Exportar cidades");
            System.out.println("6. Importar cidades");
            System.out.println("7. Exportar veículos");
            System.out.println("8. Importar veículos");
            System.out.println("9. Exportar rotas");
            System.out.println("10. Importar rotas");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    exportarTodosDados();
                    break;
                case 2:
                    importarTodosDados();
                    break;
                case 3:
                    exportarEstados();
                    break;
                case 4:
                    importarEstados();
                    break;
                case 5:
                    exportarCidades();
                    break;
                case 6:
                    importarCidades();
                    break;
                case 7:
                    exportarVeiculos();
                    break;
                case 8:
                    importarVeiculos();
                    break;
                case 9:
                    exportarRotas();
                    break;
                case 10:
                    importarRotas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void exportarTodosDados() {
        System.out.print("Informe o diretório para exportação: ");
        String diretorio = scanner.nextLine();
        CSVUtils.exportarTudo(diretorio);
        System.out.println("Exportação concluída com sucesso!");
        aguardarEntrada();
    }

    private void importarTodosDados() {
        System.out.print("Informe o diretório para importação: ");
        String diretorio = scanner.nextLine();
        System.out.println("ATENÇÃO: Esta operação substituirá todos os dados atuais!");
        System.out.print("Confirmar importação? (S/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            CSVUtils.importarTudo(diretorio);
            System.out.println("Importação concluída com sucesso!");
        } else {
            System.out.println("Importação cancelada.");
        }
        aguardarEntrada();
    }

    private void exportarEstados() {
        System.out.print("Informe o caminho do arquivo CSV para exportação: ");
        String caminho = scanner.nextLine();
        CSVUtils.exportarEstados(caminho);
        System.out.println("Estados exportados com sucesso!");
        aguardarEntrada();
    }

    private void importarEstados() {
        System.out.print("Informe o caminho do arquivo CSV para importação: ");
        String caminho = scanner.nextLine();
        CSVUtils.importarEstados(caminho);
        System.out.println("Estados importados com sucesso!");
        aguardarEntrada();
    }

    private void exportarCidades() {
        System.out.print("Informe o caminho do arquivo CSV para exportação: ");
        String caminho = scanner.nextLine();
        CSVUtils.exportarCidades(caminho);
        System.out.println("Cidades exportadas com sucesso!");
        aguardarEntrada();
    }

    private void importarCidades() {
        System.out.print("Informe o caminho do arquivo CSV para importação: ");
        String caminho = scanner.nextLine();
        CSVUtils.importarCidades(caminho);
        System.out.println("Cidades importadas com sucesso!");
        aguardarEntrada();
    }

    private void exportarVeiculos() {
        System.out.print("Informe o caminho do arquivo CSV para exportação: ");
        String caminho = scanner.nextLine();
        CSVUtils.exportarVeiculos(caminho);
        System.out.println("Veículos exportados com sucesso!");
        aguardarEntrada();
    }

    private void importarVeiculos() {
        System.out.print("Informe o caminho do arquivo CSV para importação: ");
        String caminho = scanner.nextLine();
        CSVUtils.importarVeiculos(caminho);
        System.out.println("Veículos importados com sucesso!");
        aguardarEntrada();
    }

    private void exportarRotas() {
        System.out.print("Informe o caminho do arquivo CSV para exportação: ");
        String caminho = scanner.nextLine();
        CSVUtils.exportarRotas(caminho);
        System.out.println("Rotas exportadas com sucesso!");
        aguardarEntrada();
    }

    private void importarRotas() {
        System.out.print("Informe o caminho do arquivo CSV para importação: ");
        String caminho = scanner.nextLine();
        CSVUtils.importarRotas(caminho);
        System.out.println("Rotas importadas com sucesso!");
        aguardarEntrada();
    }
}