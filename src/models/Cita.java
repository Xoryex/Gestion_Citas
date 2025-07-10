package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private int dniRecep;
    private int codHorario;
    private int dniPct;
    private int dniDoc;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaCita;
    private int idEstadoCita;
    private int idTipoAtencion;
    private LocalDate fechaReprogra;
    private LocalDate fechaAnulacion;
    
    // Campos adicionales para mostrar en la tabla
    private String nombrePaciente;
    private String nombreDoctor;
    private String nombreRecepcionista;
    private String consultorio;
    private String estadoCita;
    private String tipoAtencion;

    public Cita() {}

    public Cita(int dniRecep, int codHorario, int dniPct, int dniDoc, 
                LocalTime horaInicio, LocalTime horaFin, LocalDate fechaCita, 
                int idEstadoCita, int idTipoAtencion) {
        this.dniRecep = dniRecep;
        this.codHorario = codHorario;
        this.dniPct = dniPct;
        this.dniDoc = dniDoc;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaCita = fechaCita;
        this.idEstadoCita = idEstadoCita;
        this.idTipoAtencion = idTipoAtencion;
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

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }

    public int getIdEstadoCita() { return idEstadoCita; }
    public void setIdEstadoCita(int idEstadoCita) { this.idEstadoCita = idEstadoCita; }

    public int getIdTipoAtencion() { return idTipoAtencion; }
    public void setIdTipoAtencion(int idTipoAtencion) { this.idTipoAtencion = idTipoAtencion; }

    public LocalDate getFechaReprogra() { return fechaReprogra; }
    public void setFechaReprogra(LocalDate fechaReprogra) { this.fechaReprogra = fechaReprogra; }

    public LocalDate getFechaAnulacion() { return fechaAnulacion; }
    public void setFechaAnulacion(LocalDate fechaAnulacion) { this.fechaAnulacion = fechaAnulacion; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getNombreDoctor() { return nombreDoctor; }
    public void setNombreDoctor(String nombreDoctor) { this.nombreDoctor = nombreDoctor; }

    public String getNombreRecepcionista() { return nombreRecepcionista; }
    public void setNombreRecepcionista(String nombreRecepcionista) { this.nombreRecepcionista = nombreRecepcionista; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }

    public String getEstadoCita() { return estadoCita; }
    public void setEstadoCita(String estadoCita) { this.estadoCita = estadoCita; }

    public String getTipoAtencion() { return tipoAtencion; }
    public void setTipoAtencion(String tipoAtencion) { this.tipoAtencion = tipoAtencion; }
}
