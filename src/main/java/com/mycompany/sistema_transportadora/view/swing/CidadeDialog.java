package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.mycompany.sistema_transportadora.model.entidades.Cidade;
import com.mycompany.sistema_transportadora.model.entidades.Estado;

public class CidadeDialog extends JDialog {

    private JTextField nomeField;
    private JComboBox<Estado> estadoCombo;

    public CidadeDialog(JFrame parent) {
        super(parent, "Cadastrar Cidade", true);
        setSize(400, 150);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo Nome
        JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nomePanel.add(new JLabel("Nome da Cidade:"));
        nomeField = new JTextField(20);
        nomePanel.add(nomeField);
        mainPanel.add(nomePanel);

        // Combo Estados
        JPanel estadoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        estadoPanel.add(new JLabel("Estado:"));
        estadoCombo = new JComboBox<>();
        carregarEstados();
        estadoPanel.add(estadoCombo);
        mainPanel.add(estadoPanel);

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCidade());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void carregarEstados() {
        List<Estado> estados = Estado.listarAtivos();
        estados.forEach(e -> estadoCombo.addItem(e));
    }

    private void salvarCidade() {
        try {
            String nome = nomeField.getText().trim();
            Estado estado = (Estado) estadoCombo.getSelectedItem();
            
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("O nome da cidade é obrigatório!");
            }
            
            if (estado == null) {
                throw new IllegalArgumentException("Selecione um estado válido!");
            }
            
            Cidade.adicionarCidade(estado.getCodigo(), nome);
            dispose();
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Erro de Cadastro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}