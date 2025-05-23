package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.mycompany.sistema_transportadora.model.entidades.Veiculo;
import com.mycompany.sistema_transportadora.model.enums.TipoVeiculo;

public class VeiculoDialog extends JDialog {

    private JComboBox<TipoVeiculo> tipoCombo;
    private JTextField placaField;
    private JTextField capacidadeField;

    public VeiculoDialog(JFrame parent) {
        super(parent, "Cadastro de Veículo", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tipo
        mainPanel.add(new JLabel("Tipo do Veículo:"));
        tipoCombo = new JComboBox<>(TipoVeiculo.values());
        mainPanel.add(tipoCombo);

        // Placa
        mainPanel.add(new JLabel("Placa (AAA-9999):"));
        placaField = new JTextField();
        mainPanel.add(placaField);

        // Capacidade
        mainPanel.add(new JLabel("Capacidade (kg):"));
        capacidadeField = new JTextField();
        mainPanel.add(capacidadeField);

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::salvarVeiculo);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void salvarVeiculo(ActionEvent evt) {
        try {
            // Validação básica
            if (placaField.getText().isBlank() || capacidadeField.getText().isBlank()) {
                throw new IllegalArgumentException("Todos os campos são obrigatórios!");
            }

            // Conversão de valores
            double capacidade = Double.parseDouble(
                capacidadeField.getText().replace(",", ".")
            );

            // Cadastro
            Veiculo.cadastrar(
                (TipoVeiculo) tipoCombo.getSelectedItem(),
                placaField.getText(),
                capacidade
            );

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Formato numérico inválido para capacidade!",
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
}