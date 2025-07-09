package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblHorario extends JPanel {

    private JTable tablaHorario;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblHorario() {
        setLayout(new BorderLayout());
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = {
            "ID Horario", "Hora Inicio", "Hora Fin", "Día", 
            "Límite de Pacientes", "Estado del Horario", 
            "DNI del Doctor", "Nombre del Doctor", "Consultorio"
        };

        // Modelo vacío, sin datos por defecto
        modelo = new DefaultTableModel(columnas, 0) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, String.class, String.class, String.class,
                Integer.class, String.class, String.class, String.class, String.class
            };

            private final boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };

        tablaHorario = new JTable(modelo);
        tablaHorario.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaHorario.getTableHeader().setResizingAllowed(false);
        tablaHorario.getTableHeader().setReorderingAllowed(false);
        
        // Mejorar apariencia de la tabla
        tablaHorario.setRowHeight(25);
        tablaHorario.setSelectionBackground(new Color(184, 207, 229));
        tablaHorario.setSelectionForeground(Color.BLACK);
        tablaHorario.setGridColor(new Color(200, 200, 200));
        tablaHorario.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaHorario.getTableHeader().setForeground(Color.WHITE);
        tablaHorario.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaHorario);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        
        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Método para cargar datos desde un ResultSet
     * @param rs ResultSet con los datos de los horarios
     */
    public void cargarDatos(ResultSet rs) {
        try {
            // Limpiar la tabla
            limpiarTabla();
            
            // Cargar datos del ResultSet
            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getString("ID Horario");
                fila[1] = rs.getString("Hora Inicio");
                fila[2] = rs.getString("Hora Fin");
                fila[3] = rs.getString("Día");
                fila[4] = rs.getInt("Límite de Pacientes");
                fila[5] = rs.getString("Estado del Horario");
                fila[6] = rs.getString("DNI del Doctor");
                fila[7] = rs.getString("Nombre del Doctor");
                fila[8] = rs.getString("Consultorio");
                
                modelo.addRow(fila);
            }
            
            // Actualizar la vista
            modelo.fireTableDataChanged();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de horarios: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para limpiar todos los datos de la tabla
     */
    public void limpiarTabla() {
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
        return tablaHorario.getSelectedRow();
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
     * Método para obtener el ID del horario seleccionado
     * @return ID del horario seleccionado, null si no hay selección
     */
    public String getIdHorarioSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 0);
        }
        return null;
    }

    /**
     * Método para obtener el DNI del doctor del horario seleccionado
     * @return DNI del doctor, null si no hay selección
     */
    public String getDniDoctorSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 6);
        }
        return null;
    }

    /**
     * Método para establecer un mensaje cuando no hay datos
     */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = {"No hay datos disponibles", "", "", "", 0, "", "", "", ""};
        modelo.addRow(fila);
    }

    /**
     * Método para actualizar los datos de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    /**
     * Método para cargar datos desde array (mantengo por compatibilidad)
     * @param datos array bidimensional con los datos
     */
    public void cargarDatos(Object[][] datos) {
        limpiarTabla();
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }

    // Getters
    public JTable getTablaHorario() {
        return tablaHorario;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public DefaultTableModel getModeloTabla() {
        return modelo;
    }

    public JScrollPane getScrollTabla() {
        return scrollTabla;
    }
    public JTable getTabla() {
        return getTablaHorario();
    }
}