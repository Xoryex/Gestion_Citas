package src.views.admin;

import java.util.Scanner;

import src.database.Bd;


public class MenuMantenimiento {

    public MenuMantenimiento(Scanner tcl,Bd bd) {
        String opcion;
        do {
            System.out.println("=== Menú de Mantenimiento ===");
            System.out.println("1. Pacientes");
            System.out.println("2. Recepcionistas");
            System.out.println("3. Horarios");
            System.out.println("4. Consultorios");
            System.out.println("5. Doctors");
            System.out.println("6. Especialidades");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Aquí puedes agregar el código para mostrar las tablas
                    break;
                case "2":
                    // Aquí puedes llamar a otro método para el menú de edición
                    break;
                case "3":
                    // Aquí puedes llamar a otro método para el menú de edición
                    break;
                case "4":
                    // Código para la opción 4
                    break;
                case "5":
                    // Código para la opción 5
                    break;
                case "6":
                    new MenuEspecialidades(tcl,bd);
                    break;
                case "0":
                    System.out.println("Saliendo del menú de mantenimiento.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
