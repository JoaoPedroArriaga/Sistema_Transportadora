package com.mycompany.sistema_transportadora;

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
            System.out.println("4. Gerenciar Paradas");
            System.out.println("5. Gerenciar Rotas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    MenuEstados(scanner);
                    break;
                case 2:
                    MenuCidades(scanner);
                    break;
                case 3:
                    MenuEnderecos(scanner);
                    break;
                case 4:
                    MenuParada(scanner);
                    break;
                case 5:
                    MenuRotas(scanner);
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

    private static void MenuEstados(Scanner scanner) {
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

    private static void MenuCidades(Scanner scanner) {
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

    private static void MenuEnderecos(Scanner scanner) {
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
            Endereco.AdicionarEndereco(logradouro, estado, cidade);
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
            endereco.AtualizarEndereco(codEndereco, logradouro, estado, cidade);
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
            endereco.DesativarEndereco(codEndereco);
            System.out.println("Endereço excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void MenuParada(Scanner scanner) {
    int opcao;
    do {
        System.out.println("\n--- MENU PARADA ---");
        System.out.println("1. Adicionar Parada");
        System.out.println("2. Registrar Chegada na Parada");
        System.out.println("3. Listar Paradas Ativas");
        System.out.println("4. Desativar Parada");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        
        opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        
        switch (opcao) {
            case 1:
                AdicionarParadaUI(scanner);
                break;
            case 2:
                RegistrarChegadaUI(scanner);
                break;
            case 3:
                ListarParadasUI();
                break;
            case 4:
                DesativarParadaUI(scanner);
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
        }
    } while (opcao != 0);
}

    private static void AdicionarParadaUI(Scanner scanner) {
        System.out.println("\n--- ADICIONAR PARADA ---");
        
        // Listar endereços ativos
        System.out.println("Endereços disponíveis:");
        Endereco.ListarAtivos().forEach(e -> System.out.println(e.getCodEndereco() + " - " + e.getLogradouro()));
        
        System.out.print("Selecione o código do endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Informe a data e hora prevista:");
        System.out.print("Dia (1-31): ");
        int dia = scanner.nextInt();
        System.out.print("Mês (1-12): ");
        int mes = scanner.nextInt() - 1; // Calendar.MONTH é 0-based
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
            Endereco endereco = Endereco.BuscarPorCodigo(codEndereco);
            Parada.AdicionarParada(endereco, dataHoraPrevista);
            System.out.println("Parada adicionada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void RegistrarChegadaUI(Scanner scanner) {
        System.out.println("\n--- REGISTRAR CHEGADA ---");
        
        System.out.println("Paradas ativas:");
        Parada.listarAtivas().forEach(p -> System.out.println(p.getCodParada() + " - " + p.getLocal().getLogradouro()));
        
        System.out.print("Selecione o código da parada: ");
        int codParada = scanner.nextInt();
        scanner.nextLine();
        
        Calendar dataHoraReal = Calendar.getInstance();
        System.out.println("Data/hora real registrada: " + 
            String.format("%02d/%02d/%04d %02d:%02d",
                dataHoraReal.get(Calendar.DAY_OF_MONTH),
                dataHoraReal.get(Calendar.MONTH) + 1,
                dataHoraReal.get(Calendar.YEAR),
                dataHoraReal.get(Calendar.HOUR_OF_DAY),
                dataHoraReal.get(Calendar.MINUTE)));
        
        try {
            Parada.RegistrarChegada(codParada, dataHoraReal);
            System.out.println("Chegada registrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void ListarParadasUI() {
        System.out.println("\n--- PARADAS ATIVAS ---");
        List<Parada> ativas = Parada.listarAtivas();
        if (ativas.isEmpty()) {
            System.out.println("Nenhuma parada cadastrada.");
        } else {
            ativas.forEach(System.out::println);
        }
    }

    private static void DesativarParadaUI(Scanner scanner) {
        System.out.println("\n--- DESATIVAR PARADA ---");
        
        System.out.println("Paradas ativas:");
        Parada.listarAtivas().forEach(p -> System.out.println(p.getCodParada() + " - " + p.getLocal().getLogradouro()));
        
        System.out.print("Selecione o código da parada a desativar: ");
        int codParada = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Parada.DesativarParada(codParada);
            System.out.println("Parada desativada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
        private static void MenuRotas(Scanner scanner) {
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
                    registrarChegadaParada(scanner);
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
        List<Rota> rotasAtivas = Rota.ListarAtivas();
        if (rotasAtivas.isEmpty()) {
            System.out.println("Nenhuma rota cadastrada.");
        } else {
            rotasAtivas.forEach(rota -> {
                System.out.println("[" + rota.getCodRota() + "] " + 
                    rota.getOrigem().getCidade().getNome() + " → " + 
                    rota.getDestino().getCidade().getNome() + 
                    " - " + rota.getStatus_Rota());
            });
        }
    }

    private static void criarNovaRota(Scanner scanner) {
        System.out.println("\n--- NOVA ROTA ---");
        
        // Listar veículos disponíveis (simulação)
        System.out.println("Veículos disponíveis:");
        System.out.println("1 - Caminhão BA-1234");
        System.out.println("2 - Van SP-5678");
        System.out.print("Selecione o veículo: ");
        int codVeiculo = scanner.nextInt();
        
        // Listar cargas disponíveis (simulação)
        System.out.println("\nCargas disponíveis:");
        System.out.println("1 - Eletrônicos (500kg)");
        System.out.println("2 - Móveis (1200kg)");
        System.out.print("Selecione a carga: ");
        int codCarga = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nSelecione o ENDEREÇO DE ORIGEM:");
        Endereco origem = selecionarEndereco(scanner);
        
        System.out.println("\nSelecione o ENDEREÇO DE DESTINO:");
        Endereco destino = selecionarEndereco(scanner);
        
        try {
            Rota.AdicionarRota(codVeiculo, codCarga, origem, destino);
            System.out.println("\nRota criada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static Endereco selecionarEndereco(Scanner scanner) {
        List<Endereco> enderecosAtivos = Endereco.ListarAtivos();
        enderecosAtivos.forEach(e -> {
            System.out.println(e.getCodEndereco() + " - " + 
                e.getLogradouro() + ", " + 
                e.getCidade().getNome() + "/" + 
                e.getEstado().getNome());
        });
        
        System.out.print("Selecione o endereço: ");
        int codEndereco = scanner.nextInt();
        scanner.nextLine();
        
        return Endereco.BuscarPorCodigo(codEndereco);
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
            // Cria uma nova parada (que automaticamente é adicionada à lista estática)
            Parada.AdicionarParada(enderecoParada, dataHoraPrevista);
            
            // Obtém a última parada criada
            Parada novaParada = Parada.buscarPorCodigo(Parada.listarAtivas().size());
            
            // Adiciona à rota selecionada
            Rota.AdicionarParada(codRota, novaParada);
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
        List<Rota> rotasPlanejadas = Rota.ListarAtivas().stream()
            .filter(r -> r.getStatus_Rota() == Status_Rota.PLANEJADA)
            .toList();
        
        if (rotasPlanejadas.isEmpty()) {
            System.out.println("Nenhuma rota planejada disponível.");
            return;
        }
        
        rotasPlanejadas.forEach(r -> {
            System.out.println(r.getCodRota() + " - " + 
                r.getOrigem().getCidade().getNome() + " → " + 
                r.getDestino().getCidade().getNome());
        });
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Registrando data/hora de partida...");
        Calendar dataPartida = Calendar.getInstance();
        
        try {
            Rota.IniciarRota(codRota, dataPartida);
            System.out.println("Rota iniciada com sucesso em " + 
                dataPartida.get(Calendar.DAY_OF_MONTH) + "/" +
                (dataPartida.get(Calendar.MONTH) + 1) + "/" +
                dataPartida.get(Calendar.YEAR) + " " +
                dataPartida.get(Calendar.HOUR_OF_DAY) + ":" +
                dataPartida.get(Calendar.MINUTE));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void registrarChegadaParada(Scanner scanner) {
        System.out.println("\n--- REGISTRAR CHEGADA ---");
        
        // Listar rotas em andamento
        List<Rota> rotasEmAndamento = Rota.ListarAtivas().stream()
            .filter(r -> r.getStatus_Rota() == Status_Rota.EM_ANDAMENTO)
            .toList();
        
        if (rotasEmAndamento.isEmpty()) {
            System.out.println("Nenhuma rota em andamento.");
            return;
        }
        
        System.out.println("Rotas em andamento:");
        rotasEmAndamento.forEach(r -> {
            System.out.println("Rota " + r.getCodRota() + ": " + 
                r.getOrigem().getCidade().getNome() + " → " + 
                r.getDestino().getCidade().getNome());
            
            System.out.println("Paradas:");
            r.getParadas().forEach(p -> {
                System.out.println("  " + p.getCodParada() + " - " + 
                    p.getLocal().getCidade().getNome() + 
                    (p.getDataHoraReal() == null ? " (pendente)" : " (concluída)"));
            });
        });
        
        System.out.print("Informe o código da parada: ");
        int codParada = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Registrando data/hora de chegada...");
        Calendar dataChegada = Calendar.getInstance();
        
        try {
            Parada.RegistrarChegada(codParada, dataChegada);
            System.out.println("Chegada registrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void finalizarRota(Scanner scanner) {
        System.out.println("\n--- FINALIZAR ROTA ---");
        
        // Listar rotas em andamento
        List<Rota> rotasEmAndamento = Rota.ListarAtivas().stream()
            .filter(r -> r.getStatus_Rota() == Status_Rota.EM_ANDAMENTO)
            .toList();
        
        if (rotasEmAndamento.isEmpty()) {
            System.out.println("Nenhuma rota em andamento.");
            return;
        }
        
        rotasEmAndamento.forEach(r -> {
            System.out.println(r.getCodRota() + " - " + 
                r.getOrigem().getCidade().getNome() + " → " + 
                r.getDestino().getCidade().getNome() + 
                " - Partida: " + formatarData(r.getDataPartida()));
        });
        
        System.out.print("Selecione o código da rota: ");
        int codRota = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Registrando data/hora de chegada...");
        Calendar dataChegada = Calendar.getInstance();
        
        try {
            Rota.FinalizarRota(codRota, dataChegada);
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
            Rota.CancelarRota(codRota);
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
            Rota rota = Rota.BuscarPorCodigo(codRota);
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
}