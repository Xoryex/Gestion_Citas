package src.views.admin;


import java.util.Scanner;

import src.data.Consultorios;
import src.data.Doctores;
import src.data.Especialidades;
import src.data.Pacientes;
import src.data.Users;
import src.views.MenuPerfil; 



public class Menu{
   
   public Menu(Scanner tcl,Users usuarios,Doctores doctores,Especialidades especialidades,Consultorios consultorios,Pacientes pacientes,String usuarioAct){
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
                  new MenuPerfil(tcl,usuarios,usuarioAct);
                  opcion=(usuarios.ValidationUser(usuarioAct)?"1":"0");
               break;
            case "2":
                  new MenuMantenimiento(tcl,usuarios,doctores,especialidades,consultorios, pacientes);
               break;
            case "3":
               
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!opcion.equals("0"));
   }

}