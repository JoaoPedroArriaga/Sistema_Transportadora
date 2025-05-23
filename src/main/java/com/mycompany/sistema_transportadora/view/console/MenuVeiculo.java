package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;
import java.util.Calendar;
import java.util.List;

import com.mycompany.sistema_transportadora.model.entidades.Manutencao;
import com.mycompany.sistema_transportadora.model.entidades.Veiculo;
import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuVeiculo extends MenuBase {
    
    public MenuVeiculo(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR VEÍCULOS");
            System.out.println("1. Listar veículos ativos");
            System.out.println("2. Cadastrar novo veículo");
            System.out.println("3. Registrar manutenção");
            System.out.println("4. Atualizar quilometragem");
            System.out.println("5. Desativar veículo");
            System.out.println("6. Listar por status");
            System.out.println("7. Gerenciar Manutenções");
            System.out.println("8. Exportar para CSV");
            System.out.println("9. Importar de CSV");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarVeiculos();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    registrarManutencao();
                    break;
                case 4:
                    atualizarQuilometragem();
                    break;
                case 5:
                    desativarVeiculo();
                    break;
                case 6:
                    listarPorStatus();
                    break;
                case 7:
                    gerenciarManutencoes();
                    break;
                case 8:
                    exportarParaCSV();
                    break;
                case 9:
                    importarDeCSV();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarVeiculos() {
        System.out.println("\nVeículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        aguardarEntrada();
    }

    private void cadastrarVeiculo() {
        System.out.println("\nTipos disponíveis:");
        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            System.out.println(tipo.ordinal() + " - " + tipo.getDescricao());
        }
        System.out.print("Selecione o tipo: ");
        int tipoOpcao = scanner.nextInt();
        limparBuffer();
        
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Peso máximo transportável (kg): ");
        float pesoMaximo = scanner.nextFloat();
        System.out.print("Volume máximo transportável (m³): ");
        float volumeMaximo = scanner.nextFloat();
        System.out.print("Ano de fabricação: ");
        int anoFabricacao = scanner.nextInt();
        limparBuffer();
        
        try {
            Veiculo.cadastrar(
                TipoVeiculo.values()[tipoOpcao],
                placa,
                pesoMaximo,
                volumeMaximo,
                anoFabricacao
            );
            System.out.println("Veículo cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void registrarManutencao() {
        System.out.println("\nVeículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codigo = scanner.nextInt();
        System.out.print("Quilometragem atual: ");
        float kmAtual = scanner.nextFloat();
        limparBuffer();
        
        try {
            Veiculo veiculo = Veiculo.buscarPorCodigo(codigo);
            veiculo.registrarManutencao(kmAtual, Calendar.getInstance());
            System.out.println("Manutenção registrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void atualizarQuilometragem() {
        System.out.println("\nVeículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codigo = scanner.nextInt();
        System.out.print("Nova quilometragem: ");
        float kmAtual = scanner.nextFloat();
        limparBuffer();
        
        try {
            Veiculo veiculo = Veiculo.buscarPorCodigo(codigo);
            veiculo.atualizarQuilometragem(kmAtual);
            System.out.println("Quilometragem atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void desativarVeiculo() {
        System.out.println("\nVeículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codigo = scanner.nextInt();
        limparBuffer();
        
        try {
            Veiculo.desativarVeiculo(codigo);
            System.out.println("Veículo desativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void listarPorStatus() {
        System.out.println("\nStatus disponíveis:");
        for (StatusVeiculo status : StatusVeiculo.values()) {
            System.out.println(status.ordinal() + " - " + status.getDescricao());
        }
        System.out.print("Selecione o status: ");
        int statusOpcao = scanner.nextInt();
        limparBuffer();
        
        List<Veiculo> veiculos = Veiculo.listarPorStatus(StatusVeiculo.values()[statusOpcao]);
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo encontrado com este status.");
        } else {
            veiculos.forEach(System.out::println);
        }
        aguardarEntrada();
    }

    private void gerenciarManutencoes() {
        System.out.println("\nVeículos disponíveis:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codVeiculo = scanner.nextInt();
        limparBuffer();
        
        List<Manutencao> manutencoes = Manutencao.listarPorVeiculo(codVeiculo);
        if (manutencoes.isEmpty()) {
            System.out.println("Nenhuma manutenção registrada para este veículo.");
        } else {
            System.out.println("\nManutenções do veículo:");
            manutencoes.forEach(System.out::println);
        }
        
        System.out.println("\n1. Registrar nova manutenção");
        System.out.println("2. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        limparBuffer();
        
        if (opcao == 1) {
            registrarManutencao(codVeiculo);
        }
    }

    private void registrarManutencao(int codVeiculo) {
    System.out.println("\nInforme os dados da manutenção:");
    System.out.print("Tipo de serviço: ");
    String tipoServico = scanner.nextLine();
    
    System.out.print("Custo: R$");
    float custo = scanner.nextFloat();
    limparBuffer();
    
    System.out.println("Data da manutenção:");
    System.out.print("Dia (1-31): ");
    int dia = scanner.nextInt();
    System.out.print("Mês (1-12): ");
    int mes = scanner.nextInt() - 1; // Adjust for 0-based Calendar months
    System.out.print("Ano: ");
    int ano = scanner.nextInt();
    limparBuffer();
    
    // Prompt for current km
    System.out.print("Quilometragem atual do veículo: ");
    float kmAtual = scanner.nextFloat();
    limparBuffer();
    
    Calendar data = Calendar.getInstance();
    data.set(ano, mes, dia);
    
    try {
        Manutencao.registrarManutencao(codVeiculo, data, tipoServico, custo);
        Veiculo veiculo = Veiculo.buscarPorCodigo(codVeiculo);
        veiculo.registrarManutencao(kmAtual, data);
        System.out.println("Manutenção registrada com sucesso!");
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