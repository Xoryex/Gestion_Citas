package HerramientasConsultaReporte;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTblRecepcionista extends JPanel {
    private JTable tablaRecepcionistas;
    private JScrollPane scrollTabla;

    public pnlTblRecepcionista() {
        setLayout(new BorderLayout());
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473)); // Añadido el tamaño preferido
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Definimos los nombres de las columnas
        String[] columnas = {
            "DNI", "Nombre", "Apellido", "Teléfono",
            "Citas Programadas", "Citas Anuladas", "Admin"
        };

        // Creamos un modelo de tabla con columnas pero sin datos inicialmente
        DefaultTableModel modeloTabla = new DefaultTableModel(null, columnas) {
            // Tipos de datos por columna
            private final Class<?>[] tipos = new Class[] {
                String.class, String.class, String.class, String.class, // Cambiado DNI y Teléfono a String
                Integer.class, Integer.class, String.class
            };

            private final boolean[] editable = new boolean[] {
                false, false, false, false, false, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return editable[columnIndex];
            }
        };

        tablaRecepcionistas = new JTable(modeloTabla);
        tablaRecepcionistas.setFont(new Font("Roboto", Font.PLAIN, 14));
        tablaRecepcionistas.getTableHeader().setReorderingAllowed(false);
        tablaRecepcionistas.getTableHeader().setResizingAllowed(false);

        scrollTabla = new JScrollPane(tablaRecepcionistas);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    // Método público para acceder a la tabla
    public JTable getTablaRecepcionistas() {
        return tablaRecepcionistas;
    }
    
    // Método para obtener el modelo de la tabla
    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaRecepcionistas.getModel();
    }
}