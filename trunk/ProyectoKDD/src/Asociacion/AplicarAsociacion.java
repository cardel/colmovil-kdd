/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Asociacion;

import Clustering.RecortarDatosEntrada;
import Persistencia.FachadaBDConWeka;
import javax.swing.JOptionPane;
import weka.core.Instances;
import weka.associations.Apriori;

/**
 *
 * @author Dayana
 */
public class AplicarAsociacion {

    Instances instancia;

    public AplicarAsociacion() {
        instancia = null;
    }

    public void realizarConsultaABaseDeDatosTipoWekaInstances(String consulta) {
        try {
            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema al cargar los datos, favor verifque su consulta");
            System.out.println(e.toString());
        }

    }

    public String aplicarAprioriWeka(int algoritmo, int porcentaje) {
        String salida = "";
        Instances instanciaInterna = instancia;

        if (porcentaje < 100) {
            RecortarDatosEntrada recortarDatosEntrada = new RecortarDatosEntrada();
            instanciaInterna = recortarDatosEntrada.recontrarEntrada(instancia, porcentaje);
        }

        switch (algoritmo) {
            case 0:
                salida = algoritmoApriori(instanciaInterna);
                break;
            case 1:
                salida = algoritmoFPGrowth(instanciaInterna);
                break;
            default:
                break;

        }

        return salida;
    }

    public String algoritmoApriori(Instances instancia) {
        String salida = "";

        Apriori objApriori = new Apriori();

        try {

            objApriori.buildAssociations(instancia);

            salida = "RESULTADOS ASOCIACIÓN CON APRIORI";
            salida += "\n" + instancia.toString();
            salida += "\n" + objApriori.toString();
            salida += "\n--------------------------\n";
            for (int i = 0; i < objApriori.getAllTheRules().length; i++) {
                salida += "Regla" + i +" "+objApriori.getInstancesNoClass().instance(i).toString() + "\n";
            }
            salida += "\n";
            salida += "\n";
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return salida;
    }

    public String algoritmoFPGrowth(Instances instancia) {
        String salida = "";

        return salida;

    }
}
