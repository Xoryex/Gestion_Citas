package ventanasmantenimiento;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.utils.Conexion;

public class pnlHorarioMant extends JPanel {
    private JTable tblHorario;
    private DefaultTableModel modelHorario;
    private Connection conn = Conexion.getConnection();

    public pnlHorarioMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Horarios"));
        
        String[] columnas = {"ID Horario", "Día", "Hora Inicio", "Hora Fin", "Límite de Pacientes", "Estado del Horario", "Turno"};
        modelHorario = new DefaultTableModel(columnas, 0);
        tblHorario = new JTable(modelHorario);
        
        JScrollPane scrollPane = new JScrollPane(tblHorario);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblHorario;
    }
    
    public DefaultTableModel getModelo() {
        return modelHorario;
    }
    
    
    public void agregar() {
        JTextField txtID = new JTextField();
        JTextField txtDia = new JTextField();
        JTextField txtHoraInicio = new JTextField();
        JTextField txtHoraFin = new JTextField();
        JTextField txtLimitePacientes = new JTextField();
        JTextField txtEstado = new JTextField();
        JTextField txtTurno = new JTextField();

        Object[] mensaje = {
            "ID Horario:", txtID,
            "Día:", txtDia,
            "Hora Inicio:", txtHoraInicio,
            "Hora Fin:", txtHoraFin,
            "Límite de Pacientes:", txtLimitePacientes,
            "Estado del Horario:", txtEstado,
            "Turno:", txtTurno
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Horario", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String id = txtID.getText().trim();
            String dia = txtDia.getText().trim();
            String horaInicio = txtHoraInicio.getText().trim();
            String horaFin = txtHoraFin.getText().trim();
            String limite = txtLimitePacientes.getText().trim();
            String estado = txtEstado.getText().trim();
            String turno = txtTurno.getText().trim();

            if (id.isEmpty() || dia.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty() || limite.isEmpty() || estado.isEmpty() || turno.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarHorario(?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, id);
                    stmt.setString(2, dia);
                    stmt.setString(3, horaInicio);
                    stmt.setString(4, horaFin);
                    stmt.setString(5, limite);
                    stmt.setString(6, estado);
                    stmt.setString(7, turno);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Horario agregado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
            }
        }
    }

    public void actualizar() {
        int filaSeleccionada = tblHorario.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String idActual = modelHorario.getValueAt(filaSeleccionada, 0).toString();
        String diaActual = modelHorario.getValueAt(filaSeleccionada, 1).toString();
        String horaInicioActual = modelHorario.getValueAt(filaSeleccionada, 2).toString();
        String horaFinActual = modelHorario.getValueAt(filaSeleccionada, 3).toString();
        String limiteActual = modelHorario.getValueAt(filaSeleccionada, 4).toString();
        String estadoActual = modelHorario.getValueAt(filaSeleccionada, 5).toString();
        String turnoActual = modelHorario.getValueAt(filaSeleccionada, 6).toString();

        JTextField txtDia = new JTextField(diaActual);
        JTextField txtHoraInicio = new JTextField(horaInicioActual);
        JTextField txtHoraFin = new JTextField(horaFinActual);
        JTextField txtLimitePacientes = new JTextField(limiteActual);
        JTextField txtEstado = new JTextField(estadoActual);
        JTextField txtTurno = new JTextField(turnoActual);

        Object[] mensaje = {
            "ID Horario (no editable): " + idActual,
            "Día:", txtDia,
            "Hora Inicio:", txtHoraInicio,
            "Hora Fin:", txtHoraFin,
            "Límite de Pacientes:", txtLimitePacientes,
            "Estado del Horario:", txtEstado,
            "Turno:", txtTurno
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Horario", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String dia = txtDia.getText().trim();
            String horaInicio = txtHoraInicio.getText().trim();
            String horaFin = txtHoraFin.getText().trim();
            String limite = txtLimitePacientes.getText().trim();
            String estado = txtEstado.getText().trim();
            String turno = txtTurno.getText().trim();

            if (dia.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty() || limite.isEmpty() || estado.isEmpty() || turno.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ActualizarHorario(?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, idActual);
                    stmt.setString(2, dia);
                    stmt.setString(3, horaInicio);
                    stmt.setString(4, horaFin);
                    stmt.setString(5, limite);
                    stmt.setString(6, estado);
                    stmt.setString(7, turno);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Horario actualizado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    public void eliminar() {
        int filaSeleccionada = tblHorario.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String id = modelHorario.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el horario con ID: " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarHorario(?)}");
                    stmt.setString(1, id);
                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Horario eliminado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public void cargarDatos() {
        modelHorario.setRowCount(0); // Limpiar la tabla
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_MostrarHorarios()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("ID_Horario");
                    String dia = rs.getString("Dia");
                    String horaInicio = rs.getString("HoraInicio");
                    String horaFin = rs.getString("HoraFin");
                    String limite = rs.getString("LimitePacientes");
                    String estado = rs.getString("EstadoHorario");
                    String turno = rs.getString("Turno");
                    modelHorario.addRow(new Object[]{id, dia, horaInicio, horaFin, limite, estado, turno});
                }
                rs.close();
                stmt.close();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}