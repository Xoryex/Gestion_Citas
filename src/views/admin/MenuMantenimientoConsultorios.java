package src.views.admin;

import java.util.Scanner;
<<<<<<< HEAD
import src.entidades.Consultorio;
=======
>>>>>>> 0939171aaa1b7b492a271fa0e2a108ca56ce3079
import src.utils.Link;

public class MenuMantenimientoConsultorios {

    public MenuMantenimientoConsultorios(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Consultorio ===");
            System.out.println("1. Agregar consultorio");
            System.out.println("2. Editar consultorio");
            System.out.println("3. Eliminar consultorio");
            System.out.println("4. Ver lista de consultorios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println("\n-- Registrar consultorio --");
                    while (true) {
                        
                        link.consultorios.Registrar();

                        System.out.print("¿Desea registrar otro consultorio? Enter para continuar, 0 para finalizar: ");
                        String respuesta = tcl.nextLine().trim();
                        if (respuesta.equals("0")) break;
                    }
                    break;
                case "2":
                    System.out.println("\n-- Editar consultorio --");
                    link.consultorios.editarPorCodigo(tcl);
                    break;
                case "3":
                    System.out.println("\n-- Eliminar consultorio --");
                    link.consultorios.eliminarPorCodigo(tcl);
                    break;
                case "4":
                    System.out.println("\n-- Lista de consultorios registrados --");
                    link.consultorios.mostrarRegistros();
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
