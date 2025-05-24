package views;
import java.util.Scanner;
import data.Users;
import utils.Clean;

public class Perfil{
      
   public Perfil(Scanner tcl, Users usuarios, String usuarioAct){
      String i;
      do{

         System.out.println("=====PERFIL=====");
         usuarios.ShowDate(usuarioAct);
         System.out.println("1. Cambiar nombre");
         System.out.println("2. Cambiar contraseña");
         System.out.println("3. Eliminar perfil");
         System.out.println("0. Salir");
         System.out.println("================");
         i =tcl.nextLine();
         
         switch(i){
            case "1":
                  usuarioAct=usuarios.ChangeNameUser(tcl,usuarioAct);
               break;
            case "2":
                  usuarios.ChangePassUser(tcl, usuarioAct);
               break;
            case "3":
                  usuarios.DropUser(usuarioAct);
                  i="0";
               break;
            default:
               System.out.println("Opcion no valida");
         }

         }while(!i.equals("0"));
   }
}