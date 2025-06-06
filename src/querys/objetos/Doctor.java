package src.querys.objetos;

public class Doctor extends Persona{
    private String codEspecialidad;
    private String correo;

    public Doctor(String nombre, String apellido, String codEspecialidad, String dni, String tlf, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codEspecialidad = codEspecialidad;
        this.dni = dni;
        this.tlf = tlf;
        this.correo = correo;
    }

    public String getnombre() { return nombre; }
    public String getapellido() { return apellido; }
    public String getcodEspecialidad() { return codEspecialidad; }
    public String getdni() { return dni; }
    public String gettlf() { return tlf; }
    public String getcorreo() { return correo; }

    public void setnombre(String nombre) { this.nombre = nombre; }
    public void setapellido(String apellido) { this.apellido = apellido; }
    public void setcodEspecialidad(String codEspecialidad) { this.codEspecialidad = codEspecialidad; }
    public void setdni(String dni) { this.dni = dni; }
    public void settlf(String telefono) { this.tlf = telefono; }
    public void setcorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return "Doctor {" +
               "DNI='" + dni + '\'' +
               ", Nombre='" + nombre + '\'' +
               ", Apellido='" + apellido + '\'' +
               ", Especialidad='" + codEspecialidad + '\'' +
               ", Teléfono='" + tlf + '\'' +
               ", Correo='" + correo + '\'' +
               '}';
    }
}