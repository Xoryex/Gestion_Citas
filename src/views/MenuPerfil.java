package src.views;

import java.util.Scanner;

import src.utils.Link;


public class MenuPerfil{
      
   public MenuPerfil(Scanner tcl, Link link, String usuarioAct){
      String opcion;
      do{

         System.out.println("=====PERFIL=====");
         link.usuarios.MostrarDatos(usuarioAct);
         System.out.println("1. Cambiar Nombre");
         System.out.println("2. Cambiar usuario");
         System.out.println("3. Cambiar contraseña");
         System.out.println("4. Eliminar perfil");
         System.out.println("0. Salir");
         System.out.println("================");
         opcion =tcl.nextLine();
         
         switch(opcion){
            case "1":
                  //usuarioAct=link.usuarios.CambiarNombre(tcl,usuarioAct);
               break;
            case "2":
                  usuarioAct=link.usuarios.CambiarUsuario(tcl,usuarioAct);
               break;
            case "3":
                  link.usuarios.CambiarContraseña(tcl, usuarioAct);
               break;
            case "4":
                  link.usuarios.Eliminar(usuarioAct);
                  opcion="0";
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }
}