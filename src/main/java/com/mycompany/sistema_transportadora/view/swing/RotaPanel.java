package com.mycompany.sistema_transportadora.view.swing;
import com.mycompany.sistema_transportadora.utils.DateUtils;

import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class RotaPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;

    public RotaPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        
        JButton addBtn = new JButton("Nova Rota");
        addBtn.addActionListener(e -> showRotaDialog());
        toolBar.add(addBtn);
        
        JButton iniciarBtn = new JButton("Iniciar Rota");
        iniciarBtn.addActionListener(e -> iniciarRota());
        toolBar.add(iniciarBtn);

        add(toolBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Origem", "Destino", "Status", "Partida"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        Rota.listarAtivas().forEach(r -> tableModel.addRow(new Object[]{
            r.getCodigo(), 
            r.getOrigem().getCidade().getNome(),
            r.getDestino().getCidade().getNome(),
            r.getStatus(),
            r.getDataPartida() != null ? DateUtils.formatDateTime(r.getDataPartida()) : "-"
        }));
    }

    private void showRotaDialog() {
        new RotaDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        loadData();
    }

    private void iniciarRota() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            try {
                Rota.iniciarRota(codigo, Calendar.getInstance());
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}