package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblDoctor extends JPanel {

    private JTable tablaDoctores;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblDoctor() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = {
            "DNI", "Nombre", "Especialidad", "Estado",
            "Citas Pendientes", "Citas Atendidas", "Consultorio", "Correo", "Teléfono"
        };

        // Modelo de tabla sin datos iniciales
        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, String.class, String.class, String.class,
                Integer.class, Integer.class, String.class, String.class, String.class
            };

            private final boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false, false, false
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

        tablaDoctores = new JTable(modelo);
        tablaDoctores.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaDoctores.getTableHeader().setResizingAllowed(false);
        tablaDoctores.getTableHeader().setReorderingAllowed(false);
        
        // Mejorar apariencia de la tabla
        tablaDoctores.setRowHeight(25);
        tablaDoctores.setSelectionBackground(new Color(184, 207, 229));
        tablaDoctores.setSelectionForeground(Color.BLACK);
        tablaDoctores.setGridColor(new Color(200, 200, 200));
        tablaDoctores.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaDoctores.getTableHeader().setForeground(Color.WHITE);
        tablaDoctores.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaDoctores);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        
        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Método para cargar datos desde un ResultSet
     * @param rs ResultSet con los datos de los doctores
     */
    public void cargarDatos(ResultSet rs) {
        try {
            // Limpiar la tabla
            limpiarTabla();
            
            // Cargar datos del ResultSet
            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getString("DNI");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Especialidad");
                fila[3] = rs.getString("Estado");
                fila[4] = rs.getInt("citas_pendientes");
                fila[5] = rs.getInt("citas_atendidas");
                fila[6] = rs.getString("consultorio");
                fila[7] = rs.getString("correo");
                fila[8] = rs.getString("telefono");
                
                modelo.addRow(fila);
            }
            
            // Actualizar la vista
            modelo.fireTableDataChanged();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de doctores: " + e.getMessage(), 
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
        return tablaDoctores.getSelectedRow();
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
     * Método para obtener el DNI del doctor seleccionado
     * @return DNI del doctor seleccionado, null si no hay selección
     */
    public String getDniDoctorSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 0);
        }
        return null;
    }

    /**
     * Método para obtener el nombre del doctor seleccionado
     * @return nombre del doctor seleccionado, null si no hay selección
     */
    public String getNombreDoctorSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 1);
        }
        return null;
    }

    /**
     * Método para establecer un mensaje cuando no hay datos
     */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = {"No hay datos disponibles", "", "", "", "", "", "", "", ""};
        modelo.addRow(fila);
    }

    /**
     * Método para actualizar los datos de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

  

    /**
     * Método para buscar un doctor por DNI
     * @param dni DNI del doctor a buscar
     * @return índice de la fila si se encuentra, -1 si no se encuentra
     */
    public int buscarDoctorPorDni(String dni) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (dni.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    // Getters originales y nuevos
    public JTable getTablaDoctores() {
        return tablaDoctores;
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
}