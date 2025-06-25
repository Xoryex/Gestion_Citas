package src.models;

public class Horario {
    private String codigo_horario;
    private String dia;
    private String tiempo;
    private int estado; // cantidad de doctores disponibles ese día
    private int limitePacientes; // límite de pacientes para ese horario


    public String getCodigo_horario() {
        return codigo_horario;
    }
    public void setCodigo_horario(String codigo_horario) {
        this.codigo_horario = codigo_horario;
    }
    // Getters y setters
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String hora) {
        this.tiempo = hora;
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

   
}
