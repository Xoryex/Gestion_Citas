package src.frames.HerramientasConsultaReporte;

import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.utils.Conexion;

public class pnlTblCita extends JPanel {

    private JTable tblCRCitas;
    private JScrollPane scrollTabla;
    private DefaultTableModel modeloTabla;
    private Connection conn = Conexion.getConnection();

    public pnlTblCita() {
        setBackground(new Color(57, 93, 129));
        setPreferredSize(new Dimension(1120, 473));
        setLayout(new BorderLayout());

        initComponents();
        cargarDatosDesdeBD();
    }

    private void initComponents() {
        String[] columnas = {
            "ID de Cita", "DNI del NomPct", "NomPct", "NomConst",
            "NomDoc", "Hora Inicio", "Hora Fin", "Atención", "EstadoCita", "NomRecep"
        };

        modeloTabla = new DefaultTableModel(null, columnas) {
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

        tblCRCitas = new JTable(modeloTabla);
        tblCRCitas.setFont(new Font("Roboto", Font.PLAIN, 14));
        tblCRCitas.getTableHeader().setResizingAllowed(false);
        tblCRCitas.getTableHeader().setReorderingAllowed(false);

        scrollTabla = new JScrollPane(tblCRCitas);
        add(scrollTabla, BorderLayout.CENTER);
    }

    public JTable getTablaCitas() {
        return tblCRCitas;
    }

    public void cargarDatosDesdeBD() {
        modeloTabla.setRowCount(0); // Limpiar tabla

        try (
            CallableStatement stmt = conn != null ? conn.prepareCall("{CALL PA_CRUD_ListarCitas()}") : null;
            ResultSet rs = stmt != null ? stmt.executeQuery() : null
        ) {
            if (rs != null) {
                while (rs.next()) {
                    String CodCita = rs.getString("CodCita");
                    String DniPct = rs.getString("DniPct");
                    String NomPct = rs.getString("NomPct");
                    String NomConst = rs.getString("NomConst");
                    String NomDoc = rs.getString("NomDoc");
                    String horaInicio = rs.getString("HoraInicio");
                    String horaFin = rs.getString("HoraFin");
                    String TipoAtencion = rs.getString("TipoAtencion");
                    String EstadoCita = rs.getString("EstadoCita");
                    String NomRecep = rs.getString("NomRecep");

                    modeloTabla.addRow(new Object[] {
                        CodCita, DniPct, NomPct, NomConst, NomDoc,
                        horaInicio, horaFin, TipoAtencion, EstadoCita, NomRecep
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}
