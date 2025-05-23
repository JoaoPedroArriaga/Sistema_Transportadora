package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.Estado;

public class EstadoDialog extends JDialog {

    private JTextField nomeField;

    public EstadoDialog(JFrame parent) {
        super(parent, "Cadastrar Estado", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo Nome
        JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nomePanel.add(new JLabel("Nome do Estado:"));
        nomeField = new JTextField(20);
        nomePanel.add(nomeField);
        mainPanel.add(nomePanel);

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEstado());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void salvarEstado() {
        try {
            String nome = nomeField.getText().trim();
            
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("O nome do estado é obrigatório!");
            }
            
            Estado.registrarEstado(nome);
            dispose();
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Erro de Cadastro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}