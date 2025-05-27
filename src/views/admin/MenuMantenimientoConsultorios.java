package src.views.admin;

import java.util.Scanner;
import src.utils.Link;

public class MenuMantenimientoConsultorios {

    public MenuMantenimientoConsultorios(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Consultorio ===");
            System.out.println("1. Agregar consultorio");
            System.out.println("2. Editar Nombre del consultorio");
            System.out.println("3. Editar el Piso del consultorio");
            System.out.println("4. editar la habitacion del consultorio");
            System.out.println("5. Ver lista de consultorios");
            System.out.println("6. Eliminar consultorios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine().trim();

            switch (opcion) {
                case "1":
                    link.consultorios.RegistrarConsultorio(tcl);
                    break;
                case "2":
                    link.consultorios.CambioNombre(tcl);
                    break;
                case "3":
                    link.consultorios.CambioPiso(tcl);
                    break;
                case "4":
                    link.consultorios.CambioHabitacion(tcl);
                    break;
                case "5":
                    link.consultorios.ListaConsult();
                    break;
                case "6":
                    link.consultorios.Eliminar(tcl);
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
