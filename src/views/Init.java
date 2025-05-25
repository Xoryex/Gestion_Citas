package src.views;

import java.util.Scanner;
import src.data.Users;
//import src.utils.Clean;
import src.data.Consultorios;
import src.data.Doctores;
import src.data.Especialidades;


public class Init{
      
   public Init(Scanner tcl,Users usuarios,Doctores doctores,Especialidades especialidades,Consultorios consultorios){
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
               new Login(tcl,usuarios,doctores,especialidades,consultorios);
               break;
            case "2":
               new MenuRegister(tcl,usuarios);
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