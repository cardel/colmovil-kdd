/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltrosYAlgortimos;

/**
 *
 * @author Carlos
 */
import Persistencia.FachadaBDConWeka;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.TimeSeriesDelta;

public class LlenarConMissing {

    public void llenarConMissingDatos(String consulta) {

        try {

            FachadaBDConWeka fachadaBDConWeka = new FachadaBDConWeka();
            Instances dataQuery = fachadaBDConWeka.realizarConsultaABaseDeDatosTipoWeka(consulta);
            weka.core.Instances data = new weka.core.Instances(dataQuery);
            weka.filters.unsupervised.attribute.TimeSeriesDelta timeSeriesDelta = new weka.filters.unsupervised.attribute.TimeSeriesDelta();
            Filter fil = new TimeSeriesDelta();
            timeSeriesDelta.setInputFormat(data);

            // timeSeriesDelta.main(new String[]{"-h"});
            /////////////////////////////////////////////////////////////
            /*-M
            Para los casos al principio o al final del conjunto de datos donde
            los valores traducidos no son conocidos, eliminar los casos
            (Por defecto es utilizar los valores perdidos).*/
            //////////////////////////////////////////////////////////////////////

            timeSeriesDelta.main(new String[]{"-h"});
            //NOTA NO SE SABE COMO DEBE DE ENTRAR EL ARCHIVO CON ESPACIOS VACIOS O VALORES QUE NO CONCUERDEN CON EL TIPO DE ATRIBUTO
            /////////////////////////////////////////////////////////////////////////////////////////
            //timeSeriesDelta.setOptions(new String[]{"-M"});

            weka.core.Instances filteredData = fil.useFilter(data, timeSeriesDelta);
            //UseFilter(fil, new String[]{"-M"});

            System.out.println(filteredData);
        } catch (Exception e) {
        }


    }
}
