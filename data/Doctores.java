package data;

import java.util.TreeMap;

public class Doctores {

    private final TreeMap<String, Doctor> doctores = new TreeMap<>();

    public Doctores() {

    }

    // Mostrar un doctor específico por su DNI
    public void ShowDoctor(String DNI) {
        Doctor doctor = doctores.get(DNI);
        if (doctor != null) {
            System.out.println(doctor);
        } else {
            System.out.println("No se encontró un doctor con ese DNI.");
        }
    }

    // Agregar un doctor nuevo al mapa
    public void AddDoctor(Doctor doctor) {
        if (doctores.containsKey(doctor.getDNI())) {
            System.out.println("Ya existe un doctor con ese DNI.");
        } else {
            doctores.put(doctor.getDNI(), doctor);
            System.out.println("Doctor agregado correctamente.");
        }
    }

    // Eliminar un doctor usando su DNI
    public void DropDoctor(String dni) {
        if (doctores.remove(dni) != null) {
            System.out.println("Doctor eliminado con éxito.");
        } else {
            System.out.println("No se encontró un doctor con ese DNI.");
        }
    }

    // Mostrar todos los doctores ordenados alfabéticamente por nombre
    public void ShowAllDoctors() {
        if (doctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
            return;
        }

        doctores.values().stream()
            .sorted((d1, d2) -> d1.getNombre().compareToIgnoreCase(d2.getNombre()))
            .forEach(System.out::println);
    }
}
