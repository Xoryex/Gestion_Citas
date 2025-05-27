package src.views.admin;

import java.util.Scanner;
import src.utils.Link;



public class MenuMantenimiento {

    public MenuMantenimiento(Scanner tcl,Link link) {
        String opcion;
        do {
            System.out.println("=== Menú de Mantenimiento ===");
            System.out.println("1. Pacientes");//\
            System.out.println("2. Recepcionistas");
            System.out.println("3. Horarios");
            System.out.println("4. Consultorios"); //\
            System.out.println("5. Doctores");
            System.out.println("6. Especialidades");//\
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = tcl.nextLine();

            switch (opcion) {
                case "1":
                    new MenuMantenimientoPacientes(tcl, link);
                    break;
                case "2":
                    // Aquí puedes llamar a otro método para el menú de edición
                    break;
                case "3":
                    new MenuMantenimientoHorarios(tcl, link);
                    break;
                case "4":
                    new MenuMantenimientoConsultorios(tcl, link);
                    break;
                case "5":
                    new MenuMantenimientoDoctores(tcl, link);
                    break;
                case "6":
                    new MenuMantenimientoEspecialidades(tcl,link);
                    break;
                case "0":
                    System.out.println("Saliendo del menú de mantenimiento.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!opcion.equals("0"));
    }
}
