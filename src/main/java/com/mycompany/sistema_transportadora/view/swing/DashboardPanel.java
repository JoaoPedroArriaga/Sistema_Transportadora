package com.mycompany.sistema_transportadora.view.swing;

import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mycompany.sistema_transportadora.model.entidades.Rota;

public class DashboardPanel extends JPanel {
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        initComponents();
        refreshData();
    }

    private void initComponents() {
        dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
            "Status das Rotas",          // Chart title
            "Status",                    // X-axis label
            "Quantidade",                // Y-axis label
            dataset,
            PlotOrientation.VERTICAL,
            false,                       // No legend
            true,                        // Tooltips
            false                        // URLs
        );

        // Styling the chart
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);

        add(chartPanel, BorderLayout.CENTER);
    }

    public void refreshData() {
        dataset.clear();
        Map<String, Integer> statusCounts = countStatuses();
        statusCounts.forEach((status, count) -> 
            dataset.addValue(count, "Rotas", status)
        );
    }

    private Map<String, Integer> countStatuses() {
        Map<String, Integer> counts = new HashMap<>();
        try {
            List<Rota> rotas = Rota.listarAtivas();
            for (Rota rota : rotas) {
                String status = rota.getStatus().toString();
                counts.put(status, counts.getOrDefault(status, 0) + 1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar dados das rotas: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return counts;
    }
}