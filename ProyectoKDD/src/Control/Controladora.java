/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import GUI.ColmovilGUI;
import Papelera.FachadaBD;
import Persistencia.FachadaBDConWeka;
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
    FachadaBDConWeka objFachadaBDConWeka;
    ColmovilGUI objColmovilGUI;

    public Controladora()
    {
       objFachadaBD= new FachadaBD();
       objFachadaBDConWeka= new FachadaBDConWeka();
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
        Vector<String> vectorNombreTablas = new Vector<String>();
        try {
            String sqlConsultaNombreTablas;
            sqlConsultaNombreTablas = "show tables";
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaNombreTablas);
            //System.out.println("Nombre Tablas");
            vectorNombreTablas.addElement("--");
            while (tabla.next()) {
                //System.out.println("Codigo: " + tabla.getString(1));
                vectorNombreTablas.addElement(tabla.getString(1).toString());
            }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorNombreTablas;
    }

     public Vector<String> consultaNombreAtributos(String nombreTabla)
    {
        Vector<String> vectorNombreAtributos = new Vector<String>();
        try {
            String sqlConsultaNombreAtributos;
            sqlConsultaNombreAtributos = "SELECT column_name " + "FROM information_schema.columns " + "WHERE table_schema ='colmovil' and table_name='" + nombreTabla + "'";
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaNombreAtributos);
           // System.out.println("Atributos");
            while (tabla.next()) {
                //System.out.println("Codigo: " + tabla.getString(1));
                vectorNombreAtributos.addElement(tabla.getString(1).toString());
            }
            //        for(int j=0; j<vectorNombreAtributos.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorNombreAtributos.elementAt(j));
            //        }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorNombreAtributos;
    }

    public Vector<String> consultaTipoAtributo(String nombreAtributo, String nombretabla)
    {
        Vector<String> vectorTipoAtributo = new Vector<String>();
        try {
            String sqlConsultaTipoAtributo;
            sqlConsultaTipoAtributo = "SELECT DATA_TYPE,COLUMN_NAME " + "FROM information_schema.columns " + "WHERE table_schema ='colmovil' and table_name='" + nombretabla + "'and column_name='" + nombreAtributo + "'";
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaTipoAtributo);

            while (tabla.next()) {
                //System.out.println("tabla de tipo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
                vectorTipoAtributo.addElement(tabla.getString(1).toString());
            }
            //conexion.close();
//            System.out.println("Conexion cerrada");
//                    for(int j=0; j<vectorTipoAtributo.size(); j++)
//                    {
//                            System.out.println(" ++++++++++  vector tipo de atributo: " + vectorTipoAtributo.elementAt(j));
//                    }
        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorTipoAtributo;
    }

    public Vector<String> consultaMax(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorMaximo = new Vector<String>();
        try {
            String sqlConsultaMaximo;
            sqlConsultaMaximo = "SELECT MAX(" + nombreAtributo + ") " + "FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaMaximo);
            //System.out.println("consultando maximo");
            //
            while (tabla.next()) {
                //System.out.println("maximo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
                vectorMaximo.addElement(tabla.getString(1).toString());
            }
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorMaximo;
    }

    public Vector<String> consultaMin(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorMinimo = new Vector<String>();
        try {
            String sqlConsultaMinimo;
            sqlConsultaMinimo = "SELECT MIN(" + nombreAtributo + ") " + "FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaMinimo);
            // System.out.println("consultando minimo");
            //
            while (tabla.next()) {
                //System.out.println("minimo: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
                vectorMinimo.addElement(tabla.getString(1).toString());
            }
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }
        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorMinimo;
    }

    public Vector<String> consultaPromedio(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorPromedio = new Vector<String>();
        try {
            String sqlConsultaPromedio;
            sqlConsultaPromedio = "SELECT AVG(" + nombreAtributo + ") " + "FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaPromedio);
            //System.out.println("consultando promedio");
            //
            while (tabla.next()) {
                //System.out.println("promedio: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
                vectorPromedio.addElement(tabla.getString(1).toString());
            }
            // conexion.close();
            //System.out.println("Conexion cerrada");
            // conexion.close();
            //System.out.println("Conexion cerrada");
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorPromedio;
    }

    public Vector<String> consultaDesvEstandar(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorDesvEst = new Vector<String>();
        try {
            String sqlConsultaDesvEst;
            sqlConsultaDesvEst = "SELECT STD(" + nombreAtributo + ") " + "FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaDesvEst);
            //System.out.println("consultando desv est");
            //
            while (tabla.next()) {
                //System.out.println("desv est: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
                vectorDesvEst.addElement(tabla.getString(1).toString());
            }
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorDesvEst;
    }

    public Vector<String> consultaDistintos(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorDistintos = new Vector<String>();
        try {
            String sqlConsultaDistintos;
            //sqlConsultaDistintos = "SELECT COUNT( DISTINCT " + nombreAtributo + ")" + " FROM " + nombreTabla;
            sqlConsultaDistintos = "SELECT DISTINCT " + nombreAtributo + " FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaDistintos);
            // System.out.println("consultando dISTINTOS");
            tabla.last();
            //System.out.println("dISTINTOS: " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
            vectorDistintos.addElement(String.valueOf(tabla.getRow()));
           
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorDistintos;
    }

    public Vector<Integer> consultaGraficoDispersion(String nombreAtributoNumerico, String nombreTabla)
    {
        Vector<Integer> VectorDatosNumericos = new Vector<Integer>();
        try {
            String sqlConsultaDistintos;
            sqlConsultaDistintos = "SELECT " + nombreAtributoNumerico + " FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlConsultaDistintos);
            while (tabla.next()) {
                VectorDatosNumericos.addElement(tabla.getInt(1));
            }
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }

        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return VectorDatosNumericos;
    }

    public Vector<String> consultaValoresPosiblesAtributoNominal(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorValoresPosiblesAtributoNominal = new Vector<String>();
        try {
            String sqlValoresPosiblesAtributoNominal;
            sqlValoresPosiblesAtributoNominal = "SELECT DISTINCT " + nombreAtributo + " FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlValoresPosiblesAtributoNominal);
            // System.out.println("consultando valores posibles");
            //
            while (tabla.next()) {
                //System.out.println("dISTINTOS atribNominal: " + tabla.getInt(1));//es 1 porque solo necesito la primera columna del tipo
                vectorValoresPosiblesAtributoNominal.addElement(tabla.getString(1));
            }
            //        for(int j=0; j<vectorTipoAtributo.size(); j++)
            //        {
            //                System.out.println("vector: " + vectorTipoAtributo.elementAt(j));
            //        }
        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorValoresPosiblesAtributoNominal;
    }

    public Vector<Vector> consultaTablaEstadisticasAtributoNominal(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorValoresPosibles= new Vector<String>();
        Vector<Vector> tablaAtributoNominal=new Vector<Vector>();
        String sqlCantidadValoresPosibles;
        String valorPosible="";
        String cantidadValorPosible="";
        vectorValoresPosibles=consultaValoresPosiblesAtributoNominal(nombreAtributo, nombreTabla);
        for(int i=0; i<vectorValoresPosibles.size();i++)
        {
           Vector<String> nuevoVector= new Vector<String>();
            valorPosible=vectorValoresPosibles.elementAt(i);
              try {
                sqlCantidadValoresPosibles = "SELECT COUNT("+ nombreAtributo+")"
                                             + " FROM "+ nombreTabla
                                             +" WHERE "+nombreAtributo+" = '"+valorPosible+"'";

                Connection conexion = objFachadaBD.abrirConexion();
                Statement sentencia = conexion.createStatement();
                ResultSet tabla = sentencia.executeQuery(sqlCantidadValoresPosibles);
                //System.out.println("consultando cantidad valores posibles");
                while(tabla.next())
                {
                  //System.out.println("tabla atributo nominal " + tabla.getString(1));//es 1 porque solo necesito la primera columna del tipo
                   cantidadValorPosible=tabla.getString(1);
                }
                //*****  llenar vector
                nuevoVector.add(valorPosible);
                nuevoVector.add(cantidadValorPosible);
                //************   llenas tabla
                tablaAtributoNominal.addElement(nuevoVector);

                //conexion.close();
                // System.out.println("Conexion cerrada");
            } catch (SQLException ex) {
                Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        return tablaAtributoNominal;
    }

    public Vector<String> unirConsultaEstadisticas(String nombreAtributo, String nombreTabla)
    {
        Vector<String> vectorestadisticas= new Vector<String>();
        try {
            String sqlEstadisticas;
            sqlEstadisticas = "SELECT MAX("+nombreAtributo+"), MIN("+nombreAtributo+"), AVG("+nombreAtributo+"), STD("+nombreAtributo+"), COUNT(DISTINCT "+nombreAtributo +")" +
                             " FROM " + nombreTabla;
            ResultSet tabla = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(sqlEstadisticas);
            
            // System.out.println("consulta ESTADISTICAS");
            //
            while (tabla.next()) {
                //System.out.println("dISTINTOS atribNominal: " + tabla.getInt(1));//es 1 porque solo necesito la primera columna del tipo
                vectorestadisticas.addElement(tabla.getString(1));
                vectorestadisticas.addElement(tabla.getString(2));
                vectorestadisticas.addElement(tabla.getString(3));
                vectorestadisticas.addElement(tabla.getString(4));
                vectorestadisticas.addElement(tabla.getString(5));
            }
                    for(int j=0; j<vectorestadisticas.size(); j++)
                    {
                            System.out.println("VECTOR ESTADISTICAS: " + vectorestadisticas.elementAt(j));
                    }
        } catch (Exception ex) {
            Logger.getLogger(Controladora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorestadisticas;
    }

    public void eliminarAtributosSeleccionados(Vector<String> vectorAtributosNoSeleccionados, String nombreVista)
    {
        ConsultasVistas objConsultasVistas= new ConsultasVistas();
        objConsultasVistas.borrarUnaVista(nombreVista);
        objConsultasVistas.crearUnaVistaConVectorAtributos(nombreVista, vectorAtributosNoSeleccionados);
        System.out.println("eliminando");
    }

    public void eliminarOutliers(String nombreVista, String nombreAtributoLimpiarOutliers,Vector<String> restoAtributos, int minimo, int maximo)
    {
        ConsultasVistas objConsultasVistas= new ConsultasVistas();
        objConsultasVistas.crearVistaSinOutliers(nombreVista, nombreAtributoLimpiarOutliers,restoAtributos, minimo, maximo);   
    }

    
}
