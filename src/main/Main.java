package main;

import utils.Conexion;
import views.Init;

class Main {
   public static void main(String[] args) {
      // Iniciar la aplicación
      new Init();

      // Cerrar conexión
      Conexion.closeConnection(); // Cerrar conexión al finalizar
   }
}