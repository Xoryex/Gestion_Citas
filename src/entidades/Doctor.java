package src.entidades;

public class Doctor {
    private String nombre, apellido, codEspecialidad, dni;

    public Doctor(String nombre, String apellido, String codEspecialidad, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codEspecialidad = codEspecialidad;
        this.dni = dni;
    }
    public String getNombre() {
         return nombre;
    }
       
    public String getApellido() {
        return apellido;
    }

    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public String getDni() {
        return dni;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
}
