package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;
import java.awt.*;
import com.mycompany.sistema_transportadora.utils.CSVUtils;

public class BackupDialog extends JDialog {
    
    public BackupDialog(JFrame parent) {
        super(parent, "Backup e Restauração", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton exportAllBtn = new JButton("Exportar Todos os Dados");
        exportAllBtn.addActionListener(e -> exportAll());
        
        JButton importAllBtn = new JButton("Importar Todos os Dados");
        importAllBtn.addActionListener(e -> importAll());
        
        mainPanel.add(exportAllBtn);
        mainPanel.add(importAllBtn);
        
        add(mainPanel);
    }
    
    private void exportAll() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            CSVUtils.exportarTudo(fc.getSelectedFile().getPath());
            JOptionPane.showMessageDialog(this, "Exportação concluída!");
        }
    }
    
    private void importAll() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (confirmAction("Isso substituirá todos os dados atuais. Continuar?")) {
                CSVUtils.importarTudo(fc.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(this, "Importação concluída!");
            }
        }
    }
    
    private boolean confirmAction(String message) {
        return JOptionPane.showConfirmDialog(
            this, message, "Confirmação", JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }
}