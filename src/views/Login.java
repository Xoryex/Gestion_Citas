package src.views;
import java.util.Scanner;

import src.utils.Link;
//import src.utils.Clean;
import src.views.admin.MenuAdmin;


public class Login {
   public String usuario, pass;
   
   public Login(Scanner tcl,Link link){

         System.out.println("===========LOGIN===========");
         do{
         System.out.print("   Usuario: ");
         usuario=tcl.nextLine();
         }while(usuario.equals(""));
         do {
         System.out.print("   Password: ");
         pass=tcl.nextLine();
         } while (pass.equals(""));
         System.out.println("===========================");
         

         switch(link.usuarios.ValidarCredenciales(usuario,pass)){
            case "1":
               new MenuAdmin(tcl,link,usuario);
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
