package views.herramientasconsultareporte;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblPaciente extends JPanel {

    private JTable tablaPacientes;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;

    public pnlTblPaciente() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = {
            "DNI", "Nombre", "Teléfono", "Género", "Citas Asistidas", "Citas Perdidas",
            "Email", "Fecha de Nacimiento", "Dirección", "Ocupación", "Grupo Sanguíneo",
            "Procedencia", "Estado Civil", "Grupo Étnico", "Centro de Trabajo",
            "Grado de Instrucción", "Hijos"
        };

        // Modelo de tabla sin datos iniciales
        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, String.class, String.class, String.class, Integer.class, Integer.class,
                String.class, String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, Integer.class
            };

            private final boolean[] columnEditables = new boolean[columnas.length];

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
     * Método para cargar datos desde un ResultSet
     * @param rs ResultSet con los datos de los pacientes
     */
    public void cargarDatos(ResultSet rs) {
        try {
            // Limpiar la tabla
            limpiarTabla();
            
            // Cargar datos del ResultSet
            while (rs.next()) {
                Object[] fila = new Object[17];
                fila[0] = rs.getString("DNI");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Teléfono");
                fila[3] = rs.getString("Género");
                fila[4] = rs.getInt("Citas Asistidas");
                fila[5] = rs.getInt("Citas Perdidas");
                fila[6] = rs.getString("Email");
                fila[7] = rs.getString("Fecha de Nacimiento");
                fila[8] = rs.getString("Dirección");
                fila[9] = rs.getString("Ocupación");
                fila[10] = rs.getString("Grupo Sanguíneo");
                fila[11] = rs.getString("Procedencia");
                fila[12] = rs.getString("Estado Civil");
                fila[13] = rs.getString("Grupo Étnico");
                fila[14] = rs.getString("Centro de Trabajo");
                fila[15] = rs.getString("Grado de Instrucción");
                fila[16] = rs.getInt("Hijos");
                
                modelo.addRow(fila);
            }
            
            // Actualizar la vista
            modelo.fireTableDataChanged();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos de pacientes: " + e.getMessage(), 
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
        return tablaPacientes.getSelectedRow();
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
     * Método para obtener el DNI del paciente seleccionado
     * @return DNI del paciente seleccionado, null si no hay selección
     */
    public String getDniPacienteSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 0);
        }
        return null;
    }

    /**
     * Método para obtener el nombre del paciente seleccionado
     * @return nombre del paciente seleccionado, null si no hay selección
     */
    public String getNombrePacienteSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 1);
        }
        return null;
    }

    /**
     * Método para obtener el teléfono del paciente seleccionado
     * @return teléfono del paciente seleccionado, null si no hay selección
     */
    public String getTelefonoPacienteSeleccionado() {
        int filaSeleccionada = getFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) getValorCelda(filaSeleccionada, 2);
        }
        return null;
    }

    /**
     * Método para obtener el email del paciente seleccionado
     * @return email del paciente seleccionado, null si no hay selección
     */
    public String getEmailPacienteSeleccionado() {
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
        Object[] fila = {"No hay datos disponibles", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        modelo.addRow(fila);
    }

    /**
     * Método para actualizar los datos de la tabla
     */
    public void actualizarTabla() {
        modelo.fireTableDataChanged();
    }

    /**
     * Método para buscar un paciente por DNI
     * @param dni DNI del paciente a buscar
     * @return índice de la fila si se encuentra, -1 si no se encuentra
     */
    public int buscarPacientePorDni(String dni) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (dni.equals(modelo.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método para buscar un paciente por nombre
     * @param nombre nombre del paciente a buscar
     * @return índice de la fila si se encuentra, -1 si no se encuentra
     */
    public int buscarPacientePorNombre(String nombre) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nombreTabla = (String) modelo.getValueAt(i, 1);
            if (nombreTabla != null && nombreTabla.toLowerCase().contains(nombre.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    // Getters
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