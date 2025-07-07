package src.querys;

import java.sql.*;
import javax.swing.JOptionPane;
import src.models.User;

import src.utils.Conexion;

public class QueryUser implements Query<User> {
   public static User usuario_actual = new User();
   private Connection con = Conexion.getConnection();

   @Override
   public void actualizar(User datos_modificados) {

   }

   @Override
   public void Insetar(User persona) {
      


      
         JOptionPane.showMessageDialog(null, "El usuario ya existe");

      
         
      
   }

   @Override
   public String[][] seleccionar() {
      return null;
   }

   @Override
   public void Eliminar(String indice) {

   }

   public boolean IniciarSesion(int dni, String contraseña) {
      
         try {
            CallableStatement cstm = con.prepareCall("{call paLogin(?,?)}");
            cstm.setInt(1, dni);
            cstm.setString(2, contraseña);
            ResultSet rs = cstm.executeQuery();

            usuario_actual.setDni(rs.getInt("DniRecep"));
            usuario_actual.setNombre(rs.getString("NombreRecep"));
            usuario_actual.setApellido(rs.getString("ApellidoRecep"));
            usuario_actual.setTlf(rs.getInt("TelefonoRecep"));
            usuario_actual.setContraseña(rs.getString("Contrasena"));
            usuario_actual.setAdmin(rs.getBoolean("Admin"));

            return true;

         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
         }
      }

   }



