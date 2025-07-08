package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblCita extends JPanel {

    private JTable tblCRCitas;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblCita() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = {
            "ID de Cita", "DNI del Paciente", "Paciente", "Consultorio",
            "Doctor", "Hora Inicio", "Hora Fin", "Atención", "Estado", "Recepcionista"
        };

        // Modelo de tabla sin datos iniciales
        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class
            };

            private final boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false, false, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnEditables[columnIndex];
            }
        };

        tblCRCitas = new JTable(modelo);
        tblCRCitas.setFont(new Font("Roboto", Font.PLAIN, 14));
        tblCRCitas.getTableHeader().setResizingAllowed(false);
        tblCRCitas.getTableHeader().setReorderingAllowed(false);
        
        // Mejorar apariencia de la tabla
        tblCRCitas.setRowHeight(25);
        tblCRCitas.setSelectionBackground(new Color(184, 207, 229));
        tblCRCitas.setSelectionForeground(Color.BLACK);
        tblCRCitas.setGridColor(new Color(200, 200, 200));
        tblCRCitas.getTableHeader().setBackground(new Color(70, 130, 180));
        tblCRCitas.getTableHeader().setForeground(Color.WHITE);
        tblCRCitas.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tblCRCitas);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        
        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Método para cargar datos desde un ResultSet
     * @param rs ResultSet con los datos de las citas
     */
    public void cargarDatos(ResultSet rs) {
        try {
            // Limpiar la tabla
            limpiarTabla();
            
            // Cargar datos del ResultSet
            while (rs.next()) {
                Object[] fila = new Object[10];
                fila[0] = rs.getString("id_cita");
                fila[1] = rs.getString("dni_paciente");
                fila[2] = rs.getString("nombre_paciente");
                fila[3] = rs.getString("consultorio");
                fila[4] = rs.getString("nombre_doctor");
                fila[5] = rs.getString("hora_inicio");
                fila[6] = rs.getString("hora_fin");
                fila[7] = rs.getString("fecha_atencion");
                fila[8] = rs.getString("estado");
                fila[9] = rs.getString("recepcionista");
                
                modelo.addRow(fila);
            }
            
            // Actualizar la vista
            modelo.fireTableDataChanged();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de citas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para limpiar todos los datos de la tabla
     */
    public void limpiarTabla() {
        // Remover todas las filas
        modelo.setRowCount(0);
    }

    /**
     * Método para obtener el número de filas en la tabla
     * @return número de filas
     */
    public int getNumeroFilas() {
        return modelo.getRowCount();
    }

    /**
     * Método para obtener la fila seleccionada
     * @return índice de la fila seleccionada, -1 si no hay selección
     */
    public int getFilaSeleccionada() {
        return tblCRCitas.getSelectedRow();
    }

    /**
     * Método para obtener el valor de una celda específica
     * @param fila índice de la fila
     * @param columna índice de la columna
     * @return valor de la celda
     */
    public Object getValorCelda(int fila, int columna) {
        return modelo.getValueAt(fila, columna);
    }

    /**
     * Método para obtener el ID de la cita seleccionada
     * @return ID de la cita seleccionada, null si no hay selección
     */
    public String getIdCitaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 0);
        }
        return null;
    }

    /**
     * Método para establecer un mensaje cuando no hay datos
     */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = {"No hay datos disponibles", "", "", "", "", "", "", "", "", ""};
        modelo.addRow(fila);
    }

    /**
     * Método para actualizar los datos de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    // Getters
    public JTable getTablaCitas() {
        return tblCRCitas;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JScrollPane getScrollTabla() {
        return scrollTabla;
    }
}