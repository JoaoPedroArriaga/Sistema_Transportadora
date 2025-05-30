package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Cidade;
import com.mycompany.sistema_transportadora.model.entidades.Estado;
import com.mycompany.sistema_transportadora.utils.EventManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class CidadePanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Estado> estadoCombo;
    private JLabel statusLabel;

    public CidadePanel() {
        setLayout(new BorderLayout());
        initComponents();
        carregarEstados();
        loadData();
        registrarListeners();
    }

    private void registrarListeners() {
        // Registrar para receber notificações de mudança
        EventManager.addEstadoListener(this::carregarEstados);
        
        // Remover listener quando o painel for fechado
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                EventManager.removeEstadoListener(CidadePanel.this::carregarEstados);
            }
        });
    }
    
    private void carregarEstados() {
        Estado estadoSelecionado = (Estado) estadoCombo.getSelectedItem();
        estadoCombo.removeAllItems();
        
        List<Estado> estados = Estado.listarAtivos();
        for (Estado estado : estados) {
            estadoCombo.addItem(estado);
        }
        
        // Restaurar seleção anterior se possível
        if (estadoSelecionado != null) {
            for (int i = 0; i < estadoCombo.getItemCount(); i++) {
                Estado item = estadoCombo.getItemAt(i);
                if (item.getCodigo() == estadoSelecionado.getCodigo()) {
                    estadoCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Recarregar dados da tabela
        loadData();
    }

    private void initComponents() {
        // Toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        estadoCombo = new JComboBox<>();
        
        JButton addBtn = createToolbarButton("Adicionar", "add.png");
        addBtn.addActionListener(e -> adicionarCidade());
        
        JButton removeBtn = createToolbarButton("Remover", "remove.png");
        removeBtn.addActionListener(e -> removerCidade());

        toolBar.add(new JLabel("Estado:"));
        toolBar.add(estadoCombo);
        toolBar.addSeparator();
        toolBar.add(addBtn);
        toolBar.add(removeBtn);

        add(toolBar, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Nome", "Estado", "Ativa"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        // Status bar
        statusLabel = new JLabel(" Total de cidades: 0 ");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
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
                Estado estadoSelecionado = (Estado) estadoCombo.getSelectedItem();
                if (estadoSelecionado != null) {
                    List<Cidade> cidades = Cidade.listarAtivas();
                    for (Cidade cidade : cidades) {
                        if (cidade.getCodEstado() == estadoSelecionado.getCodigo()) {
                            tableModel.addRow(new Object[]{
                                cidade.getCodigo(),
                                cidade.getNome(),
                                estadoSelecionado.getNome(),
                                cidade.isAtivo() ? "Sim" : "Não"
                            });
                        }
                    }
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    statusLabel.setText(" Total de cidades: " + tableModel.getRowCount());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        CidadePanel.this,
                        "Erro ao carregar dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }

    private void adicionarCidade() {
        Estado estadoSelecionado = (Estado) estadoCombo.getSelectedItem();
        if (estadoSelecionado == null) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um estado antes de adicionar uma cidade",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        CidadeDialog dialog = new CidadeDialog((JFrame) SwingUtilities.getWindowAncestor(this), estadoSelecionado);
        dialog.setVisible(true);
        loadData();
    }

    private void removerCidade() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codigo = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Tem certeza que deseja desativar esta cidade?", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Cidade.desativarCidade(codigo);
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
                "Selecione uma cidade para desativar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
