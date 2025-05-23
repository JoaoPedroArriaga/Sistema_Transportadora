package com.mycompany.sistema_transportadora;

import com.mycompany.sistema_transportadora.utils.EncodingFixer;
import com.mycompany.sistema_transportadora.view.swing.MainFrame;

public class Sistema_Transportadora {
    public static void main(String[] args) {
        EncodingFixer.fixEncoding();
        new MainFrame().setVisible(true); // Apenas Swing
    }
}