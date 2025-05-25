package src.views.admin;


import java.util.Scanner;

import src.utils.Link;
import src.views.MenuPerfil; 



public class MenuAdmin{
   
   public MenuAdmin(Scanner tcl,Link link,String usuarioAct){
         String opcion;
      do{
         link.usuarios.Bienvenida(usuarioAct);
         System.out.println("======MENU======");
         System.out.println("1. Perfil");
         System.out.println("2. Mantenimiendo");
         System.out.println("3. Transacciones");
         System.out.println("0. Exit");
         System.out.println("================");
         
         opcion =tcl.nextLine();
         
         switch(opcion){
            case "1":
                  new MenuPerfil(tcl,link,usuarioAct);
                  opcion=(link.usuarios.ValidarCredenciales(usuarioAct)?"1":"0");
               break;
            case "2":
                  new MenuMantenimiento(tcl,link);
               break;
            case "3":
               
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }

}