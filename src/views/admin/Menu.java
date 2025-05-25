package src.views.admin;


import java.util.Scanner;

import src.database.Bd;
import src.views.MenuPerfil; 

public class Menu{
   
   public Menu(Scanner tcl,Bd bd,String usuarioAct){
         String opcion;
      do{

         System.out.println("======MENU======");
         System.out.println("1. Perfil");
         System.out.println("2. Mantenimiendo");
         System.out.println("3. Transacciones");
         System.out.println("0. Exit");
         System.out.println("================");
         
         opcion =tcl.nextLine();
         
         switch(opcion){
            case "1":
                  new MenuPerfil(tcl,bd,usuarioAct);
                  opcion=(bd.usuarios.ValidationUser(usuarioAct)?"1":"0");
               break;
            case "2":
                  new MenuMantenimiento(tcl,bd);
               break;
            case "3":
               
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }

}