package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlEspecialidadMant extends JPanel {
    private JTable tblEspecialidad;
    private DefaultTableModel modelEspecialidad;
    private Connection conn = Conexion.getConnection();

    public pnlEspecialidadMant() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Especialidades"));

        String[] columnas = {"Código", "Especialidad"};
        modelEspecialidad = new DefaultTableModel(columnas, 0);
        tblEspecialidad = new JTable(modelEspecialidad);

        JScrollPane scrollPane = new JScrollPane(tblEspecialidad);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTabla() {
        return tblEspecialidad;
    }

    public DefaultTableModel getModelo() {
        return modelEspecialidad;
    }

    // Método para insertar una especialidad usando un procedimiento almacenado
    public void agregar() {
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();

        Object[] mensaje = {
            "Código:", txtCodigo,
            "Especialidad:", txtNombre
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Especialidad", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String codEspecia = txtCodigo.getText().trim();
            String especialidad = txtNombre.getText().trim();

            if (codEspecia.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarEspecialidad(?, ?)}");
                    stmt.setString(1, codEspecia);      // CodEspecia
                    stmt.setString(2, especialidad);    // Especialidad

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Especialidad agregada correctamente.");
                    stmt.close();
                    cargarDatos(); // Recargar datos después de agregar
                    conn.close();
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
        int filaSeleccionada = tblEspecialidad.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String codEspecia = modelEspecialidad.getValueAt(filaSeleccionada, 0).toString();
        String especialidadActual = modelEspecialidad.getValueAt(filaSeleccionada, 1).toString();

        JTextField txtNuevoNombre = new JTextField(especialidadActual);

        Object[] mensaje = {
            "Código (no editable): " + codEspecia,
            "Nuevo nombre:", txtNuevoNombre
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Especialidad", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevaEspecialidad = txtNuevoNombre.getText().trim();

            if (nuevaEspecialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarEspecialidad(?, ?)}");
                    stmt.setString(1, codEspecia);         // CodEspecia
                    stmt.setString(2, nuevaEspecialidad);  // Especialidad
                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Especialidad actualizada correctamente.");
                    stmt.close();
                    cargarDatos();
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
        int filaSeleccionada = tblEspecialidad.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String codEspecia = modelEspecialidad.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar la especialidad con código: " + codEspecia + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarEspecialidad(?)}");
                    stmt.setString(1, codEspecia); // CodEspecia
                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Especialidad eliminada correctamente.");
                    stmt.close();
                    cargarDatos();
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
        modelEspecialidad.setRowCount(0); // Limpiar la tabla
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarEspecialidad()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String codEspecia = rs.getString("CodEspecia");
                    String especialidad = rs.getString("Especialidad");
                    modelEspecialidad.addRow(new Object[]{codEspecia, especialidad});
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
