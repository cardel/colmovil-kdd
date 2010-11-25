/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;


//package org.jfree.chart.demo;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a bar chart.
 */
public class GraficoDeBarras {
    Vector<String> categoriaEjeX;

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public GraficoDeBarras()
    {

    }


    public BufferedImage crearGrafico()
    {
        //estos dos vectores son los que le entrarían a la funcion crear grafico
        Vector<String> vectorCategoriaEjeX= new Vector<String>();
        Vector<Double> vectorValoresEjeY= new Vector<Double>();
        // categorías
        String series1 = "Operadores";
        int tamaNoVector=18;

        //********************** esto es para llenar los vectores
        for(int i=0; i<tamaNoVector; i++)
        {
            String operador= "Operador"+i;
            vectorCategoriaEjeX.addElement(operador);
            System.out.println("categoria operador: "+vectorCategoriaEjeX.elementAt(i));
        }

        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(5214.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(5391.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7671.0);
        vectorValoresEjeY.addElement(7925.0);
        //******************************************************

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i=0; i<tamaNoVector; i++)
        {
            dataset.addValue(vectorValoresEjeY.elementAt(i), series1, vectorCategoriaEjeX.elementAt(i));
        }
        
         //create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart",       // chart title
            "Operador",               // domain axis label
            "Frecuencia",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            false,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );


        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer render= (BarRenderer)plot.getRenderer();
        render.setMaximumBarWidth(.1);
        render.setBaseItemLabelGenerator(new CategoryLabelGenerator());
        plot.getRenderer().setSeriesPaint(0, new Color(61, 111, 167));// asignar color a barras
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));//ubicar el label de la categoria del eje x
        BufferedImage imagenGrafico= chart.createBufferedImage(745, 323);// crea la imagen del chart
        return imagenGrafico;

    }

}

