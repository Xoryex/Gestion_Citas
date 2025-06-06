package src.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {
    
    static String  server="localhost",
            port="1433",
            db="GESTION_CITA",
            user="sa",
            pass="1234";


public static Connection getConexion(){

    String url =
                        "jdbc:sqlserver://"+server+":"+port+";"
                        + "database="+db+";"
                        + "user="+user+";"
                        + "password="+pass+";"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=5;";

        try {
            Connection con = DriverManager.getConnection(url);
            return con;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    
}

}
