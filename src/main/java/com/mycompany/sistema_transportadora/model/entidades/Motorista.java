package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.enums.StatusMotorista;
import com.mycompany.sistema_transportadora.utils.TextFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Motorista extends Entidade {
    private static final List<Motorista> motoristas = new ArrayList<>();
    
    private final String nome;
    private final String cpf;
    private final String cnh;
    private String telefone;
    private StatusMotorista status;

    private Motorista(int codigo, String nome, String cpf, String cnh, String telefone) {
        super(codigo);
        this.nome = validarNome(nome);
        this.cpf = validarCPF(cpf);
        this.cnh = validarCNH(cnh);
        this.telefone = validarTelefone(telefone);
        this.status = StatusMotorista.DISPONIVEL;
    }

    public static void cadastrar(String nome, String cpf, String cnh, String telefone) {
        validarCNHUnica(cnh);
        motoristas.add(new Motorista(motoristas.size() + 1, nome, cpf, cnh, telefone));
    }

    public static Motorista buscarPorCodigo(int codigo) {
        validarCodigo(codigo, motoristas.size());
        return motoristas.get(codigo - 1);
    }

    public static List<Motorista> listarAtivos() {
        return motoristas.stream()
            .filter(Motorista::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static List<Motorista> listarPorStatus(StatusMotorista status) {
        return motoristas.stream()
            .filter(m -> m.getStatus() == status)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarMotorista(int codigo) {
        Motorista motorista = buscarPorCodigo(codigo);
        
        if (!motorista.isAtivo()) {
            throw new IllegalStateException("Motorista já está desativado");
        }
        
        motorista.desativar();
        motorista.status = StatusMotorista.DESLIGADO;
    }

    public void atualizarStatus(StatusMotorista novoStatus) {
        this.status = Objects.requireNonNull(novoStatus);
    }

    public void atualizarTelefone(String novoTelefone) {
        this.telefone = validarTelefone(novoTelefone);
    }

    // Métodos de validação
    private static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        return nome.trim();
    }

    private static String validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")) {
            throw new IllegalArgumentException("CPF inválido");
        }
        return TextFormatter.formatarCPF(cpf);
    }

    private static String validarCNH(String cnh) {
        if (cnh == null || !cnh.matches("\\d{11}")) {
            throw new IllegalArgumentException("CNH deve conter 11 dígitos");
        }
        return cnh;
    }

    private static String validarTelefone(String telefone) {
        if (telefone == null || !telefone.matches("(\\(?\\d{2}\\)?)?\\s?\\d{4,5}[\\s-]?\\d{4}")) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        return TextFormatter.formatarTelefone(telefone);
    }

    private static void validarCNHUnica(String cnh) {
        if (motoristas.stream().anyMatch(m -> m.getCnh().equals(cnh))) {
            throw new IllegalArgumentException("CNH já cadastrada");
        }
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public String getTelefone() {
        return telefone;
    }

    public StatusMotorista getStatus() {
        return status;
    }

    public String getNomeFormatado() {
        return TextFormatter.capitalizar(nome);
    }

    public String getCpfFormatado() {
        return TextFormatter.formatarCPF(cpf);
    }

    public String getTelefoneFormatado() {
        return TextFormatter.formatarTelefone(telefone);
    }

    @Override
    public String toString() {
        return String.format("Motorista [%d] - %s | CPF: %s | CNH: %s | Tel: %s | Status: %s | %s",
            getCodigo(),
            getNomeFormatado(),
            getCpfFormatado(),
            cnh,
            getTelefoneFormatado(),
            status,
            isAtivo() ? "Ativo" : "Inativo");
    }
}