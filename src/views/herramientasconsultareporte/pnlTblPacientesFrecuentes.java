package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblPacientesFrecuentes extends JPanel {

    private JTable tablaPacientes;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblPacientesFrecuentes() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = { "Nombre Paciente", "Total Visitas" };

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

        tablaPacientes = new JTable(modelo);
        tablaPacientes.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaPacientes.getTableHeader().setResizingAllowed(false);
        tablaPacientes.getTableHeader().setReorderingAllowed(false);

        // Mejorar apariencia de la tabla
        tablaPacientes.setRowHeight(25);
        tablaPacientes.setSelectionBackground(new Color(184, 207, 229));
        tablaPacientes.setSelectionForeground(Color.BLACK);
        tablaPacientes.setGridColor(new Color(200, 200, 200));
        tablaPacientes.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaPacientes.getTableHeader().setForeground(Color.WHITE);
        tablaPacientes.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));

        scrollTabla = new JScrollPane(tablaPacientes);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Carga los datos desde un ResultSet
     * @param rs ResultSet con los pacientes frecuentes
     */
    public void cargarDatos(ResultSet rs) {
        try {
            limpiarTabla();
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("Nombre Paciente");
                fila[1] = rs.getInt("Total Visitas");
                modelo.addRow(fila);
            }
            modelo.fireTableDataChanged();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar pacientes frecuentes: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia todos los datos de la tabla
     */
    public void limpiarTabla() {
        modelo.setRowCount(0);
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
        return tablaPacientes.getSelectedRow();
    }

    /**
     * Obtiene el valor de una celda
     * @param fila índice de la fila
     * @param columna índice de la columna
     * @return valor de la celda
     */
    public Object getValorCelda(int fila, int columna) {
        return modelo.getValueAt(fila, columna);
    }

    /**
     * @return nombre del paciente seleccionado, o null si no hay selección
     */
    public String getPacienteSeleccionado() {
        int f = getFilaSeleccionada();
        return f != -1 ? (String) getValorCelda(f, 0) : null;
    }

    /**
     * Muestra mensaje cuando no hay datos en la tabla
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
     * Busca un paciente por nombre
     * @param nombre nombre a buscar
     * @return índice de la fila encontrada, o -1 si no existe
     */
    public int buscarPacientePorNombre(String nombre) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (nombre.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    // Getters de componentes

    public JTable getTablaPacientes() {
        return tablaPacientes;
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
        return getTablaPacientes();
    }
}