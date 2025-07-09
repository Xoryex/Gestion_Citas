package views.ventanasmantenimiento;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.Conexion;

public class pnlPacienteMant extends JPanel {
    private JTable tblPaciente;
    private DefaultTableModel modelPaciente;
    private Connection conn = Conexion.getConnection();
    
    public pnlPacienteMant() {
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Pacientes"));
        
        // Columnas según la vista Vw_ListarPaciente
        String[] columnas = {"DNI", "Nombre", "Apellido", "Teléfono", "Género", "Email", 
                           "Fecha de Nacimiento", "Dirección", "Ocupación", "Grupo Sanguíneo", 
                           "Procedencia", "Estado Civil", "Grupo Étnico", "Centro Trabajo", 
                           "Grado Instrucción", "Hijos"};
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
        JComboBox<String> cmbGeneroPct = new JComboBox<>(new String[]{"M", "F"});
        JTextField txtEmailPct = new JTextField();
        JTextField txtFechNaciPct = new JTextField();
        JTextField txtDirecPct = new JTextField();
        JTextField txtOcupPct = new JTextField();
        JComboBox<String> cmbGrupSang = new JComboBox<>(new String[]{"A+", "A−", "B+", "B−", "AB+", "AB−", "O+", "O−"});
        JTextField txtProcedenciaPct = new JTextField();
        JComboBox<String> cmbEstCivil = new JComboBox<>(new String[]{"1", "2", "3", "4"}); // 1=Casado, 2=Soltero, 3=Viudo, 4=Divorciado
        JTextField txtGrupEtnicoPct = new JTextField();
        JTextField txtCentrTrabPct = new JTextField();
        JComboBox<String> cmbGradInstr = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"}); // 1=Sin estudios, 2=Primaria, 3=Secundaria, 4=Técnico, 5=Universitario, 6=Posgrado
        JTextField txtHijosPct = new JTextField();

        Object[] mensaje = {
            "DNI (solo números):", txtDniPct,
            "Nombre Completo:", txtNomPct,
            "Apellido:", txtApellPct,
            "Teléfono (9 dígitos):", txtTlfPct,
            "Género:", cmbGeneroPct,
            "Email:", txtEmailPct,
            "Fecha de Nacimiento (YYYY-MM-DD):", txtFechNaciPct,
            "Dirección:", txtDirecPct,
            "Ocupación:", txtOcupPct,
            "Grupo Sanguíneo:", cmbGrupSang,
            "Procedencia:", txtProcedenciaPct,
            "Estado Civil (1=Casado, 2=Soltero, 3=Viudo, 4=Divorciado):", cmbEstCivil,
            "Grupo Étnico:", txtGrupEtnicoPct,
            "Centro de Trabajo:", txtCentrTrabPct,
            "Grado de Instrucción (1=Sin estudios, 2=Primaria, 3=Secundaria, 4=Técnico, 5=Universitario, 6=Posgrado):", cmbGradInstr,
            "Hijos:", txtHijosPct
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Paciente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String dniPct = txtDniPct.getText().trim();
                String nomPct = txtNomPct.getText().trim();
                String apellPct = txtApellPct.getText().trim();
                String tlfPct = txtTlfPct.getText().trim();
                String generoPct = cmbGeneroPct.getSelectedItem().toString();
                String emailPct = txtEmailPct.getText().trim();
                String fechNaciPct = txtFechNaciPct.getText().trim();
                String direcPct = txtDirecPct.getText().trim();
                String ocupPct = txtOcupPct.getText().trim();
                String grupSangNombre = cmbGrupSang.getSelectedItem().toString();
                String procedenciaPct = txtProcedenciaPct.getText().trim();
                String estCivilPct = cmbEstCivil.getSelectedItem().toString();
                String grupEtnicoPct = txtGrupEtnicoPct.getText().trim();
                String centrTrabPct = txtCentrTrabPct.getText().trim();
                String gradInstrPct = cmbGradInstr.getSelectedItem().toString();
                String hijosPct = txtHijosPct.getText().trim();

                // Validaciones básicas
                if (dniPct.isEmpty() || nomPct.isEmpty() || apellPct.isEmpty() || tlfPct.isEmpty() || 
                    emailPct.isEmpty() || fechNaciPct.isEmpty() || direcPct.isEmpty() || ocupPct.isEmpty() || 
                    procedenciaPct.isEmpty() || grupEtnicoPct.isEmpty() || centrTrabPct.isEmpty() || 
                    hijosPct.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                    return;
                }

                // Validar DNI de exactamente 8 dígitos
                if (!dniPct.matches("\\d{8}")) {
                    JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos numéricos.");
                    return;
                }
                
                // Validar que teléfono sea numérico y tenga 9 dígitos
                if (!tlfPct.matches("\\d{9}")) {
                    JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 9 dígitos numéricos.");
                    return;
                }

                // Validar formato de fecha
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                java.util.Date fecha = sdf.parse(fechNaciPct);
                java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());

                // Convertir valores numéricos
                int dni = Integer.parseInt(dniPct);
                long telefono = Long.parseLong(tlfPct);
                int gradoInstr = Integer.parseInt(gradInstrPct);
                int hijos = Integer.parseInt(hijosPct);
                int estadoCivil = Integer.parseInt(estCivilPct);

                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setInt(1, dni);
                    stmt.setString(2, nomPct);
                    stmt.setString(3, apellPct);
                    stmt.setLong(4, telefono);
                    stmt.setString(5, generoPct);
                    stmt.setString(6, emailPct);
                    stmt.setDate(7, sqlFecha);
                    stmt.setString(8, direcPct);
                    stmt.setString(9, ocupPct);
                    stmt.setString(10, grupSangNombre);
                    stmt.setString(11, procedenciaPct);
                    stmt.setInt(12, estadoCivil);
                    stmt.setString(13, grupEtnicoPct);
                    stmt.setString(14, centrTrabPct);
                    stmt.setInt(15, gradoInstr);
                    stmt.setInt(16, hijos);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Paciente agregado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en formato numérico: " + e.getMessage());
            } catch (java.text.ParseException e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha. Use YYYY-MM-DD");
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

        // Convertir estado civil de texto a número
        String estadoCivilNum = "1";
        switch (estCivilPct) {
            case "Casado": estadoCivilNum = "1"; break;
            case "Soltero": estadoCivilNum = "2"; break;
            case "Viudo": estadoCivilNum = "3"; break;
            case "Divorciado": estadoCivilNum = "4"; break;
        }

        // Extraer solo la fecha de datetime
        if (fechNaciPct.contains(" ")) {
            fechNaciPct = fechNaciPct.split(" ")[0];
        }

        JTextField txtNomPct = new JTextField(nomPct);
        JTextField txtApellPct = new JTextField(apellPct);
        JTextField txtTlfPct = new JTextField(tlfPct);
        JComboBox<String> cmbGeneroPct = new JComboBox<>(new String[]{"M", "F"});
        cmbGeneroPct.setSelectedItem(generoPct);
        JTextField txtEmailPct = new JTextField(emailPct);
        JTextField txtFechNaciPct = new JTextField(fechNaciPct);
        JTextField txtDirecPct = new JTextField(direcPct);
        JTextField txtOcupPct = new JTextField(ocupPct);
        JComboBox<String> cmbGrupSang = new JComboBox<>(new String[]{"A+", "A−", "B+", "B−", "AB+", "AB−", "O+", "O−"});
        cmbGrupSang.setSelectedItem(grupSangNombre);
        JTextField txtProcedenciaPct = new JTextField(procedenciaPct);
        JComboBox<String> cmbEstCivil = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        cmbEstCivil.setSelectedItem(estadoCivilNum);
        JTextField txtGrupEtnicoPct = new JTextField(grupEtnicoPct);
        JTextField txtCentrTrabPct = new JTextField(centrTrabPct);
        JComboBox<String> cmbGradInstr = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
        cmbGradInstr.setSelectedItem(gradInstrPct);
        JTextField txtHijosPct = new JTextField(hijosPct);

        Object[] mensaje = {
            "DNI (no editable): " + dniPct,
            "Nombre Completo:", txtNomPct,
            "Apellido:", txtApellPct,
            "Teléfono:", txtTlfPct,
            "Género:", cmbGeneroPct,
            "Email:", txtEmailPct,
            "Fecha de Nacimiento (YYYY-MM-DD):", txtFechNaciPct,
            "Dirección:", txtDirecPct,
            "Ocupación:", txtOcupPct,
            "Grupo Sanguíneo:", cmbGrupSang,
            "Procedencia:", txtProcedenciaPct,
            "Estado Civil:", cmbEstCivil,
            "Grupo Étnico:", txtGrupEtnicoPct,
            "Centro de Trabajo:", txtCentrTrabPct,
            "Grado de Instrucción (1=Sin estudios, 2=Primaria, 3=Secundaria, 4=Técnico, 5=Universitario, 6=Posgrado):", cmbGradInstr,
            "Hijos:", txtHijosPct
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Paciente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String nuevoNomPct = txtNomPct.getText().trim();
                String nuevoApellPct = txtApellPct.getText().trim();
                String nuevoTlfPct = txtTlfPct.getText().trim();
                String nuevoGeneroPct = cmbGeneroPct.getSelectedItem().toString();
                String nuevoEmailPct = txtEmailPct.getText().trim();
                String nuevoFechNaciPct = txtFechNaciPct.getText().trim();
                String nuevoDirecPct = txtDirecPct.getText().trim();
                String nuevoOcupPct = txtOcupPct.getText().trim();
                String nuevoGrupSangNombre = cmbGrupSang.getSelectedItem().toString();
                String nuevoProcedenciaPct = txtProcedenciaPct.getText().trim();
                String nuevoEstCivilPct = cmbEstCivil.getSelectedItem().toString();
                String nuevoGrupEtnicoPct = txtGrupEtnicoPct.getText().trim();
                String nuevoCentrTrabPct = txtCentrTrabPct.getText().trim();
                String nuevoGradInstrPct = cmbGradInstr.getSelectedItem().toString();
                String nuevoHijosPct = txtHijosPct.getText().trim();

                // Validaciones
                if (nuevoNomPct.isEmpty() || nuevoApellPct.isEmpty() || nuevoTlfPct.isEmpty() || 
                    nuevoEmailPct.isEmpty() || nuevoFechNaciPct.isEmpty() || nuevoDirecPct.isEmpty() || 
                    nuevoOcupPct.isEmpty() || nuevoProcedenciaPct.isEmpty() || nuevoGrupEtnicoPct.isEmpty() || 
                    nuevoCentrTrabPct.isEmpty() || nuevoHijosPct.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                    return;
                }

                // Conversiones
                int dni = Integer.parseInt(dniPct);
                long telefono = Long.parseLong(nuevoTlfPct);
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                java.util.Date fecha = sdf.parse(nuevoFechNaciPct);
                java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
                
                int gradoInstr = Integer.parseInt(nuevoGradInstrPct);
                int hijos = Integer.parseInt(nuevoHijosPct);
                int estadoCivil = Integer.parseInt(nuevoEstCivilPct);

                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ModificarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setInt(1, dni);
                    stmt.setString(2, nuevoNomPct);
                    stmt.setString(3, nuevoApellPct);
                    stmt.setLong(4, telefono);
                    stmt.setString(5, nuevoGeneroPct);
                    stmt.setString(6, nuevoEmailPct);
                    stmt.setDate(7, sqlFecha);
                    stmt.setString(8, nuevoDirecPct);
                    stmt.setString(9, nuevoOcupPct);
                    stmt.setString(10, nuevoGrupSangNombre);
                    stmt.setString(11, nuevoProcedenciaPct);
                    stmt.setInt(12, estadoCivil);
                    stmt.setString(13, nuevoGrupEtnicoPct);
                    stmt.setString(14, nuevoCentrTrabPct);
                    stmt.setInt(15, gradoInstr);
                    stmt.setInt(16, hijos);

                    stmt.execute();

                    JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente.");
                    stmt.close();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en formato numérico: " + e.getMessage());
            } catch (java.text.ParseException e) {
                JOptionPane.showMessageDialog(this, "Error en formato de fecha. Use YYYY-MM-DD");
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

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar el paciente con DNI: " + dniPct + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarPaciente(?)}");
                    stmt.setInt(1, Integer.parseInt(dniPct));
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
        modelPaciente.setRowCount(0);
        try {
            if (conn != null) {
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ListarPaciente()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Object[] fila = new Object[16];
                    fila[0] = rs.getInt("Dni");
                    fila[1] = rs.getString("Nombre");
                    fila[2] = rs.getString("Apellido");
                    fila[3] = rs.getLong("Telefono");
                    fila[4] = rs.getString("Genero");
                    fila[5] = rs.getString("Email");
                    fila[6] = rs.getDate("Fecha de Nacimiento");
                    fila[7] = rs.getString("Direccion");
                    fila[8] = rs.getString("Ocupacion");
                    fila[9] = rs.getString("Grupo Sanguineo");
                    fila[10] = rs.getString("Procedencia");
                    fila[11] = rs.getString("Estado Civil");
                    fila[12] = rs.getString("Grupo_Etnico");
                    fila[13] = rs.getString("Centro_Trabajo");
                    fila[14] = rs.getInt("Grado_Instruccion");
                    fila[15] = rs.getInt("Hijos");
                    
                    modelPaciente.addRow(fila);
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