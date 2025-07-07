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
      


      if (ExisteUsuario(persona.getDni(), persona.getContraseña())) {
         JOptionPane.showMessageDialog(null, "El usuario ya existe");

      } else {
         try (CallableStatement cbtm = con.prepareCall("{call Sp_insert_user(?,?,?,?,?,?)}")) {
            cbtm.setString(1, persona.getDni());
            cbtm.setString(2, persona.getNombre());
            cbtm.setString(3, persona.getApellido());
            cbtm.setString(4, persona.getTlf());
            cbtm.setString(5, persona.getContraseña());
            cbtm.setBoolean(6, persona.getAdmin());
            cbtm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
         }
      }
   }

   @Override
   public String[][] seleccionar() {
      return null;
   }

   @Override
   public void Eliminar(String indice) {

   }

   public boolean IniciarSesion(String dni, String contraseña) {
      if (ExisteUsuario(dni, contraseña)) {
         try {
            CallableStatement cstm = con
                  .prepareCall("{call paLogin(?,?)}");
            cstm.setString(1, dni);
            cstm.setString(2, contraseña);
            ResultSet rs = cstm.executeQuery();

            usuario_actual.setDni(rs.getString("DniRecep"));
            usuario_actual.setNombre(rs.getString("NombreRecep"));
            usuario_actual.setApellido(rs.getString("ApellidoRecep"));
            usuario_actual.setTlf(rs.getString("TelefonoRecep"));
            usuario_actual.setContraseña(rs.getString("Contrasena"));
            usuario_actual.setAdmin(rs.getBoolean("Admin"));

            return true;

         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean ExisteUsuario(String dni, String contraseña) {
      try {
         PreparedStatement pstm = con
               .prepareStatement("select * from Recepcionista where DniRecep=? and Contrasena =?");
         pstm.setString(1, dni);
         pstm.setString(2, contraseña);
         ResultSet rs = pstm.executeQuery();

         if (rs.next()) {
            return true;
         } else {
            JOptionPane.showMessageDialog(null, "No existe una cuenta con estas credenciales");
            return false;
         }

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
         return false;
      }

   }
}
