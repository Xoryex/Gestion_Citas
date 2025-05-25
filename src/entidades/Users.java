   package src.entidades;
   import java.util.HashMap;
   import java.util.Scanner;
   
   public class Users{
      private static HashMap<String,User> listausuarios=new HashMap<>();
      
      public Users(){
         listausuarios.put("admin",new User("EQUIPO_GESTION_CITAS", "admin", true));
      }
      
   

      public String CambiarNombre(Scanner tcl,String usuarioAct){
         String contraseña,nuevonombre;
         
         System.out.println("Ingresar nuevo nombre");
         nuevonombre=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         contraseña=tcl.nextLine();
         
         if(listausuarios.get(usuarioAct).equals(contraseña)){
            Eliminar(usuarioAct);
            Crear(nuevonombre, contraseña);
            System.out.println("Nombre [Anterior: "+usuarioAct +" :: Actual: "+nuevonombre+"]");
            return nuevonombre;
         }else{
            System.out.println("No se puede cambiar el nombre");
            return usuarioAct;
         }
         
         
      }
      
      public void Eliminar(String usuario){
         listausuarios.remove(usuario);
         System.out.println("usuario fue eliminado con exito");
      }
      
      public boolean Crear (String usuario,String contraseña){
         if(listausuarios.containsKey(usuario)){
            return false;
         }else{
            listausuarios.put(usuario,contraseña);
            return true;
         }
         }
         
      public String ValidarCredenciales (String user,String pass){
         if(ValidarCredenciales(user) && listausuarios.get(user).GetContraseña().equals(pass)&&listausuarios.get(user).GetAdmin()){
            return "1"; 
         }else if(ValidarCredenciales(user) && listausuarios.get(user).GetContraseña().equals(pass)&&!listausuarios.get(user).GetAdmin()){
            return "2";
         }else{
            return "0";
         }  
      
      }
      public boolean ValidarCredenciales (String usuario){
         return listausuarios.containsKey(usuario);  
      }

      public void CambiarContraseña (Scanner tcl,String usuario){
         String nuevacontraseña,nuevacontraseña1,contraseñaactual;
         
         System.out.println("Ingresar nuevo contraseña");
         nuevacontraseña=tcl.nextLine();
         System.out.println("Ingresar nueva contraseña para veficar");
         nuevacontraseña1=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         contraseñaactual=tcl.nextLine();

         if(nuevacontraseña.equals(nuevacontraseña1)){

            if(listausuarios.get(usuario).GetContraseña().equals(contraseñaactual)){
               Eliminar(usuario);(usuario);
               Crear(usuario,nuevacontraseña);
               System.out.println("Nombre [Anterior: "+contraseñaactual +" :: Actual: "+nuevacontraseña+"]");
            
            }else{
               System.out.println("la contraseña actual no es la correcta");
            }
         } else{
            System.out.println("contraseñas nuevas diferentes");
            
         }
         
         

      }
      
         public void MostrarDatos(String user){
         System.out.println("Usuario: "+user+listausuarios.get(user).GetDate());
      }
      
      public void Bienvenida(String user){
         System.out.println("Welcome: "+listausuarios.get(user).GetNombre());
      }

   }