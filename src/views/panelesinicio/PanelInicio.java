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
        JLabel lblTituloInicio = new JLabel("Bienvenido, estas son las citas pendientes:", SwingConstants.CENTER);
        lblTituloInicio.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTituloInicio, BorderLayout.NORTH);

        // Configurar tabla con las columnas correctas
        String[] columnas = {"ID", "Paciente", "Apellido", "Fecha", "Hora Inicio", "Hora Fin", "Estado"};
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
            // Llamar al procedimiento almacenado corregido
            stmt = Conexion.getConnection().prepareCall("{call PA_CRUD_MostrarCitasPendientes}");
            rs = stmt.executeQuery();

            // Procesar resultados con los nombres correctos de las columnas
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("ID");
                fila[1] = rs.getString("NombrePaciente");
                fila[2] = rs.getString("ApellidoPaciente");
                fila[3] = rs.getDate("Fecha");
                fila[4] = rs.getTime("HoraInicio");
                fila[5] = rs.getTime("HoraFin");
                fila[6] = rs.getInt("Estado") == 1 ? "Pendiente" : "Cancelada";
                
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
