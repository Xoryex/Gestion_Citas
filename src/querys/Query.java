package src.querys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import src.utils.Conexion;

public class Query {
    public static Connection con = Conexion.getConexion();
    public static PreparedStatement pstm;
    public static ResultSet rs;

}
