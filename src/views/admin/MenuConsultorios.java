package src.views.admin;

import java.util.Scanner;

public class MenuConsultorios {

    public MenuConsultorios(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú Consultorio ===");
            System.out.println("1. Agregar consultorio");
            System.out.println("2. Editar consultorio");
            System.out.println("3. Eliminar consultorio");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Código para agregar consultorio
                    System.out.println("Agregar consultorio seleccionado.");
                    break;
                case "2":
                    // Código para editar consultorio
                    System.out.println("Editar consultorio seleccionado.");
                    break;
                case "3":
                    // Código para eliminar consultorio
                    System.out.println("Eliminar consultorio seleccionado.");
                    break;
                case "0":
                    System.out.println("Saliendo del menú Consultorio.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
