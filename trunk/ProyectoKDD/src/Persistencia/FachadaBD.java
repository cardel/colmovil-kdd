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

    public FachadaBD()
    {
        url="jdbc:mysql://localhost:3306/Colmovil";
        usuario= "colmovil";
        contrasena= "colmovil";
    }

    public Connection abrirConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(url,usuario, contrasena);
                System.out.println( "Conexion exitosa");
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


    public static void main(String[] args)
    {
        FachadaBD objFachadaBD= new FachadaBD();
        objFachadaBD.abrirConexion();
        objFachadaBD.cerrarConexion();

    }
}
