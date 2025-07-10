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
        String[] columnas = {
            "DNI", "Nombre", "Especialidad", "Estado",
            "Citas Pendientes", "Citas Atendidas", "Consultorio", "Correo", "Teléfono"
        };

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

    public void cargarDatos(ResultSet rs) {
        try {
            limpiarTabla();

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getString("DNI");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Especialidad");
                fila[3] = rs.getString("Estado");
                fila[4] = rs.getInt("CitasPendientes");
                fila[5] = rs.getInt("CitasAtendidas");
                fila[6] = rs.getString("Consultorio");
                fila[7] = rs.getString("Correo");
                fila[8] = rs.getString("Telefono"); // ← corregido aquí

                modelo.addRow(fila);
            }

            modelo.fireTableDataChanged();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar datos de doctores: " + e.getMessage(),
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
        return tablaDoctores.getSelectedRow();
    }

    public Object getValorCelda(int fila, int columna) {
        return modelo.getValueAt(fila, columna);
    }

    public String getDniDoctorSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 0);
        }
        return null;
    }

    public String getNombreDoctorSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 1);
        }
        return null;
    }

    public void mostrarMensajeSinDatos() {
        limpiarTabla();
        Object[] fila = {"No hay datos disponibles", "", "", "", "", "", "", "", ""};
        modelo.addRow(fila);
    }

    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    public int buscarDoctorPorDni(String dni) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (dni.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

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

    public JTable getTabla() {
        return getTablaDoctores();
    }
}
