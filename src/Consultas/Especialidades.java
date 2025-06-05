package src.Consultas;

import java.util.Scanner;
import java.util.TreeMap;
import src.utils.Link;

public class Especialidades {
    public static TreeMap<String, String> especialidades = new TreeMap<>();
//
    public Especialidades() {
        agregarConClaveGenerada("Cardiología");
        agregarConClaveGenerada("Cirugía General");
        agregarConClaveGenerada("Dermatología");
        agregarConClaveGenerada("Ginecología y Obstetricia");
        agregarConClaveGenerada("Medicina General");
        agregarConClaveGenerada("Medicina Interna");
        agregarConClaveGenerada("Neurología");
        agregarConClaveGenerada("Oftalmología");
        agregarConClaveGenerada("Otorrinolaringología");
        agregarConClaveGenerada("Pediatría");
        agregarConClaveGenerada("Traumatología y Ortopedia");
    }

    // 👇 Método para generar clave y agregar al mapa
    private void agregarConClaveGenerada(String nombre) {
        String baseClave = nombre.replaceAll("[^A-Za-z]", "").toUpperCase();
        baseClave = baseClave.length() >= 3 ? baseClave.substring(0, 3) : baseClave;

        String clave = baseClave;
        int contador = 1;
        while (especialidades.containsKey(clave)) {
            clave = baseClave + contador;
            contador++;
        }

        especialidades.put(clave, nombre);
    }

    // 👇 Nuevo método simplificado de impresión
    public void imprimirEspecialidades() {
        System.out.println("=== Lista de Especialidades ===");
        if (especialidades.isEmpty()) {
            System.out.println("No hay especialidades registradas.");
        } else {
            for (var entry : especialidades.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }
        }
    }

    public void AgregarEspecialidad(Scanner tcl) {
        System.out.print("Ingrese el nombre de la nueva especialidad: ");
        String nombre = tcl.nextLine();
        agregarConClaveGenerada(nombre);
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

    // Eliminar la antigua
    especialidades.remove(codigo);

    // Agregar con nueva clave generada a partir del nuevo nombre
    agregarConClaveGenerada(nuevoNombre);

    System.out.println("Especialidad editada correctamente.");
}


    public void eliminarEspecialidad(Scanner tcl, Link link) {
        System.out.print("Ingrese el código de la especialidad a eliminar: ");
        String codigo = tcl.nextLine().trim().toUpperCase();
        if (!especialidades.containsKey(codigo)) {
            System.out.println("El código no existe.");
           
        }
        // Verificar si algún doctor usa esta especialidad
        boolean enUso = link.doctores.getListaDoctores().values().stream()
            .anyMatch(doc -> doc.getcodEspecialidad().equals(codigo));
        if (enUso) {
            System.out.println("No se puede eliminar la especialidad porque está asignada a uno o más doctores.");
            
        }
        especialidades.remove(codigo);
        System.out.println("Especialidad eliminada correctamente.");
    }
}
