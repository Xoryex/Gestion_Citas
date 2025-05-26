package src.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Horarios  {
    // Lista para guardar los valores de los horarios
    private List<Horario> listaHorarios = new ArrayList<>();

    // Método para agregar un horario leyendo por teclado
    public void agregarHorario(Scanner tcl) {
        System.out.print("Ingrese el día: ");
        String dia = tcl.nextLine();
        System.out.print("Ingrese la hora: ");
        String hora = tcl.nextLine();
        listaHorarios.add(new Horario(dia, hora));
        System.out.println("Horario agregado correctamente.");
    }

    // Método para mostrar todos los horarios guardados
    public void imprimirHorarios() {
        System.out.println("=== Lista de Horarios ===");
        if (listaHorarios.isEmpty()) {
            System.out.println("No hay horarios registrados.");
        } else {
            for (Horario horario : listaHorarios) {
                System.out.println(horario);
            }
        }
    }
}