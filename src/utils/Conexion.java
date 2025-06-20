package src.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {

    public static Connection con = Conexion.getConexion();

    static String   server="localhost",
                    port="1433",
                    db="GESTION_CITA",
                    user="sa",
                    pass="1234";

    
    private static Connection getConexion(){

    String url =
                        "jdbc:sqlserver://"+server+":"+port+";"
                        + "database="+db+";"
                        + "user="+user+";"
                        + "password="+pass+";"
                        + "encrypt=true;"          // Habilitar encriptación
                        + "trustServerCertificate=true;" // Aceptar certificado no confiable
                        + "loginTimeout=30;";

        try {
            return DriverManager.getConnection(url);
        }
        catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    
}

}
