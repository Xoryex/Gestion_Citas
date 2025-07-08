package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

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
        
        String[] columnas = {"ID Horario", "Día", "Hora Inicio", "Hora Fin", "Límite de Pacientes", "Estado del Horario"};
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
        JTextField txtCodHorario = new JTextField();
        JTextField txtDia = new JTextField();
        JTextField txtHoraInicio = new JTextField();
        JTextField txtHoraFin = new JTextField();
        JTextField txtLimitPct = new JTextField();
        JTextField txtEstadoHorario = new JTextField();

        Object[] mensaje = {
            "ID Horario:", txtCodHorario,
            "Día:", txtDia,
            "Hora Inicio:", txtHoraInicio,
            "Hora Fin:", txtHoraFin,
            "Límite de Pacientes:", txtLimitPct,
            "Estado del Horario:", txtEstadoHorario
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Horario", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String codHorario = txtCodHorario.getText().trim();
            String dia = txtDia.getText().trim();
            String horaInicio = txtHoraInicio.getText().trim();
            String horaFin = txtHoraFin.getText().trim();
            String limitPct = txtLimitPct.getText().trim();
            String estadoHorario = txtEstadoHorario.getText().trim();

            if (codHorario.isEmpty() || dia.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty() || limitPct.isEmpty() || estadoHorario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarHorario(?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, codHorario);      // CodHorario
                    stmt.setString(2, dia);            // Dia
                    stmt.setString(3, horaInicio);     // HoraInicio
                    stmt.setString(4, horaFin);        // HoraFin
                    stmt.setString(5, limitPct);       // LimitPct
                    stmt.setString(6, estadoHorario);  // EstadoHorario

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

        String codHorario = modelHorario.getValueAt(filaSeleccionada, 0).toString();
        String diaActual = modelHorario.getValueAt(filaSeleccionada, 1).toString();
        String horaInicioActual = modelHorario.getValueAt(filaSeleccionada, 2).toString();
        String horaFinActual = modelHorario.getValueAt(filaSeleccionada, 3).toString();
        String limitPctActual = modelHorario.getValueAt(filaSeleccionada, 4).toString();
        String estadoHorarioActual = modelHorario.getValueAt(filaSeleccionada, 5).toString();

        JTextField txtDia = new JTextField(diaActual);
        JTextField txtHoraInicio = new JTextField(horaInicioActual);
        JTextField txtHoraFin = new JTextField(horaFinActual);
        JTextField txtLimitPct = new JTextField(limitPctActual);
        JTextField txtEstadoHorario = new JTextField(estadoHorarioActual);

        Object[] mensaje = {
            "ID Horario (no editable): " + codHorario,
            "Día:", txtDia,
            "Hora Inicio:", txtHoraInicio,
            "Hora Fin:", txtHoraFin,
            "Límite de Pacientes:", txtLimitPct,
            "Estado del Horario:", txtEstadoHorario
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Horario", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String dia = txtDia.getText().trim();
            String horaInicio = txtHoraInicio.getText().trim();
            String horaFin = txtHoraFin.getText().trim();
            String limitPct = txtLimitPct.getText().trim();
            String estadoHorario = txtEstadoHorario.getText().trim();

            if (dia.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty() || limitPct.isEmpty() || estadoHorario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarHorario(?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, codHorario);      // CodHorario
                    stmt.setString(2, dia);            // Dia
                    stmt.setString(3, horaInicio);     // HoraInicio
                    stmt.setString(4, horaFin);        // HoraFin
                    stmt.setString(5, limitPct);       // LimitPct
                    stmt.setString(6, estadoHorario);  // EstadoHorario

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

        String codHorario = modelHorario.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el horario con ID: " + codHorario + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarHorario(?)}");
                    stmt.setString(1, codHorario); // CodHorario
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
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarHorario()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String codHorario = rs.getString("CodHorario");
                    String dia = rs.getString("Dia");
                    String horaInicio = rs.getString("HoraInicio");
                    String horaFin = rs.getString("HoraFin");
                    String limitPct = rs.getString("LimitPct");
                    String estadoHorario = rs.getString("EstadoHorario");

                    modelHorario.addRow(new Object[]{codHorario, dia, horaInicio, horaFin, limitPct, estadoHorario});
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