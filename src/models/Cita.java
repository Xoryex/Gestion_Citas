package models;

import java.sql.Time;
import java.sql.Date;

public class Cita {
    private int idCita;
    private int dniRecep;
    private int codHorario;
    private int dniPct;
    private int dniDoc;
    private Time horaInicio;
    private Time horaFin;
    private Date fechaCita;
    private int idEstadoCita;
    private int idTipoAtencion;
    private Date fechaReprogra;
    private Date fechaAnulacion;

    public Cita() {}

    public Cita(int dniRecep, int codHorario, int dniPct, int dniDoc, Time horaInicio, Time horaFin, Date fechaCita, int idEstadoCita, int idTipoAtencion, Date fechaReprogra, Date fechaAnulacion) {
        
    }



    // Getters y Setters
    public int getDniRecep() { return dniRecep; }
    public void setDniRecep(int dniRecep) { this.dniRecep = dniRecep; }

    public int getCodHorario() { return codHorario; }
    public void setCodHorario(int codHorario) { this.codHorario = codHorario; }

    public int getDniPct() { return dniPct; }
    public void setDniPct(int dniPct) { this.dniPct = dniPct; }

    public int getDniDoc() { return dniDoc; }
    public void setDniDoc(int dniDoc) { this.dniDoc = dniDoc; }

    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }

    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }

    public Date getFechaCita() { return fechaCita; }
    public void setFechaCita(Date fechaCita) { this.fechaCita = fechaCita; }

    public int getIdTipoAtencion() { return idTipoAtencion; }
    public void setIdTipoAtencion(int idTipoAtencion) { this.idTipoAtencion = idTipoAtencion; }

    public Date getFechaReprogra() { return fechaReprogra; }
    public void setFechaReprogra(Date fechaReprogra) { this.fechaReprogra = fechaReprogra; }

    public Date getFechaAnulacion() { return fechaAnulacion; }

    public void setFechaAnulacion(Date fechaAnulacion) { this.fechaAnulacion = fechaAnulacion; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdEstadoCita() { return idEstadoCita; }
    public void setIdEstadoCita(int idEstadoCita) { this.idEstadoCita = idEstadoCita; }

}
