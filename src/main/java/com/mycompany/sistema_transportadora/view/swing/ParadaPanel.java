package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.utils.DateUtils;

public class ParadaPanel extends JPanel {
    
    private DefaultTableModel tableModel;
    private JTable table;

    public ParadaPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Toolbar
        JToolBar toolBar = new JToolBar();
        JButton addBtn = new JButton("Nova Parada");
        JButton registrarBtn = new JButton("Registrar Chegada");

        addBtn.addActionListener(e -> adicionarParada());
        registrarBtn.addActionListener(e -> registrarChegada());

        toolBar.add(addBtn);
        toolBar.add(registrarBtn);
        add(toolBar, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Local", "Prevista", "Real", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            Parada.listarAtivas().forEach(parada -> 
                tableModel.addRow(new Object[]{
                    parada.getCodigo(),
                    parada.getLocal().getLogradouro(),
                    DateUtils.formatDateTime(parada.getDataHoraPrevista()),
                    parada.getDataHoraReal() != null ? 
                        DateUtils.formatDateTime(parada.getDataHoraReal()) : "Pendente",
                    parada.getDataHoraReal() != null ? "Concluída" : "Pendente"
                })
            );
        });
    }

    private void adicionarParada() {
        ParadaDialog dialog = new ParadaDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setParadaSavedListener(() -> loadData()); // Listener para atualização
        dialog.setVisible(true); // Exibir o diálogo
    }

    private void registrarChegada() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int codigo = (int) tableModel.getValueAt(row, 0);
        try {
            Parada.registrarChegada(codigo, Calendar.getInstance());
            loadData();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}