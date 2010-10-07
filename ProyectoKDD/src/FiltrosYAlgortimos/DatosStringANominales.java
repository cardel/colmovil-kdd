/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltrosYAlgortimos;

import Persistencia.FachadaBDConWeka;
import weka.core.Instances;

/**
 *
 * @author Carlos
 */
public class DatosStringANominales {

    public void convertirStringANominar(String consulta) {

        try {

            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            Instances dataQuery = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta);

            weka.core.Instances data = new weka.core.Instances(dataQuery);
            data.setClassIndex(data.numAttributes() - 1); // This might not be needed
            weka.filters.unsupervised.attribute.StringToNominal stringToNominal = new weka.filters.unsupervised.attribute.StringToNominal();

            System.out.println(data);
            stringToNominal.setInputFormat(data);

            weka.core.Instances filteredData = weka.filters.unsupervised.attribute.StringToNominal.useFilter(data, stringToNominal);
            System.out.println(filteredData);
        } catch (Exception e) {
        }


    }
}
