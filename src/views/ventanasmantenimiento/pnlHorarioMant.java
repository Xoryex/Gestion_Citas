package views.ventanasmantenimiento;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlHorarioMant extends JPanel {
    private JTable tblHorario;
    private DefaultTableModel modelHorario;
    private Connection conn = Conexion.getConnection();

    public pnlHorarioMant() {
        initComponents();
        cargarDatos();  // Carga al inicio
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Horarios"));
        
        String[] columnas = {
            "ID Horario", "Día", "Hora Inicio", "Hora Fin",
            "Turno", "Límite de Pacientes", "Estado"
        };
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
        JTextField txtCodHorario   = new JTextField();
        JTextField txtDia          = new JTextField();
        JTextField txtHoraInicio   = new JTextField();
        JTextField txtHoraFin      = new JTextField();
        JTextField txtLimitPct     = new JTextField();
        JTextField txtEstadoHorario= new JTextField();

        Object[] mensaje = {
            "ID Horario:", txtCodHorario,
            "Día:", txtDia,
            "Hora Inicio (HH:MM:SS):", txtHoraInicio,
            "Hora Fin (HH:MM:SS):", txtHoraFin,
            "Límite de Pacientes:", txtLimitPct,
            "Estado (1=Habilitado, 0=Deshabilitado):", txtEstadoHorario
        };

        int opcion = JOptionPane.showConfirmDialog(
            this, mensaje, "Agregar Horario", JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int codHorario    = Integer.parseInt(txtCodHorario.getText().trim());
                String dia        = txtDia.getText().trim();
                Time horaInicio   = Time.valueOf(txtHoraInicio.getText().trim());
                Time horaFin      = Time.valueOf(txtHoraFin.getText().trim());
                int limitPct      = Integer.parseInt(txtLimitPct.getText().trim());
                boolean estado    = txtEstadoHorario.getText().trim().equals("1");

                if (dia.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Día es obligatorio.");
                    return;
                }

                CallableStatement stmt = conn.prepareCall(
                    "{CALL PA_CRUD_InsertarHorario(?, ?, ?, ?, ?, ?)}"
                );
                stmt.setInt(1, codHorario);
                stmt.setString(2, dia);
                stmt.setTime(3, horaInicio);
                stmt.setTime(4, horaFin);
                stmt.setInt(5, limitPct);
                stmt.setBoolean(6, estado);

                stmt.execute();
                stmt.close();
                JOptionPane.showMessageDialog(this, "Horario agregado correctamente.");
                cargarDatos();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.");
            } catch (IllegalArgumentException ie) {
                JOptionPane.showMessageDialog(this, "Formato de hora inválido.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    this, "Error al insertar: " + e.getMessage()
                );
            }
        }
    }

    public void actualizar() {
        int fila = tblHorario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String codHorarioStr = modelHorario.getValueAt(fila, 0).toString();
        String diaActual     = modelHorario.getValueAt(fila, 1).toString();
        String horaInicioAct = modelHorario.getValueAt(fila, 2).toString();
        String limitPctAct   = modelHorario.getValueAt(fila, 5).toString();
        String estadoAct     = modelHorario.getValueAt(fila, 6).toString();

        JTextField txtDia        = new JTextField(diaActual);
        JTextField txtHoraInicio = new JTextField(horaInicioAct);
        JTextField txtLimitPct   = new JTextField(limitPctAct);
        JTextField txtEstado     = new JTextField(estadoAct.equals("Habilitado") ? "1" : "0");

        Object[] mensaje = {
            "ID Horario (no editable): " + codHorarioStr,
            "Día:", txtDia,
            "Hora Inicio (HH:MM:SS):", txtHoraInicio,
            "Límite de Pacientes:", txtLimitPct,
            "Estado (1=Habilitado, 0=Deshabilitado):", txtEstado
        };

        int opcion = JOptionPane.showConfirmDialog(
            this, mensaje, "Actualizar Horario", JOptionPane.OK_CANCEL_OPTION
        );
        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int codHorario  = Integer.parseInt(codHorarioStr);
            String dia      = txtDia.getText().trim();
            Time horaInicio= Time.valueOf(txtHoraInicio.getText().trim());
            int limitPct    = Integer.parseInt(txtLimitPct.getText().trim());
            boolean estado  = txtEstado.getText().trim().equals("1");

            CallableStatement stmt = conn.prepareCall(
                "{CALL PA_CRUD_ModificarHorario(?, ?, ?, ?, ?)}"
            );
            stmt.setInt(1, codHorario);
            stmt.setTime(2, horaInicio);
            stmt.setString(3, dia);
            stmt.setInt(4, limitPct);
            stmt.setBoolean(5, estado);

            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(this, "Horario actualizado correctamente.");
            cargarDatos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.");
        } catch (IllegalArgumentException ie) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this, "Error al actualizar: " + e.getMessage()
            );
        }
    }

    public void eliminar() {
        int fila = tblHorario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String codHorarioStr = modelHorario.getValueAt(fila, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Eliminar el horario con ID: " + codHorarioStr + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int codHorario = Integer.parseInt(codHorarioStr);
            CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarHorario(?)}");
            stmt.setInt(1, codHorario);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(this, "Horario eliminado correctamente.");
            cargarDatos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this, "Error al eliminar: " + e.getMessage()
            );
        }
    }

    public void cargarDatos() {
        modelHorario.setRowCount(0);
        try {
            CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarHorario()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modelHorario.addRow(new Object[]{
                    rs.getString("Codigo"),
                    rs.getString("Dia"),
                    rs.getString("HoraInicio"),
                    rs.getString("HoraFin"),
                    rs.getString("Turno"),
                    rs.getString("Limite_Pacientes"),
                    rs.getString("Estado")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this, "Error al cargar datos: " + e.getMessage()
            );
        }
    }
}
