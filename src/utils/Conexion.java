package src.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DB = "GESTION_CITA";
    private static final String USER = "sa";
    private static final String PASS = "1234";
    
    public static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            String url = "jdbc:sqlserver://" + SERVER + ":" + PORT + ";" +
                        "databaseName=" + DB + ";" +
                        "user=" + USER + ";" +
                        "password=" + PASS + ";" +
                        "encrypt=true;" +
                        "trustServerCertificate=true;" +
                        "loginTimeout=30;";
            
            try {
                con = DriverManager.getConnection(url);
                JOptionPane.showMessageDialog(null, "Conexión exitosa");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error de conexión:\n" + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                con = null; // Asegurar que sea null en caso de error
            }
        }
        return con;
    }

    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al cerrar conexión:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            con = null; // Asegurar limpieza
        }
    }
}