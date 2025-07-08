package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;


public class pnlConsultorioMant extends JPanel {
    private JTable tblConsultorio;
    private DefaultTableModel modelConsultorio;

    public pnlConsultorioMant() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Consultorios"));

        String[] columnas = {"ID Consultorio", "Nombre del Consultorio", "Nombre de la Especialidad"};
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

    // Método para insertar un Consultorio usando un procedimiento almacenado
    public void agregar() {
        JTextField txtID = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtEspecialidad = new JTextField();

        Object[] mensaje = {
            "Código:", txtID,
            "Nombre del Consultorio:", txtNombre,
            "Nombre de la Especialidad:", txtEspecialidad
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Consultorio", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String codConst = txtID.getText().trim();
            String nomConst = txtNombre.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();

            if (codConst.isEmpty() || nomConst.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try (
                Connection conn = Conexion.getConnection();
                CallableStatement stmt = conn != null ? conn.prepareCall("{CALL PA_CRUD_InsertarConsultorio(?, ?, ?)}") : null
            ) {
                if (conn != null && stmt != null) {
                    stmt.setString(1, codConst);
                    stmt.setString(2, nomConst);
                    stmt.setString(3, especialidad);
                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Consultorio agregado correctamente.");
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

        String codConst = modelConsultorio.getValueAt(filaSeleccionada, 0).toString();
        String nomConstActual = modelConsultorio.getValueAt(filaSeleccionada, 1).toString();
        String especialidadActual = modelConsultorio.getValueAt(filaSeleccionada, 2).toString();

        JTextField txtNuevoNombre = new JTextField(nomConstActual);
        JTextField txtNuevaEspecialidad = new JTextField(especialidadActual);

        Object[] mensaje = {
            "Código (no editable): " + codConst,
            "Nuevo nombre:", txtNuevoNombre,
            "Nueva especialidad:", txtNuevaEspecialidad
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Consultorio", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNomConst = txtNuevoNombre.getText().trim();
            String nuevaEspecialidad = txtNuevaEspecialidad.getText().trim();

            if (nuevoNomConst.isEmpty() || nuevaEspecialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos.");
                return;
            }

            try (
                Connection conn = Conexion.getConnection();
                CallableStatement stmt = conn != null ? conn.prepareCall("{CALL PA_CRUD_ModificarConsultorio(?, ?, ?)}") : null
            ) {
                if (conn != null && stmt != null) {
                    stmt.setString(1, codConst);
                    stmt.setString(2, nuevoNomConst);
                    stmt.setString(3, nuevaEspecialidad);
                    stmt.execute();

                    modelConsultorio.setValueAt(nuevoNomConst, filaSeleccionada, 1);
                    modelConsultorio.setValueAt(nuevaEspecialidad, filaSeleccionada, 2);

                    JOptionPane.showMessageDialog(this, "Consultorio actualizado correctamente.");
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

        String codConst = modelConsultorio.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el Consultorio con código: " + codConst + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try (
                Connection conn = Conexion.getConnection();
                CallableStatement stmt = conn != null ? conn.prepareCall("{CALL PA_CRUD_EliminarConsultorio(?)}") : null
            ) {
                if (conn != null && stmt != null) {
                    stmt.setString(1, codConst);
                    stmt.execute();

                    modelConsultorio.removeRow(filaSeleccionada);

                    JOptionPane.showMessageDialog(this, "Consultorio eliminado correctamente.");
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
        // Limpia la tabla
        modelConsultorio.setRowCount(0);
        try (
            Connection conn = Conexion.getConnection();
            CallableStatement stmt = conn != null ? conn.prepareCall("{CALL PA_CRUD_ListarConsulta()}") : null;
            ResultSet rs = stmt != null ? stmt.executeQuery() : null
        ) {
            if (conn != null && rs != null) {
                while (rs.next()) {
                    String codConst = rs.getString("CodConst");
                    String nomConst = rs.getString("NomConst");
                    String especialidad = rs.getString("Especialidad");
                    modelConsultorio.addRow(new Object[]{codConst, nomConst, especialidad});
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

}