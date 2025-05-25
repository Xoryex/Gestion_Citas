package views;

import java.util.Scanner;

public class MenuRecepcionista {

    public MenuRecepcionista(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú Recepcionista ===");
            System.out.println("1. Agregar recepcionista");
            System.out.println("2. Editar recepcionista");
            System.out.println("3. Eliminar recepcionista");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Código para agregar recepcionista
                    
                    break;
                case "2":
                    // Código para editar recepcionista
                    
                    break;
                case "3":
                    // Código para eliminar recepcionista
                    
                    break;
                case "0":
                    
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
