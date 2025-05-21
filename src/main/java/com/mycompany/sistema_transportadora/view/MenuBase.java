package com.mycompany.sistema_transportadora.view;

import java.util.Scanner;

public abstract class MenuBase {
    protected Scanner scanner;

    public MenuBase(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void exibir();

    protected void exibirCabecalho(String titulo) {
        System.out.println("\n=== " + titulo + " ===");
    }

    protected int lerOpcao() {
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }

    protected void limparBuffer() {
        scanner.nextLine();
    }

    protected void aguardarEntrada() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
}