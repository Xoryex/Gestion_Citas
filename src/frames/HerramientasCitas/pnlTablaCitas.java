package HerramientasCitas;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTablaCitas extends JPanel {

    private JTable tablaCitas;
    private JScrollPane scrollTabla;

    public pnlTablaCitas() {
        setLayout(new BorderLayout());
        setBackground(new Color(226, 232, 238));

        String[] columnas = {
            "ID de Cita", "Paciente", "Fecha de la Cita", "Doctor", "Hora",
            "Hora de Inicio", "Hora de Fin", "Estado", "Consultorio",
            "Atención", "Recepcionista"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] tipos = {
                Integer.class, String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class, String.class, String.class
            };

            private final boolean[] editable = new boolean[11];

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
        tablaCitas.getTableHeader().setResizingAllowed(false);

        scrollTabla = new JScrollPane(tablaCitas);
        add(scrollTabla, BorderLayout.CENTER);
    }
}
