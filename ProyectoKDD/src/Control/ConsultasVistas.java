/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Papelera.FachadaBD;
import Persistencia.FachadaBDConWeka;
import java.sql.*;
import java.util.Vector;


/**
 *
 * @author Gema
 */
public class ConsultasVistas {
    FachadaBDConWeka objFachadaBDConWeka;
    FachadaBD objFachadaBD;
    Vector<String> nombreVistas;

    public ConsultasVistas() {
        objFachadaBDConWeka= new FachadaBDConWeka();
        objFachadaBD= new FachadaBD();

        nombreVistas= new Vector<String>();
        nombreVistas.addElement("vista_contrato");
        nombreVistas.addElement("vista_cliente");
        nombreVistas.addElement("vista_equipo_celular");
        nombreVistas.addElement("vista_localizacion");
        nombreVistas.addElement("vista_oficina");
        nombreVistas.addElement("vista_operador");
        nombreVistas.addElement("vista_plan_datos");
        nombreVistas.addElement("vista_plan_voz");
        nombreVistas.addElement("vista_operador_roaming");
        nombreVistas.addElement("vista_retiro");

    }

    public void crearVistas()
    {
        String consulta_sql;
        String tablaOriginal;
        for(int i=0; i<nombreVistas.size(); i++)
        {
            tablaOriginal=nombreVistas.elementAt(i).substring(6);
             consulta_sql="CREATE VIEW "+ nombreVistas.elementAt(i) +" AS SELECT * FROM "+tablaOriginal +";";
            try{
                ResultSet consulta = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta_sql);
                System.out.println("***********************  crear vista: " );
            }
             catch(SQLException e){ System.out.println(e); }
             catch(Exception e){ System.out.println(e); }
        }
    }

    public void borrarVistas()
    {
        String consulta_sql;
        for(int i=0; i<nombreVistas.size(); i++)
        {
             consulta_sql="DROP VIEW IF EXISTS "+nombreVistas.elementAt(i) +";";
            try{
                ResultSet consulta = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta_sql);
                System.out.println("***********************  drop vista: " );
            }
             catch(SQLException e){ System.out.println(e); }
             catch(Exception e){ System.out.println(e); }
        }
    }

    public void borrarUnaVista(String nombreVista)
    {
        String consulta_sql="DROP VIEW IF EXISTS "+nombreVista +";";
        try{
            ResultSet consulta = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta_sql);
            System.out.println("***********************  drop Una vista: " );
        }
        catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
     }

    public void crearUnaVista(String nombreVista)
    {
        String tablaOriginal=nombreVista.substring(6);
        String consulta_sql="CREATE VIEW "+ nombreVista +" AS SELECT * FROM "+tablaOriginal +";";
        try{
            ResultSet consulta = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta_sql);
            System.out.println("***********************  crear vista: " );
        }
         catch(SQLException e){ System.out.println(e); }
         catch(Exception e){ System.out.println(e); }

    }

    public void crearUnaVistaConVectorAtributos(String nombreVista, Vector<String> atributos)
    {
        String tablaOriginal=nombreVista.substring(6);
        String consulta_sql="CREATE VIEW "+ nombreVista+" AS SELECT ";
        for(int i=0; i<atributos.size()-1; i++)
        {
            consulta_sql+=atributos.elementAt(i)+"," ;
            
        }
        consulta_sql+=atributos.elementAt(atributos.size()-1);
        consulta_sql+=" FROM "+tablaOriginal +";";
        System.out.println("consulta completa :"+consulta_sql);
        try{
                ResultSet consulta = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta_sql);
                System.out.println("***********************  crear vista: " );
            }
             catch(SQLException e){ System.out.println(e); }
             catch(Exception e){ System.out.println(e); }
        
   }
}
         
         
        
    


