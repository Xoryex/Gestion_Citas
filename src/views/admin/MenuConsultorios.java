package src.views.admin;

import java.util.Scanner;
import src.database.Bd;
import src.entidades.Consultorio;

public class MenuConsultorios {

    public MenuConsultorios(Scanner tcl, Bd bd) {
        String opcion;

        do {
            System.out.println("\n=== Menú Consultorio ===");
            System.out.println("1. Agregar consultorio");
            System.out.println("2. Editar consultorio");
            System.out.println("3. Eliminar consultorio");
            System.out.println("4. Ver lista de consultorios");  // Nueva opción
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println("\n-- Registrar consultorio --");
                    while (true) {
                        Consultorio nuevo = new Consultorio();
                        nuevo.Registrar();

                        System.out.print("¿Desea registrar otro consultorio? Enter para continuar, 0 para finalizar: ");
                        String respuesta = tcl.nextLine().trim();
                        if (respuesta.equals("0")) break;
                    }
                    break;

                case "2":
                    System.out.println("\n-- Editar consultorio --");
                    Consultorio.editarPorCodigo(tcl);
                    break;

                case "3":
                    System.out.println("\n-- Eliminar consultorio --");
                    Consultorio.eliminarPorCodigo(tcl);
                    break;

                case "4":
                    System.out.println("\n-- Lista de consultorios registrados --");
                    Consultorio.mostrarRegistros();
                    break;

                case "0":
                    System.out.println("Salió del menú de consultorios.");
                    break;

                default:
                    System.out.println("Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}