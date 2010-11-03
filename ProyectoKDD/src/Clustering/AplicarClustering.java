/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clustering;

import Persistencia.FachadaBDConWeka;
import javax.swing.JOptionPane;
import weka.core.Instances;
import weka.clusterers.SimpleKMeans;

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
            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema al cargar los datos, favor verifque su consulta");
            System.out.println(e.toString());
        }

    }

    public String aplicarClustering(int algoritmo, int numberKMeans, int porcentaje) {
        String salida = "";
        Instances instanciaInterna = instancia;

        if (porcentaje < 100) {
            RecortarDatosEntrada recortarDatosEntrada = new RecortarDatosEntrada();
            instanciaInterna = recortarDatosEntrada.recontrarEntrada(instancia, porcentaje);
        }

        switch (algoritmo) {
            case 0:
                salida = algortimoKMeans(instanciaInterna, numberKMeans);
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Se encuentra en construccion");
                break;
            default:
                break;
        }
        return salida;
    }

    public String algortimoKMeans(Instances instanciaInterna, int numeroClusters) {
        String salida = "";
        SimpleKMeans means = new SimpleKMeans();

        try {
            means.setMaxIterations(10);
            means.setNumClusters(numeroClusters);
            means.buildClusterer(instanciaInterna);
            salida = "RESULTADOS CLUTERING";
            salida += "\n" + instanciaInterna.toString();
            salida += "\n" + means.toString();
            for(int i=0; i< means.getClusterSizes().length; i++)
            {
               salida += "\n Cluster "+ i +" total: "+ means.getClusterSizes()[i] + " Porcentaje "+ (double)means.getClusterSizes()[i]*100/(double)instanciaInterna.numInstances();
            }
            salida += "\n";
            salida += "\n";
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return salida;
    }

    public void aplicarPreprocesamiento(int procedimiento, String argumentos[])
    {
        switch(procedimiento)
        {
            case 1:
                //Binarizar
                BinarizacionAtributos binarizacionAtributos = new BinarizacionAtributos();
                instancia=binarizacionAtributos.binarizar(instancia, Integer.parseInt(argumentos[0]), argumentos[1]);
                break;
        }
    }
}
