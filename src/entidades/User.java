package src.entidades;



public class User {

    private  String dni,nombre, apellido, tlf,contraseña;
    private  boolean admin;

    public User(String dni,String nombre,String apellido,String tlf,String contraseña,boolean admin){
        this.dni=dni;
        this.nombre=nombre;
        this.apellido=apellido;
        this.tlf=tlf;
        this.contraseña=contraseña;
        this.admin=admin;
    }

    public String GetContraseña (){
        return this.contraseña;
    }
    public String GetNombre (){
        return this.nombre;
    }
    public String GetApellido (){
        return this.apellido;
    }
    public String GetTlf (){
        return this.tlf;
    }    
    public String GetDni (){
        return this.dni;
    }
    public boolean GetAdmin(){
        return this.admin;
    }


    public void SetNombre (String nombre){
        this.nombre=nombre;
    }
    public void SetApellido (String apellido){
        this.apellido=apellido;
    }
    public void SetContraseña (String contraseña){
        this.contraseña=contraseña;
    }
    public void SetDni (String dni){
        this.dni=dni;
    }
    public void SetTlf (String tlf){
        this.tlf=tlf;
    }
    public void SetAdmin(boolean admin){
        this.admin=admin;
    }
}
