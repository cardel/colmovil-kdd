/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Persistencia.FachadaBD;
import java.sql.*;
import javax.swing.*;
import java.util.Vector;
/**
 *
 * @author Dayana
 */
public class ConsultaNulos
{
    FachadaBD objFachada;
    Vector<String> vectorNombreAtributos;

    public ConsultaNulos()
    {
        objFachada = new FachadaBD();
        vectorNombreAtributos= new Vector<String>();
    }


    public int contarValoresNulosPorAtributo(String nombreTabla, String nombreAtributo)
    {
        String sql_select;
        int cantidadNulos=0;
        sql_select="SELECT COUNT(*) FROM " +nombreTabla+" where"+nombreAtributo+" is NULL;";
        sql_select="SELECT COUNT(*) FROM " +nombreTabla+" where "+nombreAtributo+" = '';";

         try{
            Connection conn= objFachada.abrirConexion();
            Statement sentencia = conn.createStatement();
            ResultSet nulos = sentencia.executeQuery(sql_select);

            while(nulos.next()){
               cantidadNulos=Integer.parseInt(nulos.getString(1));
               System.out.println("Valores nulos: " + nulos.getString(1));

            }
            //conn.close();
             System.out.println("Conexion cerrada");

         }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }
         return cantidadNulos;
    }

    public int totalRegistros(String nombreTabla)
    {
        int cantidadRegistrosTabla=0;
        String cantidadRegistros;
        String sql_select_n= "SELECT COUNT(*) FROM "+nombreTabla+";";
        System.out.println("nombre de la tabla "+nombreTabla);
            try
            {
                Connection conn= objFachada.abrirConexion();
                Statement sentencia = conn.createStatement();
                ResultSet cantDatos = sentencia.executeQuery(sql_select_n);

                while(cantDatos.next()){
                  cantidadRegistros = cantDatos.getString(1);
                  System.out.println("Cantidad de registros "+cantidadRegistros);
                  cantidadRegistrosTabla= Integer.parseInt(cantidadRegistros);
                  System.out.println(cantidadRegistrosTabla);
                }

                //conn.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
            catch(Exception e){ System.out.println(e); }
        System.out.println("cantidad  de reg: "+cantidadRegistrosTabla);
        return cantidadRegistrosTabla;

    }

    public void contarValoresNulosPorRegistro(String nombreTabla)
    {
//        Controladora objControladora= new Controladora();
//        vectorNombreAtributos= objControladora.consultaNombreAtributos(nombreTabla);
//        String sql_select, sql_select_n,cantidadRegistros;
//
//        int contadorNulos=0, cantidadRegistrosTabla=0;
//
//        sql_select_n= "SELECT COUNT(*) FROM"+nombreTabla+";";
//
//            try
//            {
//                Connection conn= objFachada.abrirConexion();
//                Statement sentencia = conn.createStatement();
//                ResultSet cantDatos = sentencia.executeQuery(sql_select_n);
//
//                while(cantDatos.next()){
//                  cantidadRegistros = cantDatos.getString(1);
//                  cantidadRegistrosTabla= Integer.parseInt(cantidadRegistros);
//                  System.out.println(cantidadRegistrosTabla);
//                }
//
//                //conn.close();
//
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
//            catch(Exception e){ System.out.println(e); }
//
//       for(int j=0; j<cantidadRegistrosTabla;j++)
//       {
//        for (int i=0; i<vectorNombreAtributos.size(); i++)
//        {
//             System.out.println("entra al for");
//            sql_select= "SELECT COUNT(*) FROM"+nombreTabla+"where"+vectorNombreAtributos.elementAt(i)+"is NULL LIMIT"+ j+","+j+";";
//           // sql_select= "SELECT COUNT(*) FROM"+nombreTabla+"where"+vectorNombreAtributos.elementAt(i)+" = '';";
//
//            try
//            {
//                Connection conn= objFachada.abrirConexion();
//                Statement sentencia = conn.createStatement();
//                ResultSet nulos = sentencia.executeQuery(sql_select);
//
//                if(nulos.getString(1) == null ? "1" == null : nulos.getString(1).equals("1"))
//                {
//                    contadorNulos+=1;
//                }
//                //conn.close();
//
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
//            catch(Exception e){ System.out.println(e); }
//
//
//        }
//        }

    }

}
