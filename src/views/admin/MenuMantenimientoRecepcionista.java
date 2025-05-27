package src.views.admin;

import java.util.Scanner;

import src.utils.Link;

public class MenuMantenimientoRecepcionista {
public MenuMantenimientoRecepcionista(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Recepcionista ===");
            System.out.println("1. Agregar Recepcionista");
            System.out.println("2. Editar paciente");
            System.out.println("3. Eliminar paciente");
            System.out.println("4. Imprimir pacientes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    
                    break;
                case "2":
                    
                    break;
                case "3":
                    
                    break;
                case "4":

                    break;
                case "0":
                    System.out.println("Saliendo del menú Paciente.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
