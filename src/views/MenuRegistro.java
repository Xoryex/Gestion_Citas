package src.views;
import java.util.Scanner;

import src.utils.Link;

import src.utils.Clean;

public class MenuRegistro {
   
   public MenuRegistro(Scanner tcl,Link link){
         Clean.cmd();
         System.out.println("===========Register===========");
         System.out.print("   Usuario: ");
         String user=tcl.nextLine();
         System.out.print("   Nombre: ");
         String name=tcl.nextLine();
         System.out.print("   Password: ");
         String pass=tcl.nextLine();
         System.out.println("==============================");
         
         if(link.usuarios.Crear(user,name,pass,false)){
           System.out.println("Usuario registrado con exito"); 
         }else{
            System.out.println("Credenciales ocupadadas"); 
            }
        
      }
   }
