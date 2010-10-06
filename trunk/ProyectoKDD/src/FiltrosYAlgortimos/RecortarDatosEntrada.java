/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltrosYAlgortimos;

import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 *
 * @author Carlos
 */
public class RecortarDatosEntrada {

    public void recontrarEntrada(String consulta, int porcentaje) {

        try {
            InstanceQuery query = new InstanceQuery();
            query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
            query.setUsername("root");
            query.setPassword("");
            query.connectToDatabase();
            Instances dataQuery = query.retrieveInstances(consulta);
            weka.core.Instances data = new weka.core.Instances(dataQuery);
            data.setClassIndex(data.numAttributes() - 1); // This might not be needed
            RemovePercentage rp = new RemovePercentage();
            rp.setPercentage(porcentaje);

            rp.setInputFormat(data);


            //ESTE ALGORITMO ELIMINA PORCENTAJE DE DATOS EN UN ARCHIVO
            //TIENE QUE SER UN DOUBLE O SI NO NO LO VA A TOMAR

            rp.setOptions(new String[]{"-h", "70.0"});
            // -C col
            Instances filteredData = RemovePercentage.useFilter(data, rp);

            System.out.println(filteredData);

        } catch (Exception e) {
        }
    }
}
