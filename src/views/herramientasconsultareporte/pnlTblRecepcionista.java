package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblRecepcionista extends JPanel {
    private JTable tablaRecepcionistas;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblRecepcionista() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = {
            "DNI", "Nombre", "Apellido", "Teléfono",
            "Citas Programadas", "Citas Anuladas", "Admin"
        };

        // Modelo de tabla sin datos iniciales
        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, String.class, String.class, String.class,
                Integer.class, Integer.class, String.class
            };

            private final boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false
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

        tablaRecepcionistas = new JTable(modelo);
        tablaRecepcionistas.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaRecepcionistas.getTableHeader().setResizingAllowed(false);
        tablaRecepcionistas.getTableHeader().setReorderingAllowed(false);
        
        // Mejorar apariencia de la tabla
        tablaRecepcionistas.setRowHeight(25);
        tablaRecepcionistas.setSelectionBackground(new Color(184, 207, 229));
        tablaRecepcionistas.setSelectionForeground(Color.BLACK);
        tablaRecepcionistas.setGridColor(new Color(200, 200, 200));
        tablaRecepcionistas.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaRecepcionistas.getTableHeader().setForeground(Color.WHITE);
        tablaRecepcionistas.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaRecepcionistas);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        
        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Método para cargar datos desde un ResultSet
     * @param rs ResultSet con los datos de las recepcionistas
     */
    public void cargarDatos(ResultSet rs) {
        try {
            // Limpiar la tabla
            limpiarTabla();
            
            // Cargar datos del ResultSet
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("DNI");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Apellido");
                fila[3] = rs.getString("Teléfono");
                fila[4] = rs.getInt("Citas Programadas");
                fila[5] = rs.getInt("Citas Anuladas");
                fila[6] = rs.getString("Admin");
                
                modelo.addRow(fila);
            }
            
            // Actualizar la vista
            modelo.fireTableDataChanged();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de recepcionistas: " + e.getMessage(), 
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
        return tablaRecepcionistas.getSelectedRow();
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
     * Método para obtener el DNI de la recepcionista seleccionada
     * @return DNI de la recepcionista seleccionada, null si no hay selección
     */
    public String getDniRecepcionistaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 0);
        }
        return null;
    }

    /**
     * Método para obtener el nombre de la recepcionista seleccionada
     * @return nombre de la recepcionista seleccionada, null si no hay selección
     */
    public String getNombreRecepcionistaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 1);
        }
        return null;
    }

    /**
     * Método para obtener el apellido de la recepcionista seleccionada
     * @return apellido de la recepcionista seleccionada, null si no hay selección
     */
    public String getApellidoRecepcionistaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 2);
        }
        return null;
    }

    /**
     * Método para obtener el nombre completo de la recepcionista seleccionada
     * @return nombre completo de la recepcionista seleccionada, null si no hay selección
     */
    public String getNombreCompletoRecepcionistaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            String nombre = (String) getValorCelda(filaSeleccionada, 1);
            String apellido = (String) getValorCelda(filaSeleccionada, 2);
            return nombre + " " + apellido;
        }
        return null;
    }

    /**
     * Método para obtener el teléfono de la recepcionista seleccionada
     * @return teléfono de la recepcionista seleccionada, null si no hay selección
     */
    public String getTelefonoRecepcionistaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 3);
        }
        return null;
    }

    /**
     * Método para verificar si la recepcionista seleccionada es admin
     * @return true si es admin, false si no lo es o no hay selección
     */
    public boolean esAdminRecepcionistaSeleccionada() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            String admin = (String) getValorCelda(filaSeleccionada, 6);
            return "Sí".equals(admin) || "Si".equals(admin) || "YES".equals(admin) || "1".equals(admin);
        }
        return false;
    }

    /**
     * Método para establecer un mensaje cuando no hay datos
     */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = {"No hay datos disponibles", "", "", "", "", "", ""};
        modelo.addRow(fila);
    }

    /**
     * Método para actualizar los datos de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    /**
     * Método para insertar datos fácilmente
     * @param fila Array de objetos con los datos de la recepcionista
     */
    public void agregarRecepcionista(Object[] fila) {
        modelo.addRow(fila);
    }

    /**
     * Método para buscar una recepcionista por DNI
     * @param dni DNI de la recepcionista a buscar
     * @return índice de la fila si se encuentra, -1 si no se encuentra
     */
    public int buscarRecepcionistaPorDni(String dni) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (dni.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método para buscar una recepcionista por nombre
     * @param nombre nombre de la recepcionista a buscar
     * @return índice de la fila si se encuentra, -1 si no se encuentra
     */
    public int buscarRecepcionistaPorNombre(String nombre) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nombreTabla = (String) modelo.getValueAt(i, 1);
            if (nombreTabla != null && nombreTabla.toLowerCase().contains(nombre.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    // Getters
    public JTable getTablaRecepcionistas() {
        return tablaRecepcionistas;
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
        return getTablaRecepcionistas();
    }
}