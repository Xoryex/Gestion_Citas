import java.util.Scanner;

import data.Consultorios;
import data.Doctores;
import data.Especialidades;
import data.Users;

import views.Init;

class Main {
   public static void main(String[] args) {
            
       Scanner tcl= new Scanner(System.in);
       Users usuarios=new Users();
       Doctores doctores=new Doctores();
       Especialidades especialidades =new Especialidades();
       Consultorios consultorios=new Consultorios();
    
      new Init(tcl,usuarios,doctores,especialidades,consultorios);
   
   }
}