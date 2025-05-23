package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.model.enums.*;

public class CargaPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;

    public CargaPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        
        JButton addBtn = new JButton("Nova Carga");
        addBtn.addActionListener(e -> showCargaDialog());
        toolBar.add(addBtn);
        
        JButton statusBtn = new JButton("Atualizar Status");
        statusBtn.addActionListener(e -> atualizarStatus());
        toolBar.add(statusBtn);

        add(toolBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Tipo", "Descrição", "Peso", "Status"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        Carga.listarAtivas().forEach(c -> tableModel.addRow(new Object[]{
            c.getCodigo(), c.getTipo(), c.getDescricao(), c.getPeso() + " kg", c.getStatus()
        }));
    }

    private void showCargaDialog() {
        new CargaDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        loadData();
    }

    private void atualizarStatus() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            Carga carga = Carga.buscarCarga(codigo);
            
            StatusCarga novoStatus = (StatusCarga) JOptionPane.showInputDialog(
                this, "Selecione o novo status:", "Atualizar Status", 
                JOptionPane.QUESTION_MESSAGE, null, 
                StatusCarga.values(), carga.getStatus()
            );
            
            if (novoStatus != null) {
                carga.atualizarStatus(novoStatus);
                loadData();
            }
        }
    }
}