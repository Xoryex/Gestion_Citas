package src.entidades;

public class Doctor {
    private String nombre;
    private String apellido;
    private String codEspecialidad;
    private String dni;
    private String telefono;
    private String correo;

    public Doctor(String nombre, String apellido, String codEspecialidad, String dni, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codEspecialidad = codEspecialidad;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters
    public String getnombre() { return nombre; }
    public String getapellido() { return apellido; }
    public String getcodEspecialidad() { return codEspecialidad; }
    public String getdni() { return dni; }
    public String gettelefono() { return telefono; }
    public String getcorreo() { return correo; }

    // Setters
    public void setnombre(String nombre) { this.nombre = nombre; }
    public void setapellido(String apellido) { this.apellido = apellido; }
    public void setcodEspecialidad(String codEspecialidad) { this.codEspecialidad = codEspecialidad; }
    public void setdni(String dni) { this.dni = dni; } // Importante para editarDNI
    public void settlf(String telefono) { this.telefono = telefono; }
    public void setcorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return "Doctor {" +
               "DNI='" + dni + '\'' +
               ", Nombre='" + nombre + '\'' +
               ", Apellido='" + apellido + '\'' +
               ", Especialidad='" + codEspecialidad + '\'' +
               ", Teléfono='" + telefono + '\'' +
               ", Correo='" + correo + '\'' +
               '}';
    }
}