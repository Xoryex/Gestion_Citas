package src.views;

import java.util.Scanner;

import src.database.Bd;



public class Init{
      
   public Init(Scanner tcl,Bd bd){
      String opcion;
      do{
         
         System.out.println("=====INICIO=====");
         System.out.println("1. Login");
         System.out.println("2. Registrar");
         System.out.println("0. Salir");
         System.out.println("================");
         opcion =tcl.nextLine();
         
         
         
         switch(opcion){
            case "1":
               new Login(tcl,bd);
               break;
            case "2":
               new MenuRegister(tcl,bd);
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