package src.views;

import java.util.Scanner;

import src.database.Bd;


public class MenuPerfil{
      
   public MenuPerfil(Scanner tcl, Bd bd, String usuarioAct){
      String opcion;
      do{

         System.out.println("=====PERFIL=====");
         bd.usuarios.ShowDate(usuarioAct);
         System.out.println("1. Cambiar nombre");
         System.out.println("2. Cambiar contraseña");
         System.out.println("3. Eliminar perfil");
         System.out.println("0. Salir");
         System.out.println("================");
         opcion =tcl.nextLine();
         
         switch(opcion){
            case "1":
                  usuarioAct=bd.usuarios.ChangeNameUser(tcl,usuarioAct);
               break;
            case "2":
                  bd.usuarios.ChangePassUser(tcl, usuarioAct);
               break;
            case "3":
                  bd.usuarios.DropUser(usuarioAct);
                  opcion="0";
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }
}