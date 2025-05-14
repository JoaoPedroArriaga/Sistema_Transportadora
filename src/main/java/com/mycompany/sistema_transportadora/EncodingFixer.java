package com.mycompany.sistema_transportadora;

import java.util.Scanner;

import javax.swing.UIManager;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncodingFixer {

    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static boolean alreadyConfigured = false;

    /**
     * Configura o encoding UTF-8 para todo o aplicativo
     * Deve ser chamado no início da aplicação
     */
    public static void fixEncoding() {
        if (alreadyConfigured) {
            return;
        }

        try {
            // Configuração do sistema
            System.setProperty("file.encoding", UTF8.name());
            java.nio.charset.Charset.defaultCharset(); // Força a JVM a reconhecer

            // Configuração de saída padrão
            System.setOut(new PrintStream(System.out, true, UTF8));
            System.setErr(new PrintStream(System.err, true, UTF8));

            // Configuração do console (especialmente para Windows)
            configureConsoleEncoding();

            alreadyConfigured = true;
        } catch (Exception e) {
            System.err.println("Erro ao configurar encoding: " + e.getMessage());
        }
    }

    private static void configureConsoleEncoding() {
        try {
            if (isWindows()) {
                // Configura o console do Windows para UTF-8
                new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Não foi possível configurar o console: " + e.getMessage());
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * Cria um Scanner configurado com UTF-8
     */
    public static Scanner createUtf8Scanner() {
        return new Scanner(System.in, UTF8);
    }

    /**
     * Corrige strings que podem ter problemas de encoding
     */
    public static String fixString(String input) {
        if (input == null) return null;
        return new String(input.getBytes(StandardCharsets.ISO_8859_1), UTF8);
    }

    public static void configureSwingEncoding() {
        System.setProperty("swing.encoding", "UTF-8");
        UIManager.put("OptionPane.messageEncoding", "UTF-8");
    }
}