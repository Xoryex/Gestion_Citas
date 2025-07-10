package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblResumenCitasEstado extends JPanel {

    private JTable tablaResumen;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblResumenCitasEstado() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = { "Estado", "Total Citas" };

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

        tablaResumen = new JTable(modelo);
        tablaResumen.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaResumen.getTableHeader().setResizingAllowed(false);
        tablaResumen.getTableHeader().setReorderingAllowed(false);

        // Mejorar apariencia de la tabla
        tablaResumen.setRowHeight(25);
        tablaResumen.setSelectionBackground(new Color(184, 207, 229));
        tablaResumen.setSelectionForeground(Color.BLACK);
        tablaResumen.setGridColor(new Color(200, 200, 200));
        tablaResumen.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaResumen.getTableHeader().setForeground(Color.WHITE);
        tablaResumen.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaResumen);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Carga los datos desde un ResultSet
     * @param rs ResultSet con el resumen de citas por estado
     */
    public void cargarDatos(ResultSet rs) {
        try {
            limpiarTabla();
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("Estado");
                fila[1] = rs.getInt("Total Citas");
                modelo.addRow(fila);
            }
            modelo.fireTableDataChanged();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar resumen de estados: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Limpia todos los datos de la tabla */
    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    /** @return número de filas en la tabla */
    public int getNumeroFilas() {
        return modelo.getRowCount();
    }

    /** @return índice de la fila seleccionada, o -1 si no hay selección */
    public int getFilaSeleccionada() {
        return tablaResumen.getSelectedRow();
    }

    /**
     * @param fila índice de la fila
     * @param col  índice de la columna
     * @return valor de la celda
     */
    public Object getValorCelda(int fila, int col) {
        return modelo.getValueAt(fila, col);
    }

    /**
     * @return estado seleccionado, o null si no hay selección
     */
    public String getEstadoSeleccionado() {
        int f = getFilaSeleccionada();
        return f != -1 ? (String) getValorCelda(f, 0) : null;
    }

    /** Muestra mensaje cuando no hay datos en la tabla */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = { "No hay datos disponibles", "" };
        modelo.addRow(fila);
    }

    /** Refresca la vista de la tabla */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    /**
     * Busca un estado por nombre
     * @param estado texto a buscar
     * @return índice de la fila encontrada, o -1 si no existe
     */
    public int buscarEstadoPorNombre(String estado) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (estado.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    // Getters de componentes

    public JTable getTablaResumen() {
        return tablaResumen;
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
        return getTablaResumen();
    }
}