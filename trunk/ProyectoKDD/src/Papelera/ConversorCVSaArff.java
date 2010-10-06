/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Papelera;

import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 *
 * @author carlos
 */
public class ConversorCVSaArff {

    public void convertirArchivo(String archivoCVS, String archivoArff) throws Exception {

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(archivoCVS));
        Instances data = loader.getDataSet();

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(archivoArff));
        saver.writeBatch();
    }
}
