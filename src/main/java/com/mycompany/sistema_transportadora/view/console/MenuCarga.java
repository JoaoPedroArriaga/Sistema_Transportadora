package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;
import com.mycompany.sistema_transportadora.model.entidades.Carga;
import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import com.mycompany.sistema_transportadora.model.enums.TipoCarga;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuCarga extends MenuBase {
    
    public MenuCarga(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR CARGAS");
            System.out.println("1. Listar cargas ativas");
            System.out.println("2. Cadastrar nova carga");
            System.out.println("3. Atualizar status da carga");
            System.out.println("4. Desativar carga");
            System.out.println("5. Exportar para CSV");
            System.out.println("6. Importar de CSV");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarCargas();
                    break;
                case 2:
                    cadastrarCarga();
                    break;
                case 3:
                    atualizarStatusCarga();
                    break;
                case 4:
                    desativarCarga();
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

    private void listarCargas() {
        System.out.println("\nCargas ativas:");
        Carga.listarAtivas().forEach(System.out::println);
        aguardarEntrada();
    }

    private void cadastrarCarga() {
        System.out.println(TipoCarga.listarOpcoes());
        System.out.print("Selecione o tipo de carga: ");
        int tipoOpcao = scanner.nextInt();
        TipoCarga tipoSelecionado = TipoCarga.values()[tipoOpcao];
        limparBuffer();
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Peso (kg): ");
        float peso = scanner.nextFloat();
        System.out.print("Volume (m³): ");
        float volume = scanner.nextFloat();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        limparBuffer();
        
        try {
            Carga.adicionarCarga(
                tipoSelecionado,
                descricao,
                peso,
                volume,
                quantidade
            );
            System.out.println("Carga cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void atualizarStatusCarga() {
        System.out.println("\nCargas ativas:");
        Carga.listarAtivas().forEach(System.out::println);
        
        System.out.print("Selecione o código da carga: ");
        int codigo = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Status disponíveis:");
        for (StatusCarga status : StatusCarga.values()) {
            System.out.println(status.ordinal() + " - " + status);
        }
        System.out.print("Novo status: ");
        int statusOpcao = scanner.nextInt();
        limparBuffer();
        
        try {
            Carga carga = Carga.buscarCarga(codigo);
            carga.atualizarStatus(StatusCarga.values()[statusOpcao]);
            System.out.println("Status atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void desativarCarga() {
        System.out.println("\nCargas ativas:");
        Carga.listarAtivas().forEach(System.out::println);
        
        System.out.print("Selecione o código da carga: ");
        int codigo = scanner.nextInt();
        limparBuffer();
        
        try {
            Carga.desativarCarga(codigo);
            System.out.println("Carga desativada com sucesso!");
        } catch (Exception e) {
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