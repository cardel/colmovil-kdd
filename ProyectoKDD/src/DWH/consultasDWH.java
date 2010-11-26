/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DWH;

import GUI.CategoryLabelGenerator;
import GUI.GraficoDeBarras;
import Papelera.FachadaBD;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Carlos
 */
public class ConsultasDWH {

    public ConsultasDWH() {
    }

    public BufferedImage editarParametros(int numeroConsulta, int hora, int dia, int mes, int anio) {

        FachadaBD objFachadaBD = new FachadaBD();
        objFachadaBD.abrirConexion();
        BufferedImage imagenGrafico = null;
        try {
            String consulta;
            String restriccionPorHora = "";
            String restriccionPorDia = "";
            String restriccionPorMes = "";
            String restriccionPorAnnio = "";

            //Parametros
            switch (hora) {
                //Todas
                case 0:
                    restriccionPorHora = "";
                    break;
                //Madrugada 12am a 6am
                case 1:
                    restriccionPorHora = " and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))>0 and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))<6";
                    break;
                //    Mañana 6am a 12pm
                case 2:
                    restriccionPorHora = " and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))>6 and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))<12";
                    break;

                //Tarde  12pm a 6pm
                case 3:
                    restriccionPorHora = " and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))>12 and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))<18";
                    break;
                //  Noche  6pm a 12am
                case 4:
                    restriccionPorHora = " and HOUR(DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d %H:%i:%S'))>18";
                    break;
            }


            switch(dia) {
                /*
                 * Todas*/
            case 0:
                restriccionPorDia = "";
                break;
               /*
                Fines de semana*/
                //DATE_FORMAT(DATE_ADD( startdate, INTERVAL ctr SECOND),'%W'), DATE_FORMAT(DATE_ADD( startdate, INTERVAL ctr SECOND), '%M')
                case 1:

                restriccionPorDia =" and DATE_FORMAT(fecha_inicio,'%W')='Viernes' or DATE_FORMAT(DATE_ADD( startdate, INTERVAL ctr SECOND),'%W')='Sabado' or DATE_FORMAT(DATE_ADD( startdate, INTERVAL ctr SECOND),'%W')='Domingo') ";
                /*
                Lunes
                /*
                Martes
                Miercoles
                Jueves
                Viernes
                Sabado
                Domingo
                Año Nuevo
                Reyes Magos
                Dia del Trabajo
                Semana Santa
                Grito de Independencia
                Batalla de Boyaca
                Dia de la Raza
                Independencia de Cartagena
                Dia de Amor y Amistad
                Halloween
                Dia de las velitas
                Navidad

                 */
                
            }

            if (mes == 0) {
                restriccionPorMes = "";
            }

            if (anio == 0) {
                restriccionPorAnnio = "";
            }
            switch (numeroConsulta) {
                case 0:
                    consulta = "select nombre, count(*) as total_llamadas from (select b.nombre from llamada a, operador b where a.id_operador_destino=b.id_operador and a.utilizo_roaming='NO' " + restriccionPorHora + restriccionPorDia+" ) as c group by nombre";
                    break;
                default:
                    return null;
            }
            System.out.println(consulta);
            ResultSet resultado = objFachadaBD.realizarConsultaABaseDeDatos(consulta);
            GraficoDeBarras graficoDeBarras = new GraficoDeBarras();

            Vector<String> vectorCategoriaEjeX = new Vector<String>();
            Vector<Double> vectorValoresEjeY = new Vector<Double>();

            String series1 = "Operadores Locales";

            while (resultado.next()) {
                vectorCategoriaEjeX.add(resultado.getString(1));
                vectorValoresEjeY.add(Double.parseDouble(resultado.getString(2)));
            }

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            int tamaNoVector = vectorCategoriaEjeX.size();
            for (int i = 0; i < tamaNoVector; i++) {
                dataset.addValue(vectorValoresEjeY.elementAt(i), series1, vectorCategoriaEjeX.elementAt(i));
            }

            //create the chart...
            JFreeChart chart = ChartFactory.createBarChart(
                    "Grafico de Barras operadores", // chart title
                    "Operador", // domain axis label
                    "Frecuencia", // range axis label
                    dataset, // data
                    PlotOrientation.VERTICAL, // orientation
                    false, // include legend
                    true, // tooltips?
                    false // URLs?
                    );
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer render = (BarRenderer) plot.getRenderer();
            render.setMaximumBarWidth(.1);
            render.setBaseItemLabelGenerator(new CategoryLabelGenerator());
            plot.getRenderer().setSeriesPaint(0, new Color(61, 111, 167));// asignar color a barras
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));//ubicar el label de la categoria del eje x
            imagenGrafico = chart.createBufferedImage(745, 323);// crea la imagen del chart

            //Hora

            //Nombre dia

            //nombreMes


            //nombreAnio

            //Fiesta

            //Festivo

            //Fin de semana
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            objFachadaBD.cerrarConexion();

        }
        return imagenGrafico;

    }
}
