package src.querys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import src.utils.Conexion;

public class Query {
    protected static Connection con = Conexion.getConexion();
}
