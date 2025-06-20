package src.objetos;

public class Paciente extends Persona {
    private boolean genero;
    private String fecha_nacimiento,direccion,ocupacion,procedencia,grupo_etnico,centro_trabajo;
    private int grupo_sanguineo,grado_instruccion,numero_hijos;

    public void setCentro_trabajo(String centro_trabajo) {
        this.centro_trabajo = centro_trabajo;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public void setGenero(boolean genero) {
        this.genero = genero;
    }
    public void setGrado_instruccion(int grado_instruccion) {
        this.grado_instruccion = grado_instruccion;
    }
    public void setGrupo_etnico(String grupo_etnico) {
        this.grupo_etnico = grupo_etnico;
    }
    public void setGrupo_sanguineo(int grupo_sanguineo) {
        this.grupo_sanguineo = grupo_sanguineo;
    }
    public void setNumero_hijos(int numero_hijos) {
        this.numero_hijos = numero_hijos;
    }
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }
    
    public String getCentro_trabajo() {
        return centro_trabajo;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    public int getGrado_instruccion() {
        return grado_instruccion;
    }
    public String getGrupo_etnico() {
        return grupo_etnico;
    }
    public int getGrupo_sanguineo() {
        return grupo_sanguineo;
    }
    public int getNumero_hijos() {
        return numero_hijos;
    }
    public String getOcupacion() {
        return ocupacion;
    }
    public String getProcedencia() {
        return procedencia;
    }
    public boolean getGenero() {
        return genero;
    }
    
}
