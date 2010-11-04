/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Discretizacion;

import Papelera.FachadaBD;
import Persistencia.FachadaBDConWeka;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;
import weka.clusterers.DBScan;
import weka.core.Instance;

/**
 *
 * @author Dayana
 */
public class Discretizar {

    Instances instancia;
    String consulta;
    FachadaBD objFachadaBD;
    FachadaBDConWeka objFachadaBDConWeka;

    public Discretizar()
    {
        instancia = null;
        objFachadaBD= new FachadaBD();
        objFachadaBDConWeka= new FachadaBDConWeka();
    }

    public void discretizarAtributos(Instance instanciaInterna,String nombreTabla, String nombreAtributo)
    {
      Discretize objDiscretize = new Discretize();
      String [] opciones;
      consulta = "select"+nombreAtributo+"from"+nombreTabla+";";
       try {
            objFachadaBDConWeka = new FachadaBDConWeka();
            instancia = objFachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente");

            objDiscretize.input(instanciaInterna);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema al cargar los datos, favor verifque su consulta");
            System.out.println(e.toString());
        }
      try {
        
        boolean dis = objDiscretize.input(instanciaInterna);
        opciones = objDiscretize.getOptions();
        //int tamano= objDiscretize.getBins();
        String tamano= objDiscretize.binsTipText();
        System.out.println(dis);


      }
      catch (Exception ex) {
            Logger.getLogger(Discretizar.class.getName()).log(Level.SEVERE, null, ex);
        }



      System.out.println("todo salió bn");

    }

    public static void main (String []args)
    {
        Discretizar objDiscretizar = new Discretizar();
        String tabla = "oficina";
        String atributo = "numero_empleados";
        //objDiscretizar.discretizarAtributos(tabla, atributo);
    }

}
