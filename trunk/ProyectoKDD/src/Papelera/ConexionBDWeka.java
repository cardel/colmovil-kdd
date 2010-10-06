/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Papelera;

import weka.experiment.*;
import java.sql.ResultSet;

/**
 *
 * @author carands
 */
public class ConexionBDWeka {

    public void conectarBaseWeka() {

        try {


            InstanceQuery query = new InstanceQuery();

            query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");

            query.setUsername("root");
            query.setPassword("");
            query.connectToDatabase();

            query.execute("select * from cliente");

            ResultSet salida = query.getResultSet();

            while (salida.next()) {
                System.out.println(salida.getString(3));
            }
            query.disconnectFromDatabase();
            System.exit(0);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
