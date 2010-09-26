/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cargaYManejoDeDatos;

import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class ManejoArchivosCVS {

    /*
     * Trabaja con cvsReader
     */
    public void cargarArchivoCVS() {

        CsvReader cvsReader = null;
        char deliminador = ',';
        String pathFichero = "./Datos/bank.csv";
        //String encabezado[] = {"fecha_inicio", "fecha finalizacion", "id_contrato", "numero_origen", "id_operador_destino", "pais_destino", "numero_destino", "utilizo_roaming", "operador_roaming"};


        try {
            File fichero = new File(pathFichero);
            FileReader freader = new FileReader(fichero);
            cvsReader = new CsvReader(freader, deliminador);

            String[] headers = null;
            List listaRegistros = new ArrayList();

            // Leemos las cabeceras del fichero (primera fila).
            if (cvsReader.readHeaders()) {

                //cvsReader.setHeaders(encabezado);
                headers = cvsReader.getHeaders();

                System.out.println("------- CABECERAS DEL FICHERO ------------");
                for (int i = 0; i < headers.length; i++) {
                    System.out.println(headers[i]);
                }
            }
            //Leer una linea
            while (cvsReader.readRecord()) {
                // Mostramos por pantalla:
                System.out.println("-" + cvsReader.getValues()[1] + "-");
            }
            System.out.println("-----------------------------------------------------");

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}
