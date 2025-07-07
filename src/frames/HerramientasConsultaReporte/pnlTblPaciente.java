package HerramientasConsultaReporte;
    
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblPaciente extends JPanel {

    private JTable tablaPacientes;
    private JScrollPane scrollTabla;

    public pnlTblPaciente() {
        setLayout(new BorderLayout());
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473)); // Añadido el tamaño preferido

        String[] columnas = {
            "DNI", "Nombre", "Teléfono", "Género", "Citas Asistidas", "Citas Perdidas",
            "Email", "Fecha de Nacimiento", "Dirección", "Ocupación", "Grupo Sanguíneo",
            "Procedencia", "Estado Civil", "Grupo Étnico", "Centro de Trabajo",
            "Grado de Instrucción", "Hijos"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] tiposColumna = {
                String.class, String.class, String.class, String.class, Integer.class, Integer.class, // Cambiado DNI a String
                String.class, String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, Integer.class
            };

            private final boolean[] columnasEditables = new boolean[columnas.length]; // Inicializado correctamente

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return tiposColumna[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnasEditables[columnIndex];
            }
        };

        tablaPacientes = new JTable(modelo);
        tablaPacientes.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaPacientes.getTableHeader().setResizingAllowed(false);
        tablaPacientes.getTableHeader().setReorderingAllowed(false);

        scrollTabla = new JScrollPane(tablaPacientes);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    // Método público para acceder a la tabla
    public JTable getTablaPacientes() {
        return tablaPacientes;
    }
    
    // Método para obtener el modelo de la tabla
    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaPacientes.getModel();
    }
}