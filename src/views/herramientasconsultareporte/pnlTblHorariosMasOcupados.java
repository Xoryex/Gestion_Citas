package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblHorariosMasOcupados extends JPanel {

    private JTable tablaHorarios;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblHorariosMasOcupados() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = { "Estado Horario", "Total Citas" };

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

        tablaHorarios = new JTable(modelo);
        tablaHorarios.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaHorarios.getTableHeader().setResizingAllowed(false);
        tablaHorarios.getTableHeader().setReorderingAllowed(false);

        // Mejorar apariencia de la tabla
        tablaHorarios.setRowHeight(25);
        tablaHorarios.setSelectionBackground(new Color(184, 207, 229));
        tablaHorarios.setSelectionForeground(Color.BLACK);
        tablaHorarios.setGridColor(new Color(200, 200, 200));
        tablaHorarios.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaHorarios.getTableHeader().setForeground(Color.WHITE);
        tablaHorarios.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaHorarios);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Carga los datos desde un ResultSet
     * @param rs ResultSet con los horarios más ocupados
     */
    public void cargarDatos(ResultSet rs) {
        try {
            limpiarTabla();
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("Estado Horario");
                fila[1] = rs.getInt("Total Citas");
                modelo.addRow(fila);
            }
            modelo.fireTableDataChanged();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar horarios: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia todos los datos de la tabla
     */
    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public void cargarDatos(Object[][] datos) {
        limpiarTabla();
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }


    /**
     * @return número de filas en la tabla
     */
    public int getNumeroFilas() {
        return modelo.getRowCount();
    }

    /**
     * @return índice de la fila seleccionada, -1 si no hay selección
     */
    public int getFilaSeleccionada() {
        return tablaHorarios.getSelectedRow();
    }

    /**
     * @param fila    índice de la fila
     * @param columna índice de la columna
     * @return valor de la celda
     */
    public Object getValorCelda(int fila, int columna) {
        return modelo.getValueAt(fila, columna);
    }

    /**
     * @return descripción del horario seleccionado, o null si no hay selección
     */
    public String getHorarioSeleccionado() {
        int f = getFilaSeleccionada();
        return f != -1 ? (String) getValorCelda(f, 0) : null;
    }

    /**
     * Muestra mensaje cuando no hay datos
     */
    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = { "No hay datos disponibles", "" };
        modelo.addRow(fila);
    }

    /**
     * Refresca la vista de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    /**
     * Busca un horario por texto
     * @param texto texto a buscar en la columna Horario
     * @return índice de la fila encontrada, -1 si no existe
     */
    public int buscarHorarioPorTexto(String texto) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (texto.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    // Getters de componentes

    public JTable getTablaHorarios() {
        return tablaHorarios;
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
        return getTablaHorarios();
    }
}