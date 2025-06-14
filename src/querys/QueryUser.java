   package src.querys;



import java.sql.SQLException;

import javax.swing.JOptionPane;

import src.querys.objetos.User;

   
   public class QueryUser extends Query{
      static User usuarioactual =new User() ;
      
   
      public static void Verificarusuario (String dni , String contraseña){
         
         try{

            pstm = con.prepareStatement("select * from Recepcionista where DniRecep=? and Contrasena =?");
            pstm.setString(1, dni);
            pstm.setString(2, contraseña);
            rs=pstm.executeQuery();
            
            
            if (rs.next()) {
               //usuarioactual.dni=rs.getString(1);
               //usuarioactual.nombre=rs.getString(2);
               //usuarioactual.apellido=rs.getString(3) ;
               //usuarioactual.tlf=rs.getString(4) ;
               //usuarioactual.contraseña=rs.getString(5) ;
               //usuarioactual.admin=rs.getBoolean(6) ;
               JOptionPane.showMessageDialog(null, "Bienvenido"+rs.getString(2) );
               //return true;
            }//else{
               //return false;
               //}
            
            
         }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());

            //return false;
         }
         
      
   }
}
