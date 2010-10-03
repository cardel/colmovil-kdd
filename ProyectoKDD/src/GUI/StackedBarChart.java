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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
/**
 *
 * @author Gema
 */
public class StackedBarChart {

   public StackedBarChart()
   {

   }
   //a este metodo le debe entrar un arreglo con los datos


   public BufferedImage createStackedBarChart()
   {
       double[][] data = new double[][]{
            {1100, 3000, 3200, 2650, 2999, 1000, 2000, 3000, 4000, 2000},
            {1200, 3000, 2000, 2000, 3000, 2000, 4000, 2000, 1000, 3000}};
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

//       CategoryPlot plot = chart.getCategoryPlot();
//       plot.getRenderer().setSeriesPaint(0, new Color(30, 100, 175));
//       plot.getRenderer().setSeriesPaint(1, new Color(90, 190, 110));
       BufferedImage imagenGrafico= chart.createBufferedImage(335, 205);
       return imagenGrafico;
   }

}
