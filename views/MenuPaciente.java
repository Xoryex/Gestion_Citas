package views;

import java.util.Scanner;

public class MenuPaciente {

    public MenuPaciente(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú Paciente ===");
            System.out.println("1. Agregar paciente");
            System.out.println("2. Editar paciente");
            System.out.println("3. Eliminar paciente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Código para agregar paciente
                    
                    break;
                case "2":
                    // Código para editar paciente
                    
                    break;
                case "3":
                    // Código para eliminar paciente
                    
                    break;
                case "4":
                    System.out.println("Saliendo del menú Paciente.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("4"));
    }
}