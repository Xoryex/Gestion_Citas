package src.entidades;

import java.util.ArrayList;
import java.util.List;

public class Horarios  {
    // Lista para guardar los valores de los horarios (puedes cambiar el tipo Object por otro más específico después)
    private List<Horario> listaHorarios = new ArrayList<>();

    // Método para agregar un valor a la lista de horarios
    public void agregarHorario(String dia, String hora) {   
        listaHorarios.add(new Horario(dia, hora));
        // Aquí podrías agregar validaciones adicionales si es necesario
        System.out.println("Horario agregado correctamente.");
    }

    // (Opcional) Método para mostrar todos los horarios guardados
    public void imprimirHorarios() {
        System.out.println("=== Lista de Horarios ===");
        if (listaHorarios.isEmpty()) {
            System.out.println("No hay horarios registrados.");
        } else {
            for (Object horario : listaHorarios) {
                System.out.println(horario);
            }
        }
    }
}