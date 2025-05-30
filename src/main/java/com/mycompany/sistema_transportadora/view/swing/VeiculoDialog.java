package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Veiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;

public class VeiculoDialog extends JDialog {

    private JComboBox<TipoVeiculo> tipoCombo;
    private JTextField placaField;
    private JFormattedTextField capacidadeField;
    private JFormattedTextField volumeField;
    private JFormattedTextField anoField;
    private final NumberFormat numberFormat;

    public VeiculoDialog(JFrame parent) {
        super(parent, "Cadastro de Veículo", true);
        setSize(500, 350);
        setLocationRelativeTo(parent);
        
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Tipo
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Tipo do Veículo:"), gbc);
        
        gbc.gridx = 1;
        tipoCombo = new JComboBox<>(TipoVeiculo.values());
        tipoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoVeiculo) {
                    setText(((TipoVeiculo) value).getDescricao());
                }
                return this;
            }
        });
        mainPanel.add(tipoCombo, gbc);

        // Placa
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Placa (ex: ABC1234 ou ABC1D23):"), gbc);
        
        gbc.gridx = 1;
        placaField = new JTextField(15);
        placaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarPlaca();
            }
        });
        mainPanel.add(placaField, gbc);

        // Capacidade
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Capacidade de Carga (kg):"), gbc);
        
        gbc.gridx = 1;
        capacidadeField = new JFormattedTextField(numberFormat);
        capacidadeField.setColumns(10);
        capacidadeField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validarCampoNumerico(capacidadeField, "Capacidade de Carga");
            }
        });
        mainPanel.add(capacidadeField, gbc);

        // Volume
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Volume Máximo (m³):"), gbc);
        
        gbc.gridx = 1;
        volumeField = new JFormattedTextField(numberFormat);
        volumeField.setColumns(10);
        volumeField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validarCampoNumerico(volumeField, "Volume Máximo");
            }
        });
        mainPanel.add(volumeField, gbc);

        // Ano de Fabricação
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Ano de Fabricação:"), gbc);
        
        gbc.gridx = 1;
        anoField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        anoField.setColumns(10);
        anoField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validarAno();
            }
        });
        mainPanel.add(anoField, gbc);

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::salvarVeiculo);
        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void validarPlaca() {
        String placa = placaField.getText().trim().toUpperCase();
        String placaSemFormatacao = placa.replace("-", "");
        
        if (placaSemFormatacao.isEmpty()) {
            placaField.setBackground(Color.WHITE);
            return;
        }
        
        boolean valida = placaSemFormatacao.length() == 7 && 
                (placaSemFormatacao.matches("[A-Z]{3}[0-9]{4}") || 
                 placaSemFormatacao.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}"));
        
        placaField.setBackground(valida ? Color.WHITE : new Color(255, 200, 200));
    }

    private void validarCampoNumerico(JFormattedTextField field, String nomeCampo) {
        try {
            double valor = parseDouble(field.getText());
            if (valor <= 0) {
                field.setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(
                    this,
                    nomeCampo + " deve ser maior que zero!",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE
                );
            } else {
                field.setBackground(Color.WHITE);
            }
        } catch (NumberFormatException e) {
            field.setBackground(new Color(255, 200, 200));
            JOptionPane.showMessageDialog(
                this,
                "Valor inválido para " + nomeCampo + "!",
                "Erro de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void validarAno() {
        try {
            int ano = parseInt(anoField.getText());
            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
            if (ano < 1900 || ano > anoAtual) {
                anoField.setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(
                    this,
                    "Ano deve ser entre 1900 e " + anoAtual,
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE
                );
            } else {
                anoField.setBackground(Color.WHITE);
            }
        } catch (NumberFormatException e) {
            anoField.setBackground(new Color(255, 200, 200));
            JOptionPane.showMessageDialog(
                this,
                "Ano de fabricação inválido!",
                "Erro de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void salvarVeiculo(ActionEvent evt) {
        try {
            // Resetar cores de fundo
            placaField.setBackground(Color.WHITE);
            capacidadeField.setBackground(Color.WHITE);
            volumeField.setBackground(Color.WHITE);
            anoField.setBackground(Color.WHITE);
            
            // Verificar campos vazios
            if (placaField.getText().isBlank() || 
                capacidadeField.getText().isBlank() || 
                volumeField.getText().isBlank() || 
                anoField.getText().isBlank()) {
                
                throw new IllegalArgumentException("Todos os campos são obrigatórios!");
            }

            // Validar campos
            validarPlaca();
            validarCampoNumerico(capacidadeField, "Capacidade de Carga");
            validarCampoNumerico(volumeField, "Volume Máximo");
            validarAno();
            
            // Verificar se algum campo está inválido
            if (placaField.getBackground().equals(new Color(255, 200, 200)) ||
                capacidadeField.getBackground().equals(new Color(255, 200, 200)) ||
                volumeField.getBackground().equals(new Color(255, 200, 200)) ||
                anoField.getBackground().equals(new Color(255, 200, 200))) {
                
                throw new IllegalArgumentException("Corrija os campos destacados em vermelho!");
            }

            // Obter valores
            double capacidade = parseDouble(capacidadeField.getText());
            double volume = parseDouble(volumeField.getText());
            int ano = parseInt(anoField.getText());
            String placa = placaField.getText().toUpperCase().replace("-", "");
            
            // Cadastrar veículo
            Veiculo.cadastrar(
                (TipoVeiculo) tipoCombo.getSelectedItem(),
                placa,
                capacidade,
                volume,
                ano
            );

            JOptionPane.showMessageDialog(
                this,
                "Veículo cadastrado com sucesso!",
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
        }
    }

    private double parseDouble(String text) throws NumberFormatException {
        try {
            return numberFormat.parse(text).doubleValue();
        } catch (ParseException e) {
            throw new NumberFormatException("Valor numérico inválido");
        }
    }

    private int parseInt(String text) throws NumberFormatException {
        return Integer.parseInt(text.replaceAll("[^\\d]", ""));
    }
}