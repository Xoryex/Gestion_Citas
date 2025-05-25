package views.admin;
import data.Consultorios; 
import data.Doctores;
import data.Especialidades;
import data.Users;
import views.MenuPerfil;

import java.util.Scanner; 

public class Menu{
   
   public Menu(Scanner tcl,Users usuarios,Doctores doctores,Especialidades especialidades,Consultorios consultorios,String usuarioAct){
         String opcion;
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
                  new MenuPerfil(tcl,usuarios,usuarioAct);
                  i=(usuarios.ValidationUser(usuarioAct)?"1":"0");
               break;
            case "2":
                  new MenuMantenimiento(tcl,usuarios,doctores,especialidades,consultorios);
               break;
            case "3":
               
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!i.equals("0"));
   }

}