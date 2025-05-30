package src.views.admin;

import java.util.Scanner;
import src.utils.Link;


public class MenuMantenimientoHorarios {

    public MenuMantenimientoHorarios(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Horario ===");
            System.out.println("1. Agregar horario");
            System.out.println("2. Editar horario");
            System.out.println("3. Eliminar horario");
            System.out.println("4. Listar horarios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    link.horarios.agregarHorario(tcl);
                    
                    break;
                case "2":
                    // Código para editar horario
                    
                    break;
                case "3":
                    // Código para eliminar horario
                    
                    break;
                case "4":
                    // Código para listar horarios
                    link.horarios.imprimirHorarios();
                    break;
                case "0":
                    System.out.println("Saliendo del menú Horario.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
