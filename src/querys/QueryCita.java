package querys;

import models.*;
import utils.Conexion;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.swing.*;


public class QueryCita implements Query<Cita> {

    @Override
    public void Insetar(Cita cita) {
        
    }

    @Override
    public void Eliminar(int IdCita) {
        //ya hay un metodo para anular la cita
        }

    @Override
    public void actualizar(Cita cita) {
    }

    @Override
    public String[][] seleccionar() {
        //no se esta usando
        return null;
    }

    public ArrayList<String[]> CitasPendientesReprogramadas() {
        Conexion.closeConnection();
        ArrayList<String[]> filas = new ArrayList<>();
        
        try {

            CallableStatement stmt = Conexion.getConnection().prepareCall("{CALL PA_CRUD_ListarCitasPendientesReprogramadas}");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String[] fila = {
                    rs.getString("CodCita"),
                    rs.getString("DniPct"),
                    rs.getString("NombrePaciente"),
                    rs.getString("NomConst"),
                    rs.getString("NombreDoctor"),
                    rs.getTime("HoraInicio").toString(),
                    rs.getTime("HoraFin").toString(),
                    rs.getString("FechaCita"),
                    rs.getString("FechaReprogra"),
                    rs.getString("TipoAtencion"),
                    rs.getString("EstadoCita"),
                    rs.getString("NombreRecepcionista")
                };
                filas.add(fila);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar las citas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            Conexion.closeConnection();
        }
        
        return filas;
    }
    
    public ArrayList<String[]> CitasReprogramadas() {
        String sql = "{CALL PA_CRUD_ListarCitasReprogramadas}";
        ArrayList<String[]> filas = new ArrayList<>();
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] fila = {
                    rs.getString("CodCita"),
                    rs.getString("DniPct"),
                    rs.getString("NombrePaciente"),
                    rs.getString("NomConst"),
                    rs.getString("NombreDoctor"),
                    rs.getTime("HoraInicio").toString(),
                    rs.getTime("HoraFin").toString(),
                    rs.getString("FechaCita"),
                    rs.getString("FechaReprogra"),
                    rs.getString("TipoAtencion"),
                    rs.getString("EstadoCita"),
                    rs.getString("NombreRecepcionista")
                };
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar las citas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return filas;
    }

    public String[][] seleccionarConFiltro(String filtro) {
        String sql = "{CALL PA_CRUD_ListarCitasConFiltro(?)}";
        List<String[]> filas = new ArrayList<>();
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            
            stmt.setString(1, filtro);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String[] fila = {
                    rs.getString("IdCita"),
                    rs.getString("DniPaciente"),
                    rs.getString("NombrePaciente"),
                    rs.getString("Consultorio"),
                    rs.getString("NombreDoctor"),
                    rs.getTime("HoraInicio").toString(),
                    rs.getTime("HoraFin").toString(),
                    rs.getString("Atencion"),
                    rs.getString("Estado"),
                    rs.getString("NombreRecepcionista")
                };
                filas.add(fila);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar citas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return filas.toArray(new String[0][]);
    }
    
    public ArrayList<Doctor> getDoctoresFiltrados(Especialidad especialidad, Date fecha) {
        ArrayList<Doctor> doctores = new ArrayList<>();
        String sql = "{CALL PA_ListarDoctoresFiltrados(?,?)}";
        
        try {Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery(sql);
             stmt.setInt(1, especialidad.getIdEspecialidad());
             stmt.setDate(2, fecha);
            
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDni(rs.getInt("DniDoc"));
                doctor.setNombre(rs.getString("NomDoc"));
                doctor.setApellido(rs.getString("ApellDoc"));
                doctor.setTlf(rs.getInt("TlfDoc"));
                doctor.setCodConst(rs.getInt("CodConst"));
                doctores.add(doctor);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener doctores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return doctores;
    }
    
    public ArrayList<Especialidad> getEspecialidadesActivas() {
        Conexion.closeConnection();
        ArrayList<Especialidad> especialidades = new ArrayList<>();
        
        try {
            Connection con = Conexion.getConnection();
            // doctores con horarios activos y consultorios de la misma especialidad 
            //falta crear ese procedimiento almacenado
            String sql = "{CALL PA_ListarEspecialidadesActivas}"; 
            CallableStatement stmt = con.prepareCall(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                especialidades.add(new Especialidad(rs.getInt("CodEspecia"), rs.getString("Especialidad")));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener especialidades: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            Conexion.closeConnection();
        }
        
        return especialidades;
    }
    
    public ArrayList<TipoAtencion> getTiposAtencion() {
        ArrayList<TipoAtencion> tiposAtencion = new ArrayList<>();
        //tipos de atenciones desde la misma tabla tipoatencion
        //falta crear ese procedimiento almacenado
        String sql = "{CALL PA_ListarTiposAtencion}";   
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tiposAtencion.add(new TipoAtencion(rs.getInt("IdTipoAtencion"), rs.getString("TipoAtencion")));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener tipos de atención: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return tiposAtencion;
    }
    
    public Paciente getPaciente(int dni) {
        String sql = "{CALL PA_CRUD_SelectPaciente(?)}";
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            
            stmt.setInt(1, dni);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setDni(rs.getInt("DniPct"));
                paciente.setNombre(rs.getString("NomPct"));
                paciente.setApellido(rs.getString("ApellPct"));
                paciente.setTlf(rs.getInt("TlfPct"));
                return paciente;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }

    
    public String getConsultorioPorDoctor(Doctor doctor) {
        String sql = "{CALL PA_ConsultorioPorDoctor(?)}";

        
        try (Connection con = Conexion.getConnection();
             CallableStatement  stmt = con.prepareCall(sql) ) {
            
            stmt.setInt(1, doctor.getDni());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("NomConst");
            }else{
                return null;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener consultorio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        
        return null;
        }
    }
    
    public void anularCita(Cita cita) {
        String sql = "{CALL PA_CRUD_AnularCita(?)}";
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, cita.getIdCita());
            stmt.execute();
            
            JOptionPane.showMessageDialog(null, "Cita anulada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al anular la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
