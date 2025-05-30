package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Estado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstadoPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    
    public EstadoPanel() {
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        // Barra de ferramentas
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton addBtn = createToolbarButton("Adicionar", "add.png");
        addBtn.addActionListener(e -> adicionarEstado());
        toolBar.add(addBtn);
        
        JButton removeBtn = createToolbarButton("Remover", "remove.png");
        removeBtn.addActionListener(e -> desativarEstado());
        toolBar.add(removeBtn);
        
        add(toolBar, BorderLayout.NORTH);
        
        // Tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Nome", "Ativo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        
        // Painel de rolagem
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de status
        statusLabel = new JLabel(" Total de estados: 0 ");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private JButton createToolbarButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setMargin(new Insets(5, 10, 5, 10));
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + iconName));
            button.setIcon(icon);
        } catch (Exception e) {
            // Ícone não encontrado
        }
        return button;
    }
    
    private void loadData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                tableModel.setRowCount(0);
                List<Estado> estados = Estado.listarAtivos();
                for (Estado estado : estados) {
                    tableModel.addRow(new Object[]{
                        estado.getCodigo(),
                        estado.getNome(),
                        estado.isAtivo() ? "Sim" : "Não"
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    statusLabel.setText(" Total de estados: " + tableModel.getRowCount());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        EstadoPanel.this,
                        "Erro ao carregar dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }
    
    private void adicionarEstado() {
        EstadoDialog dialog = new EstadoDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        loadData();
    }
    
    private void desativarEstado() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(
                this, 
                "Deseja desativar este estado?", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                
                try {
                    Estado.desativarEstado(codigo);
                    loadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Erro ao desativar: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um estado",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
