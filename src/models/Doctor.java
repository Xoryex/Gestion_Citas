package models;

public class Doctor extends Persona {
    private String especialidad;
    private int codConst;
    
    public Doctor() {
        super();
    }
    
    public Doctor(int dni, String nombre, String apellido, int tlf, String especialidad, int codConst) {
        super(dni, nombre, apellido, tlf);
        this.especialidad = especialidad;
        this.codConst = codConst;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public int getCodConst() {
        return codConst;
    }
    
    public void setCodConst(int codConst) {
        this.codConst = codConst;
    }
}
