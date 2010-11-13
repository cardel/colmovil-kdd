/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author Gema
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
/**
 *
 * @author Gema
 */
public class StackedBarChart {
    double data [][];

   public StackedBarChart()
   {

   }
   //a este metodo le debe entrar un arreglo con los datos


   public BufferedImage createStackedBarChart(int cantidadNulos, int cantidadRegistros)
   {
       int n=0;
       data = new double[2][1];
       for(int i=0; i<1; i++)
       {
           data[0][i]=(double)(cantidadNulos);
           data[1][i]=(double)(cantidadRegistros-cantidadNulos);
           System.out.println("cantidad Reg: "+ cantidadRegistros +"  cantidadNulos: "+cantidadNulos );
       }
//       double[][] data = new double[][]{
//            {1100, 3000, 3200, 2650, 2999, 1000, 2000, 3000, 4000, 2000},
//            {1200, 3000, 2000, 2000, 3000, 2000, 4000, 2000, 1000, 3000}};
       CategoryDataset dataset = DatasetUtilities.createCategoryDataset("Team", "", data);
       JFreeChart chart= ChartFactory.createStackedBarChart(
               "", //titulo del grafico
               "", //etiqueta valores eje x
               "", //etiqueta valores eje y
               dataset, // datos
               PlotOrientation.VERTICAL, //orientacion del grafico
               false, //incluye leyenda?
               true, //incluye tooltips?
               false); //URLs?
               //ChartFactory.createScatterPlot(null, null, null, null, PlotOrientation.HORIZONTAL, true, true, true)
       CategoryPlot plot = chart.getCategoryPlot();
       BarRenderer render= (BarRenderer)plot.getRenderer();
       render.setMaximumBarWidth(.2);
       render.setBaseItemLabelGenerator(new CategoryLabelGenerator());
       //render.setItemLabelFont(new Font("Serif", Font.PLAIN,10));
       render.setBaseItemLabelsVisible(true);
//       plot.getRenderer().setSeriesPaint(0, new Color(30, 100, 175));
//       plot.getRenderer().setSeriesPaint(1, new Color(90, 190, 110));
       BufferedImage imagenGrafico= chart.createBufferedImage(348, 206);
       return imagenGrafico;
   }

}
