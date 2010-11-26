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
import weka.filters.unsupervised.instance.ReservoirSample;

/**
 *
 * @author Carlos
 */
public class ConsultasDWH {

    public ConsultasDWH() {
    }

    public BufferedImage editarParametros(int numeroConsulta, int hora, int dia, int mes, String anio, String pais) {

        FachadaBD objFachadaBD = new FachadaBD();
        objFachadaBD.abrirConexion();
        BufferedImage imagenGrafico = null;
        try {
            String consulta = "";
            String restriccionPorHora = "";
            String restriccionPorDia = "";
            String restriccionPorMes = "";
            String restriccionPorAnnio = "";
            String titulo = "";
            String titulobarra = "";
            String tituloEjeX = "";
            String consultaPais = "";
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
            //Consulta al DW

            String consultaDW = "";
            switch (dia) {
                /*
                 * Todas*/
                case 0:
                    restriccionPorDia = "";
                    break;
                /* Fines de semana*/
                case 1:

                    restriccionPorDia = " and (DATE_FORMAT(a.fecha_inicio,'%W')='Friday' or DATE_FORMAT(a.fecha_inicio,'%W')='Saturday' or DATE_FORMAT(a.fecha_inicio,'%W')='Sunday') ";
                    break;
                /*   Lunes*/
                case 2:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Monday' ";
                    break;
                //Martes
                case 3:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Tuesday' ";
                    break;
                //Miercoles
                case 4:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Wednesday' ";
                    break;
                //Jueves
                case 5:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Thursday' ";
                    break;
                //Viernes
                case 6:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Friday' ";
                    break;
                //Sabado
                case 7:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Saturday' ";
                    break;
                //Domingo
                case 8:
                    restriccionPorDia = " and DATE_FORMAT(a.fecha_inicio,'%W')='Sunday";
                    break;
                /*
                 * SE CONSULTA LA DIMENSION FECHA DE LA BODEGA DE DATOS
                 */


                //Año Nuevo
                case 9:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'anio nuevo'";
                    break;
                //Reyes Magos
                case 10:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Dia de Reyes'";
                    break;
                //Dia del Trabajo
                case 11:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Dia del trabajo'";
                    break;
                //Semana Santa
                case 12:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Semana Santa'";
                    break;
                //Grito de Independencia
                case 13:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Dia de la independencia'";
                    break;
                //Batalla de Boyaca
                case 14:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Batalla de Boyaca'";
                    break;
                //Dia de la Raza
                case 15:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Dia de la raza'";
                    break;
                //Independencia de Cartagena
                case 16:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Indepedencia de Cartagena'";
                    break;
                //Halloween
                case 17:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Halloween'";
                    break;
                //Dia de las velitas
                case 18:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Dia de la Virgen'";
                    break;
                //Navidad
                case 19:
                    consultaDW = "select dia, mes, anio from dw_fecha where nombre_fiesta = 'Navidad'";
                    break;
            }
            if (!consultaDW.equals("")) {
                // System.out.println(consultaDW);
                FachadaBD objFachadaBDDW = new FachadaBD();
                objFachadaBDDW.abrirConexion();

                ResultSet resultadoDW = objFachadaBDDW.realizarConsultaABaseDeDatos(consultaDW);

                resultadoDW.next();
                restriccionPorDia = " and (DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d') = DATE_FORMAT('" + resultadoDW.getString(3) + "-" + resultadoDW.getString(2) + "-" + resultadoDW.getString(1) + "', '%Y-%m-%d') ";

                while (resultadoDW.next()) {
                    restriccionPorDia += " or DATE_FORMAT(a.fecha_inicio, '%Y-%m-%d') = DATE_FORMAT('" + resultadoDW.getString(3) + "-" + resultadoDW.getString(2) + "-" + resultadoDW.getString(1) + "', '%Y-%m-%d') ";
                }
                restriccionPorDia += ") ";
                objFachadaBDDW.cerrarConexion();
                // System.out.println(restriccionPorDia);
            }

            switch (mes) {
                //Todos
                case 0:
                    restriccionPorMes = "";
                    break;
                //Enero
                case 1:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'January' ";
                    break;
                //Febrero
                case 2:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'February' ";
                    break;
                //Marzo
                case 3:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'March' ";
                    break;
                //Abril
                case 4:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'April' ";
                    break;
                //Mayo
                case 5:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'May' ";
                    break;
                //Junio
                case 6:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'June' ";
                    break;
                //Julio
                case 7:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'July' ";
                    break;
                //Agosto
                case 8:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'August' ";
                    break;
                //Septiembre
                case 9:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'September' ";
                    break;
                //Octubre
                case 10:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'October' ";
                    break;
                //Noviembre
                case 11:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'November' ";
                    break;
                //Diciembre
                case 12:
                    restriccionPorMes = " and DATE_FORMAT(a.fecha_inicio,'%M') = 'December' ";
                    break;

            }

            if (anio.equals("Todos")) {
                restriccionPorAnnio = "";
            }
            if (anio.equals("2008")) {
                restriccionPorAnnio = " and DATE_FORMAT(a.fecha_inicio,'%Y')='2008' ";
            }
            if (anio.equals("2009")) {
                restriccionPorAnnio = " and DATE_FORMAT(a.fecha_inicio,'%Y')='2009' ";
            }

            if (pais.equals("Todos")) {
                consultaPais = "";
            } else {
                consultaPais = "and b.pais_donde_opera ='" + pais + "' ";
            }
            switch (numeroConsulta) {

                // Operadores nacionales mas utilizados
                case 0:
                    consulta = "select nombre, count(*) as total_llamadas from (select b.nombre from llamada a, operador b where a.id_operador_destino=b.id_operador and a.utilizo_roaming='NO' " + restriccionPorHora + restriccionPorDia + restriccionPorMes + restriccionPorAnnio + " ) as c group by nombre";
                    titulo = "Operadores Nacionales";
                    titulobarra = "Grafico operadores";
                    tituloEjeX = "Operador";
                    break;
                //Operadores extranjeros mas usados para roaming
                case 1:
                    consulta = "select nombre, count(*) as total_llamadas from (select b.nombre_operador as nombre from llamada a, operador_roaming b where a.operador_roaming=b.id_operador_roaming and a.utilizo_roaming='SI' " + restriccionPorHora + restriccionPorDia + restriccionPorMes + restriccionPorAnnio +consultaPais+ " ) as c group by nombre";
                    titulo = "Operadores Internacionales";
                    titulobarra = "Grafico operadores";
                    tituloEjeX = "Operador";
                    break;
                //Operadores extranjeros con mejores tarifas de roamming
                case 2:
                    if (pais.equals("Todos")) {
                        consulta = "select nombre_operador, tarifa_voz_por_minuto from operador_roaming group by nombre_operador having min(tarifa_voz_por_minuto)";

                    } else {
                        consulta = "select nombre_operador, tarifa_voz_por_minuto from operador_roaming where pais_donde_opera='"+pais+"'";
                    }
                    titulo = "Tarifas operadores " + pais;
                    titulobarra = "Grafico operadores";
                    tituloEjeX = "Operador";
                    break;
                //Franjas de mayor uso
                case 3:
                    consulta = "select hora, sum(duracion_segundos) as total_llamadas from (select HOUR(DATE_FORMAT(fecha_inicio, '%Y-%m-%d %H:%i:%S')) as hora, TIME_TO_SEC(TIMEDIFF(DATE_FORMAT(fecha_finalizacion, '%Y-%m-%d %H:%i:%S'), DATE_FORMAT(fecha_inicio, '%Y-%m-%d %H:%i:%S'))) as duracion_segundos from llamada where  1 " + restriccionPorHora + restriccionPorDia + restriccionPorMes + restriccionPorAnnio + " ) as c group by hora";
                    titulo = "Uso de la red en segundos";
                    titulobarra = "Grafico uso de la red";
                    tituloEjeX = "Hora";
                    break;
                //Planes mas escogidos de voz
                case 4:
                    consulta = "select nombre, count(*) from (select b.nombre from contrato a, plan_voz b where a.id_plan_voz=b.id_plan_voz and a.id_plan_voz != 1) as t group by nombre";
                    titulo = "Planes de voz";
                    titulobarra = "Planes de voz";
                    tituloEjeX = "Total";
                    break;
                //Planes mas escogidos de datos
                case 5:
                    consulta = "select nombre, count(*) from (select b.nombre from contrato a, plan_datos b where a.id_plan_datos=b.id_plan_datos and a.id_plan_datos != 1) as t group by nombre";

                    titulo = "Planes de Datos";
                    titulobarra = "Planes de Datos";
                    tituloEjeX = "Total";
                    break;
                default:
                    return null;
            }
            //System.out.println(consulta);
            ResultSet resultado = objFachadaBD.realizarConsultaABaseDeDatos(consulta);
            GraficoDeBarras graficoDeBarras = new GraficoDeBarras();

            Vector<String> vectorCategoriaEjeX = new Vector<String>();
            Vector<Double> vectorValoresEjeY = new Vector<Double>();

            String series1 = titulo;

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
                    titulobarra, // chart title
                    tituloEjeX, // domain axis label
                    "Total", // range axis label
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

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            objFachadaBD.cerrarConexion();

        }
        return imagenGrafico;

    }
}
