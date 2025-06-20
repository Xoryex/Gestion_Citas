   package src.querys;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import src.objetos.User;

import static src.utils.Conexion.con;
   
   public class QueryUser implements Query<User>{
      
      
      @Override
      public void Modificar( User modificacion) {
          // TODO Auto-generated method stub
          
      }
      @Override
      public void Crear(User persona) {
          // TODO Auto-generated method stub
          
      }
      @Override
      public Object Mostrar() {
          // TODO Auto-generated method stub
          return null;
      }
      @Override
      public void Eliminar(String indice) {
          // TODO Auto-generated method stub
          
      }

      public static boolean Verificarusuario (String dni , String contraseña){
         try{

            PreparedStatement pstm = con.prepareStatement("select * from Recepcionista where DniRecep=? and Contrasena =?");
            pstm.setString(1, dni);
            pstm.setString(2, contraseña);
            ResultSet rs = pstm.executeQuery();
            
            
            if (rs.next()) {
               usuario_actual.dni=rs.getString(1);
               usuario_actual.nombre=rs.getString(2);
               usuario_actual.apellido=rs.getString(3) ;
               usuario_actual.tlf=rs.getString(4);
               usuario_actual.contraseña=rs.getString(5) ;
               usuario_actual.admin=rs.getBoolean(6) ;
               
               return true;
            }else{
               JOptionPane.showMessageDialog(null, "No existe tu perfil");
               return false;
               }

         }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return false;
         }
         
      
   }
   }
