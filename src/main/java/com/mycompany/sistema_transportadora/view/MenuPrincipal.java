package com.mycompany.sistema_transportadora.view;

import java.util.Scanner;
import com.mycompany.sistema_transportadora.utils.EncodingFixer;

public class MenuPrincipal {
    private Scanner scanner;
    private MenuEstado menuEstado;
    private MenuCidade menuCidade;
    private MenuEndereco menuEndereco;
    private MenuMotorista menuMotorista;
    private MenuVeiculo menuVeiculo;
    private MenuCarga menuCarga;
    private MenuParada menuParada;
    private MenuRota menuRota;

    public MenuPrincipal() {
        EncodingFixer.fixEncoding();
        this.scanner = EncodingFixer.createUtf8Scanner();
        inicializarMenus();
    }

    private void inicializarMenus() {
        this.menuEstado = new MenuEstado(scanner);
        this.menuCidade = new MenuCidade(scanner);
        this.menuEndereco = new MenuEndereco(scanner);
        this.menuMotorista = new MenuMotorista(scanner);
        this.menuVeiculo = new MenuVeiculo(scanner);
        this.menuCarga = new MenuCarga(scanner);
        this.menuParada = new MenuParada(scanner);
        this.menuRota = new MenuRota(scanner);
    }

    public void exibir() {
        int opcao;
        do {
            exibirCabecalho();
            opcao = lerOpcao();
            limparBuffer();
            
            switch (opcao) {
                case 1:
                    menuEstado.exibir();
                    break;
                case 2:
                    menuCidade.exibir();
                    break;
                case 3:
                    menuEndereco.exibir();
                    break;
                case 4:
                    menuMotorista.exibir();
                    break;
                case 5:
                    menuVeiculo.exibir();
                    break;
                case 6:
                    menuCarga.exibir();
                    break;
                case 7:
                    menuParada.exibir();
                    break;
                case 8:
                    menuRota.exibir();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private void exibirCabecalho() {
        System.out.println("\n=== SISTEMA TRANSPORTADORA ===");
        System.out.println("1. Gerenciar Estados");
        System.out.println("2. Gerenciar Cidades");
        System.out.println("3. Gerenciar Endereços");
        System.out.println("4. Gerenciar Motoristas");
        System.out.println("5. Gerenciar Veículos");
        System.out.println("6. Gerenciar Cargas");
        System.out.println("7. Gerenciar Paradas");
        System.out.println("8. Gerenciar Rotas");
        System.out.println("0. Sair");
    }

    private int lerOpcao() {
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }

    private void limparBuffer() {
        scanner.nextLine();
    }
}