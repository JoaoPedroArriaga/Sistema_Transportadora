package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Cidade;
import com.mycompany.sistema_transportadora.model.entidades.Endereco;
import com.mycompany.sistema_transportadora.model.entidades.Estado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.EmptyBorder;

public class EnderecoDialog extends JDialog {
    
    private JTextField logradouroField;
    private final Estado estado;
    private final Cidade cidade;
    
    public EnderecoDialog(JFrame parent, Estado estado, Cidade cidade) {
        super(parent, "Novo Endereço", true);
        this.estado = estado;
        this.cidade = cidade;
        setSize(400, 200);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Estado (apenas visualização)
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Estado:"), gbc);
        
        gbc.gridx = 1;
        JLabel estadoLabel = new JLabel(estado.getNome());
        mainPanel.add(estadoLabel, gbc);
        
        // Cidade (apenas visualização)
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Cidade:"), gbc);
        
        gbc.gridx = 1;
        JLabel cidadeLabel = new JLabel(cidade.getNome());
        mainPanel.add(cidadeLabel, gbc);
        
        // Logradouro
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Logradouro:"), gbc);
        
        gbc.gridx = 1;
        logradouroField = new JTextField(15);
        logradouroField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampo();
            }
        });
        mainPanel.add(logradouroField, gbc);
        
        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEndereco());
        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void validarCampo() {
        if (logradouroField.getText().trim().isEmpty()) {
            logradouroField.setBackground(new Color(255, 200, 200));
        } else {
            logradouroField.setBackground(Color.WHITE);
        }
    }
    
    private void salvarEndereco() {
        // Resetar cor
        logradouroField.setBackground(Color.WHITE);
        
        try {
            String logradouro = logradouroField.getText().trim();
            
            if (logradouro.isEmpty()) {
                throw new IllegalArgumentException("Logradouro é obrigatório!");
            }
            
            Endereco.adicionarEndereco(logradouro, estado, cidade);
            dispose();
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, 
                ex.getMessage(), 
                "Erro de Cadastro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}