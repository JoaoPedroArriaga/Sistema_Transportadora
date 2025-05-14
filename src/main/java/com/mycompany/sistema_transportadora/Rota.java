package com.mycompany.sistema_transportadora;

import java.util.*;

public final class Rota {
    private static final List<Rota> rotas = new ArrayList<>();
    
    private final int cod_rota;
    private final int cod_veiculo;
    private final int cod_carga;
    private final Endereco origem;
    private final Endereco destino;
    private final List<Parada> paradas;
    private Calendar data_partida;
    private Calendar data_chegada;
    private Status_Rota status;

    private Rota(int cod_rota, int cod_veiculo, int cod_carga, Endereco origem, Endereco destino) {
        this.cod_rota = cod_rota;
        this.cod_veiculo = cod_veiculo;
        this.cod_carga = cod_carga;
        this.origem = origem;
        this.destino = destino;
        this.paradas = new ArrayList<>();
        this.status = Status_Rota.PLANEJADA;
    }

    // Getters
    public int getCodRota() { 
        return cod_rota; 
    }

    public int getCodVeiculo() { 
        return cod_veiculo; 
    }

    public int getCodCarga() { 
        return cod_carga; 
    }

    public Endereco getOrigem() { 
        return origem; 
    }

    public Endereco getDestino() { 
        return destino; 
    }

    public List<Parada> getParadas() { 
        return Collections.unmodifiableList(paradas); 
    }

    public Calendar getDataPartida() { 
        return data_partida; 
    }

    public Calendar getDataChegada() { 
        return data_chegada; 
    }

    public Status_Rota getStatus_Rota() { 
        return status; 
    }

    public boolean isAtiva() { 
        return status != Status_Rota.CANCELADA; 
    }

    // Validações
    private static void ValidarVeiculo(int cod_veiculo) {
        // Implementação futura quando existir a classe Veículo
        if (cod_veiculo < 1) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
    }

    private static void ValidarCarga(int cod_carga) {
        // Implementação futura quando existir a classe Carga
        if (cod_carga < 1) {
            throw new IllegalArgumentException("Código de carga inválido");
        }
    }

    private static void ValidarEnderecos(Endereco origem, Endereco destino) {
        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Endereços não podem ser nulos");
        }
        if (!origem.isAtivo() || !destino.isAtivo()) {
            throw new IllegalArgumentException("Endereços devem estar ativos");
        }
        if (origem.equals(destino)) {
            throw new IllegalArgumentException("Origem e destino não podem ser iguais");
        }
    }

    private static void ValidarCodRota(int cod_rota) {
        if (cod_rota < 1 || cod_rota > rotas.size()) {
            throw new IllegalArgumentException("Código de rota inválido");
        }
    }

    private static void ValidarRotaAtiva(int cod_rota) {
        if (!rotas.get(cod_rota - 1).isAtiva()) {
            throw new IllegalArgumentException("Rota inativa");
        }
    }

    // Métodos CRUD
    public static void AdicionarRota(int cod_veiculo, int cod_carga, 
                                   Endereco origem, Endereco destino) {
        ValidarVeiculo(cod_veiculo);
        ValidarCarga(cod_carga);
        ValidarEnderecos(origem, destino);
        
        int novoCodigo = rotas.size() + 1;
        rotas.add(new Rota(novoCodigo, cod_veiculo, cod_carga, origem, destino));
    }

    public static void AdicionarParada(int cod_rota, Parada parada) {
        ValidarCodRota(cod_rota);
        ValidarRotaAtiva(cod_rota);
        
        Rota rota = rotas.get(cod_rota - 1);
        
        if (rota.status != Status_Rota.PLANEJADA) {
            throw new IllegalStateException("Só é possível adicionar paradas em rotas planejadas");
        }
        
        rota.paradas.add(parada);
    }

    public static void IniciarRota(int cod_rota, Calendar data_partida) {
        ValidarCodRota(cod_rota);
        ValidarRotaAtiva(cod_rota);
        
        Rota rota = rotas.get(cod_rota - 1);
        
        if (rota.status != Status_Rota.PLANEJADA) {
            throw new IllegalStateException("Só é possível iniciar rotas planejadas");
        }
        
        rota.data_partida = data_partida;
        rota.status = Status_Rota.EM_ANDAMENTO;
    }

    public static void FinalizarRota(int cod_rota, Calendar data_chegada) {
        ValidarCodRota(cod_rota);
        ValidarRotaAtiva(cod_rota);
        
        Rota rota = rotas.get(cod_rota - 1);
        
        if (rota.status != Status_Rota.EM_ANDAMENTO) {
            throw new IllegalStateException("Só é possível finalizar rotas em andamento");
        }
        
        rota.data_chegada = data_chegada;
        rota.status = Status_Rota.CONCLUIDA;
    }

    public static void CancelarRota(int cod_rota) {
        ValidarCodRota(cod_rota);
        ValidarRotaAtiva(cod_rota);
        
        Rota rota = rotas.get(cod_rota - 1);
        rota.status = Status_Rota.CANCELADA;
    }

    // Métodos de consulta
    public static Rota BuscarPorCodigo(int cod_rota) {
        ValidarCodRota(cod_rota);
        return rotas.get(cod_rota - 1);
    }

    public static List<Rota> ListarAtivas() {
        List<Rota> ativas = new ArrayList<>();
        for (Rota r : rotas) {
            if (r.isAtiva()) {
                ativas.add(r);
            }
        }
        return Collections.unmodifiableList(ativas);
    }

    // Método auxiliar para formatar data
    private String FormatarData(Calendar data) {
        if (data == null) return "Não definida";
        return String.format("%02d/%02d/%04d %02d:%02d", 
            data.get(Calendar.DAY_OF_MONTH),
            data.get(Calendar.MONTH) + 1,
            data.get(Calendar.YEAR),
            data.get(Calendar.HOUR_OF_DAY),
            data.get(Calendar.MINUTE));
    }

    @Override
    public String toString() {
        return String.format(
            "Rota [%d] - Veículo: %d | Carga: %d\n" +
            "Origem: %s, %s\n" +
            "Destino: %s, %s\n" +
            "Partida: %s | Chegada: %s\n" +
            "Status_Rota: %s | Paradas: %d",
            cod_rota, cod_veiculo, cod_carga,
            origem.getLogradouro(), origem.getCidade().getNome(),
            destino.getLogradouro(), destino.getCidade().getNome(),
            FormatarData(data_partida), FormatarData(data_chegada),
            status, paradas.size()
        );
    }
}