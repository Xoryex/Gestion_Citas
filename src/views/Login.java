package src.views;
import java.util.Scanner;

import src.utils.Link;
//import src.utils.Clean;
import src.views.admin.Menu;


public class Login {
   public String user, pass;
   
   public Login(Scanner tcl,Link link){

         System.out.println("===========LOGIN===========");
         System.out.print("   Usuario: ");
         user=tcl.nextLine();
         System.out.print("   Password: ");
         pass=tcl.nextLine();
         System.out.println("===========================");
         

         if(link.usuarios.ValidationUser(user,pass)){
            new Menu(tcl,link,user);
         }
 
         }
         
   }
