/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Asociacion;

import Persistencia.FachadaBDConWeka;
import javax.swing.JOptionPane;
import weka.core.Instances;
import weka.associations.Apriori;
import weka.associations.FPGrowth;

/**
 *
 * @author Dayana
 */
public class AplicarAsociacion {

    Instances instance;

    public AplicarAsociacion()
    {
        instance = null;
    }
    public void realizarConsultaABaseDeDatosTipoWekaInstances(String consulta) {
        try {
            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            instance = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema al cargar los datos, favor verifque su consulta");
            System.out.println(e.toString());
        }

    }

    public String aplicarAprioriWeka(int algoritmo)
    {
        String salida = "";

        switch(algoritmo)
        {
            case 0: salida = algoritmoApriori(instance); break;
            case 1: salida = algoritmoFPGrowth(instance); break;
            default: break;

        }
        return salida;
    }

    public String algoritmoApriori(Instances instancia)
    {
        String salida = "";
        Apriori objApriori = new Apriori();

        try {

            objApriori.buildAssociations(instancia);
            salida = objApriori.globalInfo();

            for (int i=0; i< objApriori.getNumRules();i++)
            {

            }
        }

        catch (Exception e) {
            System.out.println(e.toString());
        }

        return salida;
    }

    public String algoritmoFPGrowth(Instances instancia)
    {
        String salida = "";
        
        return salida;

    }

}
