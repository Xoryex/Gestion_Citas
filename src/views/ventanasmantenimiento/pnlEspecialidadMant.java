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

    // Método para insertar una especialidad
    // Método para insertar una especialidad (versión modificada para recibir el código desde Java)
public void agregar() {
    JTextField txtCodigo = new JTextField();
    JTextField txtNombre = new JTextField();

    Object[] mensaje = {
        "Código (número):", txtCodigo,
        "Especialidad:", txtNombre
    };

    int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Especialidad", JOptionPane.OK_CANCEL_OPTION);

    if (opcion == JOptionPane.OK_OPTION) {
        String codEspeciaStr = txtCodigo.getText().trim();
        String especialidad = txtNombre.getText().trim();

        if (codEspeciaStr.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código y la especialidad no pueden estar vacíos.");
            return;
        }

        try {
            int codEspecia = Integer.parseInt(codEspeciaStr);

            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarEspecialidad(?, ?)}");
                stmt.setInt(1, codEspecia);       // Código ingresado por el usuario
                stmt.setString(2, especialidad);   // Especialidad ingresada

                stmt.execute();

                JOptionPane.showMessageDialog(this, "Especialidad agregada correctamente.");
                stmt.close();
                cargarDatos(); // Recargar datos después de agregar
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número entero.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
        }
    }
}


    // Método para actualizar una especialidad
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
                    stmt.setInt(1, Integer.parseInt(codEspecia)); // CodEspecia
                    stmt.setString(2, nuevaEspecialidad);          // Especialidad

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
                    String codEspecia = rs.getString("Codigo");
                    String especialidad = rs.getString("Nombre Especialidad");

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
