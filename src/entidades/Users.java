   package src.entidades;
   import java.util.HashMap;
   import java.util.Scanner;
   
   public class Users{
      private static HashMap<String,User> listausuarios=new HashMap<>();
      
      public Users(){
         listausuarios.put("admin",new User("EQUIPO_GESTION_CITAS", "admin", true));
      }
      
      public String CambiarUsuario(Scanner tcl,String usuarioAct){
         
         System.out.println("Ingresar nuevo nombre");
         String nuevousuario=tcl.nextLine();
         System.out.println("Ingresar contraseña actual");
         String contraseña=tcl.nextLine();
         
         if(listausuarios.get(usuarioAct).GetContraseña().equals(contraseña)){
            String nombre = listausuarios.get(usuarioAct).GetNombre();
            boolean admin = listausuarios.get(usuarioAct).GetAdmin();

            listausuarios.remove(usuarioAct);

            Crear(nuevousuario,nombre, contraseña,admin);
            System.out.println("Nombre [Anterior: "+usuarioAct +" :: Actual: "+nuevousuario+"]");
            return nuevousuario;
         }else{
            System.out.println("No se puede cambiar el nombre");
            return usuarioAct;
         }
         
         
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
               listausuarios.get(usuario).SetContraseña(nuevacontraseña);
               System.out.println("Nombre [Anterior: "+contraseñaactual +" :: Actual: "+nuevacontraseña+"]");
            
            }else{
               System.out.println("la contraseña actual no es la correcta");
            }
         } else{
            System.out.println("contraseñas nuevas diferentes");
            
         }
      }

      public void CambiarNombre(Scanner tcl, String usuario){
         String nuevonombre,contraseñaactual;
         

         System.out.println("Ingresar nuevo Nombre");
         nuevonombre=tcl.nextLine();

         System.out.println("Ingresar contraseña actual");
         contraseñaactual=tcl.nextLine();  

         if(listausuarios.get(usuario).GetContraseña().equals(contraseñaactual)){
            listausuarios.get(usuario).SetNombre(nuevonombre);
            System.out.println("nombre del usuario cambiado");
         }else{
            System.out.println("contraseña incorrecta");
         }
      }


      public void Eliminar(String usuario){

         if(listausuarios.get(usuario).GetAdmin()){
            if (Contar(true)==1) {
               System.out.println("no se puede realizar la accion");
            }else if (Contar(false)>0){
                  listausuarios.remove(usuario);
                  System.out.println("usuario fue eliminado con exito");
            }
         }else if(!listausuarios.get(usuario).GetAdmin()){
            listausuarios.remove(usuario);
            System.out.println("usuario fue eliminado con exito");
         }

            }
         
      public boolean Crear (String usuario,String nombre, String contraseña,boolean admin ){
         if(listausuarios.containsKey(usuario)){
            return false;
         }else{
            listausuarios.put(usuario,new User(nombre,contraseña,admin));
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

      public int Contar(boolean quien){
         int n=0;
         for(User user : listausuarios.values()){
            
            if(user.GetAdmin()&&quien){
               n ++;
            }else if(!user.GetAdmin() &&!quien){
               n ++;
            }
            
         }
         return n ;
      }
      
      public void MostrarDatos(String user){
         System.out.println("Usuario: "+user+listausuarios.get(user).GetDate());
      }
      
      public void Bienvenida(String user){
         System.out.println("Welcome: "+listausuarios.get(user).GetNombre());
      }

   }