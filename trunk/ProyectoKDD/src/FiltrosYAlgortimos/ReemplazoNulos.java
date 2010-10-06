/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltrosYAlgortimos;

/**
 *
 * @author Carlos
 */
//import java.util.logging.Filter;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

/**
 *
 * @author Pc
 */
public class ReemplazoNulos {

    public void reemplazarNulos(String consulta) {

        try {
            InstanceQuery query = new InstanceQuery();
            query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
            query.setUsername("root");
            query.setPassword("");
            query.connectToDatabase();
            // Reemplaza todos los valores perdidos para atributos nominales y numéricos en un conjunto de datos con
            //las modas si es nominal y medias de los datos numericos.
            //declaration of the filter

            Instances data = query.retrieveInstances(consulta);
            ReplaceMissingValues x = new ReplaceMissingValues();

            x.setInputFormat(data);

            System.out.println("----------------");
            System.out.println("Datos con nulos");
            System.out.println("----------------");

            Instances newdata = Filter.useFilter(data, x);
            for (int i = 0; i < 10; i++) {
                System.out.println(data.instance(i));
            }

            //filter the dataset
            System.out.println("----------------");
            System.out.println("Datos sin nulos");
            System.out.println("----------------");

            for (int i = 0; i < 10; i++) {
                System.out.println(newdata.instance(i));
            }

        } catch (Exception e) {
        }

    }
}
