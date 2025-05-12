package com.mycompany.sistema_transportadora;
import java.util.Scanner;

public class Sistema_Transportadora {

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        System.out.println("Informe o nome da Cidade: ");
        Cidade.AdicionarCidade(1, scn.next());
        scn.close();

        Cidade.ListarAtivas();
    }
}
