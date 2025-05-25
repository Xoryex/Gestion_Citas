package views.noadmin;

import java.util.Scanner;

public class MenuCita {

    public MenuCita(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú Cita ===");
            System.out.println("1. Agregar cita");
            System.out.println("2. Editar cita");
            System.out.println("3. Eliminar cita");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Código para agregar cita
                    
                    break;
                case "2":
                    // Código para editar cita
                    
                    break;
                case "3":
                    // Código para eliminar cita
                    
                    break;
                case "0":
                    System.out.println("Saliendo del menú Cita.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
