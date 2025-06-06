package src.querys.objetos;

public class Consultorio {

    private String nombre;
    private String piso;
    private String numCuarto;

    public Consultorio(String nombre, String piso, String numCuarto){
        this.nombre = nombre;
        this.piso = piso;
        this.numCuarto = numCuarto;
    }

    public String getNombre() { 
        return nombre; }
    public String getPiso() { 
        return piso; }
    public String getNumCuarto() { 
        return numCuarto; }

    public void setNombre(String nombre) { 
        this.nombre = nombre; }
    public void setPiso(String piso) { 
        this.piso = piso; }
    public void setNumCuarto(String numCuarto) { 
        this.numCuarto = numCuarto; }
}
