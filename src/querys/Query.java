package src.querys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import src.utils.Conexion;

public class Query {
    static Connection con = Conexion.getConexion();
    static PreparedStatement pstm;
    static ResultSet rs;

}
