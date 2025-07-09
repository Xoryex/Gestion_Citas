package views.ventanasmantenimiento;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlDoctorMant extends JPanel {
    private JTable tblDoctor;
    private DefaultTableModel modelDoctor;

    public pnlDoctorMant() {
        initComponents();
        cargarDatos(); // Mostrar datos al iniciar
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Doctores"));

        String[] columnas = {"DNI", "Nombre Completo", "Especialidad", "Código de Consultorio", "Correo", "Teléfono"};
        modelDoctor = new DefaultTableModel(columnas, 0);
        tblDoctor = new JTable(modelDoctor);

        JScrollPane scrollPane = new JScrollPane(tblDoctor);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTabla() {
        return tblDoctor;
    }

    public DefaultTableModel getModelo() {
        return modelDoctor;
    }

    // Agregar Doctor
    public void agregar() {
        JTextField txtDNI = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JComboBox<String> cmbEspecialidad = crearComboEspecialidades();
        JTextField txtCodConsultorio = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();

        Object[] mensaje = {
            "DNI:", txtDNI,
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Especialidad:", cmbEspecialidad,
            "Código de Consultorio:", txtCodConsultorio,
            "Correo:", txtCorreo,
            "Teléfono:", txtTelefono
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Doctor", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String dni = txtDNI.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidadSeleccionada = (String) cmbEspecialidad.getSelectedItem();
            String codConsultorio = txtCodConsultorio.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();

            // Validar campos vacíos
            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || especialidadSeleccionada == null
                    || especialidadSeleccionada.startsWith("Seleccione")
                    || codConsultorio.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            // Extraer el código de especialidad del ComboBox (formato: "001 - Cardiología")
            String codigoEspecialidad = especialidadSeleccionada.split(" - ")[0];

            // Validar DNI de exactamente 8 dígitos
            if (!dni.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos numéricos.");
                return;
            }

            // Validar teléfono de exactamente 9 dígitos
            if (!telefono.matches("\\d{9}")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 9 dígitos numéricos.");
                return;
            }

            try (Connection conn = Conexion.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                    return;
                }

                // Verificar si el DNI ya existe
                try (PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM Doctor WHERE DNI = ?")) {
                    checkStmt.setString(1, dni);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "Ya existe un doctor con este DNI.");
                            return;
                        }
                    }
                }

                // Obtener código de especialidad del ComboBox
                int codEspecialidad;
                try {
                    codEspecialidad = Integer.parseInt(codigoEspecialidad);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error en el código de especialidad seleccionado.");
                    return;
                }

                // Insertar doctor
                try (CallableStatement stmt = conn.prepareCall("{CALL PA_insert_Doctor(?, ?, ?, ?, ?, ?)}")) {
                    stmt.setString(1, dni);
                    stmt.setString(2, nombre);
                    stmt.setString(3, apellido);
                    stmt.setString(4, codConsultorio);
                    stmt.setString(5, correo);
                    stmt.setString(6, telefono);
                    stmt.execute();
                }

                // Insertar en tabla intermedia
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Especialidad_Doctor (DniDoc, CodEspecia) VALUES (?, ?)")) {
                    ps.setString(1, dni);
                    ps.setInt(2, codEspecialidad);
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Doctor agregado correctamente.");
                cargarDatos();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
            }
        }
    }

    // Actualizar Doctor
    public void actualizar() {
        int filaSeleccionada = tblDoctor.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String dniActual = modelDoctor.getValueAt(filaSeleccionada, 0).toString();
        String nombreCompleto = modelDoctor.getValueAt(filaSeleccionada, 1).toString();
        String[] partesNombre = nombreCompleto.split(" ", 2);
        String nombreActual = partesNombre.length > 0 ? partesNombre[0] : "";
        String apellidoActual = partesNombre.length > 1 ? partesNombre[1] : "";

        String especialidadActual = modelDoctor.getValueAt(filaSeleccionada, 2).toString();
        String codConsultorioActual = modelDoctor.getValueAt(filaSeleccionada, 3).toString();
        String correoActual = modelDoctor.getValueAt(filaSeleccionada, 4).toString();
        String telefonoActual = modelDoctor.getValueAt(filaSeleccionada, 5).toString();

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtApellido = new JTextField(apellidoActual);
        JComboBox<String> cmbEspecialidad = crearComboEspecialidades();
        // Seleccionar la especialidad actual en el ComboBox
        for (int i = 0; i < cmbEspecialidad.getItemCount(); i++) {
            String item = cmbEspecialidad.getItemAt(i);
            if (item.contains(especialidadActual)) {
                cmbEspecialidad.setSelectedIndex(i);
                break;
            }
        }
        JTextField txtCodConsultorio = new JTextField(codConsultorioActual);
        JTextField txtCorreo = new JTextField(correoActual);
        JTextField txtTelefono = new JTextField(telefonoActual);

        Object[] mensaje = {
            "DNI (no editable): " + dniActual,
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Especialidad:", cmbEspecialidad,
            "Código de Consultorio:", txtCodConsultorio,
            "Correo:", txtCorreo,
            "Teléfono:", txtTelefono
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Doctor", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidadSeleccionada = (String) cmbEspecialidad.getSelectedItem();
            String codConsultorio = txtCodConsultorio.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || especialidadSeleccionada == null
                    || especialidadSeleccionada.startsWith("Seleccione")
                    || codConsultorio.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            // Validar teléfono de exactamente 9 dígitos
            if (!telefono.matches("\\d{9}")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 9 dígitos numéricos.");
                return;
            }

            // Extraer el código de especialidad del ComboBox
            String codigoEspecialidad = especialidadSeleccionada.split(" - ")[0];

            try (Connection conn = Conexion.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                    return;
                }

                // Convertir código de especialidad
                int codEspecialidad;
                try {
                    codEspecialidad = Integer.parseInt(codigoEspecialidad);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error en el código de especialidad seleccionado.");
                    return;
                }

                try (CallableStatement stmt = conn.prepareCall("{CALL PA_actualizacion_Doctor(?, ?, ?, ?, ?, ?, ?)}")) {
                    stmt.setString(1, dniActual);
                    stmt.setString(2, nombre);
                    stmt.setString(3, apellido);
                    stmt.setString(4, codConsultorio);
                    stmt.setString(5, correo);
                    stmt.setString(6, telefono);
                    stmt.setInt(7, codEspecialidad);
                    stmt.execute();
                }

                JOptionPane.showMessageDialog(this, "Doctor actualizado correctamente.");
                cargarDatos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    // Eliminar Doctor
    public void eliminar() {
        int filaSeleccionada = tblDoctor.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String dni = modelDoctor.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el Doctor con DNI: " + dni + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try (Connection conn = Conexion.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                    return;
                }

                try (CallableStatement stmt = conn.prepareCall("{CALL PA_delete_Doctor(?)}")) {
                    stmt.setString(1, dni);
                    stmt.execute();
                }

                JOptionPane.showMessageDialog(this, "Doctor eliminado correctamente.");
                cargarDatos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    // Mostrar Doctores
    public void cargarDatos() {
        modelDoctor.setRowCount(0);
        try (Connection conn = Conexion.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                return;
            }

            try (CallableStatement stmt = conn.prepareCall("{CALL PA_ListarDoctores()}");
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    String dni = rs.getString("DNI");
                    String nombre = rs.getString("Nombre");
                    String apellido = rs.getString("Apellido");
                    String especialidad = rs.getString("Especialidad");
                    String codConsultorio = rs.getString("CodConsultorio");
                    String correo = rs.getString("Correo");
                    String telefono = rs.getString("Telefono");
                    modelDoctor.addRow(new Object[]{dni, nombre + " " + apellido, especialidad, codConsultorio, correo, telefono});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    // Método para crear ComboBox de especialidades
    private JComboBox<String> crearComboEspecialidades() {
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Seleccione una especialidad...");
        
        try (Connection conn = Conexion.getConnection()) {
            if (conn != null) {
                try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarEspecialidad()}");
                     ResultSet rs = stmt.executeQuery()) {
                    
                    while (rs.next()) {
                        String codigo = rs.getString("Codigo");
                        String nombre = rs.getString("Nombre Especialidad");
                        combo.addItem(codigo + " - " + nombre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar especialidades: " + e.getMessage());
        }
        
        combo.setSelectedIndex(0); // Seleccionar el primer item por defecto
        return combo;
    }

}
