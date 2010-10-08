/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Papelera.FachadaBD;
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
               //System.out.println("Valores nulos: " + nulos.getString(1));

            }
            //conn.close();
             //System.out.println("Conexion cerrada");

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
        //System.out.println("nombre de la tabla "+nombreTabla);
            try
            {
                Connection conn= objFachada.abrirConexion();
                Statement sentencia = conn.createStatement();
                ResultSet cantDatos = sentencia.executeQuery(sql_select_n);

                while(cantDatos.next()){
                  cantidadRegistros = cantDatos.getString(1);
                  //System.out.println("Cantidad de registros "+cantidadRegistros);
                  cantidadRegistrosTabla= Integer.parseInt(cantidadRegistros);
                 // System.out.println(cantidadRegistrosTabla);
                }

                //conn.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
            catch(Exception e){ System.out.println(e); }
        //System.out.println("cantidad  de reg: "+cantidadRegistrosTabla);
        return cantidadRegistrosTabla;

    }

public int porcentajeValoresNulosPorAtributo(String nombreTabla, String nombreAtributo)
    {
        int porcentajeNulosAtributo=0, cantidadNulosAtributo=0, cantidadRegistros=0;
        cantidadNulosAtributo = contarValoresNulosPorAtributo(nombreTabla, nombreAtributo);
        cantidadRegistros = totalRegistros(nombreTabla);

        porcentajeNulosAtributo = (cantidadNulosAtributo*100)/cantidadRegistros;
        //System.out.println("Porcentaje nulos por atributo"+porcentajeNulosAtributo);
        return porcentajeNulosAtributo;
    }

    public int porcentajeValoresNulosPorRegistro(String nombreTabla)
    {
        Controladora objControladora= new Controladora();
        vectorNombreAtributos= objControladora.consultaNombreAtributos(nombreTabla);
        String sql_select, sql_select_n,nulosRegistro;

        int contadorNulos=0,cantidadRegistrosTabla=0, cantidadNulosTabla=0, cantidadDatosTabla=0, porcentajeDeNulos=0;
        cantidadRegistrosTabla=totalRegistros(nombreTabla);
        cantidadDatosTabla= cantidadRegistrosTabla*vectorNombreAtributos.size();

        for (int i=0; i<vectorNombreAtributos.size(); i++)
        {
            //System.out.println("Atributo: " + vectorNombreAtributos.elementAt(i));

            sql_select= "SELECT COUNT(*) FROM "+nombreTabla+" where "+vectorNombreAtributos.elementAt(i)+" is NULL ;";
            sql_select= "SELECT COUNT(*) FROM "+nombreTabla+" where "+vectorNombreAtributos.elementAt(i)+" = '';";

            try
            {
                Connection conn= objFachada.abrirConexion();
                Statement sentencia = conn.createStatement();
                ResultSet nulos = sentencia.executeQuery(sql_select);

                while(nulos.next())
                {
                    nulosRegistro= nulos.getString(1);
                    cantidadNulosTabla= Integer.parseInt(nulosRegistro);
                    contadorNulos+=cantidadNulosTabla;
                }
                //conn.close();

            } catch (SQLException e) {
                System.out.println(e);
            }
            catch(Exception e){ System.out.println(e); }



        }

        porcentajeDeNulos=(contadorNulos*100)/cantidadDatosTabla;
//        System.out.println("La cantidad de datos nulos de la tabla es: "+contadorNulos);
//        System.out.println("La cantidad de datos de la tabla es: "+cantidadDatosTabla);
//        System.out.println("El porcentaje de datos nulos de la tabla es: "+porcentajeDeNulos);
        return porcentajeDeNulos;

    }

}
