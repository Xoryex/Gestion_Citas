package querys;

import models.Cita;
import models.Doctor;
import models.Especialidad;
import models.Paciente;
import models.TipoAtencion;
import utils.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class QueryCita implements Query<Cita> {

    @Override
    public void Insetar(Cita cita) {
        String sql = "{CALL PA_CRUD_InsertarCita(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            
            stmt.setInt(1, cita.getDniRecep());
            stmt.setInt(2, cita.getCodHorario());
            stmt.setInt(3, cita.getDniPct());
            stmt.setInt(4, cita.getDniDoc());
            stmt.setTime(5, Time.valueOf(cita.getHoraInicio()));
            stmt.setTime(6, Time.valueOf(cita.getHoraFin()));
            stmt.setDate(7, Date.valueOf(cita.getFechaCita()));
            stmt.setInt(9, cita.getIdTipoAtencion());
            
        
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Cita registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void Eliminar(int no) {}

    @Override
    public void actualizar(Cita cita) {
        String sql = "{CALL PA_CRUD_ModificarCita(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            
            stmt.setInt(1, cita.getDniRecep());
            stmt.setString(2, cita.getCodCita());
            stmt.setInt(3, cita.getCodHorario());
            stmt.setInt(4, cita.getDniPct());
            stmt.setInt(5, cita.getDniDoc());
            stmt.setTime(6, Time.valueOf(cita.getHoraInicio()));
            stmt.setTime(7, Time.valueOf(cita.getHoraFin()));
            stmt.setDate(8, Date.valueOf(cita.getFechaCita()));
            stmt.setInt(9, cita.getIdEstadoCita());
            stmt.setInt(10, cita.getIdTipoAtencion());
            
            if (cita.getFechaReprogra() != null) {
                stmt.setDate(11, Date.valueOf(cita.getFechaReprogra()));
            } else {
                stmt.setNull(11, Types.DATE);
            }
            
            if (cita.getFechaAnulacion() != null) {
                stmt.setDate(12, Date.valueOf(cita.getFechaAnulacion()));
            } else {
                stmt.setNull(12, Types.DATE);
            }
            
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Cita actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String[][] seleccionar() {
        String sql = "{CALL PA_CRUD_ListarCitas}";
        List<String[]> filas = new ArrayList<>();
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            
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
            JOptionPane.showMessageDialog(null, "Error al listar las citas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return filas.toArray(new String[0][]);
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
    
    public List<Doctor> getDoctoresFiltrados(Especialidad especialidad,String hora) {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "{CALL PA_ListarDoctoresFiltrados(?,?)}";
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery(sql)) {
            
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
        ArrayList<Especialidad> especialidades = new ArrayList<>();
        
        try {
            Connection con = Conexion.getConnection();
            // doctores con horarios activos y consultorios de la misma especialidad 
            //falta crear ese procedimiento almacenado
            String sql = "{CALL PA_ListarEspecialidadesActivas}"; 
            CallableStatement stmt = con.prepareCall(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                especialidades.add(new Especialidad(rs.getInt("IdEspecialidad"), rs.getString("NombreEspecialidad")));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener especialidades: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            stmt.setString(1, codCita);
            stmt.execute();
            
            JOptionPane.showMessageDialog(null, "Cita anulada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al anular la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
   
}
