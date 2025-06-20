package src.objetos;

 public class User extends Persona {
      private String contraseña;
      private boolean admin;
     
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


