package data;

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

    public String getDNI() { // <--- Añadido
        return dni;
    }

    @Override
    public String toString() {
        return "Doctor: " + nombre + " " + apellido + " | DNI: " + dni + " | Especialidad: " + codEspecialidad;
    }
}
