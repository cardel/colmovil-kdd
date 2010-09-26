/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author carlos
 */
public class FachadaBD {

    private String url;
    private String usuario;
    private String contrasena;
    private Connection conexion;

    public FachadaBD(String usuario, String contrasena,String url) {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Connection abrirConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(url,usuario, contrasena);
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
}
