package views;

import java.util.Scanner;

public class MenuEspecialidad {

    public MenuEspecialidad(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú Especialidad ===");
            System.out.println("1. Agregar especialidad");
            System.out.println("2. Editar especialidad");
            System.out.println("3. Eliminar especialidad");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Código para agregar especialidad
                    
                    break;
                case "2":
                    // Código para editar especialidad
                    
                    break;
                case "3":
                    // Código para eliminar especialidad
                    
                    break;
                case "0":
                    System.out.println("Saliendo del menú Especialidad.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
