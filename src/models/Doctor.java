package src.models;

import java.util.ArrayList;

public class Doctor extends Persona{
    private String consultorio;
    private ArrayList<Horario> listahorario = new ArrayList<>();
    private ArrayList<Especialidad> listaespecialidad = new ArrayList<>();

    public String getConsultorio() {
        return consultorio;
    }
    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public void setListaespecialidad(Especialidad especialidad) {
        listaespecialidad.add(especialidad);
    }
    public ArrayList<Horario> getListahorario() {
        return listahorario;
    }
    public ArrayList<Especialidad> getListaespecialidad() {
        return listaespecialidad;
    }
    public void setListahorario(ArrayList<Horario> listahorario) {
        this.listahorario = listahorario;
    }
}