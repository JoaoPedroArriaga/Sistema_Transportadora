// Representa uma rota de transporte dentro do sistema da transportadora.
// Uma rota possui veículo, carga, origem, destino, paradas intermediárias, datas de partida e chegada, e um status que indica seu estado atual.
// Esta classe mantém uma lista estática com todas as rotas criadas.
// Oferecendo operações para gerenciar as rotas, como adicionar paradas, iniciar, finalizar e cancelar rotas.

package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import com.mycompany.sistema_transportadora.model.enums.StatusRota;
import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import com.mycompany.sistema_transportadora.utils.DateValidator;
import com.mycompany.sistema_transportadora.utils.TextFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Rota extends Entidade {
    private static final List<Rota> rotas = new ArrayList<>(); // Lista estática contendo todas as rotas criadas.
    private final int codVeiculo; // Código do veículo associado à rota.
    private final int codCarga; // Código da carga associada à rota.
    private final Endereco origem; // Endereço de origem da rota.
    private final Endereco destino; // Endereço de destino da rota.
    private final List<Parada> paradas; // Lista de paradas intermediárias da rota.
    private Calendar dataPartida; // Data e hora da partida da rota.
    private Calendar dataChegada; // Data e hora da chegada da rota.
    private StatusRota status; // Status atual da rota.

    private Rota(int codigo, int codVeiculo, int codCarga, Endereco origem, Endereco destino) { // Construtor privado para criar uma rota.
        super(codigo);
        this.codVeiculo = codVeiculo;
        this.codCarga = codCarga;
        this.origem = origem;
        this.destino = destino;
        this.paradas = new ArrayList<>();
        this.status = StatusRota.PLANEJADA;
    }

    public int getCodVeiculo() { // Retorna o código do veículo associado à rota.
        return codVeiculo;
    }

    public int getCodCarga() { // Retorna o código da carga associada à rota.
        return codCarga;
    }

    public Endereco getOrigem() { // Retorna o endereço de origem da rota.
        return origem;
    }

    public Endereco getDestino() { // Retorna o endereço de destino da rota.
        return destino;
    }

    public List<Parada> getParadas() { // Retorna uma lista imutável com as paradas da rota.
        return Collections.unmodifiableList(paradas);
    }

    public Calendar getDataPartida() { // Retorna a data e hora da partida da rota.
        return dataPartida;
    }

    public Calendar getDataChegada() { // Retorna a data e hora da chegada da rota.
        return dataChegada;
    }

    public StatusRota getStatus() { // Retorna o status atual da rota.
        return status;
    }

    //Setters

    public void setDataPartida(Calendar dataPartida) { // Define a data/hora da partida da rota, atualiza o status para EM_ANDAMENTO caso a data seja não nula.
        this.dataPartida = dataPartida;                
        if (dataPartida != null) {
            this.status = StatusRota.EM_ANDAMENTO;
        }
    }
    
    public void setDataChegada(Calendar dataChegada) { // Define a data/hora da chegada da rota, atualiza o status para CONCLUIDA e atualiza status do veículo e carga.
        this.dataChegada = dataChegada;
        if (dataChegada != null) {
            this.status = StatusRota.CONCLUIDA;
            
            // Atualizar veículo e carga quando a rota é concluída
            Veiculo veiculo = Veiculo.buscarPorCodigo(this.codVeiculo);
            veiculo.atualizarStatus(StatusVeiculo.DISPONIVEL);
            veiculo.atualizarQuilometragem(veiculo.getKmRodados());
            
            Carga carga = Carga.buscarCarga(this.codCarga);
            carga.atualizarStatus(StatusCarga.ENTREGUE);
        }
    }

    @Override
    public boolean isAtivo() { // Indica se a rota está ativa, ou seja, não está cancelada.
        return status != StatusRota.CANCELADA;
    }

    public static void adicionarRota(int codVeiculo, int codCarga, Endereco origem, Endereco destino) { // Adiciona uma nova rota ao sistema com os dados fornecidos.
        validarVeiculo(codVeiculo);
        validarCarga(codCarga);
        validarEnderecos(origem, destino);
        
        rotas.add(new Rota(rotas.size() + 1, codVeiculo, codCarga, origem, destino));
    }

    public static void adicionarParada(int codRota, Parada parada) { // Adiciona uma parada a uma rota planejada.
        Rota rota = buscarRota(codRota);
        
        if (!rota.podeAdicionarParadas()) {
            throw new IllegalStateException("Só é possível adicionar paradas em rotas planejadas");
        }
        
        rota.paradas.add(parada);
    }

    public static void iniciarRota(int codRota, Calendar dataPartida) { // Inicia uma rota marcada como planejada, define a data de partida e atualiza status para EM_ANDAMENTO.
        Rota rota = buscarRota(codRota);
        
        if (rota.status != StatusRota.PLANEJADA) {
            throw new IllegalStateException("Só é possível iniciar rotas planejadas");
        }
        
        DateValidator.validarDataNaoNula(dataPartida, "Data de partida não pode ser nula");
        rota.dataPartida = dataPartida;
        rota.status = StatusRota.EM_ANDAMENTO;
    }

    public static void finalizarRota(int codRota, Calendar dataChegada) { // Finaliza uma rota em andamento.
        Rota rota = buscarRota(codRota);
        
        if (rota.status != StatusRota.EM_ANDAMENTO) {
            throw new IllegalStateException("Só é possível finalizar rotas em andamento");
        }
        
        rota.dataChegada = dataChegada;
        rota.status = StatusRota.CONCLUIDA;
    }

    public static void cancelarRota(int codRota) { // Cancela uma rota se não estiver concluída.
        Rota rota = buscarRota(codRota);
        if(rota.status != StatusRota.CONCLUIDA){
            if (rota.status == StatusRota.CANCELADA) {
                throw new IllegalStateException("Rota já está cancelada");
            }
            rota.status = StatusRota.CANCELADA;
        }else{
            throw new IllegalStateException("Não é possival cancelar rotas concluídas");
        }
    }

    public void atualizarStatus(StatusRota novoStatus) { // Atualiza o status da rota e libera recursos se cancelada.
        this.status = novoStatus;

        if (novoStatus == StatusRota.CANCELADA && this.status != StatusRota.CONCLUIDA) {
            // Liberar recursos se a rota for cancelada
            Veiculo veiculo = Veiculo.buscarPorCodigo(this.codVeiculo);
            veiculo.atualizarStatus(StatusVeiculo.DISPONIVEL);
            
            Carga carga = Carga.buscarCarga(this.codCarga);
            carga.atualizarStatus(StatusCarga.ARMAZENADA);
        }
    }

    private static void validarVeiculo(int codVeiculo) { // Valida se o código do veículo é válido (maior ou igual a 1).
        if (codVeiculo < 1) {
            throw new IllegalArgumentException("Código de veículo inválido");
        }
    }

    private static void validarCarga(int codCarga) { // Valida se o código da carga é válido (maior ou igual a 1)
        if (codCarga < 1) {
            throw new IllegalArgumentException("Código de carga inválido");
        }
    }

    // Valida os endereços de origem e destino.
    // Não podem ser nulos.
    // Devem estar ativos.
    // não podem ser iguais.
    
    private static void validarEnderecos(Endereco origem, Endereco destino) { 
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

    public static Rota buscarRota(int codigo) { // Busca uma rota pelo código informado
        return buscarPorCodigo(codigo, rotas);
    }

    public static List<Rota> listarAtivas() { // Retorna uma lista imutável com todas as rotas que estão ativas (não canceladas).
        return rotas.stream()
            .filter(Rota::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static List<Rota> listarPorStatus(StatusRota status) { // Retorna uma lista imutável com as rotas que possuem o status especificado.
        return rotas.stream()
            .filter(r -> r.getStatus() == status)
            .collect(Collectors.toUnmodifiableList());
    }

    public boolean podeAdicionarParadas() { // Verifica se é possível adicionar paradas na rota (apenas se estiver planejada).
        return status == StatusRota.PLANEJADA;
    }

    public String resumo() { // Retorna um resumo textual da rota com código, cidades de origem/destino e status.
        return String.format("[%d] %s → %s - %s",
            getCodigo(),
            origem.getCidade().getNome(),
            destino.getCidade().getNome(),
            status);
    }

   // Retorna uma representação completa da rota, com origem, destino, status,
   // datas formatadas e se está ativa ou inativa. 
    @Override
    public String toString() {
        return String.format("Rota [%d] - %s → %s | Status: %s | Partida: %s | Chegada: %s | %s",
            getCodigo(),
            TextFormatter.formatarEnderecoCompleto(
                origem.getLogradouro(),
                origem.getCidade().getNome(),
                origem.getEstado().getNome()
            ),
            TextFormatter.formatarEnderecoCompleto(
                destino.getLogradouro(),
                destino.getCidade().getNome(),
                destino.getEstado().getNome()
            ),
            status,
            DateUtils.formatDateTime(dataPartida),
            DateUtils.formatDateTime(dataChegada),
            isAtivo() ? "Ativa" : "Inativa");
    }
}
