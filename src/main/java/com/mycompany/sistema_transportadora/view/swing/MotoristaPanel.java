package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;
import com.mycompany.sistema_transportadora.model.enums.*;

public class MotoristaPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;

    public MotoristaPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        
        JButton addBtn = new JButton("Novo Motorista");
        addBtn.addActionListener(e -> showMotoristaDialog());
        toolBar.add(addBtn);
        
        JButton statusBtn = new JButton("Alterar Status");
        statusBtn.addActionListener(e -> alterarStatus());
        toolBar.add(statusBtn);

        add(toolBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Nome", "CPF", "Status"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        Motorista.listarAtivos().forEach(m -> tableModel.addRow(new Object[]{
            m.getCodigo(), m.getNome(), m.getCpfFormatado(), m.getStatus()
        }));
    }

    private void showMotoristaDialog() {
        new MotoristaDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        loadData();
    }

    private void alterarStatus() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            Motorista motorista = Motorista.buscarPorCodigo(codigo);
            
            StatusMotorista novoStatus = (StatusMotorista) JOptionPane.showInputDialog(
                this, "Selecione o novo status:", "Alterar Status", 
                JOptionPane.QUESTION_MESSAGE, null, 
                StatusMotorista.values(), motorista.getStatus()
            );
            
            if (novoStatus != null) {
                motorista.atualizarStatus(novoStatus);
                loadData();
            }
        }
    }
}