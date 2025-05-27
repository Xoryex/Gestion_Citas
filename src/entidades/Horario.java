package src.entidades;

public class Horario {
    private String dia;
    private String hora;
    private int estado; // cantidad de doctores disponibles ese día
    private int limitePacientes; // límite de pacientes para ese horario

    public Horario(String dia, String hora, int estado, int limitePacientes) {
        this.dia = dia;
        this.hora = hora;
        this.estado = estado;
        this.limitePacientes = limitePacientes;
    }

    // Constructor anterior para compatibilidad
    public Horario(String dia, String hora) {
        this(dia, hora, 0, 0);
    }

    // Getters y setters
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getLimitePacientes() {
        return limitePacientes;
    }

    public void setLimitePacientes(int limitePacientes) {
        this.limitePacientes = limitePacientes;
    }

    @Override
    public String toString() {
        return "Día: " + dia + ", Hora: " + hora +
               ", Doctores disponibles: " + estado +
               ", Límite de pacientes: " + limitePacientes;
    }
}
