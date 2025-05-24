package views;
import java.util.Scanner;
import data.Users;
import utils.Clean;


public class Init{
      private Scanner tcl= new Scanner(System.in);
      private Users usuarios=new Users();
      
   public Init(){
      String i;
      do{
         
         System.out.println("=====INICIO=====");
         System.out.println("1. Login");
         System.out.println("2. Registrar");
         System.out.println("0. Salir");
         System.out.println("================");
         i =tcl.nextLine();
         
         
         
         switch(i){
            case "1":
               new Login(tcl,usuarios);
               break;
            case "2":
               new Register(tcl,usuarios);
               break;
            case "0":
               System.out.println("Bye :)");
               break;
            default:
               System.out.println("Opcion no valida");
         }
         Clean.cmd(); 
         }while(!i.equals("0"));
   }
}