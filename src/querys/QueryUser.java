package querys;

import java.sql.*;
import javax.swing.JOptionPane;
import models.Recepcionista;

import utils.Conexion;

public class QueryUser implements Query<Recepcionista> {
   public static Recepcionista usuario_actual = new Recepcionista();


   @Override
   public void actualizar(Recepcionista datos_modificados) {
      CallableStatement cstm =null;
      try{ 
         cstm = Conexion.getConnection().prepareCall("{call PA_CRUD_ModificarRecepcionista(?,?,?,?,?,?)}");
         cstm.setInt(1, usuario_actual.getDni());
         cstm.setString(2, usuario_actual.getNombre());
         cstm.setString(3, usuario_actual.getApellido());
         cstm.setInt(4, usuario_actual.getTlf());
         cstm.setString(5, usuario_actual.getContraseña());
         cstm.setBoolean(6, usuario_actual.getAdmin());
         cstm.executeUpdate();

      }catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }finally{
         try {
            if (cstm != null) {
               cstm.close();
            }

         } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
      }
   }
      
   @Override
   public void Insetar(Recepcionista persona) {
      try {
         CallableStatement cstm = Conexion.getConnection().prepareCall("{call PA_CRUD_InsertarRecepcionista(?,?,?,?,?,?)}");
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
   public void Eliminar(int indice) {
                        CallableStatement cstm=null;
      try {
                        cstm = Conexion.getConnection()
                                .prepareCall("{call  PA_CRUD_EliminarRecepcionista (?) }");
                        cstm.setInt(1,indice);
                        cstm.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Recepcionista eliminado");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }finally{
                        try {
                           if(cstm != null) cstm.close(); 
                        } catch (Exception exel) {
                            JOptionPane.showMessageDialog(null,exel.getMessage());
                            
                        }
                        
                    }
   }

   public boolean IniciarSesion(int dni, String contraseña) {
      
         try {
            CallableStatement cstm = Conexion.getConnection().prepareCall("{call  paLogin(?,?)}");
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



