package src.views;
import java.util.Scanner;

import src.utils.Link;

import src.utils.Clean;

public class MenuRegistro {
   
   public MenuRegistro(Scanner tcl,Link link){
         String dni,nombre,apellido,usuario,contraseña,tlf;
         
         System.out.println("===========Register===========");
         link.usuarios.Crear(tcl,false);
         System.out.println("==============================");
 
      }
   }
