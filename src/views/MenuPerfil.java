package src.views;

import java.util.Scanner;

import src.utils.Link;


public class MenuPerfil{
      
   public MenuPerfil(Scanner tcl, Link link, String usuarioAct){
      String opcion;
      do{

         System.out.println("=====PERFIL=====");
         link.usuarios.ShowDate(usuarioAct);
         System.out.println("1. Cambiar nombre");
         System.out.println("2. Cambiar contraseña");
         System.out.println("3. Eliminar perfil");
         System.out.println("0. Salir");
         System.out.println("================");
         opcion =tcl.nextLine();
         
         switch(opcion){
            case "1":
                  usuarioAct=link.usuarios.ChangeNameUser(tcl,usuarioAct);
               break;
            case "2":
                  link.usuarios.ChangePassUser(tcl, usuarioAct);
               break;
            case "3":
                  link.usuarios.DropUser(usuarioAct);
                  opcion="0";
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }
}