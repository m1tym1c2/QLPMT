/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicmanagement;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Dell
 */
public class CreatePieChart extends JFrame {
    public CreatePieChart(String appTitle,String chartTitle){
        PieDataset dataset = createDataset();
        JFreeChart chart = creatChart(dataset, chartTitle);
        ChartPanel chartPanel = new ChartPanel (chart);
        
    }

    CreatePieChart() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private PieDataset createDataset() 
    {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Windows", 65);
        result.setValue("Linus", 15);
        result.setValue("Mac", 20);
        return result;
    }

    private JFreeChart creatChart(PieDataset dataset, String title) 
    {
        JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(0);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
    
}
