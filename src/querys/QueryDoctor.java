package src.querys;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import src.models.Doctor;
import src.models.Especialidad;
import src.models.Horario;

public class QueryDoctor implements Query<Doctor> {

    private final TreeMap<String, Doctor> listaDoctores = new TreeMap<>();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void Crear(Object datos_persona) {
        if (!(datos_persona instanceof Doctor doctor)) return;

        if (listaDoctores.containsKey(doctor.getCedula())) {
            System.out.println("Ya existe un doctor con esa cédula.");
            return;
        }

        listaDoctores.put(doctor.getCedula(), doctor);
        System.out.println("Doctor registrado correctamente.");
    }

    @Override
    public void Modificar(Object datos_modificados) {
        if (!(datos_modificados instanceof Doctor nuevosDatos)) return;

        Doctor actual = listaDoctores.get(nuevosDatos.getCedula());
        if (actual == null) {
            System.out.println("No se encontró un doctor con esa cédula.");
            return;
        }

        actual.setNombre(nuevosDatos.getNombre());
        actual.setEdad(nuevosDatos.getEdad());
        actual.setGenero(nuevosDatos.getGenero());
        actual.setConsultorio(nuevosDatos.getConsultorio());
        actual.setListahorario(nuevosDatos.getListahorario());
        // Reemplazamos especialidades por nuevas si se asignan
        actual.getListaespecialidad().clear();
        actual.getListaespecialidad().addAll(nuevosDatos.getListaespecialidad());

        System.out.println("Doctor modificado correctamente.");
    }

    @Override
    public ArrayList<Doctor> Mostrar() {
        return new ArrayList<>(listaDoctores.values());
    }

    @Override
    public void Eliminar(String cedula) {
        Doctor eliminado = listaDoctores.remove(cedula);
        if (eliminado != null) {
            System.out.println("Doctor eliminado: " + eliminado.getNombre());
        } else {
            System.out.println("No se encontró el doctor con esa cédula.");
        }
    }

    public void Listar() {
        if (listaDoctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
            return;
        }

        System.out.println("\n--- LISTA DE DOCTORES ---");
        for (Doctor d : listaDoctores.values()) {
            System.out.println("Nombre: " + d.getNombre()
                             + " | Cédula: " + d.getCedula()
                             + " | Consultorio: " + d.getConsultorio()
                             + " | Especialidades: " + d.getListaespecialidad().size()
                             + " | Horarios: " + d.getListahorario().size());
        }
    }

    public Doctor buscarPorCedula(String cedula) {
        return listaDoctores.get(cedula);
    }
}
