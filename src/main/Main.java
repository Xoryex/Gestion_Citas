package src.main;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static src.utils.Conexion.con;
import src.views.Init;



class Main {
   public static void main(String[] args) {

      Init init =new Init();
      init.setVisible(true);
      init.setLocationRelativeTo(null);
      
      try{
      con.close();
      }catch(SQLException e){
         JOptionPane.showMessageDialog(null,e.toString());
      }
   }
}