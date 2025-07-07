package ventanasmantenimiento;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.utils.Conexion;

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
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL CRU_InsertarEspecialidad(?, ?)}");
                    stmt.setString(1, codigo);
                    stmt.setString(2, nombre);

                    stmt.execute();

                    // Insertar también en la tabla visual
                    modelEspecialidad.addRow(new Object[]{codigo, nombre});

                    JOptionPane.showMessageDialog(this, "Especialidad agregada correctamente.");
                    stmt.close();
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
        JOptionPane.showMessageDialog(this, "Función actualizar aún no implementada.");
    }

    public void eliminar() {
        JOptionPane.showMessageDialog(this, "Función eliminar aún no implementada.");
    }

    public void agregarDatosEjemplo() {

    }
}
