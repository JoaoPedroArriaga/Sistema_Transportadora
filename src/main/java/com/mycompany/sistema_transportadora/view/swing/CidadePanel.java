package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Cidade;
import com.mycompany.sistema_transportadora.model.entidades.Estado;
import com.mycompany.sistema_transportadora.utils.EventManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
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
        registrarListeners();
    }

    private void registrarListeners() {
        EventManager.addEstadoListener(this::carregarEstados);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                EventManager.removeEstadoListener(CidadePanel.this::carregarEstados);
            }
        });
    }
    
    private void carregarEstados() {
        // Guarda os listeners atuais do combo
        ActionListener[] listeners = estadoCombo.getActionListeners();
        // Remove temporariamente os listeners para evitar disparos durante a atualização
        for (ActionListener listener : listeners) {
            estadoCombo.removeActionListener(listener);
        }
        
        Estado estadoSelecionado = (Estado) estadoCombo.getSelectedItem();
        estadoCombo.removeAllItems();
        
        List<Estado> estados = Estado.listarAtivos();
        for (Estado estado : estados) {
            estadoCombo.addItem(estado);
        }
        
        if (estadoSelecionado != null) {
            for (int i = 0; i < estadoCombo.getItemCount(); i++) {
                Estado item = estadoCombo.getItemAt(i);
                if (item.getCodigo() == estadoSelecionado.getCodigo()) {
                    estadoCombo.setSelectedIndex(i);
                    break;
                }
            }
        } else if (estados.size() > 0) {
            estadoCombo.setSelectedIndex(0);
        }
        
        // Restaura os listeners
        for (ActionListener listener : listeners) {
            estadoCombo.addActionListener(listener);
        }
        
        // Recarrega as cidades para o estado selecionado
        loadData();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        estadoCombo = new JComboBox<>();
        // Adiciona listener para recarregar a tabela quando o estado mudar
        estadoCombo.addActionListener(e -> loadData());
        
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
                tableModel.setRowCount(0); // Limpa a tabela
                Estado estadoSelecionado = (Estado) estadoCombo.getSelectedItem();
                if (estadoSelecionado != null) {
                    // Filtra as cidades pelo estado selecionado
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
        loadData(); // Recarrega os dados após fechar o diálogo
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
                    loadData(); // Recarrega os dados após desativar
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