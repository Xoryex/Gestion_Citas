package views.herramientascitas;
import java.awt.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTablaCitas extends JPanel {

    public JTable tablaCitas;
    private JScrollPane scrollTabla;
    public DefaultTableModel modelo;

    public pnlTablaCitas() {
        
        initComponents();
        
    }
    
    private void initComponents() {
            setLayout(new BorderLayout());
            setBackground(new Color(226, 232, 238));

            String[] columnas = {
                "ID Cita", "DNI Paciente", "Nombre Paciente", "Consultorio", "Doctor",
                "Hora Inicio", "Hora Fin", "Fecha Cita", "Fecha Reprogramada", "Atención", "Estado", "Recepcionista"
            };

            modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] tipos = {
                String.class, String.class, String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class, String.class, String.class
            };

            private final boolean[] editable = new boolean[12];

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return editable[columnIndex];
            }
        };

        tablaCitas = new JTable(modelo);
        tablaCitas.getTableHeader().setReorderingAllowed(false);
        tablaCitas.getTableHeader().setResizingAllowed(true);
        tablaCitas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollTabla = new JScrollPane(tablaCitas);
        add(scrollTabla, BorderLayout.CENTER);
    }

}
