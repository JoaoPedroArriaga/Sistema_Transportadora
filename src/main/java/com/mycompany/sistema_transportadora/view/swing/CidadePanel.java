package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.sistema_transportadora.model.entidades.*;

public class CidadePanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Estado> estadoCombo;

    public CidadePanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        
        estadoCombo = new JComboBox<>();
        Estado.listarAtivos().forEach(estadoCombo::addItem);
        toolBar.add(new JLabel("Estado:"));
        toolBar.add(estadoCombo);
        
        JButton addBtn = new JButton("Adicionar");
        addBtn.addActionListener(e -> adicionarCidade());
        toolBar.add(addBtn);
        
        JButton removeBtn = new JButton("Remover");
        removeBtn.addActionListener(e -> removerCidade());
        toolBar.add(removeBtn);

        add(toolBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Nome", "Estado", "Ativa"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        Estado estado = (Estado) estadoCombo.getSelectedItem();
        Cidade.listarAtivas().stream()
            .filter(c -> c.getCodEstado() == estado.getCodigo())
            .forEach(c -> tableModel.addRow(new Object[]{
                c.getCodigo(), c.getNome(), estado.getNome(), c.isAtivo()
            }));
    }

    private void adicionarCidade() {
        new CidadeDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        loadData(); // Atualiza a tabela após cadastro
    }

    private void removerCidade() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Confirmar remoção?", "Confirmação", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Cidade.desativarCidade(codigo);
                loadData();
            }
        }
    }
}