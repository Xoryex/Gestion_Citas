package views;
import java.util.Scanner;
import data.Users; 
import utils.Clean;

public class MenuRegister {
   
   public MenuRegister(Scanner tcl,Users usuarios){
         Clean.cmd();
         System.out.println("===========Register===========");
         System.out.print("   Usuario: ");
         String user=tcl.nextLine();
         System.out.print("   Password: ");
         String pass=tcl.nextLine();
         System.out.println("==============================");
         
         if(usuarios.CreateUser(user,pass)){
           System.out.println("Usuario registrado con exito"); 
         }else{
            System.out.println("Credenciales ocupadadas"); 
            }
        
      }
   }
