package com.mycompany.sistema_transportadora.view.console;

import java.util.Scanner;
import java.util.Calendar;
import java.util.List;
import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import com.mycompany.sistema_transportadora.model.enums.StatusRota;
import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class MenuRota extends MenuBase {
    
    public MenuRota(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void exibir() {
        int opcao;
        do {
            exibirCabecalho("GERENCIAR ROTAS");
            System.out.println("1. Listar rotas ativas");
            System.out.println("2. Criar nova rota");
            System.out.println("3. Adicionar parada a rota");
            System.out.println("4. Iniciar rota");
            System.out.println("5. Registrar chegada em parada");
            System.out.println("6. Finalizar rota");
            System.out.println("7. Cancelar rota");
            System.out.println("8. Visualizar detalhes da rota");
            System.out.println("9. Exportar para CSV");
            System.out.println("10. Importar de CSV");
            System.out.println("0. Voltar");
            
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    listarRotasAtivas();
                    break;
                case 2:
                    criarNovaRota();
                    break;
                case 3:
                    adicionarParadaARota();
                    break;
                case 4:
                    iniciarRota();
                    break;
                case 5:
                    registrarChegadaParada();
                    break;
                case 6:
                    finalizarRota();
                    break;
                case 7:
                    cancelarRota();
                    break;
                case 8:
                    visualizarDetalhesRota();
                    break;
                case 9:
                    exportarParaCSV();
                    break;
                case 10:
                    importarDeCSV();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarRotasAtivas() {
        System.out.println("\nRotas ativas:");
        Rota.listarAtivas().forEach(rota -> {
            System.out.println("[" + rota.getCodigo() + "] " + 
                rota.getOrigem().getCidade().getNome() + " → " + 
                rota.getDestino().getCidade().getNome() + 
                " - " + rota.getStatus());
        });
        aguardarEntrada();
    }

    private void criarNovaRota() {
        System.out.println("\nVeículos disponíveis:");
        List<Veiculo> veiculosDisponiveis = Veiculo.listarPorStatus(StatusVeiculo.DISPONIVEL);
        if (veiculosDisponiveis.isEmpty()) {
            System.out.println("Nenhum veículo disponível no momento.");
            aguardarEntrada();
            return;
        }
        veiculosDisponiveis.forEach(v -> {
            System.out.println(v.getCodigo() + " - " + v.getTipo() + " | Placa: " + v.getPlacaFormatada());
        });
        System.out.print("Selecione o veículo: ");
        int codVeiculo = scanner.nextInt();
        limparBuffer();
        
        System.out.println("\nCargas disponíveis:");
        List<Carga> cargasDisponiveis = Carga.listarAtivas().stream()
            .filter(c -> c.getStatus() == StatusCarga.ARMAZENADA)
            .toList();
        if (cargasDisponiveis.isEmpty()) {
            System.out.println("Nenhuma carga disponível no momento.");
            aguardarEntrada();
            return;
        }
        cargasDisponiveis.forEach(c -> {
            System.out.println(c.getCodigo() + " - " + c.getTipo() + " | " + c.getDescricao());
        });
        System.out.print("Selecione a carga: ");
        int codCarga = scanner.nextInt();
        limparBuffer();
        
        System.out.println("\nSelecione o ENDEREÇO DE ORIGEM:");
        Endereco origem = selecionarEndereco();
        
        System.out.println("\nSelecione o ENDEREÇO DE DESTINO:");
        Endereco destino = selecionarEndereco();
        
        try {
            Rota.adicionarRota(codVeiculo, codCarga, origem, destino);
            System.out.println("\nRota criada com sucesso!");
            
            Veiculo veiculo = Veiculo.buscarPorCodigo(codVeiculo);
            veiculo.atualizarStatus(StatusVeiculo.RESERVADO);
            
            Carga carga = Carga.buscarCarga(codCarga);
            carga.atualizarStatus(StatusCarga.EM_TRANSPORTE);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private Endereco selecionarEndereco() {
        List<Endereco> enderecosAtivos = Endereco.listarAtivos();
        enderecosAtivos.forEach(e -> {
            System.out.println(e.getCodigo() + " - " + 
                e.getLogradouro() + ", " + 
                e.getCidade().getNome() + "/" + 
                e.getEstado().getNome());
        });
        
        System.out.print("Selecione o endereço: ");
        int codEndereco = scanner.nextInt();
        limparBuffer();
        
        return Endereco.buscarEndereco(codEndereco);
    }

    private void adicionarParadaARota() {
        System.out.println("\nRotas planejadas:");
        Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.PLANEJADA)
            .forEach(r -> System.out.println(r.getCodigo() + " - " + r.resumo()));
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        limparBuffer();
        
        System.out.println("\nSelecione o endereço da parada:");
        Endereco enderecoParada = selecionarEndereco();
        
        System.out.println("\nInforme a data/hora prevista:");
        Calendar dataHoraPrevista = lerDataHora();
        
        try {
            Parada novaParada = Parada.adicionarParada(enderecoParada, dataHoraPrevista);
            Rota.adicionarParada(codRota, novaParada);
            System.out.println("Parada adicionada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private Calendar lerDataHora() {
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
        
        Calendar dataHora = Calendar.getInstance();
        dataHora.set(ano, mes, dia, hora, minuto);
        return dataHora;
    }

    private void iniciarRota() {
        System.out.println("\nRotas planejadas:");
        List<Rota> rotasPlanejadas = Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.PLANEJADA)
            .toList();
        
        if (rotasPlanejadas.isEmpty()) {
            System.out.println("Nenhuma rota planejada disponível.");
            aguardarEntrada();
            return;
        }
        
        rotasPlanejadas.forEach(r -> {
            System.out.println(r.getCodigo() + " - " + r.resumo());
        });
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Registrando data/hora de partida...");
        Calendar dataPartida = Calendar.getInstance();
        
        try {
            Rota.iniciarRota(codRota, dataPartida);
            System.out.println("Rota iniciada com sucesso em " + formatarData(dataPartida));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void registrarChegadaParada() {
        System.out.println("\nRotas em andamento:");
        List<Rota> rotasEmAndamento = Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.EM_ANDAMENTO)
            .toList();

        if (rotasEmAndamento.isEmpty()) {
            System.out.println("Nenhuma rota em andamento.");
            aguardarEntrada();
            return;
        }

        rotasEmAndamento.forEach(r -> {
            System.out.println("\nRota " + r.getCodigo() + ": " + r.resumo());
            System.out.println("Paradas:");
            r.getParadas().forEach(p -> {
                System.out.println("  " + p.getCodigo() + " - " + 
                    p.getLocal().getLogradouro() + 
                    (p.getDataHoraReal() == null ? " (pendente)" : " (concluída)"));
            });
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

    private void finalizarRota() {
        System.out.println("\nRotas em andamento:");
        List<Rota> rotasEmAndamento = Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.EM_ANDAMENTO)
            .toList();
        
        if (rotasEmAndamento.isEmpty()) {
            System.out.println("Nenhuma rota em andamento.");
            aguardarEntrada();
            return;
        }
        
        rotasEmAndamento.forEach(r -> {
            System.out.println(r.getCodigo() + " - " + r.resumo());
        });
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        limparBuffer();
        
        System.out.println("Registrando data/hora de chegada...");
        Calendar dataChegada = Calendar.getInstance();
        
        try {
            Rota rota = Rota.buscarRota(codRota);
            Rota.finalizarRota(codRota, dataChegada);
            
            Veiculo veiculo = Veiculo.buscarPorCodigo(rota.getCodVeiculo());
            veiculo.atualizarStatus(StatusVeiculo.DISPONIVEL);
            veiculo.atualizarQuilometragem(veiculo.getKmRodados() + 100);
            
            Carga carga = Carga.buscarCarga(rota.getCodCarga());
            carga.atualizarStatus(StatusCarga.ENTREGUE);
            
            System.out.println("Rota finalizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void cancelarRota() {
        System.out.println("\nRotas ativas:");
        Rota.listarAtivas().forEach(r -> System.out.println(r.getCodigo() + " - " + r.resumo()));
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        limparBuffer();
        
        try {
            Rota rota = Rota.buscarRota(codRota);
            Rota.cancelarRota(codRota);
            
            if (rota.getStatus() == StatusRota.PLANEJADA || rota.getStatus() == StatusRota.EM_ANDAMENTO) {
                Veiculo veiculo = Veiculo.buscarPorCodigo(rota.getCodVeiculo());
                veiculo.atualizarStatus(StatusVeiculo.DISPONIVEL);
                
                Carga carga = Carga.buscarCarga(rota.getCodCarga());
                carga.atualizarStatus(StatusCarga.ARMAZENADA);
            }
            
            System.out.println("Rota cancelada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        aguardarEntrada();
    }

    private void visualizarDetalhesRota() {
        System.out.println("\nRotas ativas:");
        Rota.listarAtivas().forEach(r -> System.out.println(r.getCodigo() + " - " + r.resumo()));
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        limparBuffer();
        
        try {
            Rota rota = Rota.buscarRota(codRota);
            System.out.println("\n" + rota.toString());
            
            System.out.println("\nParadas:");
            if (rota.getParadas().isEmpty()) {
                System.out.println("Nenhuma parada cadastrada.");
            } else {
                rota.getParadas().forEach(p -> {
                    System.out.println(" - " + p.toString());
                });
            }
        } catch (Exception e) {
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