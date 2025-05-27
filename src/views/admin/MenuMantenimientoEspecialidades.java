package src.views.admin;

import java.util.Scanner;
import src.utils.Link;


public class MenuMantenimientoEspecialidades {

    public MenuMantenimientoEspecialidades(Scanner tcl,Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Especialidad ===");
            System.out.println("1. Agregar especialidad");
            System.out.println("2. Editar especialidad");
            System.out.println("3. Eliminar especialidad");
            System.out.println("4. Listar especialidades");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    link.especialidades.AgregarEspecialidad(tcl);
                    break;
                case "2":
                    link.especialidades.editarEspecialidad(tcl);
                    break;
                case "3":
                    link.especialidades.eliminarEspecialidad(tcl, link.doctores.getListaDoctores());
                    break;
                case "4":
                    link.especialidades.imprimirEspecialidades();
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
