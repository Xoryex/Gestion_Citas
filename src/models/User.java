package src.models;

 public class User extends Persona {
      private String contraseña;
      private boolean admin;

        public User(String dni, String nombre, String apellido, String tlf, String contraseña, boolean admin) {
            super(dni, nombre, apellido, tlf);
            this.contraseña = contraseña;
            this.admin = admin;
        }

        public User() {}

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


