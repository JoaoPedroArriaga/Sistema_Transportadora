package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class ManutencaoDialog extends JDialog {
    
    private JSpinner dataSpinner;
    private JTextField tipoServicoField;
    private JTextField custoField;
    private int codVeiculo;
    
    public ManutencaoDialog(JFrame parent, int codVeiculo) {
        super(parent, "Registrar Manutenção", true);
        this.codVeiculo = codVeiculo;
        setSize(400, 200);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Veículo
        mainPanel.add(new JLabel("Veículo:"));
        JLabel veiculoLabel = new JLabel(String.valueOf(codVeiculo));
        mainPanel.add(veiculoLabel);
        
        // Data
        mainPanel.add(new JLabel("Data:"));
        dataSpinner = new JSpinner(new SpinnerDateModel());
        dataSpinner.setEditor(new JSpinner.DateEditor(dataSpinner, "dd/MM/yyyy HH:mm"));
        mainPanel.add(dataSpinner);
        
        // Tipo Serviço
        mainPanel.add(new JLabel("Tipo Serviço:"));
        tipoServicoField = new JTextField();
        mainPanel.add(tipoServicoField);
        
        // Custo
        mainPanel.add(new JLabel("Custo (R$):"));
        custoField = new JTextField();
        mainPanel.add(custoField);
        
        // Botões
        JButton saveBtn = new JButton("Salvar");
        saveBtn.addActionListener(e -> salvarManutencao());
        
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void salvarManutencao() {
        try {
            Calendar data = Calendar.getInstance();
            data.setTime(((java.util.Date) dataSpinner.getValue()));
            
            String tipoServico = tipoServicoField.getText();
            float custo = Float.parseFloat(custoField.getText());
            
            Manutencao.registrarManutencao(codVeiculo, data, tipoServico, custo);
            JOptionPane.showMessageDialog(this, "Manutenção registrada com sucesso!");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Custo inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}