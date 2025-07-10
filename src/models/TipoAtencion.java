package models;

public class TipoAtencion {
    private int idTipoAtencion;
    private String nombreTipoAtencion;

    public TipoAtencion(int idTipoAtencion, String nombreTipoAtencion) {
        this.idTipoAtencion = idTipoAtencion;
        this.nombreTipoAtencion = nombreTipoAtencion;
    }


    public int getIdTipoAtencion() {
        return idTipoAtencion;
    }

    public String getNombreTipoAtencion() {
        return nombreTipoAtencion;
    }
}
