package views.panelesinicio;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class PanelInicio extends JPanel {
    private JTable tablaInicio;
    private DefaultTableModel modeloTabla;
    
    public PanelInicio() {
        setLayout(new BorderLayout());

        // Título
        JLabel lblTituloInicio = new JLabel("Bienvenido, estas son las citas de hoy:", SwingConstants.CENTER);
        lblTituloInicio.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTituloInicio, BorderLayout.NORTH);

        // Configurar tabla
        String[] columnas = {"ID", "Paciente", "Fecha", "Hora", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaInicio = new JTable(modeloTabla);
        tablaInicio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaInicio.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollTabla = new JScrollPane(tablaInicio);
        add(scrollTabla, BorderLayout.CENTER);

        // Panel inferior con botón de actualizar
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnActualizar = new JButton("Actualizar Citas");
        btnActualizar.addActionListener(e -> cargarCitasPendientes());
        panelBotones.add(btnActualizar);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarCitasPendientes();
    }

    private void cargarCitasPendientes() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
    

            // Llamar al procedimiento almacenado
            stmt = Conexion.getConnection().prepareCall("{call PA_CRUD_CitasPendientes}");
            rs = stmt.executeQuery();

            // Procesar resultados
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("ID"); // Ajustar según el nombre real de la columna
                fila[1] = rs.getString("Paciente"); // Ajustar según el nombre real de la columna
                fila[2] = rs.getDate("Fecha"); // Ajustar según el nombre real de la columna
                fila[3] = rs.getTime("Hora"); // Ajustar según el nombre real de la columna
                fila[4] = rs.getString("Estado"); // Ajustar según el nombre real de la columna
                
                modeloTabla.addRow(fila);
            }
            
            // Mostrar mensaje si no hay datos
            if (modeloTabla.getRowCount() == 0) {
                mostrarMensaje("No hay citas pendientes para mostrar");
            }
            
        } catch (SQLException e) {
            mostrarError("Error al cargar las citas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // No cerrar la conexión aquí si es singleton
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }


}