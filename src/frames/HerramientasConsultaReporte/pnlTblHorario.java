package HerramientasConsultaReporte;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblHorario extends JPanel {

    private JTable tablaHorario;
    private JScrollPane scrollTabla;

    public pnlTblHorario() {
        setLayout(new BorderLayout());
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));

        // Definir columnas
        String[] columnas = {
            "ID Horario", "Hora Inicio", "Hora Fin", "Día", 
            "Límite de Pacientes", "Estado del Horario", 
            "DNI del Doctor", "Nombre del Doctor", "Consultorio"
        };

        // Modelo vacío, sin datos por defecto
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // todas las celdas no editables
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 4 -> Integer.class; // Límite de Pacientes
                    default -> String.class; // Todos los demás incluyendo DNI
                };
            }
        };

        tablaHorario = new JTable(modelo);
        tablaHorario.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaHorario.getTableHeader().setResizingAllowed(false);
        tablaHorario.getTableHeader().setReorderingAllowed(false);

        scrollTabla = new JScrollPane(tablaHorario);
        add(scrollTabla, BorderLayout.CENTER);
    }

    // Método público para acceder a la tabla si se necesita desde fuera
    public JTable getTablaHorario() {
        return tablaHorario;
    }

    // Método para cargar datos si lo deseas en el futuro
    public void cargarDatos(Object[][] datos) {
        DefaultTableModel modelo = (DefaultTableModel) tablaHorario.getModel();
        modelo.setRowCount(0); // limpiar tabla
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }
    
    // Método para obtener el modelo de la tabla
    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaHorario.getModel();
    }
}