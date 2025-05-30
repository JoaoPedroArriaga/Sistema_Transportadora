package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Veiculo;
import com.mycompany.sistema_transportadora.model.enums.StatusVeiculo;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;

public class VeiculoPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private final NumberFormat numberFormat;
    private JLabel statusLabel;

    public VeiculoPanel() {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(1);
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Barra de ferramentas
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton addBtn = createToolbarButton("Novo Veículo", "add.png");
        addBtn.addActionListener(e -> showVeiculoDialog());
        toolBar.add(addBtn);
        
        JButton manutencaoBtn = createToolbarButton("Registrar Manutenção", "maintenance.png");
        manutencaoBtn.addActionListener(e -> showManutencaoDialog());
        toolBar.add(manutencaoBtn);

        JButton desativarBtn = createToolbarButton("Desativar Veículo", "disable.png");
        desativarBtn.addActionListener(e -> desativarVeiculo());
        toolBar.add(desativarBtn);
        
        JButton refreshBtn = createToolbarButton("Atualizar", "refresh.png");
        refreshBtn.addActionListener(e -> loadData());
        toolBar.add(refreshBtn);

        add(toolBar, BorderLayout.NORTH);

        // Modelo da tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Tipo", "Placa", "Ano", "Capacidade (kg)", "Volume (m³)", "KM Rodados", "Última Manutenção", "Status"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0, 3 -> Integer.class;
                    case 4, 5, 6 -> Double.class;
                    case 7 -> String.class;
                    case 8 -> StatusVeiculo.class;
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
        table.setDefaultRenderer(StatusVeiculo.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (value instanceof StatusVeiculo) {
                    StatusVeiculo status = (StatusVeiculo) value;
                    setText(status.getDescricao());
                    
                    switch (status) {
                        case DISPONIVEL -> c.setBackground(new Color(220, 255, 220));
                        case EM_MANUTENCAO -> c.setBackground(new Color(255, 255, 200));
                        case RESERVADO -> c.setBackground(new Color(255, 229, 180));
                        case EM_ROTA -> c.setBackground(new Color(220, 230, 255));
                        case EM_ROTA_RETORNO -> c.setBackground(new Color(180, 200, 255));
                        case DESATIVADO -> c.setBackground(new Color(255, 220, 220));
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
        statusLabel = new JLabel(" Total de veículos: 0 ");
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
                for (Veiculo v : Veiculo.listarAtivos()) {
                    tableModel.addRow(new Object[]{
                        v.getCodigo(),
                        v.getTipo().toString(),
                        v.getPlacaFormatada(),
                        v.getAnoFabricacao(),
                        v.getCapacidadeCarga(),
                        v.getVolumeMaximoTransportavel(),
                        v.getKmRodados(),
                        (v.getUltimaManutencao() != null) ? DateUtils.formatDate(v.getUltimaManutencao()) : "Nunca",
                        v.getStatus()
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    statusLabel.setText(" Total de veículos: " + tableModel.getRowCount());
                    
                    if (tableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(
                            VeiculoPanel.this, 
                            "Nenhum veículo cadastrado!", 
                            "Informação", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        VeiculoPanel.this,
                        "Erro ao carregar dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }

    private void showVeiculoDialog() {
        VeiculoDialog dialog = new VeiculoDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        loadData();
    }

    private void showManutencaoDialog() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            new ManutencaoDialog((JFrame) SwingUtilities.getWindowAncestor(this), codigo).setVisible(true);
            loadData();
        } else {
            JOptionPane.showMessageDialog(
                this, 
                "Selecione um veículo para registrar manutenção", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    private void desativarVeiculo() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            int option = JOptionPane.showConfirmDialog(
                this, 
                "Tem certeza que deseja desativar este veículo?", 
                "Confirmar Desativação", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (option == JOptionPane.YES_OPTION) {
                try {
                    Veiculo.desativarVeiculo(codigo);
                    loadData();
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "Não foi possível desativar: " + ex.getMessage(), 
                        "Erro de Estado", 
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "Erro ao desativar veículo: " + ex.getMessage(), 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                this, 
                "Selecione um veículo para desativar", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}