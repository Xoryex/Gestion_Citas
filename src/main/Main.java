package src.main;
import java.util.Scanner;

import src.utils.Link;
import src.views.Init;



class Main {
   public static void main(String[] args) {
            
       Scanner tcl= new Scanner(System.in);
       Link link = new Link();
    
      new Init(tcl,link);
   
   }
}