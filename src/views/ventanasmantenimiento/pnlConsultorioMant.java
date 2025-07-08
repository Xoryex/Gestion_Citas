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
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Consultorios"));
        
        String[] columnas = {"ID", "Nombre del Consultorio"};
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

        Object[] mensaje = {
            "Código:", txtID,
            "Especialidad:", txtNombre
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Consultorio", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String ID = txtID.getText().trim();
            String nombre = txtNombre.getText().trim();

            if (ID.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarConsultorio(?, ?)}");
                    stmt.setString(1, ID);
                    stmt.setString(2, nombre);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Consultorio agregado correctamente.");
                    stmt.close();
                    cargarDatos();
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
        int filaSeleccionada = tblConsultorio.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String codigoActual = modelConsultorio.getValueAt(filaSeleccionada, 0).toString();
        String nombreActual = modelConsultorio.getValueAt(filaSeleccionada, 1).toString();

        JTextField txtNuevoNombre = new JTextField(nombreActual);

        Object[] mensaje = {
            "Código (no editable): " + codigoActual,
            "Nuevo nombre:", txtNuevoNombre
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Consultorio", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNuevoNombre.getText().trim();

            if (nuevoNombre.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ActualizarConsultorio(?, ?)}");
                    stmt.setString(1, codigoActual);
                    stmt.setString(2, nuevoNombre);
                    stmt.execute();

                    modelConsultorio.setValueAt(nuevoNombre, filaSeleccionada, 1);

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
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRU_EliminarConsultorio(?)}");
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
        // Limpia la tabla
        modelConsultorio.setRowCount(0);
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_MostrarConsultorios()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String nombre = rs.getString("NombreConsultorio");
                    modelConsultorio.addRow(new Object[]{id, nombre});
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