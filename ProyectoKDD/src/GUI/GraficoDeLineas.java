
package GUI;
import GUI.CategoryLabelGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * An example of a time series chart.  For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
public class GraficoDeLineas
{

    public GraficoDeLineas() {
    }

     public BufferedImage crearGrafico()
    {
        Vector<String> vectorCategoriaEjeX= new Vector<String>();
        Vector<Double> vectorValoresEjeY= new Vector<Double>();
        // categorías
        String series1 = "Consumo";
        int tamaNoVector=18;

        for(int i=0; i<tamaNoVector; i++)
        {
            String operador= "Region"+i;
            vectorCategoriaEjeX.addElement(operador);
            System.out.println("categoria region: "+vectorCategoriaEjeX.elementAt(i));
        }

        vectorValoresEjeY.addElement(1500000.0);
        vectorValoresEjeY.addElement(178000.0);
        vectorValoresEjeY.addElement(2000000.0);
        vectorValoresEjeY.addElement(45000000.0);
        vectorValoresEjeY.addElement(55300000.0);
        vectorValoresEjeY.addElement(670000.0);
        vectorValoresEjeY.addElement(300000.0);
        vectorValoresEjeY.addElement(7671000.0);
        vectorValoresEjeY.addElement(7671000.0);
        vectorValoresEjeY.addElement(7671000.0);
        vectorValoresEjeY.addElement(7671000.0);
        vectorValoresEjeY.addElement(5391000.0);
        vectorValoresEjeY.addElement(25600000.0);
        vectorValoresEjeY.addElement(7671000.0);
        vectorValoresEjeY.addElement(2897000.0);
        vectorValoresEjeY.addElement(7671000.0);
        vectorValoresEjeY.addElement(3560845.0);
        vectorValoresEjeY.addElement(7925000.0);


        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i=0; i<tamaNoVector; i++)
        {
            dataset.addValue(vectorValoresEjeY.elementAt(i), series1, vectorCategoriaEjeX.elementAt(i));
        }

         //create the chart...
        JFreeChart chart = ChartFactory.createLineChart(
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
        LineAndShapeRenderer renderer= (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setFillPaint(Color.white);
        
        plot.getRenderer().setSeriesPaint(0, new Color(123, 191, 72));
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));//ubicar el label de la categoria del eje x
        BufferedImage imagenGrafico= chart.createBufferedImage(745, 323);// crea la imagen del chart
        return imagenGrafico;

    }

  
}
