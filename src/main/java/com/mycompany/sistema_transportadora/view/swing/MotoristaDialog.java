package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.Motorista;

public class MotoristaDialog extends JDialog {
    
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cnhField;
    private JTextField telefoneField;

    public MotoristaDialog(JFrame parent) {
        super(parent, "Novo Motorista", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        mainPanel.add(nomeField);
        
        mainPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        mainPanel.add(cpfField);
        
        mainPanel.add(new JLabel("CNH:"));
        cnhField = new JTextField();
        mainPanel.add(cnhField);
        
        mainPanel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        mainPanel.add(telefoneField);

        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarMotorista());
        
        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(salvarBtn);
        btnPanel.add(cancelarBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void salvarMotorista() {
        try {
            Motorista.cadastrar(
                nomeField.getText(),
                cpfField.getText(),
                cnhField.getText(),
                telefoneField.getText()
            );
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}