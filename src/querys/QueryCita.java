package querys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Cita;
import models.Doctor;
import models.Paciente;
import utils.Conexion;

public class QueryCita implements Query<Cita> {

    @Override
    public void Insetar(Cita cita) {
        String sql = "{CALL PA_CRUD_InsertarCita(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
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
            JOptionPane.showMessageDialog(null, "Cita registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void Eliminar(int dniPaciente) {
        String sql = "{CALL PA_CRUD_EliminarCita(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection con = Conexion.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            
            // Para eliminar, necesitamos los datos de la cita a eliminar
            // Por simplicidad, solo usamos el DNI del paciente para buscar la cita
            stmt.setInt(4, dniPaciente); // DniPct
            
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Cita eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
    
    public List<Doctor> obtenerDoctores() {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT d.DniDoc, d.NomDoc, d.ApellDoc, d.TlfDoc, e.Especialidad, d.CodConst " +
                    "FROM Doctor d INNER JOIN Especialidad e ON d.CodEspecia = e.CodEspecia";
        
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDni(rs.getInt("DniDoc"));
                doctor.setNombre(rs.getString("NomDoc"));
                doctor.setApellido(rs.getString("ApellDoc"));
                doctor.setTlf(rs.getInt("TlfDoc"));
                doctor.setEspecialidad(rs.getString("Especialidad"));
                doctor.setCodConst(rs.getInt("CodConst"));
                doctores.add(doctor);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener doctores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return doctores;
    }
    
    public List<String> obtenerEspecialidades() {
        List<String> especialidades = new ArrayList<>();
        String sql = "SELECT Especialidad FROM Especialidad";
        
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                especialidades.add(rs.getString("Especialidad"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener especialidades: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return especialidades;
    }
    
    public List<String> obtenerTiposAtencion() {
        List<String> tiposAtencion = new ArrayList<>();
        String sql = "SELECT TipoAtencion FROM TipoAtencion";
        
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tiposAtencion.add(rs.getString("TipoAtencion"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener tipos de atención: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return tiposAtencion;
    }
    
    public Paciente buscarPacientePorDni(int dni) {
        String sql = "SELECT * FROM Paciente WHERE DniPct = ?";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
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
    
    public String obtenerConsultorioPorDoctor(int dniDoctor) {
        String sql = "SELECT c.NomConst FROM Doctor d INNER JOIN Consultorio c ON d.CodConst = c.CodConst WHERE d.DniDoc = ?";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, dniDoctor);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("NomConst");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener consultorio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return "-";
    }
    
    public void anularCita(String codCita) {
        String sql = "UPDATE Cita SET IdEstadoCita = 3, FechaAnulacion = GETDATE() WHERE CodCita = ?";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, codCita);
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita anulada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la cita a anular", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al anular la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int obtenerIdTipoAtencionPorNombre(String nombreTipo) {
        String sql = "SELECT IdTipoAtencion FROM TipoAtencion WHERE TipoAtencion = ?";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, nombreTipo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("IdTipoAtencion");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID tipo atención: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return 1; // Valor por defecto
    }
    
    public String generarCodigoCita() {
        return "CITA" + System.currentTimeMillis();
    }
}
