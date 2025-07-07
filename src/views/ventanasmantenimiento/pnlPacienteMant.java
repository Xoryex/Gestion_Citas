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
        
        String[] columnas = {"DNI", "Nombre Comp.", "Teléfono", "Género", "Email", "Fecha de Nac.", 
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
    
    public void agregarDatos() {

    }

    
    public void agregar() {
        JTextField txtDNI = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtFechaNac = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtOcupacion = new JTextField();
        JTextField txtGrupoSang = new JTextField();
        JTextField txtProcedencia = new JTextField();
        JTextField txtEstadoCivil = new JTextField();
        JTextField txtGrupoEtnico = new JTextField();
        JTextField txtCentroTrabajo = new JTextField();
        JTextField txtGradoInstruccion = new JTextField();
        JTextField txtHijos = new JTextField();

        Object[] mensaje = {
            "DNI:", txtDNI,
            "Nombre Comp.:", txtNombre,
            "Teléfono:", txtTelefono,
            "Género:", txtGenero,
            "Email:", txtEmail,
            "Fecha de Nac.:", txtFechaNac,
            "Dirección:", txtDireccion,
            "Ocupación:", txtOcupacion,
            "Grupo Sanguín.:", txtGrupoSang,
            "Procedencia:", txtProcedencia,
            "Estado Civil:", txtEstadoCivil,
            "Grupo Étnico:", txtGrupoEtnico,
            "Centro de trab.:", txtCentroTrabajo,
            "Grado de Instru.:", txtGradoInstruccion,
            "Hijos:", txtHijos
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Paciente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String dni = txtDNI.getText().trim();
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String genero = txtGenero.getText().trim();
            String email = txtEmail.getText().trim();
            String fechaNac = txtFechaNac.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String ocupacion = txtOcupacion.getText().trim();
            String grupoSang = txtGrupoSang.getText().trim();
            String procedencia = txtProcedencia.getText().trim();
            String estadoCivil = txtEstadoCivil.getText().trim();
            String grupoEtnico = txtGrupoEtnico.getText().trim();
            String centroTrabajo = txtCentroTrabajo.getText().trim();
            String gradoInstruccion = txtGradoInstruccion.getText().trim();
            String hijos = txtHijos.getText().trim();

            if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || genero.isEmpty() || email.isEmpty() ||
                fechaNac.isEmpty() || direccion.isEmpty() || ocupacion.isEmpty() || grupoSang.isEmpty() ||
                procedencia.isEmpty() || estadoCivil.isEmpty() || grupoEtnico.isEmpty() || centroTrabajo.isEmpty() ||
                gradoInstruccion.isEmpty() || hijos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_InsertarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, dni);
                    stmt.setString(2, nombre);
                    stmt.setString(3, telefono);
                    stmt.setString(4, genero);
                    stmt.setString(5, email);
                    stmt.setString(6, fechaNac);
                    stmt.setString(7, direccion);
                    stmt.setString(8, ocupacion);
                    stmt.setString(9, grupoSang);
                    stmt.setString(10, procedencia);
                    stmt.setString(11, estadoCivil);
                    stmt.setString(12, grupoEtnico);
                    stmt.setString(13, centroTrabajo);
                    stmt.setString(14, gradoInstruccion);
                    stmt.setString(15, hijos);

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

        String dniActual = modelPaciente.getValueAt(filaSeleccionada, 0).toString();
        String nombreActual = modelPaciente.getValueAt(filaSeleccionada, 1).toString();
        String telefonoActual = modelPaciente.getValueAt(filaSeleccionada, 2).toString();
        String generoActual = modelPaciente.getValueAt(filaSeleccionada, 3).toString();
        String emailActual = modelPaciente.getValueAt(filaSeleccionada, 4).toString();
        String fechaNacActual = modelPaciente.getValueAt(filaSeleccionada, 5).toString();
        String direccionActual = modelPaciente.getValueAt(filaSeleccionada, 6).toString();
        String ocupacionActual = modelPaciente.getValueAt(filaSeleccionada, 7).toString();
        String grupoSangActual = modelPaciente.getValueAt(filaSeleccionada, 8).toString();
        String procedenciaActual = modelPaciente.getValueAt(filaSeleccionada, 9).toString();
        String estadoCivilActual = modelPaciente.getValueAt(filaSeleccionada, 10).toString();
        String grupoEtnicoActual = modelPaciente.getValueAt(filaSeleccionada, 11).toString();
        String centroTrabajoActual = modelPaciente.getValueAt(filaSeleccionada, 12).toString();
        String gradoInstruccionActual = modelPaciente.getValueAt(filaSeleccionada, 13).toString();
        String hijosActual = modelPaciente.getValueAt(filaSeleccionada, 14).toString();

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtTelefono = new JTextField(telefonoActual);
        JTextField txtGenero = new JTextField(generoActual);
        JTextField txtEmail = new JTextField(emailActual);
        JTextField txtFechaNac = new JTextField(fechaNacActual);
        JTextField txtDireccion = new JTextField(direccionActual);
        JTextField txtOcupacion = new JTextField(ocupacionActual);
        JTextField txtGrupoSang = new JTextField(grupoSangActual);
        JTextField txtProcedencia = new JTextField(procedenciaActual);
        JTextField txtEstadoCivil = new JTextField(estadoCivilActual);
        JTextField txtGrupoEtnico = new JTextField(grupoEtnicoActual);
        JTextField txtCentroTrabajo = new JTextField(centroTrabajoActual);
        JTextField txtGradoInstruccion = new JTextField(gradoInstruccionActual);
        JTextField txtHijos = new JTextField(hijosActual);

        Object[] mensaje = {
            "DNI (no editable): " + dniActual,
            "Nombre Comp.:", txtNombre,
            "Teléfono:", txtTelefono,
            "Género:", txtGenero,
            "Email:", txtEmail,
            "Fecha de Nac.:", txtFechaNac,
            "Dirección:", txtDireccion,
            "Ocupación:", txtOcupacion,
            "Grupo Sanguín.:", txtGrupoSang,
            "Procedencia:", txtProcedencia,
            "Estado Civil:", txtEstadoCivil,
            "Grupo Étnico:", txtGrupoEtnico,
            "Centro de trab.:", txtCentroTrabajo,
            "Grado de Instru.:", txtGradoInstruccion,
            "Hijos:", txtHijos
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Paciente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String genero = txtGenero.getText().trim();
            String email = txtEmail.getText().trim();
            String fechaNac = txtFechaNac.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String ocupacion = txtOcupacion.getText().trim();
            String grupoSang = txtGrupoSang.getText().trim();
            String procedencia = txtProcedencia.getText().trim();
            String estadoCivil = txtEstadoCivil.getText().trim();
            String grupoEtnico = txtGrupoEtnico.getText().trim();
            String centroTrabajo = txtCentroTrabajo.getText().trim();
            String gradoInstruccion = txtGradoInstruccion.getText().trim();
            String hijos = txtHijos.getText().trim();

            if (nombre.isEmpty() || telefono.isEmpty() || genero.isEmpty() || email.isEmpty() ||
                fechaNac.isEmpty() || direccion.isEmpty() || ocupacion.isEmpty() || grupoSang.isEmpty() ||
                procedencia.isEmpty() || estadoCivil.isEmpty() || grupoEtnico.isEmpty() || centroTrabajo.isEmpty() ||
                gradoInstruccion.isEmpty() || hijos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_ActualizarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    stmt.setString(1, dniActual);
                    stmt.setString(2, nombre);
                    stmt.setString(3, telefono);
                    stmt.setString(4, genero);
                    stmt.setString(5, email);
                    stmt.setString(6, fechaNac);
                    stmt.setString(7, direccion);
                    stmt.setString(8, ocupacion);
                    stmt.setString(9, grupoSang);
                    stmt.setString(10, procedencia);
                    stmt.setString(11, estadoCivil);
                    stmt.setString(12, grupoEtnico);
                    stmt.setString(13, centroTrabajo);
                    stmt.setString(14, gradoInstruccion);
                    stmt.setString(15, hijos);

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

        String dni = modelPaciente.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el paciente con DNI: " + dni + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (conn != null) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_EliminarPaciente(?)}");
                    stmt.setString(1, dni);
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
                CallableStatement stmt = conn.prepareCall("{CALL PA_CRUD_MostrarPacientes()}");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String dni = rs.getString("DNI");
                    String nombre = rs.getString("NombreComp");
                    String telefono = rs.getString("Telefono");
                    String genero = rs.getString("Genero");
                    String email = rs.getString("Email");
                    String fechaNac = rs.getString("FechaNac");
                    String direccion = rs.getString("Direccion");
                    String ocupacion = rs.getString("Ocupacion");
                    String grupoSang = rs.getString("GrupoSanguineo");
                    String procedencia = rs.getString("Procedencia");
                    String estadoCivil = rs.getString("EstadoCivil");
                    String grupoEtnico = rs.getString("GrupoEtnico");
                    String centroTrabajo = rs.getString("CentroTrabajo");
                    String gradoInstruccion = rs.getString("GradoInstruccion");
                    String hijos = rs.getString("Hijos");
                    modelPaciente.addRow(new Object[]{dni, nombre, telefono, genero, email, fechaNac, direccion, ocupacion, grupoSang, procedencia, estadoCivil, grupoEtnico, centroTrabajo, gradoInstruccion, hijos});
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