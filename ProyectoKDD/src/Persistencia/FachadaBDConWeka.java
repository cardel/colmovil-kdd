/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

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
    public FachadaBDConWeka(String nombre, String password) throws Exception {


        this.nombre = nombre;
        this.password = password;
        query = new InstanceQuery();

    }
    /*
     * Constructor para nombre usuario y password definido.
     */
    public FachadaBDConWeka() throws Exception {

        nombre = "colmovil";
        password = "colmovil";
        query = new InstanceQuery();
    }

    public Instances realizarConsultaABaseDeDatosTipoWeka(String consulta) throws Exception {

        query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
        query.setUsername(nombre);
        query.setPassword(password);

        query.connectToDatabase();
        Instances dataQuery = query.retrieveInstances(consulta);
        return dataQuery;
    }
}
