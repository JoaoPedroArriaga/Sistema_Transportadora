package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import com.mycompany.sistema_transportadora.model.enums.TipoCarga;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Carga extends Entidade {
    private static final List<Carga> cargas = new ArrayList<>(); //Lista de todas as cargas cadastradas no sistema. 
    
    private final TipoCarga tipo;  //Tipo da carga (frágil, perecível, etc.) 
    private final String descricao; //Descrição da carga. 
    private final float peso;  //Peso da carga em quilogramas. 
    private final float volume;  //Volume da carga em metros cúbicos. 
    private final int quantidade; //Quantidade de itens da carga. 
    private StatusCarga status; //Status atual da carga. 
    private final Calendar dataCadastro; // Data de cadastro da carga. 

    private Carga(int codigo, TipoCarga tipo, String descricao, float peso, float volume, int quantidade) {  //Construtor privado que instancia uma nova carga.
        super(codigo);
        this.tipo = Objects.requireNonNull(tipo, "Tipo de carga não pode ser nulo");
        this.descricao = validarDescricao(descricao);
        this.peso = validarPeso(peso);
        this.volume = validarVolume(volume);
        this.quantidade = validarQuantidade(quantidade);
        this.status = StatusCarga.ARMAZENADA;
        this.dataCadastro = Calendar.getInstance();
    }

    public static void adicionarCarga(TipoCarga tipo, String descricao, float peso, float volume, int quantidade) { //adiciona uma nova carga ao sistema.
        cargas.add(new Carga(cargas.size() + 1, tipo, descricao, peso, volume, quantidade));
    }

    public static Carga buscarCarga(int codigo) { //busca uma carga pelo código.
        validarCodigo(codigo, cargas.size());
        return cargas.get(codigo - 1);
    }

    public static List<Carga> listarAtivas() { //Lista todas as cargas ativas.
        return cargas.stream()
            .filter(Carga::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarCarga(int codigo) { //Desativa uma carga pelo código.
        Carga carga = buscarCarga(codigo);
        
        if (!carga.isAtivo()) {
            throw new IllegalStateException("Carga já está desativada");
        }
        
        carga.desativar();
    }

    public void atualizarStatus(StatusCarga novoStatus) { //Atualiza o status da carga.
        this.status = Objects.requireNonNull(novoStatus, "Status não pode ser nulo");
    }

    
    private static String validarDescricao(String descricao) {   // Métodos de validação
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        return descricao.trim();
    }

    private static float validarPeso(float peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero");
        }
        return peso;
    }

    private static float validarVolume(float volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume deve ser maior que zero");
        }
        return volume;
    }

    private static int validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        return quantidade;
    }

    // Getters
    public TipoCarga getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getPeso() {
        return peso;
    }

    public float getVolume() {
        return volume;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public StatusCarga getStatus() {
        return status;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    @Override
    public String toString() {
        return String.format("Carga [%d] - %s | %s | Peso: %.2f kg | Vol: %.2f m³ | Qtd: %d | Status: %s | Cadastro: %s | %s",
            getCodigo(),
            tipo,
            descricao,
            peso,
            volume,
            quantidade,
            status,
            DateUtils.formatDateTime(dataCadastro),
            isAtivo() ? "Ativa" : "Inativa");
    }
}
