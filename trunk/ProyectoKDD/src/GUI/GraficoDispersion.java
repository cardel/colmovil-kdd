/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;
import java.awt.image.BufferedImage;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 *
 * @author Gema
 */
public class GraficoDispersion {

    public GraficoDispersion() {
    }

    public BufferedImage crearGraficodispersion( Vector<Integer> vectorDatosNumericos,String nombreAtributo)
    {
//        XYSeriesCollection dataset= new XYSeriesCollection();
//        XYSeries serie1= new XYSeries("Serie1");
//        serie1.add(1.0, 4.5);
//        serie1.add(4.4, 3.2);
//        dataset.addSeries(serie1);
//
//         XYSeries serie2= new XYSeries("Serie1");
//        serie2.add(3.2, 8.5);
//        serie2.add(4.9, 3.7);
//        dataset.addSeries(serie2);
        
        XYSeriesCollection dataset= new XYSeriesCollection();
        XYSeries serie1= new XYSeries("Serie1");
        for(int i=0; i<vectorDatosNumericos.size(); i++)
        {
            serie1.add(i,vectorDatosNumericos.elementAt(i));
        }
        dataset.addSeries(serie1);

        JFreeChart chart= ChartFactory.createScatterPlot("", //titulo
                                                        "", //label eje x
                                                        nombreAtributo, //label eje y
                                                        dataset, //datos
                                                        PlotOrientation.VERTICAL,
                                                        false, //leyenda
                                                        false, //tooltips
                                                        false); //URL
        BufferedImage imagenGrafico= chart.createBufferedImage(405, 211);
        return imagenGrafico;

    }


}
