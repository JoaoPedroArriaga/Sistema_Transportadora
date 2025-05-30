package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Estado;
import com.mycompany.sistema_transportadora.utils.EventManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.EmptyBorder;

public class EstadoDialog extends JDialog {

    private JTextField nomeField;
    
    public EstadoDialog(JFrame parent) {
        super(parent, "Cadastrar Estado", true);
        setSize(350, 180);
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
        
        // Campo Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nome do Estado:"), gbc);
        
        gbc.gridx = 1;
        nomeField = new JTextField(15);
        nomeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampo();
            }
        });
        mainPanel.add(nomeField, gbc);
        
        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEstado());
        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void validarCampo() {
        if (nomeField.getText().trim().isEmpty()) {
            nomeField.setBackground(new Color(255, 200, 200));
        } else {
            nomeField.setBackground(Color.WHITE);
        }
    }
    
    private void salvarEstado() {
        // Resetar cor
        nomeField.setBackground(Color.WHITE);
        
        try {
            String nome = nomeField.getText().trim();
            
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Nome do estado é obrigatório!");
            }
            
            Estado.registrarEstado(nome);
            
            // NOTIFICAR TODOS OS LISTENERS
            EventManager.notifyEstadoChanged();
            
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
