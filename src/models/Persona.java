package src.models;

public class Persona {
      private String nombre, apellido,correo;
      private int dni,tlf;

        public Persona(int dni, String nombre, String apellido, int  tlf) {
            this.dni = dni;
            this.nombre = nombre;
            this.apellido = apellido;
            this.tlf = tlf;
        }

        public Persona() {}

      public String getApellido() {
          return apellido;
      }
      public int getDni() {
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
      public void setDni(int dni) {
          this.dni = dni;
      }
      public void setNombre(String nombre) {
          this.nombre = nombre;
      }
      public void setTlf(int tlf) {
          this.tlf = tlf;
      }

      public String getCorreo() {
          return correo;
      }
      public void setCorreo(String correo) {
          this.correo = correo;
      }
}
