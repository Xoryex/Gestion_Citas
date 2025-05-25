package views;

import java.util.Scanner;
import data.Especialidades;

public class MenuEspecialidades {

    public MenuEspecialidades(Scanner tcl,Especialidades especialidades) {
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
                    especialidades.agregarEspecialidad(tcl);
                    break;
                case "2":
                    especialidades.editarEspecialidad(tcl);
                    break;
                case "3":
                    especialidades.eliminarEspecialidad(tcl);
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
