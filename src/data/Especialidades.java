package src.data;

import java.util.Scanner;
import java.util.TreeMap;

public class Especialidades {
    public static TreeMap<String, String> especialidades = new TreeMap<>();

    public Especialidades() {
        especialidades.put("1", "Cardiología");
        especialidades.put("2", "Cirugía General");
        especialidades.put("3", "Dermatología");
        especialidades.put("4", "Endocrinología");
        especialidades.put("5", "Gastroenterología");
        especialidades.put("6", "Ginecología y Obstetricia");
        especialidades.put("7", "Medicina General");
        especialidades.put("8", "Medicina Interna");
        especialidades.put("9", "Neurología");
        especialidades.put("10", "Odontología General y Especialidades");
        especialidades.put("11", "Oftalmología");
        especialidades.put("12", "Otorrinolaringología");
        especialidades.put("13", "Pediatría");
        especialidades.put("14", "Psicología o Psiquiatría");
        especialidades.put("15", "Traumatología y Ortopedia");
        especialidades.put("16", "Urología");
    }

    public void imprimirEspecialidades() {
        for (String key : especialidades.keySet()) {
            System.out.println(key + ". " + especialidades.get(key));
        }
    }

    public void AgregarEspecialidad(Scanner tcl) {
        System.out.print("Ingrese el código de la nueva especialidad: ");
        String codigo = tcl.nextLine();
        if (especialidades.containsKey(codigo)) {
            System.out.println("El código ya existe. Intente con otro.");
            return;
        }
        System.out.print("Ingrese el nombre de la nueva especialidad: ");
        String nombre = tcl.nextLine();
        especialidades.put(codigo, nombre);
        System.out.println("Especialidad agregada correctamente.");
    }

    public void editarEspecialidad(Scanner tcl) {
        System.out.print("Ingrese el código de la especialidad a editar: ");
        String codigo = tcl.nextLine();
        if (!especialidades.containsKey(codigo)) {
            System.out.println("El código no existe.");
            return;
        }
        System.out.print("Ingrese el nuevo nombre de la especialidad: ");
        String nuevoNombre = tcl.nextLine();
        especialidades.put(codigo, nuevoNombre);
        System.out.println("Especialidad editada correctamente.");
    }

    public void eliminarEspecialidad(Scanner tcl) {
        System.out.print("Ingrese el código de la especialidad a eliminar: ");
        String codigo = tcl.nextLine();
        if (!especialidades.containsKey(codigo)) {
            System.out.println("El código no existe.");
            return;
        }
        especialidades.remove(codigo);
        System.out.println("Especialidad eliminada correctamente.");
    }
}
