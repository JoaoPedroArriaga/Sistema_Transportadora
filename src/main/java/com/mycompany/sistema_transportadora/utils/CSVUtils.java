package com.mycompany.sistema_transportadora.utils;

import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.model.enums.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CSVUtils {
    private static final String CSV_SEPARATOR = ";";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    // Métodos genéricos ------------------------------------------------------

    private static void writeCSV(String filePath, List<String> headers, List<List<String>> rows) {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Cria diretórios se não existirem
        
        try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
            // Escrever cabeçalho
            writer.println(String.join(CSV_SEPARATOR, headers));
            
            // Escrever linhas
            for (List<String> row : rows) {
                String escapedRow = row.stream()
                    .map(field -> field == null ? "" : "\"" + field.replace("\"", "\"\"") + "\"")
                    .collect(Collectors.joining(CSV_SEPARATOR));
                writer.println(escapedRow);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever CSV: " + e.getMessage());
        }
    }

    private static List<List<String>> readCSV(String filePath) {
        List<List<String>> records = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.err.println("Arquivo não encontrado: " + filePath);
            return records;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> fields = parseCSVLine(line);
                records.add(fields);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler CSV: " + e.getMessage());
        }
        return records;
    }

    private static List<String> parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == CSV_SEPARATOR.charAt(0) && !inQuotes) {
                fields.add(field.toString().trim());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString().trim());
        return fields;
    }

    // Métodos para Estados ---------------------------------------------------

    public static void exportarEstados(String filePath) {
        List<List<String>> rows = Estado.listarAtivos().stream()
            .map(e -> Arrays.asList(
                String.valueOf(e.getCodigo()),
                e.getNome(),
                String.valueOf(e.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath, 
            Arrays.asList("CODIGO", "NOME", "ATIVO"), 
            rows
        );
    }

    public static void importarEstados(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1) // Pular cabeçalho
            .forEach(fields -> {
                try {
                    if (fields.size() >= 2) {
                        Estado.registrarEstado(fields.get(1));
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro importação estado: " + e.getMessage());
                }
            });
    }

    // Métodos para Cidades ---------------------------------------------------

    public static void exportarCidades(String filePath) {
        List<List<String>> rows = Cidade.listarAtivas().stream()
            .map(c -> Arrays.asList(
                String.valueOf(c.getCodigo()),
                String.valueOf(c.getCodEstado()),
                c.getNome(),
                String.valueOf(c.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "ESTADO", "NOME", "ATIVO"),
            rows
        );
    }

    public static void importarCidades(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 3) {
                        int codEstado = Integer.parseInt(fields.get(1));
                        String nome = fields.get(2);
                        Cidade.adicionarCidade(codEstado, nome);
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação cidade: " + e.getMessage());
                }
            });
    }

    // Métodos para Endereços -------------------------------------------------

    public static void exportarEnderecos(String filePath) {
        List<List<String>> rows = Endereco.listarAtivos().stream()
            .map(e -> Arrays.asList(
                String.valueOf(e.getCodigo()),
                e.getLogradouro(),
                String.valueOf(e.getEstado().getCodigo()),
                String.valueOf(e.getCidade().getCodigo()),
                String.valueOf(e.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "LOGRADOURO", "ESTADO", "CIDADE", "ATIVO"),
            rows
        );
    }

    public static void importarEnderecos(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 4) {
                        String logradouro = fields.get(1);
                        int codEstado = Integer.parseInt(fields.get(2));
                        int codCidade = Integer.parseInt(fields.get(3));
                        
                        Estado estado = Estado.buscarEstado(codEstado);
                        Cidade cidade = Cidade.buscarCidade(codCidade);
                        Endereco.adicionarEndereco(logradouro, estado, cidade);
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação endereço: " + e.getMessage());
                }
            });
    }

    // Métodos para Veículos --------------------------------------------------

    public static void exportarVeiculos(String filePath) {
        List<List<String>> rows = Veiculo.listarAtivos().stream()
            .map(v -> Arrays.asList(
                String.valueOf(v.getCodigo()),
                v.getTipo().name(),
                v.getPlaca(),
                String.valueOf(v.getCapacidadeCarga()),
                String.valueOf(v.getAnoFabricacao()),
                String.valueOf(v.getVolumeMaximoTransportavel()),
                String.valueOf(v.getKmRodados()),
                v.getStatus().name(),
                v.getDataUltimaManutencao() != null ? 
                    DATE_FORMAT.format(v.getDataUltimaManutencao().getTime()) : "",
                String.valueOf(v.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "TIPO", "PLACA", "PESO_MAX", "VOLUME_MAX", 
                         "ANO", "KM_RODADOS", "STATUS", "ULTIMA_MANUTENCAO", "ATIVO"),
            rows
        );
    }

    public static void importarVeiculos(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 6) {
                        TipoVeiculo tipo = TipoVeiculo.valueOf(fields.get(1));
                        String placa = fields.get(2);
                        float peso = Float.parseFloat(fields.get(3));
                        float volume = Float.parseFloat(fields.get(4));
                        int ano = Integer.parseInt(fields.get(5));
                        
                        Veiculo.cadastrar(tipo, placa, peso, volume, ano);
                        
                        // Atualiza dados adicionais se existirem
                        if (fields.size() >= 8 && !fields.get(7).isEmpty()) {
                            Veiculo veiculo = Veiculo.buscarPorCodigo(Veiculo.listarAtivos().size());
                            veiculo.atualizarStatus(StatusVeiculo.valueOf(fields.get(7)));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação veículo: " + e.getMessage());
                }
            });
    }

    // Métodos para Motoristas ------------------------------------------------

    public static void exportarMotoristas(String filePath) {
        List<List<String>> rows = Motorista.listarAtivos().stream()
            .map(m -> Arrays.asList(
                String.valueOf(m.getCodigo()),
                m.getNome(),
                m.getCpf(),
                m.getCnh(),
                m.getTelefone(),
                m.getStatus().name(),
                String.valueOf(m.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "NOME", "CPF", "CNH", "TELEFONE", "STATUS", "ATIVO"),
            rows
        );
    }

    public static void importarMotoristas(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 5) {
                        String nome = fields.get(1);
                        String cpf = fields.get(2);
                        String cnh = fields.get(3);
                        String telefone = fields.get(4);
                        
                        Motorista.cadastrar(nome, cpf, cnh, telefone);
                        
                        // Atualiza status se existir
                        if (fields.size() >= 6 && !fields.get(5).isEmpty()) {
                            Motorista motorista = Motorista.buscarPorCodigo(Motorista.listarAtivos().size());
                            motorista.atualizarStatus(StatusMotorista.valueOf(fields.get(5)));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação motorista: " + e.getMessage());
                }
            });
    }

    // Métodos para Cargas ----------------------------------------------------

    public static void exportarCargas(String filePath) {
        List<List<String>> rows = Carga.listarAtivas().stream()
            .map(c -> Arrays.asList(
                String.valueOf(c.getCodigo()),
                c.getTipo().name(),
                c.getDescricao(),
                String.valueOf(c.getPeso()),
                String.valueOf(c.getVolume()),
                String.valueOf(c.getQuantidade()),
                c.getStatus().name(),
                DATE_FORMAT.format(c.getDataCadastro().getTime()),
                String.valueOf(c.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "TIPO", "DESCRICAO", "PESO", "VOLUME", 
                         "QUANTIDADE", "STATUS", "DATA_CADASTRO", "ATIVO"),
            rows
        );
    }

    public static void importarCargas(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 6) {
                        TipoCarga tipo = TipoCarga.valueOf(fields.get(1));
                        String descricao = fields.get(2);
                        float peso = Float.parseFloat(fields.get(3));
                        float volume = Float.parseFloat(fields.get(4));
                        int quantidade = Integer.parseInt(fields.get(5));
                        
                        Carga.adicionarCarga(tipo, descricao, peso, volume, quantidade);
                        
                        // Atualiza status se existir
                        if (fields.size() >= 7 && !fields.get(6).isEmpty()) {
                            Carga carga = Carga.buscarCarga(Carga.listarAtivas().size());
                            carga.atualizarStatus(StatusCarga.valueOf(fields.get(6)));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação carga: " + e.getMessage());
                }
            });
    }

    // Métodos para Paradas ---------------------------------------------------

    public static void exportarParadas(String filePath) {
        List<List<String>> rows = Parada.listarAtivas().stream()
            .map(p -> Arrays.asList(
                String.valueOf(p.getCodigo()),
                String.valueOf(p.getLocal().getCodigo()),
                DATE_FORMAT.format(p.getDataHoraPrevista().getTime()),
                p.getDataHoraReal() != null ? 
                    DATE_FORMAT.format(p.getDataHoraReal().getTime()) : "",
                String.valueOf(p.isAtivo())
            ))
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "LOCAL", "DATA_PREVISTA", "DATA_REAL", "ATIVO"),
            rows
        );
    }

    public static void importarParadas(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 3) {
                        int codEndereco = Integer.parseInt(fields.get(1));
                        Endereco local = Endereco.buscarEndereco(codEndereco);
                        
                        Calendar dataPrevista = parseDate(fields.get(2));
                        
                        Parada parada = Parada.adicionarParada(local, dataPrevista);
                        
                        // Registra data real se existir
                        if (fields.size() >= 4 && !fields.get(3).isEmpty()) {
                            Calendar dataReal = parseDate(fields.get(3));
                            parada.setDataHoraReal(dataReal);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação parada: " + e.getMessage());
                }
            });
    }

    // Métodos para Rotas -----------------------------------------------------

    public static void exportarRotas(String filePath) {
        List<List<String>> rows = Rota.listarAtivas().stream()
            .map(r -> {
                String paradas = r.getParadas().stream()
                    .map(p -> String.valueOf(p.getCodigo()))
                    .collect(Collectors.joining(","));
                
                return Arrays.asList(
                    String.valueOf(r.getCodigo()),
                    String.valueOf(r.getCodVeiculo()),
                    String.valueOf(r.getCodCarga()),
                    String.valueOf(r.getOrigem().getCodigo()),
                    String.valueOf(r.getDestino().getCodigo()),
                    paradas,
                    r.getDataPartida() != null ? 
                        DATE_FORMAT.format(r.getDataPartida().getTime()) : "",
                    r.getDataChegada() != null ? 
                        DATE_FORMAT.format(r.getDataChegada().getTime()) : "",
                    r.getStatus().name(),
                    String.valueOf(r.isAtivo())
                );
            })
            .collect(Collectors.toList());

        writeCSV(filePath,
            Arrays.asList("CODIGO", "VEICULO", "CARGA", "ORIGEM", "DESTINO", 
                         "PARADAS", "PARTIDA", "CHEGADA", "STATUS", "ATIVO"),
            rows
        );
    }

    public static void importarRotas(String filePath) {
    List<List<String>> records = readCSV(filePath);
    if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 5) {
                        // Validar códigos antes de criar a rota
                        int codVeiculo = Integer.parseInt(fields.get(1));
                        int codCarga = Integer.parseInt(fields.get(2));
                        int codOrigem = Integer.parseInt(fields.get(3));
                        int codDestino = Integer.parseInt(fields.get(4));
                        
                        // Verificar existência dos recursos
                        Veiculo veiculo = Veiculo.buscarPorCodigo(codVeiculo);
                        Carga carga = Carga.buscarCarga(codCarga);
                        Endereco origem = Endereco.buscarEndereco(codOrigem);
                        Endereco destino = Endereco.buscarEndereco(codDestino);
                        
                        // Criar a rota
                        Rota.adicionarRota(codVeiculo, codCarga, origem, destino);
                        Rota rota = Rota.buscarRota(Rota.listarAtivas().size());
                        
                        // Adicionar paradas
                        if (fields.size() >= 6 && !fields.get(5).isEmpty()) {
                            Arrays.stream(fields.get(5).split(","))
                                .map(Integer::parseInt)
                                .forEach(codParada -> {
                                    Parada parada = Parada.buscarParada(codParada);
                                    if (parada != null) {
                                        rota.getParadas().add(parada);
                                    }
                                });
                        }
                        
                        // Atualizar datas e status
                        if (!fields.get(6).isEmpty()) {
                            rota.setDataPartida(parseDate(fields.get(6)));
                        }
                        if (!fields.get(7).isEmpty()) {
                            rota.setDataChegada(parseDate(fields.get(7)));
                        }
                        if (fields.size() >= 9 && !fields.get(8).isEmpty()) {
                            rota.atualizarStatus(StatusRota.valueOf(fields.get(8)));
                        }
                        
                        // Atualizar status do veículo e carga
                        if (rota.getStatus() == StatusRota.EM_ANDAMENTO) {
                            veiculo.atualizarStatus(StatusVeiculo.EM_ROTA);
                            carga.atualizarStatus(StatusCarga.EM_TRANSPORTE);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação rota: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        );
    }

    // Métodos para Manutenções -----------------------------------------------

    public static void exportarManutencoes(String filePath) {
        List<List<String>> rows = new ArrayList<>();
        int codigo = 1;
        
        for (Veiculo veiculo : Veiculo.listarAtivos()) {
            List<Manutencao> manutencoes = Manutencao.listarPorVeiculo(veiculo.getCodigo());
            for (Manutencao m : manutencoes) {
                rows.add(Arrays.asList(
                    String.valueOf(codigo++),
                    String.valueOf(m.getCodVeiculo()),
                    DATE_FORMAT.format(m.getData().getTime()),
                    m.getTipoServico(),
                    String.valueOf(m.getCusto()),
                    String.valueOf(m.isAtivo())
                ));
            }
        }

        writeCSV(filePath,
            Arrays.asList("CODIGO", "VEICULO", "DATA", "TIPO_SERVICO", "CUSTO", "ATIVO"),
            rows
        );
    }

    public static void importarManutencoes(String filePath) {
        List<List<String>> records = readCSV(filePath);
        if (records.isEmpty()) return;

        records.stream()
            .skip(1)
            .forEach(fields -> {
                try {
                    if (fields.size() >= 5) {
                        int codVeiculo = Integer.parseInt(fields.get(1));
                        Calendar data = parseDate(fields.get(2));
                        String tipoServico = fields.get(3);
                        float custo = Float.parseFloat(fields.get(4));
                        
                        Manutencao.registrarManutencao(codVeiculo, data, tipoServico, custo);
                    }
                } catch (Exception e) {
                    System.err.println("Erro importação manutenção: " + e.getMessage());
                }
            });
    }

    // Métodos para Exportação/Importação Completa ----------------------------

    public static void exportarTudo(String diretorio) {
        new File(diretorio).mkdirs();
        
        exportarEstados(diretorio + "/estados.csv");
        exportarCidades(diretorio + "/cidades.csv");
        exportarEnderecos(diretorio + "/enderecos.csv");
        exportarVeiculos(diretorio + "/veiculos.csv");
        exportarMotoristas(diretorio + "/motoristas.csv");
        exportarCargas(diretorio + "/cargas.csv");
        exportarParadas(diretorio + "/paradas.csv");
        exportarRotas(diretorio + "/rotas.csv");
        exportarManutencoes(diretorio + "/manutencoes.csv");
    }

    public static void importarTudo(String diretorio) {
        // Importar na ordem correta para respeitar dependências
        importarEstados(diretorio + "/estados.csv");
        importarCidades(diretorio + "/cidades.csv");
        importarEnderecos(diretorio + "/enderecos.csv");
        importarVeiculos(diretorio + "/veiculos.csv");
        importarMotoristas(diretorio + "/motoristas.csv");
        importarCargas(diretorio + "/cargas.csv");
        importarParadas(diretorio + "/paradas.csv");
        importarRotas(diretorio + "/rotas.csv");
        importarManutencoes(diretorio + "/manutencoes.csv");
    }

    // Métodos auxiliares -----------------------------------------------------

    private static Calendar parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DATE_FORMAT.parse(dateString));
            return calendar;
        } catch (Exception e) {
            System.err.println("Erro ao parsear data: " + dateString);
            return null;
        }
    }
}