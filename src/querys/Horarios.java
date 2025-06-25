package src.querys;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.models.Horario;

public class Horarios  {
    // Lista para guardar los valores de los horarios
    private List<Horario> listaHorarios = new ArrayList<>();

    // Método para agregar un horario leyendo por teclado
    public void agregarHorario(Scanner tcl) {
        System.out.print("Ingrese el día: ");
        String dia = tcl.nextLine();
        System.out.print("Ingrese la hora: ");
        String hora = tcl.nextLine();
        System.out.print("Ingrese la cantidad de doctores disponibles: ");
        int estado = Integer.parseInt(tcl.nextLine());
        System.out.print("Ingrese el límite de pacientes: ");
        int limitePacientes = Integer.parseInt(tcl.nextLine());
        listaHorarios.add(new Horario(dia, hora, estado, limitePacientes));
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