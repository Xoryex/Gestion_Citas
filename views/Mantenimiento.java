package views;

import java.util.Scanner;

public class Mantenimiento {

    public Mantenimiento(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú de Mantenimiento ===");
            System.out.println("1. Paciente");
            System.out.println("2. Recepcionista");
            System.out.println("3. Cita");
            System.out.println("4. Horario");
            System.out.println("5. Consultorio");
            System.out.println("6. Doctor");
            System.out.println("7. Especialidad");
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
                    // Código para la opción 6
                    break;
                case "7":
                    System.out.println("Saliendo del menú de mantenimiento.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("7"));
    }
}
