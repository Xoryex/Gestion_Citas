package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblDoctoresMasCitas extends JPanel {

    private JTable tblDoctores;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblDoctoresMasCitas() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        String[] columnas = { "Doctor", "Número de Citas" };
        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = {
                String.class, Integer.class
            };
            private final boolean[] columnEditables = {
                false, false
            };
            @Override public Class<?> getColumnClass(int col) {
                return columnTypes[col];
            }
            @Override public boolean isCellEditable(int row, int col) {
                return columnEditables[col];
            }
        };

        tblDoctores = new JTable(modelo);
        estiloTabla(tblDoctores);

        scrollTabla = new JScrollPane(tblDoctores);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.CENTER);
    }

    private void estiloTabla(JTable tbl) {
        tbl.setFont(new Font("Roboto", Font.PLAIN, 14));
        tbl.getTableHeader().setResizingAllowed(false);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setRowHeight(25);
        tbl.setSelectionBackground(new Color(184, 207, 229));
        tbl.setSelectionForeground(Color.BLACK);
        tbl.setGridColor(new Color(200, 200, 200));
        tbl.getTableHeader().setBackground(new Color(70, 130, 180));
        tbl.getTableHeader().setForeground(Color.WHITE);
        tbl.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
    }

    /** Carga los datos desde un ResultSet genérico */
    public void cargarDatos(ResultSet rs) {
        try {
            limpiarTabla();
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString(1);    // Nombre del doctor
                fila[1] = rs.getInt(2);       // Total de citas
                modelo.addRow(fila);
            }
            modelo.fireTableDataChanged();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar doctores: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public int getNumeroFilas() {
        return modelo.getRowCount();
    }

    public int getFilaSeleccionada() {
        return tblDoctores.getSelectedRow();
    }

    public Object getValorCelda(int fila, int col) {
        return modelo.getValueAt(fila, col);
    }

    public String getDoctorSeleccionado() {
        int f = getFilaSeleccionada();
        return f != -1 ? (String) getValorCelda(f, 0) : null;
    }

    // Getters de componentes si los necesitas
    public JTable getTablaDoctores() {
        return tblDoctores;
    }
    public DefaultTableModel getModelo() {
        return modelo;
    }
    public JScrollPane getScrollTabla() {
        return scrollTabla;
    }

    public JTable getTabla() {
    return tblDoctores;
}
}