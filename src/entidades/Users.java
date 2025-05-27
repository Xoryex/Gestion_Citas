   package src.entidades;
   import java.util.HashMap;
   import java.util.Scanner;

import src.utils.Link;
import src.views.MenuPerfil;
   
   public class Users{
      private static HashMap<String,User> listausuarios=new HashMap<>();

      public Users(){
         listausuarios.put("admin",new User("00000000","EQUIPO","GESTION_CITAS","", "admin", true));
      }

      public void CambiarDni(Scanner tcl,String usuario){
         String nuevodni,contraseña;
         System.out.println("========CAMBIAR DNI========");
         do{
         System.out.print("Ingresar nuevo dni: ");
         nuevodni=tcl.nextLine();
         nuevodni=nuevodni.replaceAll("[^0-9.]", "");
         }while(nuevodni.isEmpty()||!(nuevodni.length()==8));

         do{
         System.out.print("Ingresar contraseña actual: ");
         contraseña=tcl.nextLine();
         }while(contraseña.isEmpty());

         if(listausuarios.get(usuario).GetContraseña().equals(contraseña)){
            listausuarios.get(usuario).SetDni(nuevodni);
         }else{
            System.out.println("No se puede cambiar el dni");
         }
         
      
      }
      
      public void CambiarTelefono(Scanner tcl, String usuario){
         String nuevotlf,contraseña;
         System.out.println("========CAMBIAR TELEFONO========");
         do{
         System.out.print("Ingresar nuevo dni: ");
         nuevotlf=tcl.nextLine();
         nuevotlf=nuevotlf.replaceAll("[^0-9.]", "");
         }while(nuevotlf.isEmpty()||!(nuevotlf.length()==8));

         do{
         System.out.print("Ingresar contraseña actual: ");
         contraseña=tcl.nextLine();
         }while(contraseña.isEmpty());

         if(listausuarios.get(usuario).GetContraseña().equals(contraseña)){
            listausuarios.get(usuario).SetTlf(nuevotlf);
         }else{
            System.out.println("No se puede cambiar el telefono");
         }
      }

      public String CambiarUsuario(Scanner tcl,String usuarioAct){
         String nuevousuario,contraseñaAct;
         System.out.println("========CAMBIAR USUARIO========");
         do{
         System.out.print("Ingresar nuevo usuario:");
         nuevousuario=tcl.nextLine();
         }while(nuevousuario.isEmpty());
         do{
         System.out.print("Ingresar contraseña actual: ");
         contraseñaAct=tcl.nextLine();
         }while(contraseñaAct.isEmpty()||!(contraseñaAct.length()>=8));
         
         if(listausuarios.containsKey(nuevousuario)){

            System.out.println("El nuevo ususario ya existe");
            return usuarioAct;

         }else if(listausuarios.get(usuarioAct).GetContraseña().equals(contraseñaAct)){

            String dni=listausuarios.get(usuarioAct).GetDni();
            String nombre = listausuarios.get(usuarioAct).GetNombre();
            String apellido=listausuarios.get(usuarioAct).GetApellido();
            String tlf=listausuarios.get(usuarioAct).GetTlf();
            String contraseña =listausuarios.get(usuarioAct).GetContraseña();
            boolean admin = listausuarios.get(usuarioAct).GetAdmin();

            listausuarios.remove(usuarioAct);
            listausuarios.put(nuevousuario, new User(dni, nombre, apellido, tlf, contraseña, admin));
            System.out.println("Nombre [Anterior: "+usuarioAct +" :: Actual: "+nuevousuario+"]");
            return nuevousuario;
         }else{
            System.out.println("Contraseña invalida");
            return usuarioAct;
         }
         
         
      }
      
      public void CambiarContraseña (Scanner tcl,String usuario){
         String nuevacontraseña,nuevacontraseña1,contraseñaactual;
         do {
            System.out.println("Ingresar nuevo contraseña");
            nuevacontraseña=tcl.nextLine();
         } while (nuevacontraseña.isEmpty()||nuevacontraseña.length()<8);
         do {
            
            System.out.println("Ingresar nueva contraseña para veficar");
            nuevacontraseña1=tcl.nextLine();
         } while (nuevacontraseña1.isEmpty()||nuevacontraseña1.length()<8);
         do {
            System.out.println("Ingresar contraseña actual");
            contraseñaactual=tcl.nextLine();
         } while (contraseñaactual.isEmpty()||contraseñaactual.length()<8);

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
         do {
            System.out.print("Ingresar nuevo Nombre: ");
            nuevonombre=tcl.nextLine();
         } while (nuevonombre.isEmpty());
         do {
            System.out.print("Ingresar contraseña actual: ");
            contraseñaactual=tcl.nextLine();  
         } while (contraseñaactual.isEmpty()||contraseñaactual.length()<8);

         if(listausuarios.get(usuario).GetContraseña().equals(contraseñaactual)){
            listausuarios.get(usuario).SetNombre(nuevonombre);
            System.out.println("nombre del usuario cambiado");
         }else{
            System.out.println("contraseña incorrecta");
         }
      }
      
      public void CambiarApellido(Scanner tcl, String usuario){
         String nuevoapellido,contraseña;
         System.out.print("===========CAMBIAR APELLIDO===========");
         do{
         System.out.print("Ingresar nuevo Apellido: ");
         nuevoapellido=tcl.nextLine(); 
         } while (nuevoapellido.equals(""));

         do {
         System.out.print("Ingrese contraseña: ");
         contraseña=tcl.nextLine(); 
         } while (contraseña.equals("")||contraseña.length()<8); 

         if(listausuarios.get(usuario).GetContraseña().equals(contraseña)){
            listausuarios.get(usuario).SetApellido(nuevoapellido);
            System.out.println("Apellido del usuario cambiado");
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
         
      public void Crear (Scanner tcl,boolean admin ){
         String usuario, dni,nombre,apellido,tlf, contraseña;
         
         do{
         System.out.print("        Dni: ");
         dni=tcl.nextLine();
         dni=dni.replaceAll("[^0-9.]", "");
         }while(dni.isEmpty()||!(dni.length()==8));

         do{
         System.out.print("     Nombre: ");
         nombre=tcl.nextLine();
         }while(nombre.isEmpty());

         do{
         System.out.print("   Apellido: ");
         apellido=tcl.nextLine();
         }while(apellido.isEmpty());

         do{
         System.out.print("   Telefono: ");
         tlf=tcl.nextLine();
         tlf=tlf.replaceAll("[^0-9.]", "");
         }while(tlf.isEmpty()||!(tlf.length()==9));

         do {
         System.out.print("   Usuario: ");
         usuario=tcl.nextLine(); 
         } while (usuario.isEmpty());

         do {
         System.out.print("   Password: ");
         contraseña=tcl.nextLine(); 
         } while (contraseña.isEmpty()||contraseña.length()<8);

         
         if(listausuarios.containsKey(usuario)){
            System.out.println("Usuario ocupadado");
         }else{
            listausuarios.put(usuario,new User(dni,nombre,apellido,tlf,contraseña,admin));
            System.out.println("Usuario registrado con exito");
         }
         }
         
      public String ValidarCredenciales (String usuario,String contraseña){
         if(listausuarios.containsKey(usuario) && listausuarios.get(usuario).GetContraseña().equals(contraseña)&&listausuarios.get(usuario).GetAdmin()){
            return "1"; 
         }else if(listausuarios.containsKey(usuario) && listausuarios.get(usuario).GetContraseña().equals(contraseña)&&!listausuarios.get(usuario).GetAdmin()){
            return "2";
         }else{
            return "0";
         }  
      
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
      
      public void MostrarDatos(String usuario){
         System.out.println("\n==================DATOS "+usuario+"====================="
                           +"\n[Dni: "+listausuarios.get(usuario).GetDni()
                           +"\n|Nombre: "+listausuarios.get(usuario).GetNombre()
                           +"\n|Apellido: "+listausuarios.get(usuario).GetApellido()
                           +"\n|Telefono: "+listausuarios.get(usuario).GetTlf()
                           +"\n[Admin: "+listausuarios.get(usuario).GetAdmin());
      }

      public void MostrarDatosCompletos (String usuario){
         System.out.println("\n[Dni: "+listausuarios.get(usuario).GetDni()
                           +"|Nombre: "+listausuarios.get(usuario).GetNombre()
                           +"|Apellido: "+listausuarios.get(usuario).GetApellido()
                           +"|Telefono: "+listausuarios.get(usuario).GetTlf()
                           +"|Usuario: "+usuario
                           +"|Contraseña: "+listausuarios.get(usuario).GetContraseña()
                           +"|Admin: "+listausuarios.get(usuario).GetAdmin()+"]");
      }
      
      public void Bienvenida(String usuario){
         System.out.println("Welcome: "+listausuarios.get(usuario).GetNombre()+" "+listausuarios.get(usuario).GetApellido());
      }

      public void MostrarLista(Boolean quien){
         boolean admin;
         String listaDe = (quien)?"ADMINISTRADORES" :"RECEPCIONISTAS";
         System.out.println("======================== "+listaDe+" ========================");

         for(String usuario : listausuarios.keySet()){
            admin=listausuarios.get(usuario).GetAdmin();
            
            if(quien&&admin){
               
                        MostrarDatosCompletos(usuario);
            }else if(!quien&&!admin){
                        MostrarDatosCompletos(usuario);
            }
         }
         System.out.println("================================================================");
      }
   
      public void AgregarRecepcionista(Scanner tcl){
         System.out.println("======== AGREGAR RECEPCIONISTA ========");
         Crear(tcl, false);
         System.out.println("=======================================");
      }
      
      public void EditarRecepcionista(Scanner tcl,Link link){
         
         String usuariorecepcionista;
         MostrarLista(false);

          do {
            System.out.print("Ingresar usuario del recepcionista: ");
            usuariorecepcionista=tcl.nextLine();
         } while (usuariorecepcionista.isEmpty()||!listausuarios.containsKey(usuariorecepcionista));
         
         new MenuPerfil(tcl, link, usuariorecepcionista);

      }
   
      public void EliminarRecepcionista(Scanner tcl){
         String usuariorecepcionista;
         MostrarLista(false);

          do {
            System.out.print("Ingresar usuario del recepcionista: ");
            usuariorecepcionista=tcl.nextLine();
         } while (usuariorecepcionista.isEmpty()||!listausuarios.containsKey(usuariorecepcionista));

         Eliminar(usuariorecepcionista);

      }
   }