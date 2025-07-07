package HerramientasConsultaReporte;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblDoctor extends JPanel {

    private JTable tablaDoctores;
    private JScrollPane scrollTabla;

    public pnlTblDoctor() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        // Modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "DNI", "Nombre Completo", "Especialidad", "Chambeando?",
                "Citas Pendientes", "Citas Atendidas", "Consultorio", "Correo", "Teléfono"
            }
        ) {
            final Class<?>[] tiposColumna = new Class[]{
                String.class, String.class, String.class, String.class, // Cambiado DNI a String
                Integer.class, Integer.class, String.class, String.class, String.class // Cambiado Teléfono a String
            };

            final boolean[] columnasEditables = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return tiposColumna[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnasEditables[columnIndex];
            }
        };

        // Crear tabla y configurar
        tablaDoctores = new JTable(modelo);
        tablaDoctores.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaDoctores.getTableHeader().setReorderingAllowed(false);
        tablaDoctores.getTableHeader().setResizingAllowed(false);

        // Scroll
        scrollTabla = new JScrollPane(tablaDoctores);
        add(scrollTabla, BorderLayout.CENTER);
    }

    // Método público para acceder al modelo de la tabla
    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaDoctores.getModel();
    }

    // Método para insertar datos fácilmente
    public void agregarDoctor(Object[] fila) {
        getModeloTabla().addRow(fila);
    }
    
    // Método público para acceder a la tabla
    public JTable getTablaDoctores() {
        return tablaDoctores;
    }
}