package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class VeiculoPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;

    public VeiculoPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        
        JButton addBtn = new JButton("Novo Veículo");
        addBtn.addActionListener(e -> showVeiculoDialog());
        toolBar.add(addBtn);
        
        JButton manutencaoBtn = new JButton("Registrar Manutenção");
        manutencaoBtn.addActionListener(e -> showManutencaoDialog());
        toolBar.add(manutencaoBtn);

        add(toolBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Tipo", "Placa", "Ano", "KM", "Status"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        Veiculo.listarAtivos().forEach(v -> tableModel.addRow(new Object[]{
            v.getCodigo(), v.getTipo(), v.getPlaca(), 
            v.getKmRodados(), v.getStatus()
        }));
    }

    private void showVeiculoDialog() {
        new VeiculoDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        loadData();
    }

    private void showManutencaoDialog() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            new ManutencaoDialog((JFrame) SwingUtilities.getWindowAncestor(this), codigo).setVisible(true);
            loadData();
        }
    }
}