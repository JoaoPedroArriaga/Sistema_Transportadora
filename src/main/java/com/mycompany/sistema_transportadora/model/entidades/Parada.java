// Representa uma parada dentro do sistema da transportadora.
// Uma parada está associada a um endereço, possui uma data/hora prevista para chegada, e pode registrar a data/hora real da chegada.
//  A classe mantém uma lista estática com todas as paradas criadas, possibilitando operações como adicionar, buscar, listar ativas e desativar paradas.


package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.DateUtils;
import com.mycompany.sistema_transportadora.utils.DateValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Parada extends Entidade {
    private static final List<Parada> paradas = new ArrayList<>(); // Lista estática que mantém todas as paradas criadas.
    private Endereco local; // Endereço da parada.
    private Calendar dataHoraPrevista; // Data e hora prevista para a parada.
    private Calendar dataHoraReal; // Data e hora real da chegada na parada, pode ser nulo até ser registrada.

    private Parada(int codigo, Endereco local, Calendar dataHoraPrevista) { // Construtor privado para criar uma parada.
        super(codigo);
        this.local = local;
        this.dataHoraPrevista = dataHoraPrevista;
        this.dataHoraReal = null;
    }

    public Endereco getLocal() { // Retorna o endereço  da parada.
        return local;
    }

    public Calendar getDataHoraPrevista() { // Retorna a data e hora prevista para a parada.
        return dataHoraPrevista;
    }

    public Calendar getDataHoraReal() { // Retorna a data e hora real da chegada na parada.
        return dataHoraReal;            // Pode ser nulo se a chegada ainda não  foi registrada.
    }

    public void setDataHoraReal(Calendar dataHoraReal) {
        DateValidator.validarDataNaoNula(dataHoraReal, "Data/hora real não pode ser nula"); // Valida e registra a data e hora real da chegada na parada.
        this.dataHoraReal = dataHoraReal;
    }

    public static Parada adicionarParada(Endereco local, Calendar dataHoraPrevista) { // Adiciona uma nova parada ao sistema.
        validarEndereco(local);
        DateValidator.validarDataNaoNula(dataHoraPrevista, "Data/hora prevista não pode ser nula");
        
        Parada novaParada = new Parada(paradas.size() + 1, local, dataHoraPrevista);
        paradas.add(novaParada);
        return novaParada;
    }

    public static void registrarChegada(int codigo, Calendar dataHoraReal) {
        DateValidator.validarDataNaoNula(dataHoraReal, "Data/hora real não pode ser nula"); // Registra a chegada em uma parada já existente.
        Parada parada = buscarParada(codigo);
        parada.setDataHoraReal(dataHoraReal);
    }

    private static void validarEndereco(Endereco local) { //  Valida se o endereço é válido e ativo.
        if (local == null || !local.isAtivo()) {
            throw new IllegalArgumentException("Endereço inválido ou inativo");
        }
    }

    public static Parada buscarParada(int codigo) { // Busca uma parada pelo seu código.
        validarCodigo(codigo, paradas.size());
        return paradas.get(codigo - 1);
    }

    public static List<Parada> listarAtivas() { // Lista todas as paradas ativas.
        return paradas.stream()
            .filter(Parada::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarParada(int codigo) { // Desativa uma parada, marcando seu status como inativo.
        validarCodigo(codigo, paradas.size());
        Parada parada = paradas.get(codigo - 1);
        
        if (!parada.isAtivo()) {
            throw new IllegalStateException("Parada já está desativada");
        }
        parada.desativar();
    }

    @Override
    public String toString() {
        return String.format("Parada [%d] - Local: %s | Prevista: %s | Real: %s | %s", // Representação textual da parada, incluindo dados do local, datas previstas e reais, e status.
            getCodigo(),
            local.toString(),
            DateUtils.formatDateTime(dataHoraPrevista),
            DateUtils.formatDateTime(dataHoraReal),
            isAtivo() ? "Ativa" : "Inativa");
    }
}
