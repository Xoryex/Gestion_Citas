package src.main;
import java.util.Scanner;

import src.data.Consultorios;
import src.data.Doctores;
import src.data.Especialidades;
import src.data.Pacientes;
import src.data.Users;

import src.views.Init;

class Main {
   public static void main(String[] args) {
            
       Scanner tcl= new Scanner(System.in);
       Users usuarios=new Users();
       Doctores doctores=new Doctores();
       Especialidades especialidades =new Especialidades();
       Consultorios consultorios=new Consultorios();
       Pacientes pacientes=new Pacientes();
    
      new Init(tcl,usuarios,doctores,especialidades,consultorios, pacientes);
   
   }
}