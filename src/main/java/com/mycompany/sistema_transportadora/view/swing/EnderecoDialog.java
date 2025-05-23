package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class EnderecoDialog extends JDialog {
    
    private JComboBox<Estado> estadoCombo;
    private JComboBox<Cidade> cidadeCombo;
    private JTextField logradouroField;
    
    public EnderecoDialog(JFrame parent) {
        super(parent, "Novo Endereço", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Estado
        mainPanel.add(new JLabel("Estado:"));
        estadoCombo = new JComboBox<>();
        Estado.listarAtivos().forEach(estadoCombo::addItem);
        estadoCombo.addActionListener(e -> atualizarCidades());
        mainPanel.add(estadoCombo);
        
        // Cidade
        mainPanel.add(new JLabel("Cidade:"));
        cidadeCombo = new JComboBox<>();
        mainPanel.add(cidadeCombo);
        
        // Logradouro
        mainPanel.add(new JLabel("Logradouro:"));
        logradouroField = new JTextField();
        mainPanel.add(logradouroField);
        
        // Botões
        JButton saveBtn = new JButton("Salvar");
        saveBtn.addActionListener(e -> salvarEndereco());
        
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        atualizarCidades();
    }
    
    private void atualizarCidades() {
        cidadeCombo.removeAllItems();
        Estado estado = (Estado) estadoCombo.getSelectedItem();
        if (estado != null) {
            Cidade.listarAtivas().stream()
                .filter(c -> c.getCodEstado() == estado.getCodigo())
                .forEach(cidadeCombo::addItem);
        }
    }
    
    private void salvarEndereco() {
        try {
            Estado estado = (Estado) estadoCombo.getSelectedItem();
            Cidade cidade = (Cidade) cidadeCombo.getSelectedItem();
            String logradouro = logradouroField.getText();
            
            Endereco.adicionarEndereco(logradouro, estado, cidade);
            JOptionPane.showMessageDialog(this, "Endereço cadastrado com sucesso!");
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}