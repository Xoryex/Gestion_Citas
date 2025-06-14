package src.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {
    
    static String   server="localhost",
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
                        + "loginTimeout=30;";

        try {
            return DriverManager.getConnection(url);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    
}

}
