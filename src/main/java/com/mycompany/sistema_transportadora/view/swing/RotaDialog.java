package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class RotaDialog extends JDialog {
    
    private JComboBox<Veiculo> veiculoCombo;
    private JComboBox<Carga> cargaCombo;
    private JComboBox<Endereco> origemCombo;
    private JComboBox<Endereco> destinoCombo;
    
    public RotaDialog(JFrame parent) {
        super(parent, "Nova Rota", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Veículos
        mainPanel.add(new JLabel("Veículo:"));
        veiculoCombo = new JComboBox<>();
        Veiculo.listarAtivos().forEach(veiculoCombo::addItem);
        mainPanel.add(veiculoCombo);
        
        // Cargas
        mainPanel.add(new JLabel("Carga:"));
        cargaCombo = new JComboBox<>();
        Carga.listarAtivas().forEach(cargaCombo::addItem);
        mainPanel.add(cargaCombo);
        
        // Origem
        mainPanel.add(new JLabel("Origem:"));
        origemCombo = new JComboBox<>();
        Endereco.listarAtivos().forEach(origemCombo::addItem);
        mainPanel.add(origemCombo);
        
        // Destino
        mainPanel.add(new JLabel("Destino:"));
        destinoCombo = new JComboBox<>();
        Endereco.listarAtivos().forEach(destinoCombo::addItem);
        mainPanel.add(destinoCombo);
        
        // Botões
        JButton saveBtn = new JButton("Salvar");
        saveBtn.addActionListener(e -> saveRota());
        
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void saveRota() {
        try {
            Veiculo veiculo = (Veiculo) veiculoCombo.getSelectedItem();
            Carga carga = (Carga) cargaCombo.getSelectedItem();
            Endereco origem = (Endereco) origemCombo.getSelectedItem();
            Endereco destino = (Endereco) destinoCombo.getSelectedItem();
            
            Rota.adicionarRota(
                veiculo.getCodigo(),
                carga.getCodigo(),
                origem,
                destino
            );
            
            JOptionPane.showMessageDialog(this, "Rota criada com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}