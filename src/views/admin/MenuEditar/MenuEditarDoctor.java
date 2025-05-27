package src.views.admin.MenuEditar;

import java.util.Scanner;

import src.utils.Link;

public class MenuEditarDoctor {
    public MenuEditarDoctor(Scanner tcl, Link link) {
        String opcion;  
        do {
        System.out.println("=== Menú de Edición de Doctor ===");
        System.out.println("1. Editar nombre");
        System.out.println("2. Editar apellido");
        System.out.println("3. Editar número de telefono");
        System.out.println("4. Editar correo electrónico");
        System.out.println("5. Editar especialidad");
        System.out.println("6. Editar DNI");
        System.out.println("0. Salir");
        opcion = tcl.nextLine();

        switch (opcion) {
            case "1":
                link.doctores.editarnombre(tcl);
                break;
            case "2":
                link.doctores.editarapellido(tcl);
                break;
            case "3":
                link.doctores.editartlf(tcl);
                break;
            case "4":
                link.doctores.editarCorreo(tcl);
                break;
            case "5":
                link.doctores.editarEspecialidad(tcl);
                break;
            case "6":
                link.doctores.editarDNI(tcl);
                break;
            case "0":
                System.out.println("Saliendo del menú de edición de doctor.");
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
        } while (!opcion.equals("0"));
        
    }
}

