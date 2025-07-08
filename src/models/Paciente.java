package models;

public class Paciente extends Persona {
    private String seguroMedico;
    
    public Paciente() {
        super();
    }
    
    public Paciente(int dni, String nombre, String apellido, int tlf, String seguroMedico) {
        super(dni, nombre, apellido, tlf);
        this.seguroMedico = seguroMedico;
    }
    
    public String getSeguroMedico() {
        return seguroMedico;
    }
    
    public void setSeguroMedico(String seguroMedico) {
        this.seguroMedico = seguroMedico;
    }
}
