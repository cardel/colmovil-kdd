/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clustering;

import weka.core.Instances;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 *
 * @author Carlos
 */
public class RecortarDatosEntrada {

    Instances filteredData;

    public RecortarDatosEntrada()
    {
        filteredData = null;
    }

    public Instances recontrarEntrada(Instances instancia, int porcentaje) {

        try {
            weka.core.Instances data = instancia;
            //data.setClassIndex(data.numAttributes() - 1);
            RemovePercentage rp = new RemovePercentage();
            rp.setPercentage(porcentaje);

            rp.setInputFormat(data);
            //rp.setOptions(new String[]{"-h", "70.0"});
            filteredData = RemovePercentage.useFilter(data, rp);

        } catch (Exception e) {
               System.out.println(e.toString());
        }
        return filteredData;
    }
}
