   package src.entidades;
   import java.util.HashMap;
   import java.util.Scanner;
   
   public class Users{
      private final HashMap<String,String> users=new HashMap<>();
      
      public Users(){
         users.put("admin","admin");
      }
      
      public void ShowDate(String user){
         System.out.println("Usuario: "+user+" :: Contraseña: "+users.get(user));
      }
      
      public String ChangeNameUser(Scanner tcl,String usuarioAct){
         String pass,newname;
         
         System.out.println("Ingresar nuevo nombre");
         newname=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         pass=tcl.nextLine();
         
         if(users.get(usuarioAct).equals(pass)){
            DropUser(usuarioAct);
            CreateUser(newname,pass);
            System.out.println("Nombre [Anterior: "+usuarioAct +" :: Actual: "+newname+"]");
            return newname;
         }else{
            System.out.println("No se puede cambiar el nombre");
            return usuarioAct;
         }
         
         
      }
      
      public void DropUser(String user){
         users.remove(user);
         System.out.println("usuario fue eliminado con exito");
      }
      
      public boolean CreateUser (String user,String pass){
         if(users.containsKey(user)){
            return false;
         }else{
            users.put(user,pass);
            return true;
         }
         }
         
      public boolean ValidationUser (String user,String pass){
         if(ValidationUser(user) && users.get(user).equals(pass)){
            System.out.println("WELCOME "+user);
         return true; 
         }else{
            System.out.println("Credenciales invalidas");
         return false;
         }  
      
      }
       public boolean ValidationUser (String user){
         return users.containsKey(user);  
      }

      public void ChangePassUser (Scanner tcl,String user){
         String newpass,newpass1,actpass;
         
         System.out.println("Ingresar nuevo contraseña");
         newpass=tcl.nextLine();
         System.out.println("Ingresar nueva contraseña para veficar");
         newpass1=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         actpass=tcl.nextLine();

         if(newpass.equals(newpass1)){

            if(users.get(user).equals(actpass)){
               DropUser(user);
               CreateUser(user,newpass);
               System.out.println("Nombre [Anterior: "+actpass +" :: Actual: "+newpass+"]");
            
            }else{
               System.out.println("la contraseña actual no es la correcta");
            }
         } else{
            System.out.println("contraseñas nuevas diferentes");
            
         }
         
         

      }
      
   }