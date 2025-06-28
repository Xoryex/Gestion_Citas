package src.main;

import src.frames.Init;
import src.utils.Conexion;

class Main {
   public static void main(String[] args) {
      // Iniciar la aplicación
      new Init();
      Conexion.closeConnection(); // Cerrar conexión al finalizar
   }
}