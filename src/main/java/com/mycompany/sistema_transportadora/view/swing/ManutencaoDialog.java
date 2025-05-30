package com.mycompany.sistema_transportadora.view.swing;

import com.mycompany.sistema_transportadora.model.entidades.Veiculo;
import com.mycompany.sistema_transportadora.utils.DateUtils;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Calendar;

public class ManutencaoDialog extends JDialog {
    
    private JSpinner dataSpinner;
    private JTextField tipoServicoField;
    private JFormattedTextField custoField;
    private JFormattedTextField kmRodadosField;
    private int codVeiculo;
    
    public ManutencaoDialog(JFrame parent, int codVeiculo) {
        super(parent, "Registrar Manutenção", true);
        this.codVeiculo = codVeiculo;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Veículo
        mainPanel.add(new JLabel("Veículo:"));
        Veiculo veiculo = Veiculo.buscarPorCodigo(codVeiculo);
        JLabel veiculoLabel = new JLabel(veiculo.getPlacaFormatada() + " (Cód: " + veiculo.getCodigo() + ")");
        mainPanel.add(veiculoLabel);
        
        // Data
        mainPanel.add(new JLabel("Data:"));
        dataSpinner = new JSpinner(new SpinnerDateModel());
        dataSpinner.setEditor(new JSpinner.DateEditor(dataSpinner, "dd/MM/yyyy HH:mm"));
        mainPanel.add(dataSpinner);
        
        // Quilometragem
        mainPanel.add(new JLabel("Quilometragem Atual:"));
        kmRodadosField = new JFormattedTextField(createNumberFormatter());
        kmRodadosField.setValue(veiculo.getKmRodados());
        mainPanel.add(kmRodadosField);
        
        // Tipo Serviço
        mainPanel.add(new JLabel("Tipo Serviço:"));
        tipoServicoField = new JTextField();
        mainPanel.add(tipoServicoField);
        
        // Custo
        mainPanel.add(new JLabel("Custo (R$):"));
        custoField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        custoField.setValue(0.0);
        mainPanel.add(custoField);
        
        // Botões
        JButton saveBtn = new JButton("Salvar");
        saveBtn.addActionListener(e -> salvarManutencao());
        
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private NumberFormatter createNumberFormatter() {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(1);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setAllowsInvalid(false);
        return formatter;
    }
    
    private void salvarManutencao() {
        try {
            // Obter dados do formulário
            Calendar data = Calendar.getInstance();
            data.setTime(((java.util.Date) dataSpinner.getValue()));
            
            String tipoServico = tipoServicoField.getText().trim();
            double custo = ((Number) custoField.getValue()).doubleValue();
            double kmRodados = ((Number) kmRodadosField.getValue()).doubleValue();
            
            // Validar campos
            if (tipoServico.isEmpty()) {
                throw new IllegalArgumentException("Tipo de serviço não pode ser vazio");
            }
            if (custo <= 0) {
                throw new IllegalArgumentException("Custo deve ser maior que zero");
            }
            if (kmRodados < 0) {
                throw new IllegalArgumentException("Quilometragem não pode ser negativa");
            }
            
            // Registrar manutenção
            Veiculo.registrarManutencao(
                codVeiculo, 
                data, 
                tipoServico, 
                custo, 
                kmRodados
            );
            
            JOptionPane.showMessageDialog(
                this, 
                "Manutenção registrada com sucesso!\n" +
                "Data: " + DateUtils.formatDateTime(data) + "\n" +
                "KM: " + kmRodados + "\n" +
                "Custo: " + NumberFormat.getCurrencyInstance().format(custo),
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this, 
                "Valor numérico inválido!", 
                "Erro de Formato", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                this, 
                e.getMessage(), 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, 
                "Erro ao registrar manutenção: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}