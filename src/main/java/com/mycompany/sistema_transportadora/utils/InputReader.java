package com.mycompany.sistema_transportadora.utils;

import java.util.Scanner;
import java.util.Calendar;
import java.util.function.Function;

public class InputReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    public static Calendar lerDataHora(String mensagem) {
        System.out.println(mensagem);
        int dia = lerInteiro("Dia (1-31): ");
        int mes = lerInteiro("Mês (1-12): ");
        int ano = lerInteiro("Ano: ");
        int hora = lerInteiro("Hora (0-23): ");
        int minuto = lerInteiro("Minuto (0-59): ");
        
        return DateUtils.createCalendar(dia, mes, ano, hora, minuto);
    }

    public static <T> T lerComValidacao(
        String mensagem, 
        Function<String, T> conversor,
        Function<T, Boolean> validador,
        String mensagemErro) {
        
        while (true) {
            try {
                String input = lerString(mensagem);
                T valor = conversor.apply(input);
                if (validador.apply(valor)) {
                    return valor;
                }
                System.out.println(mensagemErro);
            } catch (Exception e) {
                System.out.println("Entrada inválida: " + e.getMessage());
            }
        }
    }
}