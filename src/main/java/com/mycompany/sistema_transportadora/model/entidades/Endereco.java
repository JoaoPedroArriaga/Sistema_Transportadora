// Representa um endereço no sistema, contendo logradouro, cidade e estado.
// A classe mantém uma lista estática de endereços que podem ser adicionados, buscados, listados (somente os ativos) e desativados.
// Atualização de endereço já existente.
// As validações garantem que o logradouro não seja vazio e que a cidade pertença ao estado informado.

package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.utils.TextFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Endereco extends Entidade {
    private static final List<Endereco> enderecos = new ArrayList<>(); // Lista de todos os endereços cadastrados.
    private String logradouro; // Nome da rua, avenida, etc.
    private Estado estado; // Estado correspondente ao endereço.
    private Cidade cidade; // Cidade correspondente ao endereço.

    private Endereco(int codigo, String logradouro, Estado estado, Cidade cidade) {
        super(codigo);
        this.logradouro = logradouro;
        this.estado = estado;
        this.cidade = cidade;
    }

    public String getLogradouro() { // Retorna o logradouro do endereço.
        return logradouro;
    }

    public Estado getEstado() { // Retorna o estado do endereço.
        return estado;
    }

    public Cidade getCidade() { // Retorna a cidade do endereço.
        return cidade;
    }

    public static void adicionarEndereco(String logradouro, Estado estado, Cidade cidade) { // Adiciona um novo endereço ao sistema.
        validarLogradouro(logradouro);
        validarRelacaoCidadeEstado(cidade, estado);
        
        enderecos.add(new Endereco(enderecos.size() + 1, logradouro, estado, cidade));
    }

    public void atualizar(String novoLogradouro, Estado novoEstado, Cidade novaCidade) { // Atualiza os dados de um endereço.
        validarLogradouro(novoLogradouro);
        validarRelacaoCidadeEstado(novaCidade, novoEstado);

        this.logradouro = novoLogradouro;
        this.estado = novoEstado;
        this.cidade = novaCidade;
    }

    private static void validarLogradouro(String logradouro) {  // Valida se o logradouro é não nulo e não vazio. 
        if (logradouro == null || logradouro.trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }
    }

    private static void validarRelacaoCidadeEstado(Cidade cidade, Estado estado) { // Valida se a cidade pertence ao estado informado.
        if (cidade == null || estado == null) {
            throw new IllegalArgumentException("Cidade e estado não podem ser nulos");
        }
        if (cidade.getCodEstado() != estado.getCodigo()) {
            throw new IllegalArgumentException(
                String.format("A cidade %s não pertence ao estado %s", 
                    cidade.getNome(), estado.getNome()));
        }
    }

    public static Endereco buscarEndereco(int codigo) { // Busca um endereço  pelo código.
        validarCodigo(codigo, enderecos.size());
        return enderecos.get(codigo - 1);
    }

    public static List<Endereco> listarAtivos() { // Lista todos os endereços ativos.
        return enderecos.stream()
            .filter(Endereco::isAtivo)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void desativarEndereco(int codigo) { // Desativa um endereço a partir do seu código.
        validarCodigo(codigo, enderecos.size());
        Endereco endereco = enderecos.get(codigo - 1);
        
        if (!endereco.isAtivo()) {
            throw new IllegalStateException("Endereço " + endereco.getLogradouro() + " já está desativado");
        }
        endereco.desativar();
    }

    @Override
    public String toString() {
        return String.format("Endereço [%d] - %s",
            getCodigo(),
            TextFormatter.formatarEnderecoCompleto(logradouro,cidade.getNome(),estado.getNome())
            ) + " - " + (isAtivo() ? "Ativo" : "Inativo");
    }
}
