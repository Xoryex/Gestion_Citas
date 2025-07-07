package models;

 public class Recepcionista extends Persona {
      private String contraseña;
      private boolean admin;

        public Recepcionista(int dni, String nombre, String apellido, int tlf, String contraseña, boolean admin) {
            super(dni, nombre, apellido, tlf);
            this.contraseña = contraseña;
            this.admin = admin;
        }

        public Recepcionista() {}

      public String getContraseña() {
          return contraseña;
      }
      public boolean getAdmin() {
          return admin;
      }
          
      public void setContraseña(String contraseña) {
          this.contraseña = contraseña;
      }
      public void setAdmin(boolean admin) {
          this.admin = admin;
      }
      
      }


