package views;
import java.util.Scanner;

public class MenuDoctor {

    public MenuDoctor(Scanner tcl) {
        String opcion;
        do {
            System.out.println("=== Menú Doctor ===");
            System.out.println("1. Agregar doctor");
            System.out.println("2. Editar doctor");
            System.out.println("3. Eliminar doctor");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    // Código para agregar doctor
                    
                    break;
                case "2":
                    // Código para editar doctor
                    
                    break;
                case "3":
                    // Código para eliminar doctor
                    
                    break;
                case "0":
                    System.out.println("Saliendo del menú Doctor.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}