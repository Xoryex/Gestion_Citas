package src.views.admin;
import java.util.Scanner;

import src.utils.Link;
import src.views.admin.MenuEditar.MenuEditarDoctor;

public class MenuMantenimientoDoctores {

    public MenuMantenimientoDoctores(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Doctor ===");
            System.out.println("1. Agregar doctor");
            System.out.println("2. Editar doctor");
            System.out.println("3. Eliminar doctor");
            System.out.println("4. Listar doctores");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    link.doctores.agregarDoctor(tcl);
                    break;
                case "2":
                    new MenuEditarDoctor (tcl, link); 
                    break;
                case "3":
                    link.doctores.borrarDoctor(tcl);
                    break;
                case "4": 
                    link.doctores.listarDoctores();
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