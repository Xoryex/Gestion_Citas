package src.models;

public class Persona {
      private String dni,nombre, apellido, tlf,correo;

        public Persona(String dni, String nombre, String apellido, String tlf) {
            this.dni = dni;
            this.nombre = nombre;
            this.apellido = apellido;
            this.tlf = tlf;
        }

        public Persona() {}

      public String getApellido() {
          return apellido;
      }
      public String getDni() {
          return dni;
      }
      public String getNombre() {
          return nombre;
      }
      public String getTlf() {
          return tlf;
      }
      public void setApellido(String apellido) {
          this.apellido = apellido;
      }
      public void setDni(String dni) {
          this.dni = dni;
      }
      public void setNombre(String nombre) {
          this.nombre = nombre;
      }
      public void setTlf(String tlf) {
          this.tlf = tlf;
      }

      public String getCorreo() {
          return correo;
      }
      public void setCorreo(String correo) {
          this.correo = correo;
      }
}
