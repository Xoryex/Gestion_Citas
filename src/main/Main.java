package main;

import views.Init;
import utils.Conexion;

class Main {
   public static void main(String[] args) {
      // Iniciar la aplicación
      new Init();
      Conexion.closeConnection(); // Cerrar conexión al finalizar
   }
}