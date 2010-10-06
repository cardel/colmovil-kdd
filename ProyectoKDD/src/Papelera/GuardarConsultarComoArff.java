/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Papelera;

import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.experiment.InstanceQuery;

/**
 *
 * @author Carlos
 */
public class GuardarConsultarComoArff {

    public void guardarConsultarComoArff(String consulta, String archivoSalida) {
        try {
            InstanceQuery query = new InstanceQuery();

            query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");

            query.setUsername("root");
            query.setPassword("");
            query.connectToDatabase();

            Instances data = query.retrieveInstances(consulta);
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("./Datos/"+archivoSalida));
            saver.writeBatch();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
