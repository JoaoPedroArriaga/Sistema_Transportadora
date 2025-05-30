package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Cidade;
import com.mycompany.sistema_transportadora.model.entidades.Endereco;
import com.mycompany.sistema_transportadora.model.entidades.Estado;
import com.mycompany.sistema_transportadora.utils.EventManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class EnderecoPanel extends JPanel {
    
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<Estado> estadoCombo;
    private JComboBox<Cidade> cidadeCombo;
    private JLabel statusLabel;

    public EnderecoPanel() {
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
                EventManager.removeEstadoListener(EnderecoPanel.this::carregarEstados);
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
        
        // Atualizar cidades baseado no estado selecionado
        atualizarCidades();
    }
    
    private void atualizarCidades() {
        Cidade cidadeSelecionada = (Cidade) cidadeCombo.getSelectedItem();
        cidadeCombo.removeAllItems();
        
        Estado estado = (Estado) estadoCombo.getSelectedItem();
        if (estado != null) {
            List<Cidade> cidades = Cidade.listarAtivas();
            for (Cidade cidade : cidades) {
                if (cidade.getCodEstado() == estado.getCodigo()) {
                    cidadeCombo.addItem(cidade);
                }
            }
        }
        
        // Restaurar seleção anterior se possível
        if (cidadeSelecionada != null) {
            for (int i = 0; i < cidadeCombo.getItemCount(); i++) {
                Cidade item = cidadeCombo.getItemAt(i);
                if (item.getCodigo() == cidadeSelecionada.getCodigo()) {
                    cidadeCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void initComponents() {
        // Toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        estadoCombo = new JComboBox<>();
        estadoCombo.addActionListener(e -> atualizarCidades());
        
        cidadeCombo = new JComboBox<>();
        
        JButton addBtn = createToolbarButton("Novo Endereço", "add.png");
        addBtn.addActionListener(e -> adicionarEndereco());
        
        toolBar.add(new JLabel("Estado:"));
        toolBar.add(estadoCombo);
        toolBar.add(new JLabel("Cidade:"));
        toolBar.add(cidadeCombo);
        toolBar.addSeparator();
        toolBar.add(addBtn);

        add(toolBar, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(
            new Object[]{"Código", "Logradouro", "Cidade", "Estado"}, 0
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
        statusLabel = new JLabel(" Total de endereços: 0 ");
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
                List<Endereco> enderecos = Endereco.listarAtivos();
                for (Endereco endereco : enderecos) {
                    tableModel.addRow(new Object[]{
                        endereco.getCodigo(),
                        endereco.getLogradouro(),
                        endereco.getCidade().getNome(),
                        endereco.getEstado().getNome()
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    statusLabel.setText(" Total de endereços: " + tableModel.getRowCount());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        EnderecoPanel.this,
                        "Erro ao carregar dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }

    private void adicionarEndereco() {
        Estado estado = (Estado) estadoCombo.getSelectedItem();
        Cidade cidade = (Cidade) cidadeCombo.getSelectedItem();
        
        if (estado == null || cidade == null) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um estado e uma cidade",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        EnderecoDialog dialog = new EnderecoDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            estado,
            cidade
        );
        dialog.setVisible(true);
        loadData();
    }
}