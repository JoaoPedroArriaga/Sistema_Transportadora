package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class EnderecoPanel extends JPanel {
    
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<Estado> estadoCombo;
    private JComboBox<Cidade> cidadeCombo;

    public EnderecoPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Toolbar
        JToolBar toolBar = new JToolBar();
        estadoCombo = new JComboBox<>();
        cidadeCombo = new JComboBox<>();
        JButton addBtn = new JButton("Novo Endereço");

        Estado.listarAtivos().forEach(estadoCombo::addItem);
        estadoCombo.addActionListener(e -> atualizarCidades());
        
        addBtn.addActionListener(e -> adicionarEndereco());

        toolBar.add(new JLabel("Estado:"));
        toolBar.add(estadoCombo);
        toolBar.add(new JLabel("Cidade:"));
        toolBar.add(cidadeCombo);
        toolBar.addSeparator();
        toolBar.add(addBtn);

        add(toolBar, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Logradouro", "Cidade", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void atualizarCidades() {
        cidadeCombo.removeAllItems();
        Estado estado = (Estado) estadoCombo.getSelectedItem();
        if (estado != null) {
            Cidade.listarAtivas().stream()
                .filter(c -> c.getCodEstado() == estado.getCodigo())
                .forEach(cidadeCombo::addItem);
        }
    }

    private void loadData() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            Endereco.listarAtivos().forEach(endereco -> 
                tableModel.addRow(new Object[]{
                    endereco.getCodigo(),
                    endereco.getLogradouro(),
                    endereco.getCidade().getNome(),
                    endereco.getEstado().getNome()
                })
            );
        });
    }

    private void adicionarEndereco() {
        Estado estado = (Estado) estadoCombo.getSelectedItem();
        Cidade cidade = (Cidade) cidadeCombo.getSelectedItem();
        String logradouro = JOptionPane.showInputDialog("Logradouro:");

        if (estado != null && cidade != null && logradouro != null) {
            try {
                Endereco.adicionarEndereco(logradouro, estado, cidade);
                loadData();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}