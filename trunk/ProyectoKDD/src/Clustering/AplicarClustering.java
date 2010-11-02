/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clustering;

import Persistencia.FachadaBDConWeka;
import weka.core.Instances;
import weka.clusterers.XMeans;
import weka.core.Instance;
import weka.filters.supervised.attribute.NominalToBinary;

/**
 *
 * @author Carlos
 */
public class AplicarClustering {

    Instances instancia;

    public AplicarClustering() {
        instancia = null;
    }

    public void realizarConsultaABaseDeDatosTipoWekaInstances(String consulta) {
        try {
            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            instancia = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWekaInstances(consulta);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public String aplicarClustering(int algortimo, int porcentaje) {
        String salida = "";
        XMeans means = new XMeans();
        try {

            boolean atributoNominal = means.checkForNominalAttributes(instancia);

            if (atributoNominal) {
                NominalToBinary filter = new NominalToBinary();
                instancia = filter.getOutputFormat();
            }

            //means.buildClusterer(instancia);
            System.out.println(means.checkForNominalAttributes(instancia));
            //System.out.println(means.numberOfClusters());
            System.out.println("-------------------");
            //System.out.println(means.toString());
            System.out.println("-------------------");
            //System.out.println(means.globalInfo());
        } catch (Exception e) {
            System.out.println(e.toString());
        }


        return salida;
    }
}
