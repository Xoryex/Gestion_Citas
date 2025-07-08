package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlPacienteMant extends JPanel {
    private JTable tblPaciente;
    private DefaultTableModel modelPaciente;
    private Connection conn = Conexion.getConnection();
    
    public pnlPacienteMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Pacientes"));
        
        String[] columnas = {"DNI", "Nombre Comp.", "Apellido" ,"Teléfono", "Género", "Email", "Fecha de Nac.", 
                           "Dirección", "Ocupación", "Grupo Sanguín.", "Procedencia", "Estado Civil", 
                           "Grupo Étnico", "Centro de trab.", "Grado de Instru.", "Hijos"};
        modelPaciente = new DefaultTableModel(columnas, 0);
        tblPaciente = new JTable(modelPaciente);
        
        JScrollPane scrollPane = new JScrollPane(tblPaciente);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblPaciente;
    }
    
    public DefaultTableModel getModelo() {
        return modelPaciente;
    }
    
    public void agregar() {
        JTextField txtDniPct = new JTextField();
        JTextField txtNomPct = new JTextField();
        JTextField txtApellPct = new JTextField();
        JTextField txtTlfPct = new JTextField();
        JTextField txtGeneroPct = new JTextField();
        JTextField txtEmailPct = new JTextField();
        JTextField txtFechNaciPct = new JTextField();
        JTextField txtDirecPct = new JTextField();
        JTextField txtOcupPct = new JTextField();
        JTextField txtGrupSangNombre = new JTextField();
        JTextField txtProcedenciaPct = new JTextField();
        JTextField txtEstCivilPct = new JTextField();
        JTextField txtGrupEtnicoPct = new JTextField();
        JTextField txtCentrTrabPct = new JTextField();
        JTextField txtGradInstrPct = new JTextField();
        JTextField txtHijosPct = new JTextField();

        Object[] mensaje = {
            "DNI:", txtDniPct,
            "Nombre Comp.:", txtNomPct,
            "Apellido:", txtApellPct,
            "Teléfono:", txtTlfPct,
            "Género:", txtGeneroPct,
            "Email:", txtEmailPct,
            "Fecha de Nac.:", txtFechNaciPct,
            "Dirección:", txtDirecPct,
            "Ocupación:", txtOcupPct,
            "Grupo Sanguín.:", txtGrupSangNombre,
            "Procedencia:", txtProcedenciaPct,
            "Estado Civil:", txtEstCivilPct,
            "Grupo Étnico:", txtGrupEtnicoPct,
            "Centro de trab.:", txtCentrTrabPct,
            "Grado de Instru.:", txtGradInstrPct,
            "Hijos:", txtHijosPct
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Paciente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String dniPct = txtDniPct.getText().trim();
            String nomPct = txtNomPct.getText().trim();
            String apellPct = txtApellPct.getText().trim();
            String tlfPct = txtTlfPct.getText().trim();
            String generoPct = txtGeneroPct.getText().trim();
            String emailPct = txtEmailPct.getText().trim();
            String fechNaciPct = txtFechNaciPct.getText().trim();
            String direcPct = txtDirecPct.getText().trim();
            String ocupPct = txtOcupPct.getText().trim();
            String grupSangNombre = txtGrupSangNombre.getText().trim();
            String procedenciaPct = txtProcedenciaPct.getText().trim();
            String estCivilPct = txtEstCivilPct.getText().trim();
            String grupEtnicoPct = txtGrupEtnicoPct.getText().trim();
            String centrTrabPct = txtCentrTrabPct.getText().trim();
            String gradInstrPct = txtGradInstrPct.getText().trim();
            String hijosPct = txtHijosPct.getText().trim();

            if (dniPct.isEmpty() || nomPct.isEmpty() || apellPct.isEmpty() || tlfPct.isEmpty() || generoPct.isEmpty() || emailPct.isEmpty() ||
                fechNaciPct.isEmpty() || direcPct.isEmpty() || ocupPct.isEmpty() || grupSangNombre.isEmpty() ||
                procedenciaPct.isEmpty() || estCivilPct.isEmpty() || grupEtnicoPct.isEmpty() || centrTrabPct.isEmpty() ||
                gradInstrPct.isEmpty() || hijosPct.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, dniPct);
                    stmt.setString(2, nomPct);
                    stmt.setString(3, apellPct);
                    stmt.setString(4, tlfPct);
                    stmt.setString(5, generoPct);
                    stmt.setString(6, emailPct);
                    stmt.setString(7, fechNaciPct);
                    stmt.setString(8, direcPct);
                    stmt.setString(9, ocupPct);
                    stmt.setString(10, grupSangNombre);
                    stmt.setString(11, procedenciaPct);
                    stmt.setString(12, estCivilPct);
                    stmt.setString(13, grupEtnicoPct);
                    stmt.setString(14, centrTrabPct);
                    stmt.setString(15, gradInstrPct);
                    stmt.setString(16, hijosPct);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Paciente agregado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
            }
        }
    }

    public void actualizar() {
        int filaSeleccionada = tblPaciente.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String dniPct = modelPaciente.getValueAt(filaSeleccionada, 0).toString();
        String nomPct = modelPaciente.getValueAt(filaSeleccionada, 1).toString();
        String apellPct = modelPaciente.getValueAt(filaSeleccionada, 2).toString();
        String tlfPct = modelPaciente.getValueAt(filaSeleccionada, 3).toString();
        String generoPct = modelPaciente.getValueAt(filaSeleccionada, 4).toString();
        String emailPct = modelPaciente.getValueAt(filaSeleccionada, 5).toString();
        String fechNaciPct = modelPaciente.getValueAt(filaSeleccionada, 6).toString();
        String direcPct = modelPaciente.getValueAt(filaSeleccionada, 7).toString();
        String ocupPct = modelPaciente.getValueAt(filaSeleccionada, 8).toString();
        String grupSangNombre = modelPaciente.getValueAt(filaSeleccionada, 9).toString();
        String procedenciaPct = modelPaciente.getValueAt(filaSeleccionada, 10).toString();
        String estCivilPct = modelPaciente.getValueAt(filaSeleccionada, 11).toString();
        String grupEtnicoPct = modelPaciente.getValueAt(filaSeleccionada, 12).toString();
        String centrTrabPct = modelPaciente.getValueAt(filaSeleccionada, 13).toString();
        String gradInstrPct = modelPaciente.getValueAt(filaSeleccionada, 14).toString();
        String hijosPct = modelPaciente.getValueAt(filaSeleccionada, 15).toString();

        JTextField txtNomPct = new JTextField(nomPct);
        JTextField txtApellPct = new JTextField(apellPct);
        JTextField txtTlfPct = new JTextField(tlfPct);
        JTextField txtGeneroPct = new JTextField(generoPct);
        JTextField txtEmailPct = new JTextField(emailPct);
        JTextField txtFechNaciPct = new JTextField(fechNaciPct);
        JTextField txtDirecPct = new JTextField(direcPct);
        JTextField txtOcupPct = new JTextField(ocupPct);
        JTextField txtGrupSangNombre = new JTextField(grupSangNombre);
        JTextField txtProcedenciaPct = new JTextField(procedenciaPct);
        JTextField txtEstCivilPct = new JTextField(estCivilPct);
        JTextField txtGrupEtnicoPct = new JTextField(grupEtnicoPct);
        JTextField txtCentrTrabPct = new JTextField(centrTrabPct);
        JTextField txtGradInstrPct = new JTextField(gradInstrPct);
        JTextField txtHijosPct = new JTextField(hijosPct);

        Object[] mensaje = {
            "DNI (no editable): " + dniPct,
            "Nombre Comp.:", txtNomPct,
            "Apellido:", txtApellPct,
            "Teléfono:", txtTlfPct,
            "Género:", txtGeneroPct,
            "Email:", txtEmailPct,
            "Fecha de Nac.:", txtFechNaciPct,
            "Dirección:", txtDirecPct,
            "Ocupación:", txtOcupPct,
            "Grupo Sanguín.:", txtGrupSangNombre,
            "Procedencia:", txtProcedenciaPct,
            "Estado Civil:", txtEstCivilPct,
            "Grupo Étnico:", txtGrupEtnicoPct,
            "Centro de trab.:", txtCentrTrabPct,
            "Grado de Instru.:", txtGradInstrPct,
            "Hijos:", txtHijosPct
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Paciente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNomPct = txtNomPct.getText().trim();
            String nuevoApellPct = txtApellPct.getText().trim();
            String nuevoTlfPct = txtTlfPct.getText().trim();
            String nuevoGeneroPct = txtGeneroPct.getText().trim();
            String nuevoEmailPct = txtEmailPct.getText().trim();
            String nuevoFechNaciPct = txtFechNaciPct.getText().trim();
            String nuevoDirecPct = txtDirecPct.getText().trim();
            String nuevoOcupPct = txtOcupPct.getText().trim();
            String nuevoGrupSangNombre = txtGrupSangNombre.getText().trim();
            String nuevoProcedenciaPct = txtProcedenciaPct.getText().trim();
            String nuevoEstCivilPct = txtEstCivilPct.getText().trim();
            String nuevoGrupEtnicoPct = txtGrupEtnicoPct.getText().trim();
            String nuevoCentrTrabPct = txtCentrTrabPct.getText().trim();
            String nuevoGradInstrPct = txtGradInstrPct.getText().trim();
            String nuevoHijosPct = txtHijosPct.getText().trim();

            if (nuevoNomPct.isEmpty() || nuevoApellPct.isEmpty() || nuevoTlfPct.isEmpty() || nuevoGeneroPct.isEmpty() || nuevoEmailPct.isEmpty() ||
                nuevoFechNaciPct.isEmpty() || nuevoDirecPct.isEmpty() || nuevoOcupPct.isEmpty() || nuevoGrupSangNombre.isEmpty() ||
                nuevoProcedenciaPct.isEmpty() || nuevoEstCivilPct.isEmpty() || nuevoGrupEtnicoPct.isEmpty() || nuevoCentrTrabPct.isEmpty() ||
                nuevoGradInstrPct.isEmpty() || nuevoHijosPct.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, dniPct);
                    stmt.setString(2, nuevoNomPct);
                    stmt.setString(3, nuevoApellPct);
                    stmt.setString(4, nuevoTlfPct);
                    stmt.setString(5, nuevoGeneroPct);
                    stmt.setString(6, nuevoEmailPct);
                    stmt.setString(7, nuevoFechNaciPct);
                    stmt.setString(8, nuevoDirecPct);
                    stmt.setString(9, nuevoOcupPct);
                    stmt.setString(10, nuevoGrupSangNombre);
                    stmt.setString(11, nuevoProcedenciaPct);
                    stmt.setString(12, nuevoEstCivilPct);
                    stmt.setString(13, nuevoGrupEtnicoPct);
                    stmt.setString(14, nuevoCentrTrabPct);
                    stmt.setString(15, nuevoGradInstrPct);
                    stmt.setString(16, nuevoHijosPct);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    public void eliminar() {
        int filaSeleccionada = tblPaciente.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            return;
        }

        String dniPct = modelPaciente.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el paciente con DNI: " + dniPct + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarPaciente(?)}");
                    stmt.setString(1, dniPct);
                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public void cargarDatos() {
        modelPaciente.setRowCount(0); // Limpiar la tabla
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarPaciente()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String dniPct = rs.getString("DniPct");
                    String nomPct = rs.getString("NomPct");
                    String apellPct = rs.getString("ApellPct");
                    String tlfPct = rs.getString("TlfPct");
                    String generoPct = rs.getString("GeneroPct");
                    String emailPct = rs.getString("EmailPct");
                    String fechNaciPct = rs.getString("FechNaciPct");
                    String direcPct = rs.getString("DirecPct");
                    String ocupPct = rs.getString("OcupPct");
                    String grupSangNombre = rs.getString("GrupSangNombre");
                    String procedenciaPct = rs.getString("ProcedenciaPct");
                    String estCivilPct = rs.getString("EstCivilPct");
                    String grupEtnicoPct = rs.getString("GrupEtnicoPct");
                    String centrTrabPct = rs.getString("CentrTrabPct");
                    String gradInstrPct = rs.getString("GradInstrPct");
                    String hijosPct = rs.getString("HijosPct");
                    modelPaciente.addRow(new Object[]{dniPct, nomPct, apellPct, tlfPct, generoPct, emailPct, fechNaciPct, direcPct, ocupPct, grupSangNombre, procedenciaPct, estCivilPct, grupEtnicoPct, centrTrabPct, gradInstrPct, hijosPct});
                }
                rs.close();
                stmt.close();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}