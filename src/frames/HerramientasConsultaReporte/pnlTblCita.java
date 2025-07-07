package HerramientasConsultaReporte;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblCita extends JPanel {

    private JTable tblCRCitas;
    private JScrollPane scrollTabla;

    public pnlTblCita() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Definir columnas
        String[] columnas = {
            "ID de Cita", "DNI del Paciente", "Paciente", "Consultorio",
            "Doctor", "Hora Inicio", "Hora Fin", "Atención", "Estado", "Recepcionista"
        };

        // Modelo de tabla sin datos iniciales
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] columnTypes = new Class<?>[] {
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class
            };

            private final boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false, false, false, false
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

        tblCRCitas = new JTable(modelo);
        tblCRCitas.setFont(new Font("Roboto", Font.PLAIN, 14));
        tblCRCitas.getTableHeader().setResizingAllowed(false);
        tblCRCitas.getTableHeader().setReorderingAllowed(false);

        scrollTabla = new JScrollPane(tblCRCitas);
        add(scrollTabla, BorderLayout.CENTER);
    }

    public JTable getTablaCitas() {
        return tblCRCitas;
    }
}
