package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlRecepcionistaMant extends JPanel {
    private JTable tblRecepcionista;
    private DefaultTableModel modelRecepcionista;
    private Connection conn = Conexion.getConnection();

    public pnlRecepcionistaMant() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Recepcionistas"));

        String[] columnas = {"DNI", "Nombre", "Apellido", "Teléfono", "Admin"};
        modelRecepcionista = new DefaultTableModel(columnas, 0);
        tblRecepcionista = new JTable(modelRecepcionista);

        JScrollPane scrollPane = new JScrollPane(tblRecepcionista);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTabla() {
        return tblRecepcionista;
    }

    public DefaultTableModel getModelo() {
        return modelRecepcionista;
    }

    public void agregar() {
        JTextField txtDNI = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtAdmin = new JTextField();

        Object[] mensaje = {
            "DNI:", txtDNI,
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Teléfono:", txtTelefono,
            "Admin (S/N):", txtAdmin
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Recepcionista", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String dni = txtDNI.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String admin = txtAdmin.getText().trim();

            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || admin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarRecepcionista(?, ?, ?, ?, ?)}");
                    stmt.setString(1, dni);         // DniRecep
                    stmt.setString(2, nombre);      // NomRecep
                    stmt.setString(3, apellido);    // ApellRecep
                    stmt.setString(4, telefono);    // TelfRecep
                    stmt.setString(5, admin);       // EsAdmin

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Recepcionista agregado correctamente.");
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
        int filaSeleccionada = tblRecepcionista.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String dniActual = modelRecepcionista.getValueAt(filaSeleccionada, 0).toString();
        String nombreActual = modelRecepcionista.getValueAt(filaSeleccionada, 1).toString();
        String apellidoActual = modelRecepcionista.getValueAt(filaSeleccionada, 2).toString();
        String telefonoActual = modelRecepcionista.getValueAt(filaSeleccionada, 3).toString();
        String adminActual = modelRecepcionista.getValueAt(filaSeleccionada, 4).toString();

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtApellido = new JTextField(apellidoActual);
        JTextField txtTelefono = new JTextField(telefonoActual);
        JTextField txtAdmin = new JTextField(adminActual);

        Object[] mensaje = {
            "DNI (no editable): " + dniActual,
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Teléfono:", txtTelefono,
            "Admin (S/N):", txtAdmin
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Recepcionista", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String admin = txtAdmin.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || admin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarRecepcionista(?, ?, ?, ?, ?)}");
                    stmt.setString(1, dniActual);   // DniRecep
                    stmt.setString(2, nombre);      // NomRecep
                    stmt.setString(3, apellido);    // ApellRecep
                    stmt.setString(4, telefono);    // TelfRecep
                    stmt.setString(5, admin);       // EsAdmin

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Recepcionista actualizado correctamente.");
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
        int filaSeleccionada = tblRecepcionista.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String dni = modelRecepcionista.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el recepcionista con DNI: " + dni + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarRecepcionista(?)}");
                    stmt.setString(1, dni); // DniRecep
                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Recepcionista eliminado correctamente.");
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
        modelRecepcionista.setRowCount(0); // Limpiar la tabla
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarRecepcionista()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String dni = rs.getString("DniRecep");
                    String nombre = rs.getString("NomRecep");
                    String apellido = rs.getString("ApellRecep");
                    String telefono = rs.getString("TelfRecep");
                    String admin = rs.getString("EsAdmin");

                    modelRecepcionista.addRow(new Object[]{dni, nombre, apellido, telefono, admin});
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