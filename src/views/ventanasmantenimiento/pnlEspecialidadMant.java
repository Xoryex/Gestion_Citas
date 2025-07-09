package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlEspecialidadMant extends JPanel {
    private JTable tblEspecialidad;
    private DefaultTableModel modelEspecialidad;

    public pnlEspecialidadMant() {
        initComponents();
        cargarDatos();
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
        // Crear ComboBox con especialidades existentes
        JComboBox<String> cmbEspecialidadExistente = new JComboBox<>();
        cmbEspecialidadExistente.addItem("Seleccione una especialidad...");
        
        // Cargar especialidades existentes en el ComboBox
        try (Connection conn = Conexion.getConnection()) {
            if (conn != null) {
                try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarEspecialidad()}");
                     ResultSet rs = stmt.executeQuery()) {
                    
                    while (rs.next()) {
                        String codigo = rs.getString("Codigo");
                        String nombre = rs.getString("Nombre Especialidad");
                        cmbEspecialidadExistente.addItem(codigo + " - " + nombre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar especialidades: " + e.getMessage());
            return;
        }

        // Si no hay especialidades existentes, mostrar mensaje
        if (cmbEspecialidadExistente.getItemCount() <= 1) {
            JOptionPane.showMessageDialog(this, "No hay especialidades registradas en el sistema.");
            return;
        }

        cmbEspecialidadExistente.setPreferredSize(new Dimension(300, 25));

        Object[] mensaje = {
            "Especialidad existente:", cmbEspecialidadExistente
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Seleccionar Especialidad", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String especialidadSeleccionada = (String) cmbEspecialidadExistente.getSelectedItem();

            if (especialidadSeleccionada == null || especialidadSeleccionada.startsWith("Seleccione")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una especialidad.");
                return;
            }

            // Extraer código y nombre de la selección
            String[] partes = especialidadSeleccionada.split(" - ", 2);
            if (partes.length != 2) {
                JOptionPane.showMessageDialog(this, "Error en el formato de la especialidad seleccionada.");
                return;
            }

            String codigoSeleccionado = partes[0];
            String nombreSeleccionado = partes[1];

            // Mostrar información de la especialidad seleccionada
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Confirma la selección de la especialidad?\n\n" +
                "Código: " + codigoSeleccionado + "\n" +
                "Nombre: " + nombreSeleccionado,
                "Confirmar Selección", 
                JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, 
                    "Especialidad seleccionada correctamente:\n\n" +
                    "Código: " + codigoSeleccionado + "\n" +
                    "Especialidad: " + nombreSeleccionado);
                
                // Aquí podrías agregar lógica adicional si necesitas hacer algo con la especialidad seleccionada
                // Por ejemplo, asignarla a un doctor, etc.
            }
        }
    }

    // Método para generar el próximo código de especialidad disponible
    private String generarProximoCodigo(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT MAX(CAST(CodEspecia AS INT)) FROM Especialidad WHERE ISNUMERIC(CodEspecia) = 1");
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                int maxCodigo = rs.getInt(1);
                return String.format("%03d", maxCodigo + 1); // Formato con 3 dígitos: 001, 002, etc.
            } else {
                return "001"; // Primer código si no hay registros
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para verificar si una especialidad ya existe
    private boolean especialidadYaExiste(Connection conn, String nombreEspecialidad) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Especialidad WHERE UPPER(Especialidad) = UPPER(?)")) {
            stmt.setString(1, nombreEspecialidad);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

            try (Connection conn = Conexion.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
                    return;
                }

                // Verificar si ya existe otra especialidad con el mismo nombre
                if (especialidadYaExiste(conn, nuevaEspecialidad) && !nuevaEspecialidad.equalsIgnoreCase(especialidadActual)) {
                    JOptionPane.showMessageDialog(this, "Ya existe una especialidad con ese nombre.");
                    return;
                }

                try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarEspecialidad(?, ?)}")) {
                    stmt.setString(1, codEspecia);         // CodEspecia
                    stmt.setString(2, nuevaEspecialidad);  // Especialidad
                    stmt.execute();
                }

                JOptionPane.showMessageDialog(this, "Especialidad actualizada correctamente.");
                cargarDatos();
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
        String nombreEspecialidad = modelEspecialidad.getValueAt(filaSeleccionada, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar la especialidad: " + nombreEspecialidad + " (Código: " + codEspecia + ")?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try (Connection conn = Conexion.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                    return;
                }

                try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarEspecialidad(?)}")) {
                    stmt.setString(1, codEspecia);
                    stmt.execute();
                }

                JOptionPane.showMessageDialog(this, "Especialidad eliminada correctamente.");
                cargarDatos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public void cargarDatos() {
        modelEspecialidad.setRowCount(0); // Limpiar la tabla
        try (Connection conn = Conexion.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                return;
            }

            try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarEspecialidad()}");
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    String codEspecia = rs.getString("Codigo");
                    String especialidad = rs.getString("Nombre Especialidad");
                    modelEspecialidad.addRow(new Object[]{codEspecia, especialidad});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    // Método alternativo para seleccionar una especialidad existente (puede ser útil en otros contextos)
    public void seleccionarEspecialidadExistente() {
        try (Connection conn = Conexion.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                return;
            }

            // Obtener todas las especialidades existentes
            java.util.List<String> especialidades = new java.util.ArrayList<>();
            java.util.Map<String, String> mapaCodigos = new java.util.HashMap<>();
            
            try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarEspecialidad()}");
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    String codigo = rs.getString("Codigo");
                    String nombre = rs.getString("Nombre Especialidad");
                    String item = codigo + " - " + nombre;
                    especialidades.add(item);
                    mapaCodigos.put(item, codigo);
                }
            }

            if (especialidades.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay especialidades registradas.");
                return;
            }

            // Crear ComboBox con las especialidades
            JComboBox<String> cmbEspecialidades = new JComboBox<>(especialidades.toArray(new String[0]));
            cmbEspecialidades.setPreferredSize(new Dimension(300, 25));

            Object[] mensaje = {
                "Seleccionar Especialidad:", cmbEspecialidades
            };

            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Seleccionar Especialidad", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {
                String seleccion = (String) cmbEspecialidades.getSelectedItem();
                if (seleccion != null) {
                    String codigoSeleccionado = mapaCodigos.get(seleccion);
                    JOptionPane.showMessageDialog(this, "Especialidad seleccionada:\n" + seleccion + "\nCódigo: " + codigoSeleccionado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar especialidades: " + e.getMessage());
        }
    }

    // Método para obtener todas las especialidades en formato ComboBox (útil para otros paneles)
    public JComboBox<String> crearComboEspecialidades() {
        JComboBox<String> combo = new JComboBox<>();
        
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
        }
        
        return combo;
    }

    // Método para agregar una nueva especialidad (crear nueva)
    public void agregarNuevaEspecialidad() {
        JTextField txtNombre = new JTextField();
        JLabel lblCodigoGenerado = new JLabel("Se generará automáticamente");
        lblCodigoGenerado.setFont(new Font("Arial", Font.ITALIC, 12));
        lblCodigoGenerado.setForeground(Color.GRAY);

        Object[] mensaje = {
            "Código:", lblCodigoGenerado,
            "Especialidad:", txtNombre
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Nueva Especialidad", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String especialidad = txtNombre.getText().trim();

            if (especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de la especialidad es obligatorio.");
                return;
            }

            try (Connection conn = Conexion.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                    return;
                }

                // Generar el próximo código disponible
                String nuevoCodigo = generarProximoCodigo(conn);
                if (nuevoCodigo == null) {
                    JOptionPane.showMessageDialog(this, "Error al generar el código de especialidad.");
                    return;
                }

                // Verificar si la especialidad ya existe
                if (especialidadYaExiste(conn, especialidad)) {
                    JOptionPane.showMessageDialog(this, "Ya existe una especialidad con ese nombre.");
                    return;
                }

                try (CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarEspecialidad(?, ?)}")) {
                    stmt.setString(1, nuevoCodigo);      // CodEspecia
                    stmt.setString(2, especialidad);     // Especialidad
                    stmt.execute();
                }

                JOptionPane.showMessageDialog(this, "Especialidad agregada correctamente.\nCódigo asignado: " + nuevoCodigo);
                cargarDatos(); // Recargar datos después de agregar

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
            }
        }
    }
}
