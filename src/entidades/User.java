package src.entidades;

import java.security.PublicKey;

public class User {

    private  String nombre,contraseña;
    private  boolean admin;

    public User(String nombre,String contraseña,boolean admin){
        this.nombre=nombre;
        this.contraseña=contraseña;
        this.admin=admin;
    }

    public String GetDate(){
        return " :: Nombre: "+this.nombre+" :: Contraseña: "+this.contraseña+" :: Admin :"+this.admin;
    }

    public String GetContraseña (){
        return this.contraseña;
    }
    public String GetNombre (){
        return this.nombre;
    }
    public boolean GetAdmin(){
        return this.admin;
    }

    public void SetContraseña (String contraseña){
        this.contraseña=contraseña;
    }
    public void SetNombre (String nombre){
        this.nombre=nombre;
    }
    public void SetAdmin(boolean admin){
        this.admin=admin;
    }
}
