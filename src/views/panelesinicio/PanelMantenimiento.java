package views.panelesinicio;

import javax.swing.*;

import views.ventanasmantenimiento.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import utils.Conexion;

public class PanelMantenimiento extends JPanel {
    
    private JComboBox<String> cmbMantenimiento;
    private JPanel pnlContenedor;
    private CardLayout cardLayout;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnExtraDoctor; // ✅ Botón que solo aparece en pnlDoctorMant

    // Paneles individuales
    private pnlRecepcionistaMant panelRecepcionista;
    private pnlConsultorioMant panelConsultorio;
    private pnlDoctorMant panelDoctor;
    private pnlEspecialidadMant panelEspecialidad;
    private pnlHorarioMant panelHorario;
    private pnlPacienteMant panelPaciente;
    
    public PanelMantenimiento() {
        initComponents();
        setupEventListeners();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        // Panel superior con ComboBox y botones
        JPanel pnlSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSuperior.setBackground(new Color(240, 240, 240));
        
        JLabel lblMantenimiento = new JLabel("Realizar mantenimiento a:");
        lblMantenimiento.setFont(new Font("Arial", Font.PLAIN, 12));
        
        String[] opciones = {"Recepcionista", "Consultorio", "Doctor", "Especialidad", "Horario", "Paciente"};
        cmbMantenimiento = new JComboBox<>(opciones);
        cmbMantenimiento.setPreferredSize(new Dimension(150, 25));
        
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        
        // ✅ Nuevo botón adicional solo para Doctor
        btnExtraDoctor = new JButton("Acción Doctor");
        btnExtraDoctor.setVisible(false); // Oculto por defecto

        // Estilo de botones
        styleButton(btnAgregar, new Color(70, 130, 180));
        styleButton(btnActualizar, new Color(70, 130, 180));
        styleButton(btnEliminar, new Color(70, 130, 180));
        styleButton(btnExtraDoctor, new Color(34, 139, 34)); // Estilo diferente para destacar

        pnlSuperior.add(lblMantenimiento);
        pnlSuperior.add(cmbMantenimiento);
        pnlSuperior.add(btnAgregar);
        pnlSuperior.add(btnActualizar);
        pnlSuperior.add(btnEliminar);
        pnlSuperior.add(btnExtraDoctor); // ✅ Agregar al panel superior
        
        // Panel contenedor con CardLayout
        cardLayout = new CardLayout();
        pnlContenedor = new JPanel(cardLayout);
        pnlContenedor.setBackground(Color.WHITE);
        
        crearPaneles();
        
        add(pnlSuperior, BorderLayout.NORTH);
        add(pnlContenedor, BorderLayout.CENTER);
    }
    
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 11));
    }
    
    private void crearPaneles() {
        panelRecepcionista = new pnlRecepcionistaMant();
        panelConsultorio = new pnlConsultorioMant();
        panelDoctor = new pnlDoctorMant();
        panelEspecialidad = new pnlEspecialidadMant();
        panelHorario = new pnlHorarioMant();
        panelPaciente = new pnlPacienteMant();
        
        pnlContenedor.add(panelRecepcionista, "Recepcionista");
        pnlContenedor.add(panelConsultorio, "Consultorio");
        pnlContenedor.add(panelDoctor, "Doctor");
        pnlContenedor.add(panelEspecialidad, "Especialidad");
        pnlContenedor.add(panelHorario, "Horario");
        pnlContenedor.add(panelPaciente, "Paciente");
    }
    
    private void setupEventListeners() {
        cmbMantenimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) cmbMantenimiento.getSelectedItem();
                cardLayout.show(pnlContenedor, seleccion);

                // ✅ Mostrar/ocultar botón extra según el panel seleccionado
                btnExtraDoctor.setVisible("Doctor".equals(seleccion));
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarRegistro();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarRegistro();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRegistro();
            }
        });

        // ✅ Acción para el botón extra - Conecta a paAsignarEspecialidadHorario_Doctor
        btnExtraDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField txtDni = new JTextField();
                JComboBox<String> cbEspecialidad = new JComboBox<>();
                JTextField txtHoraInicio = new JTextField();
                JTextField txtHoraFin = new JTextField();

                // Cargar especialidades
                try (Connection conn = Conexion.getConnection()) {
                    CallableStatement stmt = conn.prepareCall("{CALL PA_ListarSoloEspecialidad()}");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        cbEspecialidad.addItem(rs.getString("Especialidad"));
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PanelMantenimiento.this, "Error al cargar especialidades: " + ex.getMessage());
                    return;
                }

                JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
                panel.add(new JLabel("DNI del Doctor:"));
                panel.add(txtDni);
                panel.add(new JLabel("Especialidad:"));
                panel.add(cbEspecialidad);
                panel.add(new JLabel("Hora Inicio (HH:mm):"));
                panel.add(txtHoraInicio);
                panel.add(new JLabel("Hora Fin (HH:mm):"));
                panel.add(txtHoraFin);

                int result = JOptionPane.showConfirmDialog(
                    PanelMantenimiento.this,
                    panel,
                    "Asignar Especialidad y Horario a Doctor",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String dni = txtDni.getText().trim();
                    String especialidad = (String) cbEspecialidad.getSelectedItem();
                    String horaInicio = txtHoraInicio.getText().trim();
                    String horaFin = txtHoraFin.getText().trim();

                    if (dni.isEmpty() || especialidad == null || horaInicio.isEmpty() || horaFin.isEmpty()) {
                        JOptionPane.showMessageDialog(PanelMantenimiento.this, "Todos los campos son obligatorios.");
                        return;
                    }

                    try (Connection conn = Conexion.getConnection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(PanelMantenimiento.this, "No se pudo conectar a la base de datos.");
                            return;
                        }

                        int codEspecialidad = -1;
                        PreparedStatement ps = conn.prepareStatement("SELECT CodEspecia FROM Especialidad WHERE Especialidad = ?");
                        ps.setString(1, especialidad);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            codEspecialidad = rs.getInt("CodEspecia");
                        } else {
                            JOptionPane.showMessageDialog(PanelMantenimiento.this, "Especialidad no encontrada.");
                            return;
                        }
                        rs.close();
                        ps.close();

                        CallableStatement stmt = conn.prepareCall("{CALL paAsignarEspecialidadHorario_Doctor(?, ?, ?, ?)}");
                        stmt.setString(1, dni);
                        stmt.setInt(2, codEspecialidad);
                        stmt.setString(3, horaInicio);
                        stmt.setString(4, horaFin);
                        stmt.execute();
                        stmt.close();

                        JOptionPane.showMessageDialog(PanelMantenimiento.this, "Asignación realizada con éxito.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(PanelMantenimiento.this, "Error al asignar: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void agregarRegistro() {
        String seleccion = (String) cmbMantenimiento.getSelectedItem();
        switch (seleccion) {
            case "Recepcionista" -> panelRecepcionista.agregar();
            case "Consultorio" -> panelConsultorio.agregar();
            case "Doctor" -> panelDoctor.agregar();
            case "Especialidad" -> panelEspecialidad.agregar();
            case "Horario" -> panelHorario.agregar();
            case "Paciente" -> panelPaciente.agregar();
        }
    }

    private void actualizarRegistro() {
        String seleccion = (String) cmbMantenimiento.getSelectedItem();
        switch (seleccion) {
            case "Recepcionista" -> panelRecepcionista.actualizar();
            case "Consultorio" -> panelConsultorio.actualizar();
            case "Doctor" -> panelDoctor.actualizar();
            case "Especialidad" -> panelEspecialidad.actualizar();
            case "Horario" -> panelHorario.actualizar();
            case "Paciente" -> panelPaciente.actualizar();
        }
    }

    private void eliminarRegistro() {
        String seleccion = (String) cmbMantenimiento.getSelectedItem();
        switch (seleccion) {
            case "Recepcionista" -> panelRecepcionista.eliminar();
            case "Consultorio" -> panelConsultorio.eliminar();
            case "Doctor" -> panelDoctor.eliminar();
            case "Especialidad" -> panelEspecialidad.eliminar();
            case "Horario" -> panelHorario.eliminar();
            case "Paciente" -> panelPaciente.eliminar();
        }
    }

    public void agregarDatos() {
        panelRecepcionista.cargarDatos();
        panelConsultorio.cargarDatos();
        panelDoctor.cargarDatos();
        panelEspecialidad.cargarDatos();
        panelHorario.cargarDatos();
        panelPaciente.cargarDatos();
    }

    // Getters
    public pnlRecepcionistaMant getPanelRecepcionista() { return panelRecepcionista; }
    public pnlConsultorioMant getPanelConsultorio() { return panelConsultorio; }
    public pnlDoctorMant getPanelDoctor() { return panelDoctor; }
    public pnlEspecialidadMant getPanelEspecialidad() { return panelEspecialidad; }
    public pnlHorarioMant getPanelHorario() { return panelHorario; }
    public pnlPacienteMant getPanelPaciente() { return panelPaciente; }

    public JComboBox<String> getCmbMantenimiento() { return cmbMantenimiento; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnExtraDoctor() { return btnExtraDoctor; } // ✅ getter opcional
}
