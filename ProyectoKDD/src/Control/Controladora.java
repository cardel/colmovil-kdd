/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import GUI.ColmovilGUI;
import Persistencia.FachadaBD;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author Gema
 */
public class Controladora
{
    FachadaBD objFachadaBD;
    ColmovilGUI objColmovilGUI;

    public Controladora()
    {
       objFachadaBD= new FachadaBD();
       objColmovilGUI= new ColmovilGUI();
       
    }

    public void abrirConexion()
    {
        objFachadaBD.abrirConexion();
    }

    public void cerrarConexion()
    {
        objFachadaBD.cerrarConexion();
    }

    public void mostrarGUI()
    {
        
        objColmovilGUI.setVisible(true);
    }

    public Vector<String> consultaNombreTablas()
    {
        Vector<String> vectorNombreTablas= new Vector<String>();
        try {
            String sqlConsultaNombreTablas;
            sqlConsultaNombreTablas = "show tables";
            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaNombreTablas);
            System.out.println("Nombre Tablas");
            vectorNombreTablas.addElement("--");
            while(tabla.next()){
              // System.out.println("Codigo: " + tabla.getString(1));
               vectorNombreTablas.addElement(tabla.getString(1).toString());
            }

            conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorNombreTablas;

    }

    public Vector<String> consultaNombreAtributos(String nombreTabla)
    {
        Vector<String> vectorNombreAtributos= new Vector<String>();
        try {
            String sqlConsultaNombreAtributos;
            sqlConsultaNombreAtributos = "SELECT column_name "
                                         + "FROM information_schema.columns "
                                         + "WHERE table_schema ='colmovil' and table_name='"+nombreTabla+"'";
            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaNombreAtributos);
            System.out.println("Atributos");
            //
            while(tabla.next()){
              // System.out.println("Codigo: " + tabla.getString(1));
               vectorNombreAtributos.addElement(tabla.getString(1).toString());
            }

            conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorNombreAtributos.size(); j++)
//        {
//                System.out.println("vector: " + vectorNombreAtributos.elementAt(j));
//        }
        return vectorNombreAtributos; 
    }

    public Vector<String> consultaTipoAtributo(String nombreAtributo, String nombretabla)
    {
        Vector<String> vectorTipoAtributo= new Vector<String>();
        try {
            String sqlConsultaTipoAtributo;
            sqlConsultaTipoAtributo = "SELECT DATA_TYPE,COLUMN_NAME "
                                         + "FROM information_schema.columns "
                                         + "WHERE table_schema ='colmovil' and table_name='"+nombretabla+"'and column_name='"+nombreAtributo+"'";
            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaTipoAtributo);
            System.out.println("tabla de tipo: "+tabla);
            //
            while(tabla.next()){
              System.out.println("Codigo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorTipoAtributo.addElement(tabla.getString(1).toString());
            }

            conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorTipoAtributo.size(); j++)
//        {
//                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
//        }
          return vectorTipoAtributo;

    }

}
