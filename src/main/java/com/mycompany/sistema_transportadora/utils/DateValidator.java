package com.mycompany.sistema_transportadora.utils;

import java.util.Calendar;

public class DateValidator {
    
    public static void validarDataNaoNula(Calendar data, String mensagemErro) {
        if (data == null) {
            throw new IllegalArgumentException(mensagemErro);
        }
    }

    public static void validarDataFutura(Calendar data, String mensagemErro) {
        if (!DateUtils.isDataFutura(data)) {
            throw new IllegalArgumentException(mensagemErro);
        }
    }

    public static void validarDataPassada(Calendar data, String mensagemErro) {
        if (!DateUtils.isDataPassada(data)) {
            throw new IllegalArgumentException(mensagemErro);
        }
    }

    public static void validarIntervaloDatas(Calendar inicio, Calendar fim, String mensagemErro) {
        if (inicio == null || fim == null || inicio.after(fim)) {
            throw new IllegalArgumentException(mensagemErro);
        }
    }
}