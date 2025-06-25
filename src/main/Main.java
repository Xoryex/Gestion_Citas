package src.main;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import src.frames.Init;

import static src.utils.Conexion.con;



class Main {
   public static void main(String[] args) {

      new Init();
      try{
      con.close();
      }catch(SQLException e){
         JOptionPane.showMessageDialog(null,e.toString());
      }
   }
}