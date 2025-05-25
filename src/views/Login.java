package src.views;
import java.util.Scanner;

import src.utils.Link;
//import src.utils.Clean;
import src.views.admin.MenuAdmin;


public class Login {
   public String user, pass;
   
   public Login(Scanner tcl,Link link){

         System.out.println("===========LOGIN===========");
         System.out.print("   Usuario: ");
         user=tcl.nextLine();
         System.out.print("   Password: ");
         pass=tcl.nextLine();
         System.out.println("===========================");
         

         switch(link.usuarios.ValidarCredenciales(user,pass)){
            case "1":
               new MenuAdmin(tcl,link,user);
               break;
            case "2":
               //Menu de los q crean citas
               break;
            case "0":
               System.out.println("Credenciales invalidas");
            break;
            }
 
         }
         
   }
