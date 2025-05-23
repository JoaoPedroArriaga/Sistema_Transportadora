package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import com.mycompany.sistema_transportadora.model.entidades.*;
import java.util.List;

public class ParadaDialog extends JDialog {

    public interface ParadaSavedListener {
        void onParadaSaved();
    }
    
    private ParadaSavedListener listener;
    private JComboBox<Endereco> enderecoCombo;
    private JSpinner dataSpinner;

    public ParadaDialog(JFrame parent) {
        super(parent, "Nova Parada", true);
        initComponents();
        pack(); // Ajusta o tamanho automaticamente
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Endereço
        mainPanel.add(new JLabel("Endereço:"));
        enderecoCombo = new JComboBox<>();
        Endereco.listarAtivos().forEach(enderecoCombo::addItem);
        mainPanel.add(enderecoCombo);
        
        // Data Prevista
        mainPanel.add(new JLabel("Data Prevista:"));
        dataSpinner = new JSpinner(new SpinnerDateModel());
        dataSpinner.setEditor(new JSpinner.DateEditor(dataSpinner, "dd/MM/yyyy HH:mm"));
        mainPanel.add(dataSpinner);
        
        // Botões
        JButton saveBtn = new JButton("Salvar");
        saveBtn.addActionListener(e -> salvarParada());
        
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Carregar endereços
        List<Endereco> enderecos = Endereco.listarAtivos();
        enderecos.forEach(enderecoCombo::addItem);

        // Verificar se há endereços disponíveis
        if (enderecos.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, 
                "Cadastre endereços antes de criar paradas!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            saveBtn.setEnabled(false);
        }
    }

    public void setParadaSavedListener(ParadaSavedListener listener) {
        this.listener = listener;
    }
    
     private void salvarParada() {
        try {
            // Validação básica
            if (enderecoCombo.getSelectedItem() == null) {
                throw new IllegalArgumentException("Selecione um endereço!");
            }

            Calendar data = Calendar.getInstance();
            data.setTime((Date) dataSpinner.getValue());

            if (data.before(Calendar.getInstance())) {
                throw new IllegalArgumentException("Data deve ser futura!");
            }

            // Persistência
            Parada.adicionarParada(
                (Endereco) enderecoCombo.getSelectedItem(), 
                data
            );

            // Notificar o listener e fechar
            if (listener != null) {
                listener.onParadaSaved();
            }
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, 
                e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}