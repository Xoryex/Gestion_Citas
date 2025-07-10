package views.ventanasmantenimiento;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlConsultorioMant extends JPanel {
    private JTable tblConsultorio;
    private DefaultTableModel modelConsultorio;
    private Connection conn = Conexion.getConnection();

    public pnlConsultorioMant() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Consultorios"));

        String[] columnas = {"ID", "Nombre del Consultorio", "Especialidad"};
        modelConsultorio = new DefaultTableModel(columnas, 0);
        tblConsultorio = new JTable(modelConsultorio);

        JScrollPane scrollPane = new JScrollPane(tblConsultorio);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTabla() {
        return tblConsultorio;
    }

    public DefaultTableModel getModelo() {
        return modelConsultorio;
    }

    // ✅ Cambiado: ahora usa el procedimiento PA_ListarSoloEspecialidad
    private JComboBox<String> obtenerComboEspecialidades() {
        JComboBox<String> combo = new JComboBox<>();
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_ListarSoloEspecialidad()}");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    combo.addItem(rs.getString("Especialidad"));
                }

                rs.close();
                stmt.close();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar para cargar especialidades.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar especialidades: " + e.getMessage());
        }
        return combo;
    }

    public void agregar() {
        JTextField txtNombre = new JTextField();
        JComboBox<String> comboEspecialidades = obtenerComboEspecialidades();

        Object[] mensaje = {
            "Nombre del Consultorio:", txtNombre,
            "Especialidad:", comboEspecialidades
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Consultorio", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String especialidad = (String) comboEspecialidades.getSelectedItem();

            if (nombre.isEmpty() || especialidad == null || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarConsultorio(?, ?, ?)}");
                    stmt.setString(1, nombre);
                    stmt.setString(2, especialidad);
                    stmt.registerOutParameter(3, Types.NUMERIC); // CodConst generado

                    stmt.execute();

                    long codGenerado = stmt.getLong(3);
                    JOptionPane.showMessageDialog(this, "Consultorio agregado correctamente.\nCódigo generado: " + codGenerado);

                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
            }
        }
    }

    public void actualizar() {
        int filaSeleccionada = tblConsultorio.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String codigoActual = modelConsultorio.getValueAt(filaSeleccionada, 0).toString();
        String nombreActual = modelConsultorio.getValueAt(filaSeleccionada, 1).toString();

        JTextField txtNuevoNombre = new JTextField(nombreActual);
        JComboBox<String> comboEspecialidades = obtenerComboEspecialidades();

        Object[] mensaje = {
            "Código (no editable): " + codigoActual,
            "Nuevo nombre:", txtNuevoNombre,
            "Nueva especialidad:", comboEspecialidades
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Consultorio", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNuevoNombre.getText().trim();
            String nuevaEspecialidad = (String) comboEspecialidades.getSelectedItem();

            if (nuevoNombre.isEmpty() || nuevaEspecialidad == null || nuevaEspecialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarConsultorio(?, ?, ?)}");
                    stmt.setString(1, codigoActual);
                    stmt.setString(2, nuevoNombre);
                    stmt.setString(3, nuevaEspecialidad);
                    stmt.execute();

                    modelConsultorio.setValueAt(nuevoNombre, filaSeleccionada, 1);
                    modelConsultorio.setValueAt(nuevaEspecialidad, filaSeleccionada, 2);
                    JOptionPane.showMessageDialog(this, "Consultorio actualizado correctamente.");

                    stmt.close();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    public void eliminar() {
        int filaSeleccionada = tblConsultorio.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String ID = modelConsultorio.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el Consultorio con código: " + ID + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarConsultorio(?)}");
                    stmt.setString(1, ID);
                    stmt.execute();

                    modelConsultorio.removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Consultorio eliminado correctamente.");

                    stmt.close();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public void cargarDatos() {
        modelConsultorio.setRowCount(0);
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarConsulta()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("Codigo");
                    String nombre = rs.getString("NombreConsultorio");
                    String especialidad = rs.getString("Especialidad");
                    modelConsultorio.addRow(new Object[]{id, nombre, especialidad});
                }
                rs.close();
                stmt.close();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}
