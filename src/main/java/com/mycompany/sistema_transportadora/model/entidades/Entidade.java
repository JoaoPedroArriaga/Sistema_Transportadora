package com.mycompany.sistema_transportadora.model.entidades;

import com.mycompany.sistema_transportadora.model.interfaces.Ativavel;
import java.util.List;
import java.util.function.Function;

public abstract class Entidade implements Ativavel {
    protected final int codigo;
    protected boolean ativo;
    
    protected Entidade(int codigo) {
        this.codigo = codigo;
        this.ativo = true;
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    @Override
    public boolean isAtivo() {
        return ativo;
    }
    
    @Override
    public void desativar() {
        this.ativo = false;
    }
    
    protected static void validarCodigo(int codigo, int tamanhoLista) {
        if (codigo < 1 || codigo > tamanhoLista) {
            throw new IllegalArgumentException("Código inválido: " + codigo);
        }
    }
    
    protected static <T extends Entidade> T buscarPorCodigo(int codigo, List<T> lista) {
        validarCodigo(codigo, lista.size());
        return lista.get(codigo - 1);
    }
    
    protected static <T extends Entidade> void desativarEntidade(
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