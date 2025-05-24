package data;

import java.util.Scanner;

public class Mantenimiento {

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("=== Menú de Mantenimiento ===");
            System.out.println("1. Ver tablas");
            System.out.println("2. Editar tablas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    
                    // Aquí puedes agregar el código para mostrar las tablas
                    break;
                case 2:
                    
                    // Aquí puedes llamar a otro método para el menú de edición
                    break;
                case 3:
                    System.out.println("Saliendo del menú de mantenimiento.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }
}
