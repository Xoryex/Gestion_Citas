package src.views;

import java.util.Scanner;

import src.utils.Link;


public class Init{
      
   public Init(Scanner tcl,Link link){
      String opcion;
      do{
         
         System.out.println("=====INICIO=====");
         System.out.println("1. Login");
         System.out.println("2. Registrar");
         System.out.println("0. Salir");
         System.out.println("================");
         System.out.print("Seleccionar opcion: ");
         opcion =tcl.nextLine();
         
         
         
         switch(opcion){
            case "1":
               new Login(tcl,link);
               break;
            case "2":
               new MenuRegistro(tcl,link);
               break;
            case "0":
               System.out.println("Bye :)");
               break;
            default:
               System.out.println("Opcion no valida");
         }
        
         }while(!opcion.equals("0"));
   }
}