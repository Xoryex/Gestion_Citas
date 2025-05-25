package src.entidades;

import java.util.TreeMap;
import java.util.Scanner;


public class Pacientes {
    // Colección: clave = DNI, valor = nombre del paciente
    private static TreeMap<String, String> listapacientes = new TreeMap<>();

    // Agregar paciente
    public void agregarPaciente(Scanner tcl) {
        System.out.print("Ingrese el DNI del paciente: ");
        String dni = tcl.nextLine();
        if (listapacientes.containsKey(dni)) {
            System.out.println("El paciente con ese DNI ya existe.");
            return;
        }
        System.out.print("Ingrese el nombre del paciente: ");
        String nombre = tcl.nextLine();
        listapacientes.put(dni, nombre);
        System.out.println("Paciente agregado correctamente.");
    }

    // Editar paciente
    public void editarPaciente(Scanner tcl) {
        System.out.print("Ingrese el DNI del paciente a editar: ");
        String dni = tcl.nextLine();
        if (!listapacientes.containsKey(dni)) {
            System.out.println("No existe un paciente con ese DNI.");
            return;
        }
        System.out.print("Ingrese el nuevo nombre del paciente: ");
        String nuevoNombre = tcl.nextLine();
        listapacientes.put(dni, nuevoNombre);
        System.out.println("Paciente editado correctamente.");
    }

    // Borrar paciente
    public void borrarPaciente(Scanner tcl) {
        System.out.print("Ingrese el DNI del paciente a borrar: ");
        String dni = tcl.nextLine();
        if (!listapacientes.containsKey(dni)) {
            System.out.println("No existe un paciente con ese DNI.");
            return;
        }
        listapacientes.remove(dni);
        System.out.println("Paciente eliminado correctamente.");
    }

    // Imprimir todos los pacientes
    public void imprimirPacientes() {
        System.out.println("=== Lista de Pacientes ===");
        if (listapacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            listapacientes.forEach((dni, nombre) -> 
                System.out.println("DNI: " + dni + " | Nombre: " + nombre)
            );
        }
    }
}
