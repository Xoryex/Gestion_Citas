package src.querys;

import java.sql.*;
import javax.swing.JOptionPane;
import src.models.User;

import src.utils.Conexion;

public class QueryUser implements Query<User> {
   public static User usuario_actual = new User();
  

   @Override
   public void actualizar(User datos_modificados) {

   }

   @Override
   public void Insetar(User persona) {
      try {
         CallableStatement cstm = Conexion.getConnection().prepareCall("{call paInsertRecepcionista(?,?,?,?,?,?)}");
         cstm.setInt(1, persona.getDni());
         cstm.setString(2, persona.getNombre());
         cstm.setString(3, persona.getApellido());
         cstm.setInt(4, persona.getTlf());
         cstm.setString(5, persona.getContraseña());
         cstm.setBoolean(6, persona.getAdmin());
         cstm.executeUpdate();
         JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
      }catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }


      
         
      
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
            CallableStatement cstm = Conexion.getConnection().prepareCall("{call paSelectRecepcionista(?,?)}");
            cstm.setInt(1, dni);
            cstm.setString(2, contraseña);
            ResultSet rs = cstm.executeQuery();

            if (rs.next()) {
                usuario_actual.setDni(rs.getInt("DniRecep"));
                usuario_actual.setNombre(rs.getString("NomRecep"));
                usuario_actual.setApellido(rs.getString("ApellRecep"));
                usuario_actual.setTlf(rs.getInt("TelfRecep"));
                usuario_actual.setContraseña(rs.getString("Contrasena"));
                usuario_actual.setAdmin(rs.getBoolean("EsAdmin"));
            }
            
            
            return true;

         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
         }
      }

   }



