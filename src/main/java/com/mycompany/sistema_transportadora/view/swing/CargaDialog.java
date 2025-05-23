package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.model.enums.TipoCarga;

public class CargaDialog extends JDialog {
    
    private JComboBox<TipoCarga> tipoCombo;
    private JTextField descricaoField;
    private JFormattedTextField pesoField;
    private JFormattedTextField volumeField;
    private JFormattedTextField quantidadeField;

    public CargaDialog(JFrame parent) {
        super(parent, "Nova Carga", true);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Formatadores numéricos
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Float.class);
        formatter.setMinimum(0.0f);

        tipoCombo = new JComboBox<>(TipoCarga.values());
        descricaoField = new JTextField();
        pesoField = new JFormattedTextField(formatter);
        volumeField = new JFormattedTextField(formatter);
        quantidadeField = new JFormattedTextField(new NumberFormatter());

        mainPanel.add(new JLabel("Tipo:"));
        mainPanel.add(tipoCombo);
        mainPanel.add(new JLabel("Descrição:"));
        mainPanel.add(descricaoField);
        mainPanel.add(new JLabel("Peso (kg):"));
        mainPanel.add(pesoField);
        mainPanel.add(new JLabel("Volume (m³):"));
        mainPanel.add(volumeField);
        mainPanel.add(new JLabel("Quantidade:"));
        mainPanel.add(quantidadeField);

        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarCarga());
        
        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(salvarBtn);
        buttonPanel.add(cancelarBtn);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validarCampos() {
        if (descricaoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descrição é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (pesoField.getValue() == null || (Float) pesoField.getValue() <= 0) {
            JOptionPane.showMessageDialog(this, "Peso inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void salvarCarga() {
        if (!validarCampos()) return;

        try {
            TipoCarga tipo = (TipoCarga) tipoCombo.getSelectedItem();
            String descricao = descricaoField.getText().trim();
            float peso = ((Number) pesoField.getValue()).floatValue();
            float volume = ((Number) volumeField.getValue()).floatValue();
            int quantidade = ((Number) quantidadeField.getValue()).intValue();

            Carga.adicionarCarga(tipo, descricao, peso, volume, quantidade);
            JOptionPane.showMessageDialog(this, "Carga cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}