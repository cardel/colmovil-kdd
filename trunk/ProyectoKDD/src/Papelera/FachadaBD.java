/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Papelera;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import weka.experiment.InstanceQuery;

/**
 *
 * @author carlos
 */
public class FachadaBD {

    private String url;
    private String usuario;
    private String contrasena;
    private Connection conexion;
    private InstanceQuery query;

    public FachadaBD() {
        url = "jdbc:mysql://localhost:3306/colmovil";
        usuario = "colmovil";
        contrasena = "colmovil";
    }

    public Connection abrirConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(url, usuario, contrasena);
                System.out.println("Conexion exitosa");
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (!conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException exception) {
        }
    }

    public ResultSet realizarConsultaABaseDeDatos(String consulta) throws Exception {
        query = new InstanceQuery();
        query.setDatabaseURL("jdbc:mysql://localhost:3306/colmovil");
        query.setUsername("colmovil");
        query.setPassword("colmovil");

        query.connectToDatabase();
        query.execute(consulta);
        ResultSet salida = query.getResultSet();
        return salida;
    }

    public static void main(String[] args) {
        FachadaBD objFachadaBD = new FachadaBD();
        objFachadaBD.abrirConexion();
        objFachadaBD.cerrarConexion();

    }
}
