package com.mycompany.sistema_transportadora.view.swing;

import javax.swing.*;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        super("Sistema Transportadora");
        initUI();
        setupFrame();
    }
    
    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        
        setJMenuBar(createMenuBar());
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", new DashboardPanel());
        tabbedPane.addTab("Estados", new EstadoPanel());
        tabbedPane.addTab("Cidades", new CidadePanel());
        tabbedPane.addTab("Endereços", new EnderecoPanel());
        tabbedPane.addTab("Veículos", new VeiculoPanel());
        tabbedPane.addTab("Cargas", new CargaPanel());
        tabbedPane.addTab("Rotas", new RotaPanel());
        
        add(tabbedPane);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem exitItem = new JMenuItem("Sair");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        JMenu toolsMenu = new JMenu("Ferramentas");
        JMenuItem backupItem = new JMenuItem("Backup/Restauração");
        backupItem.addActionListener(e -> showBackupDialog());
        toolsMenu.add(backupItem);
        
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        
        return menuBar;
    }
    
    private void setupFrame() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    
    private void showBackupDialog() {
        new BackupDialog(this).setVisible(true);
    }
}