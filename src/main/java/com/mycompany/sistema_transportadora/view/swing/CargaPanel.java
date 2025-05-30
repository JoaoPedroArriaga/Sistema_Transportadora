package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Carga;
import com.mycompany.sistema_transportadora.model.enums.StatusCarga;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;

public class CargaPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private final NumberFormat numberFormat;

    public CargaPanel() {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Barra de ferramentas
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton addBtn = createToolbarButton("Nova Carga", "add.png");
        addBtn.addActionListener(e -> showCargaDialog());
        toolBar.add(addBtn);
        
        JButton statusBtn = createToolbarButton("Atualizar Status", "refresh.png");
        statusBtn.addActionListener(e -> atualizarStatus());
        toolBar.add(statusBtn);
        
        JButton armazenarBtn = createToolbarButton("Armazenar Carga", "warehouse.png");
        armazenarBtn.addActionListener(e -> armazenarCarga());
        toolBar.add(armazenarBtn);
        
        JButton refreshBtn = createToolbarButton("Atualizar", "refresh.png");
        refreshBtn.addActionListener(e -> loadData());
        toolBar.add(refreshBtn);

        add(toolBar, BorderLayout.NORTH);

        // Modelo da tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Tipo", "Descrição", "Peso (kg)", "Volume (m³)", "Quantidade", "Status"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0, 5 -> Integer.class;
                    case 3, 4 -> Double.class;
                    case 6 -> StatusCarga.class;
                    default -> String.class;
                };
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Renderizador para valores numéricos
        table.setDefaultRenderer(Double.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                
                if (value instanceof Double) {
                    label.setText(numberFormat.format(value));
                    label.setHorizontalAlignment(SwingConstants.RIGHT);
                }
                return label;
            }
        });
        
        // Renderizador para status com cores
        table.setDefaultRenderer(StatusCarga.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (value instanceof StatusCarga) {
                    StatusCarga status = (StatusCarga) value;
                    setText(status.toString());
                    
                    switch (status) {
                        case PENDENTE -> c.setBackground(new Color(255, 255, 200)); // amarelo claro
                        case ARMAZENADA -> c.setBackground(new Color(220, 230, 255)); // azul claro
                        case EM_TRANSPORTE -> c.setBackground(new Color(180, 200, 255)); // azul mais forte
                        case ENTREGUE -> c.setBackground(new Color(220, 255, 220)); // verde claro
                        case EXTRAVIADA, CANCELADA -> c.setBackground(new Color(255, 220, 220)); // vermelho claro
                    }
                    
                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                    }
                }
                return c;
            }
        });
        
        // Configuração do scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de status
        statusLabel = new JLabel(" Total de cargas: 0 ");
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
            // Ícone não encontrado, usar apenas texto
        }
        return button;
    }

    private void loadData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                tableModel.setRowCount(0);
                for (Carga c : Carga.listarAtivas()) {
                    tableModel.addRow(new Object[]{
                        c.getCodigo(),
                        c.getTipo().toString(),
                        c.getDescricao(),
                        c.getPeso(),
                        c.getVolume(),
                        c.getQuantidade(),
                        c.getStatus()
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    statusLabel.setText(" Total de cargas: " + tableModel.getRowCount());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        CargaPanel.this,
                        "Erro ao carregar dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }

    private void showCargaDialog() {
        CargaDialog dialog = new CargaDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        loadData();
    }

    private void atualizarStatus() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            Carga carga = Carga.buscarCarga(codigo);
            
            StatusCarga novoStatus = (StatusCarga) JOptionPane.showInputDialog(
                this, 
                "Selecione o novo status:", 
                "Atualizar Status", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                StatusCarga.values(), 
                carga.getStatus()
            );
            
            if (novoStatus != null) {
                try {
                    carga.atualizarStatus(novoStatus);
                    loadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Erro ao atualizar status: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Selecione uma carga para atualizar o status",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    private void armazenarCarga() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            Carga carga = Carga.buscarCarga(codigo);
            
            if (carga.getStatus() == StatusCarga.PENDENTE) {
                try {
                    carga.atualizarStatus(StatusCarga.ARMAZENADA);
                    loadData();
                    JOptionPane.showMessageDialog(
                        this,
                        "Carga armazenada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Erro ao armazenar carga: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Apenas cargas PENDENTES podem ser armazenadas",
                    "Status Inválido",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Selecione uma carga para armazenar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}