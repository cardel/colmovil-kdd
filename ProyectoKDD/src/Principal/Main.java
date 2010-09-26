/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Persistencia.FachadaBD;
import cargaYManejoDeDatos.ConversorCVSaArff;
import cargaYManejoDeDatos.ManejoArchivosCVS;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author carlos
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
         * Manejo de archivos CVS
         */
        ManejoArchivosCVS cargaArchivoCVS = new ManejoArchivosCVS();
        cargaArchivoCVS.cargarArchivoCVS();

        ConversorCVSaArff conversorCVSaArff = new ConversorCVSaArff();

        try {

            conversorCVSaArff.convertirArchivo("./Datos/bank.csv", "./Datos/bank.arff");
            Thread.sleep(5000);

            /*
             * Base de datos
             */
            FachadaBD fachada = new FachadaBD("root", "cardel", "jdbc:mysql://localhost:3306/colmovil");
            Connection conexion = fachada.abrirConexion();
            Statement st = conexion.createStatement();

            String query = "select * from localizacion";
            ResultSet rs = st.executeQuery(query);


            while (rs.next()) {
                System.out.println(rs.getString(1) + "-->" + rs.getString(2));
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }


        System.exit(0);
    }
}
