package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.Estado;

public class EstadoPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    
    public EstadoPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        JButton addBtn = new JButton("Adicionar");
        JButton removeBtn = new JButton("Remover");
        
        addBtn.addActionListener(e -> adicionarEstado());
        removeBtn.addActionListener(e -> desativarEstado());
        
        toolBar.add(addBtn);
        toolBar.add(removeBtn);
        add(toolBar, BorderLayout.NORTH);
        
        tableModel = new DefaultTableModel(new Object[]{"Código", "Nome", "Ativo"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        Estado.listarAtivos().forEach(estado -> 
            tableModel.addRow(new Object[]{
                estado.getCodigo(),
                estado.getNome(),
                estado.isAtivo() ? "Sim" : "Não"
            })
        );
    }
    
    private void adicionarEstado() {
        new EstadoDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        loadData(); // Atualiza a tabela após cadastro
    }
    
    private void desativarEstado() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            if (confirmDialog("Deseja realmente desativar este estado?")) {
                Estado.desativarEstado(codigo);
                loadData();
            }
        }
    }
    
    private boolean confirmDialog(String message) {
        return JOptionPane.showConfirmDialog(
            this, message, "Confirmação", JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }
}