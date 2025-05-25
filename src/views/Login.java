package src.views;
import java.util.Scanner;

import src.database.Bd;

//import src.utils.Clean;
import src.views.admin.Menu;


public class Login {
   public String user, pass;
   
   public Login(Scanner tcl,Bd bd){

         System.out.println("===========LOGIN===========");
         System.out.print("   Usuario: ");
         user=tcl.nextLine();
         System.out.print("   Password: ");
         pass=tcl.nextLine();
         System.out.println("===========================");
         

         if(bd.usuarios.ValidationUser(user,pass)){
            new Menu(tcl,bd,user);
         }
 
         }
         
   }
