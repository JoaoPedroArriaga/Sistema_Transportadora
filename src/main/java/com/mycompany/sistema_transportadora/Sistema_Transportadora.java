package com.mycompany.sistema_transportadora;

import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import com.mycompany.sistema_transportadora.model.enums.StatusMotorista;
import com.mycompany.sistema_transportadora.model.enums.StatusRota;
import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoCarga;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import com.mycompany.sistema_transportadora.utils.EncodingFixer;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Sistema_Transportadora {
    public static void main(String[] args) {
        EncodingFixer.fixEncoding();
        
        Scanner scanner = EncodingFixer.createUtf8Scanner();

        // Cadastro inicial de estados e cidades para teste
        cadastrarDadosIniciais();
        
        // Menu principal
         int opcao;
        do {
            System.out.println("\n=== SISTEMA TRANSPORTADORA ===");
            System.out.println("1. Gerenciar Estados");
            System.out.println("2. Gerenciar Cidades");
            System.out.println("3. Gerenciar Endereços");
            System.out.println("4. Gerenciar Motoristas");
            System.out.println("5. Gerenciar Veículos");
            System.out.println("6. Gerenciar Cargas");
            System.out.println("7. Gerenciar Paradas"); 
            System.out.println("8. Gerenciar Rotas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
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
                 case 4:
                    menuMotoristas(scanner);
                    break;
                case 5:
                    menuVeiculos(scanner);
                    break;
                case 6:
                    menuCargas(scanner);
                    break;
                case 7:
                    menuParadas(scanner);
                    break;
                case 8:
                    menuRotas(scanner);
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
        Estado.registrarEstado("São Paulo");
        Estado.registrarEstado("Rio de Janeiro");
        Estado.registrarEstado("Minas Gerais");
        
        // Cadastra algumas cidades para teste
        Cidade.adicionarCidade(1, "São Paulo");
        Cidade.adicionarCidade(1, "Campinas");
        Cidade.adicionarCidade(2, "Rio de Janeiro");
        Cidade.adicionarCidade(2, "Niterói");
        Cidade.adicionarCidade(3, "Belo Horizonte");
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
                    Estado.listarAtivos().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Informe o nome do novo estado: ");
                    String nomeEstado = scanner.nextLine();
                    try {
                        Estado.registrarEstado(nomeEstado);
                        System.out.println("Estado cadastrado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nEstados ativos:");
                    Estado.listarAtivos().forEach(System.out::println);
                    System.out.print("Informe o código do estado a desativar: ");
                    int codEstado = scanner.nextInt();
                    try {
                        Estado.desativarEstado(codEstado);
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
                    Cidade.listarAtivas().forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("\nEstados disponíveis:");
                    Estado.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getNome()));
                    System.out.print("Informe o código do estado: ");
                    int codEstado = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Informe o nome da nova cidade: ");
                    String nomeCidade = scanner.nextLine();
                    try {
                        Cidade.adicionarCidade(codEstado, nomeCidade);
                        System.out.println("Cidade cadastrada com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nCidades ativas:");
                    Cidade.listarAtivas().forEach(System.out::println);
                    System.out.print("Informe o código da cidade a desativar: ");
                    int codCidade = scanner.nextInt();
                    try {
                        Cidade.desativarCidade(codCidade);
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
            System.out.println("4. Desativar endereço");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    System.out.println("\nEndereços ativos:");
                    Endereco.listarAtivos().forEach(System.out::println);
                    break;
                case 2:
                    adicionarEndereco(scanner);
                    break;
                case 3:
                    atualizarEndereco(scanner);
                    break;
                case 4:
                    desativarEndereco(scanner);
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
        Estado.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getNome()));
        
        System.out.print("Selecione o código do estado: ");
        int codEstado = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Cidades disponíveis neste estado:");
        Cidade.listarAtivas().stream()
            .filter(c -> c.getCodEstado() == codEstado)
            .forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNome()));
        
        System.out.print("Selecione o código da cidade: ");
        int codCidade = scanner.nextInt();
        scanner.nextLine();
        
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
    }

    private static void atualizarEndereco(Scanner scanner) {
        System.out.println("\n--- ATUALIZAR ENDEREÇO ---");
        
        System.out.println("Endereços disponíveis:");
        Endereco.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Estados disponíveis:");
        Estado.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getNome()));
        
        System.out.print("Selecione o novo código do estado: ");
        int codEstado = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Cidades disponíveis neste estado:");
        Cidade.listarAtivas().stream()
            .filter(c -> c.getCodEstado() == codEstado)
            .forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNome()));
        
        System.out.print("Selecione o novo código da cidade: ");
        int codCidade = scanner.nextInt();
        scanner.nextLine();
        
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
    }

    private static void desativarEndereco(Scanner scanner) {
        System.out.println("\n--- DESATIVAR ENDEREÇO ---");
        
        System.out.println("Endereços disponíveis:");
        Endereco.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Endereco.desativarEndereco(codEndereco);
            System.out.println("Endereço desativado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void menuParadas(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR PARADAS ---");
            System.out.println("1. Adicionar parada");
            System.out.println("2. Registrar chegada na parada");
            System.out.println("3. Listar paradas ativas");
            System.out.println("4. Desativar parada");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    adicionarParada(scanner);
                    break;
                case 2:
                    registrarChegadaParada(scanner, false);
                    break;
                case 3:
                    listarParadasAtivas();
                    break;
                case 4:
                    desativarParada(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void adicionarParada(Scanner scanner) {
        System.out.println("\n--- ADICIONAR PARADA ---");
        
        System.out.println("Endereços disponíveis:");
        Endereco.listarAtivos().forEach(e -> System.out.println(e.getCodigo() + " - " + e.getLogradouro()));
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
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
        scanner.nextLine();
        
        Calendar dataHoraPrevista = Calendar.getInstance();
        dataHoraPrevista.set(ano, mes, dia, hora, minuto);
        
        try {
            Endereco endereco = Endereco.buscarEndereco(codEndereco);
            Parada.adicionarParada(endereco, dataHoraPrevista);
            System.out.println("Parada adicionada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

   private static void registrarChegadaParada(Scanner scanner, boolean mostrarContextoRota) {
    System.out.println("\n--- REGISTRAR CHEGADA ---");
    
    if (mostrarContextoRota) {
        // Lista rotas em andamento com suas paradas (versão detalhada)
        List<Rota> rotasEmAndamento = Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.EM_ANDAMENTO)
            .toList();

        if (rotasEmAndamento.isEmpty()) {
            System.out.println("Nenhuma rota em andamento.");
            return;
        }

        System.out.println("Rotas em andamento:");
        rotasEmAndamento.forEach(r -> {
            System.out.println("\nRota " + r.getCodigo() + ": " + 
                r.getOrigem().getCidade().getNome() + " → " + 
                r.getDestino().getCidade().getNome());
            
            System.out.println("Paradas:");
            r.getParadas().forEach(p -> {
                System.out.println("  " + p.getCodigo() + " - " + 
                    p.getLocal().getLogradouro() + 
                    (p.getDataHoraReal() == null ? " (pendente)" : " (concluída)"));
            });
        });
    } else {
        // Versão simplificada (apenas lista de paradas)
        System.out.println("Paradas disponíveis:");
        Parada.listarAtivas().forEach(p -> {
            String status = (p.getDataHoraReal() == null) ? "Pendente" : "Concluída";
            System.out.println(p.getCodigo() + " - " + 
                p.getLocal().getLogradouro() + " (" + status + ")");
        });
    }

    System.out.print("\nInforme o código da parada: ");
    int codParada = scanner.nextInt();
    scanner.nextLine();
    
    System.out.println("Registrando data/hora de chegada...");
    Calendar dataChegada = Calendar.getInstance();
    
    try {
        Parada.registrarChegada(codParada, dataChegada);
        System.out.println("Chegada registrada com sucesso em: " + formatarData(dataChegada));
    } catch (Exception e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

    private static void listarParadasAtivas() {
        System.out.println("\n--- PARADAS ATIVAS ---");
        List<Parada> ativas = Parada.listarAtivas();
        if (ativas.isEmpty()) {
            System.out.println("Nenhuma parada cadastrada.");
        } else {
            ativas.forEach(System.out::println);
        }
    }

    private static void desativarParada(Scanner scanner) {
        System.out.println("\n--- DESATIVAR PARADA ---");
        
        System.out.println("Paradas ativas:");
        Parada.listarAtivas().forEach(p -> System.out.println(p.getCodigo() + " - " + p.getLocal().getLogradouro()));
        
        System.out.print("Selecione o código da parada a desativar: ");
        int codParada = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Parada.desativarParada(codParada);
            System.out.println("Parada desativada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void menuRotas(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR ROTAS ---");
            System.out.println("1. Listar rotas ativas");
            System.out.println("2. Criar nova rota");
            System.out.println("3. Adicionar parada a rota");
            System.out.println("4. Iniciar rota");
            System.out.println("5. Registrar chegada em parada");
            System.out.println("6. Finalizar rota");
            System.out.println("7. Cancelar rota");
            System.out.println("8. Visualizar detalhes da rota");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    listarRotasAtivas();
                    break;
                case 2:
                    criarNovaRota(scanner);
                    break;
                case 3:
                    adicionarParadaARota(scanner);
                    break;
                case 4:
                    iniciarRota(scanner);
                    break;
                case 5:
                    registrarChegadaParada(scanner,true);
                    break;
                case 6:
                    finalizarRota(scanner);
                    break;
                case 7:
                    cancelarRota(scanner);
                    break;
                case 8:
                    visualizarDetalhesRota(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void listarRotasAtivas() {
        System.out.println("\n--- ROTAS ATIVAS ---");
        List<Rota> rotasAtivas = Rota.listarAtivas();
        if (rotasAtivas.isEmpty()) {
            System.out.println("Nenhuma rota cadastrada.");
        } else {
            rotasAtivas.forEach(rota -> {
                System.out.println("[" + rota.getCodigo() + "] " + 
                    rota.getOrigem().getCidade().getNome() + " → " + 
                    rota.getDestino().getCidade().getNome() + 
                    " - " + rota.getStatus());
            });
        }
    }

   private static void criarNovaRota(Scanner scanner) {
        System.out.println("\n--- NOVA ROTA ---");
        
        // Listar veículos disponíveis
        System.out.println("\nVeículos disponíveis:");
        List<Veiculo> veiculosDisponiveis = Veiculo.listarPorStatus(StatusVeiculo.DISPONIVEL);
        if (veiculosDisponiveis.isEmpty()) {
            System.out.println("Nenhum veículo disponível no momento.");
            return;
        }
        veiculosDisponiveis.forEach(v -> {
            System.out.println(v.getCodigo() + " - " + v.getTipo() + " | Placa: " + v.getPlacaFormatada());
        });
        System.out.print("Selecione o veículo: ");
        int codVeiculo = scanner.nextInt();
        
        // Listar cargas disponíveis
        System.out.println("\nCargas disponíveis:");
        List<Carga> cargasDisponiveis = Carga.listarAtivas().stream()
            .filter(c -> c.getStatus() == StatusCarga.ARMAZENADA)
            .toList();
        if (cargasDisponiveis.isEmpty()) {
            System.out.println("Nenhuma carga disponível no momento.");
            return;
        }
        cargasDisponiveis.forEach(c -> {
            System.out.println(c.getCodigo() + " - " + c.getTipo() + " | " + c.getDescricao());
        });
        System.out.print("Selecione a carga: ");
        int codCarga = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nSelecione o ENDEREÇO DE ORIGEM:");
        Endereco origem = selecionarEndereco(scanner);
        
        System.out.println("\nSelecione o ENDEREÇO DE DESTINO:");
        Endereco destino = selecionarEndereco(scanner);
        
        try {
            Rota.adicionarRota(codVeiculo, codCarga, origem, destino);
            System.out.println("\nRota criada com sucesso!");
            
            // Atualizar status dos recursos
            Veiculo veiculo = Veiculo.buscarPorCodigo(codVeiculo);
            veiculo.atualizarStatus(StatusVeiculo.RESERVADO);
            
            Carga carga = Carga.buscarCarga(codCarga);
            carga.atualizarStatus(StatusCarga.EM_TRANSPORTE);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static Endereco selecionarEndereco(Scanner scanner) {
        List<Endereco> enderecosAtivos = Endereco.listarAtivos();
        enderecosAtivos.forEach(e -> {
            System.out.println(e.getCodigo() + " - " + 
                e.getLogradouro() + ", " + 
                e.getCidade().getNome() + "/" + 
                e.getEstado().getNome());
        });
        
        System.out.print("Selecione o endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        return Endereco.buscarEndereco(codEndereco);
    }

    private static void adicionarParadaARota(Scanner scanner) {
        System.out.println("\n--- ADICIONAR PARADA ---");
        listarRotasAtivas();
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nSelecione o endereço da parada:");
        Endereco enderecoParada = selecionarEndereco(scanner);
        
        System.out.println("\nInforme a data/hora prevista:");
        Calendar dataHoraPrevista = lerDataHora(scanner);
        
        try {
            Parada novaParada = Parada.adicionarParada(enderecoParada, dataHoraPrevista);
            Rota.adicionarParada(codRota, novaParada);
            System.out.println("Parada adicionada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static Calendar lerDataHora(Scanner scanner) {
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
        scanner.nextLine();
        
        Calendar dataHora = Calendar.getInstance();
        dataHora.set(ano, mes, dia, hora, minuto);
        return dataHora;
    }

    private static void iniciarRota(Scanner scanner) {
        System.out.println("\n--- INICIAR ROTA ---");
        List<Rota> rotasPlanejadas = Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.PLANEJADA)
            .toList();
        
        if (rotasPlanejadas.isEmpty()) {
            System.out.println("Nenhuma rota planejada disponível.");
            return;
        }
        
        rotasPlanejadas.forEach(r -> {
            System.out.println(r.getCodigo() + " - " + 
                r.getOrigem().getCidade().getNome() + " → " + 
                r.getDestino().getCidade().getNome());
        });
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Registrando data/hora de partida...");
        Calendar dataPartida = Calendar.getInstance();
        
        try {
            Rota.iniciarRota(codRota, dataPartida);
            System.out.println("Rota iniciada com sucesso em " + formatarData(dataPartida));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

   private static void finalizarRota(Scanner scanner) {
        System.out.println("\n--- FINALIZAR ROTA ---");
        
        List<Rota> rotasEmAndamento = Rota.listarAtivas().stream()
            .filter(r -> r.getStatus() == StatusRota.EM_ANDAMENTO)
            .toList();
        
        if (rotasEmAndamento.isEmpty()) {
            System.out.println("Nenhuma rota em andamento.");
            return;
        }
        
        rotasEmAndamento.forEach(r -> {
            System.out.println(r.getCodigo() + " - " + 
                r.getOrigem().getCidade().getNome() + " → " + 
                r.getDestino().getCidade().getNome());
        });
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Registrando data/hora de chegada...");
        Calendar dataChegada = Calendar.getInstance();
        
        try {
            Rota rota = Rota.buscarRota(codRota);
            Rota.finalizarRota(codRota, dataChegada);
            
            // Liberar recursos
            Veiculo veiculo = Veiculo.buscarPorCodigo(rota.getCodVeiculo());
            veiculo.atualizarStatus(StatusVeiculo.DISPONIVEL);
            veiculo.atualizarQuilometragem(veiculo.getKmRodados() + 100); // Exemplo: adiciona 100km
            
            Carga carga = Carga.buscarCarga(rota.getCodCarga());
            carga.atualizarStatus(StatusCarga.ENTREGUE);
            
            System.out.println("Rota finalizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cancelarRota(Scanner scanner) {
        System.out.println("\n--- CANCELAR ROTA ---");
        listarRotasAtivas();
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Rota rota = Rota.buscarRota(codRota);
            Rota.cancelarRota(codRota);
            
            // Liberar recursos
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
    }

    private static void visualizarDetalhesRota(Scanner scanner) {
        System.out.println("\n--- DETALHES DA ROTA ---");
        listarRotasAtivas();
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
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
    }

    private static String formatarData(Calendar data) {
        if (data == null) return "Não definida";
        return String.format("%02d/%02d/%d %02d:%02d",
            data.get(Calendar.DAY_OF_MONTH),
            data.get(Calendar.MONTH) + 1,
            data.get(Calendar.YEAR),
            data.get(Calendar.HOUR_OF_DAY),
            data.get(Calendar.MINUTE));
    }

    private static void menuMotoristas(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR MOTORISTAS ---");
            System.out.println("1. Listar motoristas ativos");
            System.out.println("2. Cadastrar novo motorista");
            System.out.println("3. Atualizar motorista");
            System.out.println("4. Desativar motorista");
            System.out.println("5. Listar por status");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    System.out.println("\nMotoristas ativos:");
                    Motorista.listarAtivos().forEach(System.out::println);
                    break;
                case 2:
                    cadastrarMotorista(scanner);
                    break;
                case 3:
                    atualizarMotorista(scanner);
                    break;
                case 4:
                    desativarMotorista(scanner);
                    break;
                case 5:
                    listarMotoristasPorStatus(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarMotorista(Scanner scanner) {
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
    }

    private static void atualizarMotorista(Scanner scanner) {
        System.out.println("\n--- ATUALIZAR MOTORISTA ---");
        System.out.println("Motoristas ativos:");
        Motorista.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do motorista: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.println("Status disponíveis:");
        for (StatusMotorista status : StatusMotorista.values()) {
            System.out.println(status.ordinal() + " - " + status.getDescricao());
        }
        System.out.print("Novo status: ");
        int statusOpcao = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Motorista motorista = Motorista.buscarPorCodigo(codigo);
            motorista.atualizarTelefone(telefone);
            motorista.atualizarStatus(StatusMotorista.values()[statusOpcao]);
            System.out.println("Motorista atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void desativarMotorista(Scanner scanner) {
        System.out.println("\n--- DESATIVAR MOTORISTA ---");
        System.out.println("Motoristas ativos:");
        Motorista.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do motorista: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Motorista.desativarMotorista(codigo);
            System.out.println("Motorista desativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarMotoristasPorStatus(Scanner scanner) {
        System.out.println("\n--- LISTAR POR STATUS ---");
        System.out.println("Status disponíveis:");
        for (StatusMotorista status : StatusMotorista.values()) {
            System.out.println(status.ordinal() + " - " + status.getDescricao());
        }
        System.out.print("Selecione o status: ");
        int statusOpcao = scanner.nextInt();
        scanner.nextLine();
        
        List<Motorista> motoristas = Motorista.listarPorStatus(StatusMotorista.values()[statusOpcao]);
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista encontrado com este status.");
        } else {
            motoristas.forEach(System.out::println);
        }
    }

    private static void menuVeiculos(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR VEÍCULOS ---");
            System.out.println("1. Listar veículos ativos");
            System.out.println("2. Cadastrar novo veículo");
            System.out.println("3. Registrar manutenção");
            System.out.println("4. Atualizar quilometragem");
            System.out.println("5. Desativar veículo");
            System.out.println("6. Listar por status");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    System.out.println("\nVeículos ativos:");
                    Veiculo.listarAtivos().forEach(System.out::println);
                    break;
                case 2:
                    cadastrarVeiculo(scanner);
                    break;
                case 3:
                    registrarManutencao(scanner);
                    break;
                case 4:
                    atualizarQuilometragem(scanner);
                    break;
                case 5:
                    desativarVeiculo(scanner);
                    break;
                case 6:
                    listarVeiculosPorStatus(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarVeiculo(Scanner scanner) {
        System.out.println("\n--- CADASTRAR VEÍCULO ---");
        System.out.println("Tipos disponíveis:");
        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            System.out.println(tipo.ordinal() + " - " + tipo.getDescricao());
        }
        System.out.print("Selecione o tipo: ");
        int tipoOpcao = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Peso máximo transportável (kg): ");
        float pesoMaximo = scanner.nextFloat();
        System.out.print("Volume máximo transportável (m³): ");
        float volumeMaximo = scanner.nextFloat();
        System.out.print("Ano de fabricação: ");
        int anoFabricacao = scanner.nextInt();
        scanner.nextLine();
        
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
    }

    private static void registrarManutencao(Scanner scanner) {
        System.out.println("\n--- REGISTRAR MANUTENÇÃO ---");
        System.out.println("Veículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codigo = scanner.nextInt();
        System.out.print("Quilometragem atual: ");
        float kmAtual = scanner.nextFloat();
        scanner.nextLine();
        
        try {
            Veiculo veiculo = Veiculo.buscarPorCodigo(codigo);
            veiculo.registrarManutencao(kmAtual);
            System.out.println("Manutenção registrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void atualizarQuilometragem(Scanner scanner) {
        System.out.println("\n--- ATUALIZAR QUILOMETRAGEM ---");
        System.out.println("Veículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codigo = scanner.nextInt();
        System.out.print("Nova quilometragem: ");
        float kmAtual = scanner.nextFloat();
        scanner.nextLine();
        
        try {
            Veiculo veiculo = Veiculo.buscarPorCodigo(codigo);
            veiculo.atualizarQuilometragem(kmAtual);
            System.out.println("Quilometragem atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void desativarVeiculo(Scanner scanner) {
        System.out.println("\n--- DESATIVAR VEÍCULO ---");
        System.out.println("Veículos ativos:");
        Veiculo.listarAtivos().forEach(System.out::println);
        
        System.out.print("Selecione o código do veículo: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Veiculo.desativarVeiculo(codigo);
            System.out.println("Veículo desativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarVeiculosPorStatus(Scanner scanner) {
        System.out.println("\n--- LISTAR POR STATUS ---");
        System.out.println("Status disponíveis:");
        for (StatusVeiculo status : StatusVeiculo.values()) {
            System.out.println(status.ordinal() + " - " + status.getDescricao());
        }
        System.out.print("Selecione o status: ");
        int statusOpcao = scanner.nextInt();
        scanner.nextLine();
        
        List<Veiculo> veiculos = Veiculo.listarPorStatus(StatusVeiculo.values()[statusOpcao]);
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo encontrado com este status.");
        } else {
            veiculos.forEach(System.out::println);
        }
    }

    private static void menuCargas(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR CARGAS ---");
            System.out.println("1. Listar cargas ativas");
            System.out.println("2. Cadastrar nova carga");
            System.out.println("3. Atualizar status da carga");
            System.out.println("4. Desativar carga");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    System.out.println("\nCargas ativas:");
                    Carga.listarAtivas().forEach(System.out::println);
                    break;
                case 2:
                    cadastrarCarga(scanner);
                    break;
                case 3:
                    atualizarStatusCarga(scanner);
                    break;
                case 4:
                    desativarCarga(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarCarga(Scanner scanner) {
        System.out.println("\n--- CADASTRAR CARGA ---");
        System.out.println(TipoCarga.listarOpcoes());
        System.out.print("Selecione o tipo de carga: ");
        int tipoOpcao = scanner.nextInt();
        TipoCarga tipoSelecionado = TipoCarga.values()[tipoOpcao];
        scanner.nextLine();
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Peso (kg): ");
        float peso = scanner.nextFloat();
        System.out.print("Volume (m³): ");
        float volume = scanner.nextFloat();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        
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
    }

    private static void atualizarStatusCarga(Scanner scanner) {
        System.out.println("\n--- ATUALIZAR STATUS ---");
        System.out.println("Cargas ativas:");
        Carga.listarAtivas().forEach(System.out::println);
        
        System.out.print("Selecione o código da carga: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Status disponíveis:");
        for (StatusCarga status : StatusCarga.values()) {
            System.out.println(status.ordinal() + " - " + status);
        }
        System.out.print("Novo status: ");
        int statusOpcao = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Carga carga = Carga.buscarCarga(codigo);
            carga.atualizarStatus(StatusCarga.values()[statusOpcao]);
            System.out.println("Status atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void desativarCarga(Scanner scanner) {
        System.out.println("\n--- DESATIVAR CARGA ---");
        System.out.println("Cargas ativas:");
        Carga.listarAtivas().forEach(System.out::println);
        
        System.out.print("Selecione o código da carga: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Carga.desativarCarga(codigo);
            System.out.println("Carga desativada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}