/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;
import javax.swing.JOptionPane;

/**
 *
 * @author Dayana
 */
public class ConsultasPredefinidas {

    public ConsultasPredefinidas()
    {
        
    }

    public String retornarConsulta(int numero, String nombreVista)
    {
        String consulta = "";
        String vistaLlamadas = "llamada" + nombreVista;
        String vistaRecargas = "recarga" + nombreVista;

        switch (numero) {
                //Casos 0 a 5 con la tabla llamadas
                case 0:
                    consulta = "select HOUR(fecha_inicio) as hora, count(*) as total from " + vistaLlamadas + " group by HOUR(fecha_inicio)";
                    break;
                case 1:
                    consulta = "select HOUR(fecha_inicio), AVG(duracion_segundos) as promedio_llamada from " + vistaLlamadas + " group by HOUR(fecha_inicio)";
                    break;
                case 2:
                    consulta = "select DAY(fecha_inicio), count(*) as total from " + vistaLlamadas + " group by DAY(fecha_inicio)";
                    break;
                case 3:
                    consulta = "select DAY(fecha_inicio), SUM(duracion_segundos) as duracion_total from " + vistaLlamadas + " group by DAY(fecha_inicio)";
                    break;
                case 4:
                    consulta = "select d1.hora, d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select HOUR(t1.fecha_inicio) as hora, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, hora";
                    break;
                case 5:
                    consulta = "select d1.hora, d1.genero, d1.edad, d1.estado_civil, AVG(duracion_segundos) as promedio_llamada from (select HOUR(t1.fecha_inicio) as hora, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, hora";
                    break;
                case 6:
                    consulta = "select d1.dia, d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select DAY(t1.fecha_inicio) as dia, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, dia";
                    break;
                case 7:
                    consulta = "select d1.dia, d1.genero, d1.edad, d1.estado_civil, SUM(d1.duracion_segundos) as total_segundos from (select DAY(t1.fecha_inicio) as dia, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, dia";
                    break;
                case 8:
                    consulta = "select d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil";
                    break;
                case 9:
                    consulta = "select d1.genero, d1.edad, d1.estado_civil, AVG(d1.duracion_segundos) as promedio_llamada from (select t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil";
                    break;
                case 10:
                    consulta = "select d1.pais_destino, d1.genero, d1.edad, d1.estado_civil, count(*) as total from (select t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil";
                    break;
                case 11:
                    consulta = "select d1.pais_destino, d1.genero, d1.edad, d1.estado_civil, AVG(d1.duracion_segundos) as promedio_llamada from (select t1.duracion_segundos, t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil";
                    break;
                //Casos 8 a 9 casos con planes y contrato
                case 12:
                    consulta = "select d1.genero, d1.edad, d1.nombre, d1.estado_civil, count(*) as total from (select t1.genero, t1.estado_civil, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t3.nombre from vista_cliente as t1, vista_contrato as t2, vista_plan_voz as t3 where t1.idcliente=t2.id_cliente and t2.id_plan_voz=t3.id_plan_voz) as d1 group by genero, edad, estado_civil;";
                    break;
                case 13:
                    consulta = "select d1.genero, d1.edad, d1.nombre, d1.estado_civil, count(*) as total from (select t1.genero, t1.estado_civil, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t3.nombre from vista_cliente as t1, vista_contrato as t2, vista_plan_datos as t3 where t1.idcliente=t2.id_cliente and t2.id_plan_datos=t3.id_plan_datos) as d1 group by genero, edad, estado_civil;";
                    break;
                //Caso 10 y 11 modalidad por servicio
                case 14:
                    consulta = "select d1.genero, d1.edad, d1.tipo_plan,d1.estrato,count(*) as total from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan,estrato;";
                    break;
                case 15:
                    consulta = "select d1.genero, d1.edad, d1.tipo_plan, d1.estado_civil,d1.estrato, count(*) as total from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan, t1.estado_civil,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan, estado_civil, estrato;";
                    break;
                //Casos 12 y 13 recargas
                case 16:
                    consulta = "select medio_recarga, DAY(fecha_recarga) as dia, count(*) as total from " + vistaRecargas + " group by medio_recarga, DAY(fecha_recarga)";
                    break;

                case 17:
                    consulta = "select medio_recarga, DAY(fecha_recarga) as dia, AVG(valor_recarga) from " + vistaRecargas + " group by medio_recarga, DAY(fecha_recarga)";
                    break;
                //Casos 14 y 15 retiros
                case 18:
                    consulta = "select d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by genero, edad, causa, estado_civil;";
                    break;
                case 19:
                    consulta = "select d1.fechaM, d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, causa, estado_civil;";
                    break;
                case 20:
                    consulta = "select d1.fechaY, d1.fechaM, d1.genero, d1.edad, d1.estado_civil, d1.causa, count(*) as total from (select YEAR(t1.fecha) as fechaY, MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, fechaY, causa, estado_civil;";
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error en aplicacion");
                    return null;
            }
         System.out.println(consulta);
        return consulta;
    }

}
