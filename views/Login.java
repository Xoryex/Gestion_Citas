package views;
import java.util.Scanner;
import data.Users;
import utils.Clean;

public class Login {
   public String user, pass;
   
   public Login(Scanner tcl,Users usuarios ){

         System.out.println("===========LOGIN===========");
         System.out.print("   Usuario: ");
         user=tcl.nextLine();
         System.out.print("   Password: ");
         pass=tcl.nextLine();
         System.out.println("===========================");
         
         if(usuarios.ValidationUser(user,pass)){
            new Menu(tcl,usuarios,user);
         }
 
         }
         
   }
