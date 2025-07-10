package views.ventanasmantenimiento;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlDoctorMant extends JPanel {
    private JTable tblDoctor;
    private DefaultTableModel modelDoctor;
    private Connection conn = Conexion.getConnection();

    public pnlDoctorMant() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Doctores"));

        String[] columnas = {"DNI", "Nombre Completo", "Especialidad", "Consultorio", "Correo", "Teléfono"};
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

    public void agregar() {
    JTextField txtDNI = new JTextField();
    JTextField txtNombre = new JTextField();
    JTextField txtApellido = new JTextField();
    JComboBox<String> cbEspecialidades = new JComboBox<>();
    JComboBox<String> cbConsultorios = new JComboBox<>();
    JTextField txtCorreo = new JTextField();
    JTextField txtTelefono = new JTextField();

    cargarEspecialidades(cbEspecialidades);
    cargarConsultorios(cbConsultorios);

    Object[] mensaje = {
        "DNI:", txtDNI,
        "Nombre:", txtNombre,
        "Apellido:", txtApellido,
        "Especialidad:", cbEspecialidades,
        "Consultorio:", cbConsultorios,
        "Correo:", txtCorreo,
        "Teléfono:", txtTelefono
    };

    int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Doctor", JOptionPane.OK_CANCEL_OPTION);

    if (opcion == JOptionPane.OK_OPTION) {
        String dni = txtDNI.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String especialidadNombre = (String) cbEspecialidades.getSelectedItem();
        String consultorioNombre = (String) cbConsultorios.getSelectedItem();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || especialidadNombre == null
                || consultorioNombre == null || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            if (conn != null) {
                String codConsultorio = obtenerCodConsultorioPorNombre(consultorioNombre);
                int codEspecialidad = obtenerCodEspecialidad(especialidadNombre);

                if (codConsultorio == null || codEspecialidad == -1) {
                    JOptionPane.showMessageDialog(this, "Consultorio o especialidad no válidos.");
                    return;
                }

                CallableStatement stmt = conn.prepareCall("{CALL PA_insert_Doctor(?, ?, ?, ?, ?, ?, ?)}");
                stmt.setString(1, dni);
                stmt.setString(2, nombre);
                stmt.setString(3, apellido);
                stmt.setString(4, codConsultorio);
                stmt.setString(5, correo);
                stmt.setString(6, telefono);
                stmt.setInt(7, codEspecialidad); // Ahora se pasa al procedimiento
                stmt.execute();
                stmt.close();

                JOptionPane.showMessageDialog(this, "Doctor agregado correctamente.");
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
        String consultorioActual = modelDoctor.getValueAt(filaSeleccionada, 3).toString();
        String correoActual = modelDoctor.getValueAt(filaSeleccionada, 4).toString();
        String telefonoActual = modelDoctor.getValueAt(filaSeleccionada, 5).toString();

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtApellido = new JTextField(apellidoActual);
        JComboBox<String> cbEspecialidades = new JComboBox<>();
        JComboBox<String> cbConsultorios = new JComboBox<>();
        JTextField txtCorreo = new JTextField(correoActual);
        JTextField txtTelefono = new JTextField(telefonoActual);

        cargarEspecialidades(cbEspecialidades);
        cargarConsultorios(cbConsultorios);
        cbEspecialidades.setSelectedItem(especialidadActual);
        cbConsultorios.setSelectedItem(consultorioActual);

        Object[] mensaje = {
            "DNI (no editable): " + dniActual,
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Especialidad:", cbEspecialidades,
            "Consultorio:", cbConsultorios,
            "Correo:", txtCorreo,
            "Teléfono:", txtTelefono
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Doctor", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidadNombre = (String) cbEspecialidades.getSelectedItem();
            String consultorioNombre = (String) cbConsultorios.getSelectedItem();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || especialidadNombre == null
                    || consultorioNombre == null || correo.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                String codConsultorio = obtenerCodConsultorioPorNombre(consultorioNombre);
                int codEspecialidad = obtenerCodEspecialidad(especialidadNombre);
                if (codConsultorio == null || codEspecialidad == -1) return;

                CallableStatement stmt = conn.prepareCall("{CALL PA_actualizacion_Doctor(?, ?, ?, ?, ?, ?, ?)}");
                stmt.setString(1, dniActual);
                stmt.setString(2, nombre);
                stmt.setString(3, apellido);
                stmt.setString(4, codConsultorio);
                stmt.setString(5, correo);
                stmt.setString(6, telefono);
                stmt.setInt(7, codEspecialidad);
                stmt.execute();
                stmt.close();

                JOptionPane.showMessageDialog(this, "Doctor actualizado correctamente.");
                cargarDatos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    public void eliminar() {
        int filaSeleccionada = tblDoctor.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String dni = modelDoctor.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el Doctor con DNI: " + dni + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_delete_Doctor(?)}");
                    stmt.setString(1, dni);
                    stmt.execute();
                    stmt.close();

                    JOptionPane.showMessageDialog(this, "Doctor eliminado correctamente.");
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
        modelDoctor.setRowCount(0);
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_ListarDoctorConFiltro(?)}");
                stmt.setString(1, ""); // sin filtro
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String dni = rs.getString("DNI");
                    String nombre = rs.getString("Nombre");
                    String apellido = rs.getString("Apellido");
                    String especialidad = rs.getString("Especialidad");
                    String consultorio = rs.getString("Consultorio");
                    String correo = rs.getString("Correo");
                    String telefono = rs.getString("Telefono");
                    modelDoctor.addRow(new Object[]{dni, nombre + " " + apellido, especialidad, consultorio, correo, telefono});
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

    private void cargarConsultorios(JComboBox<String> combo) {
        try {
            CallableStatement stmt = conn.prepareCall("{CALL PA_ListarSoloConsultorio()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString("NomConst"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar consultorios: " + e.getMessage());
        }
    }


    
    private void cargarEspecialidades(JComboBox<String> combo) {
        try {
            CallableStatement stmt = conn.prepareCall("{CALL PA_ListarSoloEspecialidad()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString("Especialidad"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar especialidades: " + e.getMessage());
        }
    }

    private String obtenerCodConsultorioPorNombre(String nombreConsultorio) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT CodConst FROM Consultorio WHERE NomConst = ?")) {
            ps.setString(1, nombreConsultorio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CodConst");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int obtenerCodEspecialidad(String nombreEspecialidad) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT CodEspecia FROM Especialidad WHERE Especialidad = ?")) {
            ps.setString(1, nombreEspecialidad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("CodEspecia");
            } else {
                JOptionPane.showMessageDialog(this, "Especialidad no encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar la especialidad: " + e.getMessage());
        }
        return -1;
    }
}
