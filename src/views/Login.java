package src.views;
import java.util.Scanner;

import src.data.Consultorios;
import src.data.Doctores;
import src.data.Especialidades;
import src.data.Users;
//import src.utils.Clean;
import src.views.admin.Menu;
import src.data.Pacientes;

public class Login {
   public String user, pass;
   
   public Login(Scanner tcl,Users usuarios,Doctores doctores,Especialidades especialidades,Consultorios consultorios, Pacientes pacientes){

         System.out.println("===========LOGIN===========");
         System.out.print("   Usuario: ");
         user=tcl.nextLine();
         System.out.print("   Password: ");
         pass=tcl.nextLine();
         System.out.println("===========================");
         
         if(usuarios.ValidationUser(user,pass)){
            new Menu(tcl,usuarios,doctores,especialidades,consultorios,pacientes,user);
         }
 
         }
         
   }
