/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class Conexion {

    private String db = "punto_de_ventas";
    private String user = "root";
    private String password = "";
    private String urlMysql = "jdbc:mysql://localhost/" + db + "?SslMode=none";
    // private String urlSql = "jdbc:sqlserver://localhost:1433;databaseName=" + db 
    //     + ";integratedSecurity=true;";
    private java.sql.Connection conn = null;

    public Conexion() {

        try {
            //Coexxion mara mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn =  DriverManager.getConnection(this.urlMysql, this.user, this.password);

            //coneccion para sqlserver
            //conn = (Connection) DriverManager.getConnection(urlSql);
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Aqi esta mos");
            if (conn != null) {
                System.out.print("Conexion a la base de datos" + this.db + "...... Listo ");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.print("Error Aqui.... : " + ex);
        }
    }

    public java.sql.Connection getConn() {
        return conn;
    }
}
