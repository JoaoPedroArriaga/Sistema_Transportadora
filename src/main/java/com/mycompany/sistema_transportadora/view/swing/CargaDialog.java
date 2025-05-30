package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Carga;
import com.mycompany.sistema_transportadora.model.enums.TipoCarga;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.border.EmptyBorder;

public class CargaDialog extends JDialog {
    
    private JComboBox<TipoCarga> tipoCombo;
    private JTextField descricaoField;
    private JFormattedTextField pesoField;
    private JFormattedTextField volumeField;
    private JFormattedTextField quantidadeField;
    private final NumberFormat numberFormat;

    public CargaDialog(JFrame parent) {
        super(parent, "Nova Carga", true);
        setSize(500, 350);
        setLocationRelativeTo(parent);
        
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        
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

        // Tipo
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        tipoCombo = new JComboBox<>(TipoCarga.values());
        tipoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoCarga) {
                    setText(value.toString());
                }
                return this;
            }
        });
        mainPanel.add(tipoCombo, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        descricaoField = new JTextField(20);
        descricaoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampo(descricaoField);
            }
        });
        mainPanel.add(descricaoField, gbc);

        // Peso
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Peso (kg):"), gbc);
        
        gbc.gridx = 1;
        pesoField = new JFormattedTextField(new NumberFormatter(numberFormat));
        pesoField.setColumns(10);
        pesoField.setValue(0.0);
        pesoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampoNumerico(pesoField, "Peso");
            }
        });
        mainPanel.add(pesoField, gbc);

        // Volume
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Volume (m³):"), gbc);
        
        gbc.gridx = 1;
        volumeField = new JFormattedTextField(new NumberFormatter(numberFormat));
        volumeField.setColumns(10);
        volumeField.setValue(0.0);
        volumeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampoNumerico(volumeField, "Volume");
            }
        });
        mainPanel.add(volumeField, gbc);

        // Quantidade
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Quantidade:"), gbc);
        
        gbc.gridx = 1;
        quantidadeField = new JFormattedTextField(new NumberFormatter());
        quantidadeField.setColumns(10);
        quantidadeField.setValue(1);
        quantidadeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampoNumerico(quantidadeField, "Quantidade");
            }
        });
        mainPanel.add(quantidadeField, gbc);

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCarga());
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

    private void validarCampo(JTextField field) {
        if (field.getText().trim().isEmpty()) {
            field.setBackground(new Color(255, 200, 200));
        } else {
            field.setBackground(Color.WHITE);
        }
    }
    
    private void validarCampoNumerico(JFormattedTextField field, String nomeCampo) {
        try {
            Number value = (Number) field.getValue();
            if (value == null || value.doubleValue() <= 0) {
                field.setBackground(new Color(255, 200, 200));
            } else {
                field.setBackground(Color.WHITE);
            }
        } catch (Exception e) {
            field.setBackground(new Color(255, 200, 200));
        }
    }

    private void salvarCarga() {
        // Resetar cores
        descricaoField.setBackground(Color.WHITE);
        pesoField.setBackground(Color.WHITE);
        volumeField.setBackground(Color.WHITE);
        quantidadeField.setBackground(Color.WHITE);
        
        try {
            // Validação
            validarCampo(descricaoField);
            validarCampoNumerico(pesoField, "Peso");
            validarCampoNumerico(volumeField, "Volume");
            validarCampoNumerico(quantidadeField, "Quantidade");
            
            if (descricaoField.getBackground().equals(new Color(255, 200, 200)) ||
                pesoField.getBackground().equals(new Color(255, 200, 200)) ||
                volumeField.getBackground().equals(new Color(255, 200, 200)) ||
                quantidadeField.getBackground().equals(new Color(255, 200, 200))) {
                
                throw new IllegalArgumentException("Corrija os campos destacados em vermelho!");
            }

            // Obter valores
            TipoCarga tipo = (TipoCarga) tipoCombo.getSelectedItem();
            String descricao = descricaoField.getText().trim();
            float peso = parseFloat(pesoField.getText());
            float volume = parseFloat(volumeField.getText());
            int quantidade = parseInt(quantidadeField.getText());
            
            // Cadastrar
            Carga.adicionarCarga(tipo, descricao, peso, volume, quantidade);
            
            JOptionPane.showMessageDialog(
                this,
                "Carga cadastrada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Formato numérico inválido!",
                "Erro de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Erro de Validação",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao cadastrar carga: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private float parseFloat(String text) throws NumberFormatException {
        try {
            return numberFormat.parse(text).floatValue();
        } catch (ParseException e) {
            throw new NumberFormatException("Valor numérico inválido");
        }
    }

    private int parseInt(String text) throws NumberFormatException {
        return Integer.parseInt(text.replaceAll("[^\\d]", ""));
    }
}