package com.mycompany.sistema_transportadora.view;

import java.util.Scanner;
import java.util.Calendar;
import com.mycompany.sistema_transportadora.model.entidades.Parada;
import com.mycompany.sistema_transportadora.model.entidades.Endereco;

public class MenuParada extends MenuBase {
    
    public MenuParada(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR PARADAS");
            System.out.println("1. Adicionar parada");
            System.out.println("2. Registrar chegada na parada");
            System.out.println("3. Listar paradas ativas");
            System.out.println("4. Desativar parada");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    adicionarParada();
                    break;
                case 2:
                    registrarChegadaParada();
                    break;
                case 3:
                    listarParadasAtivas();
                    break;
                case 4:
                    desativarParada();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void adicionarParada() {
        System.out.println("\nEndereços disponíveis:");
        Endereco.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getLogradouro()));
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Informe a data e hora prevista:");
        System.out.print("Dia (1-31): ");
        int dia = scanner.nextInt();
        System.out.print("Mês (1-12): ");
        int mes = scanner.nextInt() - 1;
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        System.out.print("Hora (0-23): ");
        int hora = scanner.nextInt();
        System.out.print("Minuto (0-59): ");
        int minuto = scanner.nextInt();
        limparBuffer();
        
        Calendar dataHoraPrevista = Calendar.getInstance();
        dataHoraPrevista.set(ano, mes, dia, hora, minuto);
        
        try {
            Endereco endereco = Endereco.buscarEndereco(codEndereco);
            Parada.adicionarParada(endereco, dataHoraPrevista);
            System.out.println("Parada adicionada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void registrarChegadaParada() {
        System.out.println("\nParadas disponíveis:");
        Parada.listarAtivas().forEach(p -> {
            String status = (p.getDataHoraReal() == null) ? "Pendente" : "Concluída";
            System.out.println(p.getCodigo() + " - " + 
                p.getLocal().getLogradouro() + " (" + status + ")");
        });

        System.out.print("\nInforme o código da parada: ");
        int codParada = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Registrando data/hora de chegada...");
        Calendar dataChegada = Calendar.getInstance();
        
        try {
            Parada.registrarChegada(codParada, dataChegada);
            System.out.println("Chegada registrada com sucesso em: " + formatarData(dataChegada));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void listarParadasAtivas() {
        System.out.println("\nParadas ativas:");
        Parada.listarAtivas().forEach(System.out::println);
        aguardarEntrada();
    }

    private void desativarParada() {
        System.out.println("\nParadas ativas:");
        Parada.listarAtivas().forEach(p -> System.out.println(p.getCodigo() + " - " + p.getLocal().getLogradouro()));
        
        System.out.print("Selecione o código da parada a desativar: ");
        int codParada = scanner.nextInt();
        limparBuffer();
        
        try {
            Parada.desativarParada(codParada);
            System.out.println("Parada desativada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private String formatarData(Calendar data) {
        if (data == null) return "Não definida";
        return String.format("%02d/%02d/%d %02d:%02d",
            data.get(Calendar.DAY_OF_MONTH),
            data.get(Calendar.MONTH) + 1,
            data.get(Calendar.YEAR),
            data.get(Calendar.HOUR_OF_DAY),
            data.get(Calendar.MINUTE));
    }
}