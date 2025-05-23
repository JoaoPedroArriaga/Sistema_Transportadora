// Classe abstrata base para todas as entidades do sistema.
// Define o comportamento  padrão para atributos comuns como código e status de ativação.
// As entidades que herdam dessa classe devem implementar funcionalidades específicas conforme necessário.


package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.interfaces.Ativavel;
import java.util.List;
import java.util.function.Function;

public abstract class Entidade implements Ativavel {
    protected final int codigo; // Código identificador único da entidade.
    protected boolean ativo; // Indica se a entidade está ativa.
    
    protected Entidade(int codigo) { // Construtor protegido, utilizado apenas por classes derivadas.
        this.codigo = codigo;
        this.ativo = true;
    }
    
    public int getCodigo() { // Retorna o código da entidade.
        return codigo;
    }
    
    @Override
    public boolean isAtivo() { // Verifica se a entidade está ativa.
        return ativo;
    }
    
    @Override
    public void desativar() { // Desativa a entidade, marcando-a como inativa.
        this.ativo = false;
    }
    
    protected static void validarCodigo(int codigo, int tamanhoLista) { // Valida se o código informado está dentro do intervalo válido para a lista fornecida.
        if (codigo < 1 || codigo > tamanhoLista) {
            throw new IllegalArgumentException("Código inválido: " + codigo);
        }
    }
    
    protected static <T extends Entidade> T buscarPorCodigo(int codigo, List<T> lista) { // Busca uma entidade pelo código dentro da lista informada.
        validarCodigo(codigo, lista.size());
        return lista.get(codigo - 1);
    }
    
    protected static <T extends Entidade> void desativarEntidade( //  Desativa uma entidade específica com base no código informado, utilizando uma função de erro personalizada.
        int codigo, 
        List<T> lista,
        Function<T, String> mensagemErroProvider) {
        
        validarCodigo(codigo, lista.size());
        T entidade = lista.get(codigo - 1);
        
        if (!entidade.isAtivo()) {
            throw new IllegalStateException(mensagemErroProvider.apply(entidade));
        }
        
        entidade.desativar();
    }
}
