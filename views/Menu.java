package views;
import java.util.Scanner;
import data.Users;
import data.Especialidades;
import data.Consultorios; 
import data.Doctores;
import utils.Clean;

public class Menu{
   private Consultorios consultorios = new Consultorios();
   private Doctores doctores = new Doctores();
   
   public Menu(Scanner tcl,Users usuarios,String usuarioAct){
         String i;
      do{

         System.out.println("======MENU======");
         System.out.println("1. Perfil");
         System.out.println("2. Mantenimiendo");
         System.out.println("3. Transacciones");
         System.out.println("0. Exit");
         System.out.println("================");
         
         i =tcl.nextLine();
         
         switch(i){
            case "1":
                  new Perfil(tcl,usuarios,usuarioAct);
                  i=(usuarios.ValidationUser(usuarioAct)?"1":"0");
               break;
            case "2":
                  
               break;
            case "3":
               
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!i.equals("0"));
   }

}