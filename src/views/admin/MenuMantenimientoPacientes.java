package src.views.admin;

import java.util.Scanner;

import src.utils.Link;

public class MenuMantenimientoPacientes {

    public MenuMantenimientoPacientes(Scanner tcl, Link link) {
        String opcion;
        do {
            System.out.println("=== Menú Paciente ===");
            System.out.println("1. Agregar paciente");
            System.out.println("2. Editar paciente");
            System.out.println("3. Eliminar paciente");
            System.out.println("4. Imprimir pacientes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    link.pacientes.agregarPaciente(tcl);
                    
                    break;
                case "2":
                    link.pacientes.editarPaciente(tcl);
                    
                    break;
                case "3":
                    link.pacientes.borrarPaciente(tcl);
                    
                    break;
                case "4":
                    link.pacientes.imprimirPacientes();
                    break;
                case "0":
                    System.out.println("Saliendo del menú Paciente.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}