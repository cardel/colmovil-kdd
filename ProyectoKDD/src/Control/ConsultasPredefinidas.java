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

    public ConsultasPredefinidas() {
    }

    public String retornarConsulta(int numero, String nombreVista) {
        String consulta = "";
        String vistaLlamadas = "llamada" + nombreVista;
        String vistaRecargas = "recarga" + nombreVista;

        switch (numero) {
            //Casos 0 a 5 con la tabla llamadas

            case 0: //Perfil de usuarios femenino que contratan plan de datos -> Clustering
                consulta = "select d1.estado_civil, d1.edad_nominal, d1.estrato_nominal,d1.precio,count(*) as total from (select t1.estado_civil, t1.edad_nominal,t1.estrato_nominal, t3.precio from  vista_cliente as t1, vista_contrato as t2, plan_voz as t3 where t1.idcliente=t2.id_cliente and t1.genero = 'Femenino' and t2.id_plan_datos <> 1 and t2.id_plan_voz = t3.id_plan_voz and t3.id_plan_voz <> 1)  as d1 group by estado_civil, edad_nominal, estrato_nominal, precio;";
                break;
            case 1: //Perfil de usuarios masculino que contratan plan de datos -> Clustering
                consulta = "select d1.estado_civil, d1.edad_nominal, d1.estrato_nominal,d1.precio,count(*) as total from (select t1.estado_civil, t1.edad_nominal,t1.estrato_nominal, t3.precio from  vista_cliente as t1, vista_contrato as t2, plan_voz as t3 where t1.idcliente=t2.id_cliente and t1.genero = 'Masculino' and t2.id_plan_datos <> 1 and t2.id_plan_voz = t3.id_plan_voz and t3.id_plan_voz <> 1) as d1 group by estado_civil, edad_nominal, estrato_nominal, precio;";
                break;
            case 2: // Relaciones entre características socioeconómicas y tipos de planes (Femenino) -> Clustering
                consulta = "select d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, d1.precio, count(*) as total from (select  t1.edad_nominal, t1.estado_civil,t1.estrato_nominal, t3.precio from  vista_cliente as t1, vista_contrato as t2, plan_voz as t3 where t1.idcliente=t2.id_cliente and t1.genero = 'Femenino' and t2.id_plan_voz = t3.id_plan_voz) as d1 group by genero, edad_nominal, estado_civil, estrato_nominal,precio;";
                break;
            case 3:// Relaciones entre características socioeconómicas y tipos de planes (Masculino)->Clustering
                consulta = "select d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, d1.precio, count(*) as total from (select  t1.edad_nominal, t1.estado_civil,t1.estrato_nominal, t3.precio from  vista_cliente as t1, vista_contrato as t2, plan_voz as t3 where t1.idcliente=t2.id_cliente and t1.genero = 'Masculino' and t2.id_plan_voz = t3.id_plan_voz) as d1 group by genero, edad_nominal, estado_civil, estrato_nominal,precio;";
                break;
            case 4: //Perfil prepago masculino sin duración minutos -> Asociación
                consulta = "select  d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, count(*) as total from (select  t1.edad_nominal, t1.estado_civil,t1.estrato_nominal from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente and t1.genero = 'Masculino' and t2.tipo_plan = 'Prepago') as d1 group by  edad_nominal, estado_civil, estrato_nominal;";
                break;
            case 5: //Perfil prepago masculino con duración minutos -> Asociación
                consulta = "select d1.estrato_nominal,d1.edad_nominal ,d1.estado_civil, d1.duracion_nominal from (select t3.estrato_nominal, t3.edad_nominal, t3.estado_civil, t4.duracion_nominal from  vista_contrato as t2, vista_cliente as t3, vista_llamadas as t4 where t2.id_cliente=t3.idcliente and t2.id_contrato=t4.id_contrato and t2.tipo_plan = 'Prepago' and t3.genero = 'Masculino') as d1 group by estrato_nominal,edad_nominal, estado_civil;";
                break;
            case 6: // Perfil postpago masculino sin duración nominal -> Asociación
                consulta = "select  d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, count(*) as total from (select  t1.edad_nominal, t1.estado_civil,t1.estrato_nominal from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente and t1.genero = 'Masculino' and t2.tipo_plan = 'Postpago') as d1 group by  edad_nominal, estado_civil, estrato_nominal;";
                break;
            case 7: //Perfil postpago masculino con duración minutos -> Asociación
                consulta = "select d1.estrato_nominal,d1.edad_nominal ,d1.estado_civil, d1.duracion_nominal from (select t3.estrato_nominal, t3.edad_nominal, t3.estado_civil, t4.duracion_nominal from  vista_contrato as t2, vista_cliente as t3, vista_llamadas as t4 where t2.id_cliente=t3.idcliente and t2.id_contrato=t4.id_contrato and t2.tipo_plan = 'Postpago' and t3.genero = 'Masculino') as d1 group by estrato_nominal,edad_nominal, estado_civil;";
                break;
            case 8: //Perfil prepago femenino sin duración minutos -> Asociación
                consulta = "select  d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, count(*) as total from (select  t1.edad_nominal, t1.estado_civil,t1.estrato_nominal from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente and t1.genero = 'Femenino' and t2.tipo_plan = 'Prepago') as d1 group by  edad_nominal, estado_civil, estrato_nominal;";
                break;
            case 9: //Perfil prepago femenino con duración minutos -> Asociación
                consulta = "select d1.estrato_nominal,d1.edad_nominal ,d1.estado_civil, d1.duracion_nominal from (select t3.estrato_nominal, t3.edad_nominal, t3.estado_civil, t4.duracion_nominal from  vista_contrato as t2, vista_cliente as t3, vista_llamadas as t4 where t2.id_cliente=t3.idcliente and t2.id_contrato=t4.id_contrato and t2.tipo_plan = 'Prepago' and t3.genero = 'Femenino') as d1 group by estrato_nominal,edad_nominal, estado_civil;";
                break;
            case 10: // Perfil postpago femenino sin duración nominal -> Asociación
                consulta = "select  d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, count(*) as total from (select  t1.edad_nominal, t1.estado_civil,t1.estrato_nominal from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente and t1.genero = 'Femenino' and t2.tipo_plan = 'Postpago') as d1 group by  edad_nominal, estado_civil, estrato_nominal;";
                break;
            case 11: //Perfil postpago femenino con duración minutos -> Asociación
                consulta = "select d1.estrato_nominal,d1.edad_nominal ,d1.estado_civil, d1.duracion_nominal from (select t3.estrato_nominal, t3.edad_nominal, t3.estado_civil, t4.duracion_nominal from  vista_contrato as t2, vista_cliente as t3, vista_llamadas as t4 where t2.id_cliente=t3.idcliente and t2.id_contrato=t4.id_contrato and t2.tipo_plan = 'Postpago' and t3.genero = 'Femenino') as d1 group by estrato_nominal,edad_nominal, estado_civil;";
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
                consulta = "select d1.estado_civil, d1.edad_nominal, d1.estrato_nominal,d1.precio,count(*) as total from (select t1.estado_civil, t1.edad_nominal,t1.estrato_nominal, t3.precio from  vista_cliente as t1, vista_contrato as t2, plan_voz as t3 where t1.idcliente=t2.id_cliente and t1.genero = 'Femenino' and t2.id_plan_datos <> 1 and t2.id_plan_voz = t3.id_plan_voz and t3.id_plan_voz <> 1) as d1 group by estado_civil, edad_nominal, estrato_nominal, precio;";
                break;
            case 15:
                consulta = "select d1.edad_nominal, d1.estado_civil,d1.estrato_nominal, d1.precio, count(*) as total from (select t1.edad_nominal, t1.estado_civil,t1.estrato_nominal, t3.precio from  vista_cliente as t1, vista_contrato as t2, plan_voz as t3 where t1.idcliente=t2.id_cliente and t1.genero = 'Femenino' and t2.tipo_plan = 'Postpago' and t2.id_plan_voz = t3.id_plan_voz) as d1 group by  edad_nominal, estado_civil, estrato_nominal,precio;";
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
            case 21:
                consulta = "select d1.estrato_nominal,d1.edad_nominal ,d1.estado_civil, d1.duracion_nominal from (select t3.estrato_nominal, t3.edad_nominal, t3.estado_civil, t4.duracion_nominal from  vista_contrato as t2, vista_cliente as t3, vista_llamadas as t4 where t2.id_cliente=t3.idcliente and t2.id_contrato=t4.id_contrato and t2.tipo_plan = 'Prepago' and t3.genero = 'Masculino') as d1 group by estrato_nominal,edad_nominal, estado_civil";
                break;
            case 22:
                consulta = "SELECT t1.genero,t1.estado_civil,t1.estrato,t1.se_va,t1.edad,t3.nombre FROM cliente_3 t1, llamada012008 t2, operador t3, contrato t4 WHERE t1.edad < 20 AND t1.idcliente = t4.id_cliente AND t4.id_contrato = t2.id_contrato AND t2.id_operador_destino = t3.id_operador";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error en aplicacion");
                return null;
        }
        System.out.println(consulta);
        return consulta;
    }

    public String retornarConsultaSinDiscretizacion(int numero, String nombreVista) {
        String consulta = "";
        String vistaLlamadas = "llamada" + nombreVista;
        String vistaRecargas = "recarga" + nombreVista;

        switch (numero) {
            //Casos 0 a 5 con la tabla llamadas
            case 0:
                consulta = "select fecha_inicio from " + vistaLlamadas + " group by HOUR(fecha_inicio)";
                break;
            case 1:
                consulta = "select fecha_inicio from " + vistaLlamadas + " group by HOUR(fecha_inicio)";
                break;
            case 2:
                consulta = "select fecha_inicio from " + vistaLlamadas + " group by DAY(fecha_inicio)";
                break;
            case 3:
                consulta = "select fecha_inicio from " + vistaLlamadas + " group by DAY(fecha_inicio)";
                break;
            case 4:
                consulta = "select d1.genero, d1.estado_civil from (select HOUR(t1.fecha_inicio) as hora, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, hora";
                break;
            case 5:
                consulta = "select d1.genero, d1.estado_civil from (select HOUR(t1.fecha_inicio) as hora, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, hora";
                break;
            case 6:
                consulta = "select d1.genero, d1.estado_civil from (select DAY(t1.fecha_inicio) as dia, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, dia";
                break;
            case 7:
                consulta = "select d1.genero, d1.estado_civil from (select DAY(t1.fecha_inicio) as dia, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil, dia";
                break;
            case 8:
                consulta = "select d1.genero, d1.estado_civil from (select t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil";
                break;
            case 9:
                consulta = "select d1.genero, d1.estado_civil as promedio_llamada from (select t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t1.duracion_segundos from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by genero, edad, estado_civil";
                break;
            case 10:
                consulta = "select d1.pais_destino, d1.genero, d1.estado_civil from (select t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil";
                break;
            case 11:
                consulta = "select d1.pais_destino, d1.genero, d1.estado_civil from (select t1.duracion_segundos, t1.pais_destino, t3.genero, t3.estado_civil, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad from " + vistaLlamadas + " as t1, vista_contrato t2, vista_cliente t3 where t1.id_contrato=t2.id_contrato and t3.idcliente=t2.id_cliente ) as d1 group by pais_destino, genero, edad, estado_civil";
                break;
            //Casos 8 a 9 casos con planes y contrato
            case 12:
                consulta = "select d1.genero, d1.nombre, d1.estado_civil from (select t1.genero, t1.estado_civil, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t3.nombre from vista_cliente as t1, vista_contrato as t2, vista_plan_voz as t3 where t1.idcliente=t2.id_cliente and t2.id_plan_voz=t3.id_plan_voz) as d1 group by genero, edad, estado_civil;";
                break;
            case 13:
                consulta = "select d1.genero, d1.nombre, d1.estado_civil from (select t1.genero, t1.estado_civil, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t3.nombre from vista_cliente as t1, vista_contrato as t2, vista_plan_datos as t3 where t1.idcliente=t2.id_cliente and t2.id_plan_datos=t3.id_plan_datos) as d1 group by genero, edad, estado_civil;";
                break;
            //Caso 10 y 11 modalidad por servicio
            case 14:
                consulta = "select d1.genero, d1.tipo_plan from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan,estrato;";
                break;
            case 15:
                consulta = "select d1.genero, d1.tipo_plan, d1.estado_civil from (select t1.genero, YEAR(Curdate()) - YEAR(t1.fecha_nacimiento) as edad, t2.tipo_plan, t1.estado_civil,t1.estrato from  vista_cliente as t1, vista_contrato as t2 where t1.idcliente=t2.id_cliente) as d1 group by genero, edad, tipo_plan, estado_civil, estrato;";
                break;
            //Casos 12 y 13 recargas
            case 16:
                consulta = "select medio_recarga, fecha_recarga from " + vistaRecargas + " group by medio_recarga, DAY(fecha_recarga)";
                break;

            case 17:
                consulta = "select medio_recarga, fecha_recarga from " + vistaRecargas + " group by medio_recarga, DAY(fecha_recarga)";
                break;
            //Casos 14 y 15 retiros
            case 18:
                consulta = "select d1.genero, d1.estado_civil, d1.causa from (select YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by genero, edad, causa, estado_civil;";
                break;
            case 19:
                consulta = "select d1.genero, d1.estado_civil, d1.causa from (select MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, causa, estado_civil;";
                break;
            case 20:
                consulta = "select d1.genero, d1.estado_civil, d1.causa from (select YEAR(t1.fecha) as fechaY, MONTH(t1.fecha) as fechaM, YEAR(Curdate()) - YEAR(t3.fecha_nacimiento) as edad, t3.genero, t3.estado_civil, t1.causa from vista_retiro as t1, vista_contrato as t2, vista_cliente as t3 where t1.id_contrato=t2.id_contrato and t2.id_cliente=t3.idcliente) as d1 group by fechaM, fechaY, causa, estado_civil;";
                break;
            case 21:
                consulta = "select d1.genero, d1.edad, d1.estado_civil,d1.estrato, d1.tipo_plan from (select t3.edad, t3.genero, t3.estado_civil,t3.estrato, t2.tipo_plan from  vista_contrato as t2, vista_cliente as t3 where t2.id_cliente=t3.idcliente and t2.tipo_plan = 'Prepago') as d1 group by genero,edad, estado_civil, estrato, tipo_plan";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error en aplicacion");
                return null;
        }
        System.out.println(consulta);
        return consulta;
    }

  }
