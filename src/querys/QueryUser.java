   package src.querys;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import src.querys.objetos.User;

   
   public class QueryUser {
   
      
      
      
      public static boolean Verificarusuario (String dni , String contraseña){
         try{

            PreparedStatement pstm = Query.con.prepareStatement("select * from Recepcionista where DniRecep=? and Contrasena =?");
            pstm.setString(1, dni);
            pstm.setString(2, contraseña);
            ResultSet rs = pstm.executeQuery();
            
            
            if (rs.next()) {
               User.usuario_actual.dni=rs.getString(1);
               User.usuario_actual.nombre=rs.getString(2);
               User.usuario_actual.apellido=rs.getString(3) ;
               User.usuario_actual.tlf=rs.getString(4) ;
               User.usuario_actual.contraseña=rs.getString(5) ;
               User.usuario_actual.admin=rs.getBoolean(6) ;
               
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
