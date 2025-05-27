package src.views;

import java.util.Scanner;

import src.utils.Link;


public class MenuPerfil{
      
   public MenuPerfil(Scanner tcl, Link link, String usuarioAct){
      String opcion;
      do{

         System.out.println("=========== PERFIL ===========");
         System.out.println("1. Cambiar Dni");
         System.out.println("2. Cambiar Nombre");
         System.out.println("3. Cambiar Apellido");
         System.out.println("4. Cambiar Telefono");
         System.out.println("5. Cambiar contraseña");
         System.out.println("6. Mostrar datos de perfil");
         System.out.println("7. Eliminar perfil");
         System.out.println("0. Salir");
         System.out.println("==============================");
         System.out.print("Seleccionar opcion: ");
         opcion =tcl.nextLine();
         
         switch(opcion){
            case "1":
                  link.usuarios.CambiarDni(tcl,usuarioAct);
               break;
            case "2":
                  link.usuarios.CambiarNombre(tcl,usuarioAct);
               break;
            case "3":
                  link.usuarios.CambiarApellido(tcl, usuarioAct);
               break;
            
            
            case "5":
                  link.usuarios.CambiarContraseña(tcl, usuarioAct);
               break;

               case "6":
                  link.usuarios.MostrarDatos(usuarioAct);
               break;

               case "7":
                  link.usuarios.Eliminar(usuarioAct);
                  opcion="0";
               break;   
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }
}