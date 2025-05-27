package src.views.admin;

import java.util.Scanner;
import src.utils.Link;

public class MenuMantenimientoConsultorios {

    public MenuMantenimientoConsultorios(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Consultorio ===");
            System.out.println("1. Agregar consultorio");
            System.out.println("2. Editar consultorio");
            System.out.println("3. Eliminar consultorio");
            System.out.println("4. Ver lista de consultorios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine().trim();

            switch (opcion) {
                case "1":
                    link.consultorios.//metodo(parametro);
                    break;
                case "2":

                    break;
                case "3":
                    
                    break;
                case "4":
                    
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
