package src.main;
import java.util.Scanner;

import src.database.Bd;
import src.views.Init;

class Main {
   public static void main(String[] args) {
            
       Scanner tcl= new Scanner(System.in);
       Bd bd = new Bd();
    
      new Init(tcl,bd);
   
   }
}