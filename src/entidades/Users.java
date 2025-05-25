   package src.entidades;
   import java.util.HashMap;
   import java.util.Scanner;
   
   public class Users{
      private static HashMap<String,String> listausuarios=new HashMap<>();
      
      public Users(){
         listausuarios.put("admin","admin");
      }
      
      public void ShowDate(String user){
         System.out.println("Usuario: "+user+" :: Contraseña: "+listausuarios.get(user));
      }
      
      public String ChangeNameUser(Scanner tcl,String usuarioAct){
         String contraseña,nuevonombre;
         
         System.out.println("Ingresar nuevo nombre");
         nuevonombre=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         contraseña=tcl.nextLine();
         
         if(listausuarios.get(usuarioAct).equals(contraseña)){
            DropUser(usuarioAct);
            CreateUser(nuevonombre,contraseña);
            System.out.println("Nombre [Anterior: "+usuarioAct +" :: Actual: "+nuevonombre+"]");
            return nuevonombre;
         }else{
            System.out.println("No se puede cambiar el nombre");
            return usuarioAct;
         }
         
         
      }
      
      public void DropUser(String user){
         listausuarios.remove(user);
         System.out.println("usuario fue eliminado con exito");
      }
      
      public boolean CreateUser (String user,String pass){
         if(listausuarios.containsKey(user)){
            return false;
         }else{
            listausuarios.put(user,pass);
            return true;
         }
         }
         
      public boolean ValidationUser (String user,String pass){
         if(ValidationUser(user) && listausuarios.get(user).equals(pass)){
            System.out.println("WELCOME "+user);
         return true; 
         }else{
            System.out.println("Credenciales invalidas");
         return false;
         }  
      
      }
       public boolean ValidationUser (String user){
         return listausuarios.containsKey(user);  
      }

      public void ChangePassUser (Scanner tcl,String user){
         String nuevacontraseña,nuevacontraseña1,contraseñaactual;
         
         System.out.println("Ingresar nuevo contraseña");
         nuevacontraseña=tcl.nextLine();
         System.out.println("Ingresar nueva contraseña para veficar");
         nuevacontraseña1=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         contraseñaactual=tcl.nextLine();

         if(nuevacontraseña.equals(nuevacontraseña1)){

            if(listausuarios.get(user).equals(contraseñaactual)){
               DropUser(user);
               CreateUser(user,nuevacontraseña);
               System.out.println("Nombre [Anterior: "+contraseñaactual +" :: Actual: "+nuevacontraseña+"]");
            
            }else{
               System.out.println("la contraseña actual no es la correcta");
            }
         } else{
            System.out.println("contraseñas nuevas diferentes");
            
         }
         
         

      }
      
   }