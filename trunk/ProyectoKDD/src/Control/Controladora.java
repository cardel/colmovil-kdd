/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import GUI.ColmovilGUI;
import Papelera.FachadaBD;
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

            //conexion.close();
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

            //conexion.close();
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
            //System.out.println("tabla de tipo: "+tabla);
            //
            while(tabla.next()){
              //System.out.println("Codigo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorTipoAtributo.addElement(tabla.getString(1).toString());
            }

            //conexion.close();
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

    public Vector<String> consultaMax(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorMaximo= new Vector<String>();
        try {
            String sqlConsultaMaximo;
            sqlConsultaMaximo = "SELECT MAX("+ nombreAtributo+ ") "
                                         + "FROM "+nombreTabla;
                                         
            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaMaximo);
            System.out.println("consultando maximo");
            //
            while(tabla.next()){
              System.out.println("maximo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorMaximo.addElement(tabla.getString(1).toString());
            }

            //conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorTipoAtributo.size(); j++)
//        {
//                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
//        }
          return vectorMaximo;
    }

    public Vector<String> consultaMin(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorMinimo= new Vector<String>();
        try {
            String sqlConsultaMinimo;
            sqlConsultaMinimo = "SELECT MIN("+ nombreAtributo+ ") "
                                         + "FROM "+nombreTabla;

            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaMinimo);
            System.out.println("consultando minimo");
            //
            while(tabla.next()){
              System.out.println("minimo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorMinimo.addElement(tabla.getString(1).toString());
            }

           // conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorTipoAtributo.size(); j++)
//        {
//                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
//        }
          return vectorMinimo;
    }

    public Vector<String> consultaPromedio(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorPromedio= new Vector<String>();
        try {
            String sqlConsultaPromedio;
            sqlConsultaPromedio = "SELECT AVG("+ nombreAtributo+ ") "
                                         + "FROM "+nombreTabla;

            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaPromedio);
            System.out.println("consultando promedio");
            //
            while(tabla.next()){
              System.out.println("promedio: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorPromedio.addElement(tabla.getString(1).toString());
            }

           // conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorTipoAtributo.size(); j++)
//        {
//                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
//        }
          return vectorPromedio;
    }

    public Vector<String> consultaDesvEstandar(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorDesvEst= new Vector<String>();
        try {
            String sqlConsultaDesvEst;
            sqlConsultaDesvEst = "SELECT AVG("+ nombreAtributo+ ") "
                                         + "FROM "+nombreTabla;

            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaDesvEst);
            System.out.println("consultando desv est");
            //
            while(tabla.next()){
              System.out.println("desv est: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorDesvEst.addElement(tabla.getString(1).toString());
            }

            //conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorTipoAtributo.size(); j++)
//        {
//                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
//        }
          return vectorDesvEst;
    }

    public Vector<String> consultaDistintos(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorDistintos= new Vector<String>();
        try {
            String sqlConsultaDistintos;
            sqlConsultaDistintos = "SELECT COUNT( DISTINCT "+ nombreAtributo+")"
                                         + " FROM "+ nombreTabla;

            Connection conexion = objFachadaBD.abrirConexion();
            Statement sentencia = conexion.createStatement();
            ResultSet tabla = sentencia.executeQuery(sqlConsultaDistintos);
            System.out.println("consultando dISTINTOS");
            //
            while(tabla.next()){
              System.out.println("dISTINTOS: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
               vectorDistintos.addElement(tabla.getString(1).toString());
            }

            //conexion.close();
             System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for(int j=0; j<vectorTipoAtributo.size(); j++)
//        {
//                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
//        }
          return vectorDistintos;
    }

}
