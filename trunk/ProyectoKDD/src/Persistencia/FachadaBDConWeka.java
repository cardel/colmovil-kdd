/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.sql.ResultSet;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 *
 * @author carands
 */
public class FachadaBDConWeka {

    String nombre;
    String password;
    InstanceQuery query;

    /*
     * Constructor para indicar que se especifica nombre de usuarios y password en la BD
     */
    public FachadaBDConWeka(String nombre, String password) {


        this.nombre = nombre;
        this.password = password;
        //query = new InstanceQuery();

    }
    /*
     * Constructor para nombre usuario y password definido.
     */

    public FachadaBDConWeka() {

        nombre = "colmovil";
        password = "colmovil";
        //query = new InstanceQuery();
    }

    public ResultSet realizarConsultaABaseDeDatosTipoWeka(String consulta) throws Exception {
        query = new InstanceQuery();
        query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
        query.setUsername(nombre);
        query.setPassword(password);

        query.connectToDatabase();
        //System.out.println("Conexion exitosa");
        //Instances dataQuery = query.retrieveInstances(consulta);
        query.execute(consulta);
        ResultSet salida = query.getResultSet();
        return salida;
    }

    public Instances realizarConsultaABaseDeDatosTipoWekaInstances(String consulta) throws Exception {
        query = new InstanceQuery();
        query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
        query.setUsername(nombre);
        query.setPassword(password);

        query.connectToDatabase();
        System.out.println("Conexion exitosa");
        query.execute(consulta);
        Instances dataQuery = query.retrieveInstances(consulta);

        //ResultSet salida = query.getResultSet();
        return dataQuery;
    }

    public void cerrarConexion() throws Exception {
        query.disconnectFromDatabase();
        //System.out.println("Se cerro la Conexion");
    }
}
