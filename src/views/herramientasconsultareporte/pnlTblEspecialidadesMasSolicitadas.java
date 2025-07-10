package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblEspecialidadesMasSolicitadas extends JPanel {

    private JTable tablaEspecialidades;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblEspecialidadesMasSolicitadas() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = { "Especialidad", "Total Solicitudes" };

        // Modelo de tabla sin datos iniciales
        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, Integer.class
            };
            private final boolean[] columnEditables = new boolean[] {
                false, false
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

        tablaEspecialidades = new JTable(modelo);
        tablaEspecialidades.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaEspecialidades.getTableHeader().setResizingAllowed(false);
        tablaEspecialidades.getTableHeader().setReorderingAllowed(false);

        // Mejorar apariencia de la tabla
        tablaEspecialidades.setRowHeight(25);
        tablaEspecialidades.setSelectionBackground(new Color(184, 207, 229));
        tablaEspecialidades.setSelectionForeground(Color.BLACK);
        tablaEspecialidades.setGridColor(new Color(200, 200, 200));
        tablaEspecialidades.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaEspecialidades.getTableHeader().setForeground(Color.WHITE);
        tablaEspecialidades.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaEspecialidades);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Método para cargar datos desde un ResultSet
     * @param rs ResultSet con los datos de especialidades
     */
    public void cargarDatos(ResultSet rs) {
        try {
            // Limpiar la tabla
            limpiarTabla();

            // Cargar datos del ResultSet
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("Especialidad");
                fila[1] = rs.getInt("Total Solicitudes");
                modelo.addRow(fila);
            }

            modelo.fireTableDataChanged();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar datos de especialidades: " + e.getMessage(),
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
        return tablaEspecialidades.getSelectedRow();
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
     * Método para obtener la especialidad seleccionada
     * @return nombre de la especialidad seleccionada, null si no hay selección
     */
    public String getEspecialidadSeleccionada() {
        int fila = getFilaSeleccionada();
        return fila != -1 ? (String) getValorCelda(fila, 0) : null;
    }

    /**
     * Método para establecer un mensaje cuando no hay datos
     */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = { "No hay datos disponibles", "" };
        modelo.addRow(fila);
    }

    /**
     * Método para refrescar la vista de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    public void cargarDatos(Object[][] datos) {
        limpiarTabla();
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }


    /**
     * Método para buscar una especialidad por nombre
     * @param nombre nombre de la especialidad a buscar
     * @return índice de la fila si se encuentra, -1 si no se encuentra
     */
    public int buscarEspecialidadPorNombre(String nombre) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (nombre.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    // Getters de componentes

    public JTable getTablaEspecialidades() {
        return tablaEspecialidades;
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
        return getTablaEspecialidades();
    }
}