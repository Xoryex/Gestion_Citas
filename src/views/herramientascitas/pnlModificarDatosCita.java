package views.herramientascitas;

import models.Cita;
import models.Doctor;
import models.Paciente;
import querys.QueryCita;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class pnlModificarDatosCita extends JPanel {

    private JTextField txtDNIPaciente;
    private JLabel lblMuestraNomPaciente;
    private JComboBox<String> cbxAtencionCita;
    private JTextField txtFechaCita;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFin;
    private JComboBox<String> cbxEspecialidad;
    private JComboBox<String> cbxDoctor;
    private JLabel lblMuestraConsultorio;
    private JButton btnCancelar;
    private JButton btnGuardar;
    
    private QueryCita queryCita=new QueryCita();
    private List<Doctor> doctores = new ArrayList<>();
    private Cita citaActual = new Cita();

    public pnlModificarDatosCita() {
        
        setBackground(new Color(207, 218, 230));
        setBorder(BorderFactory.createEmptyBorder(80, 300, 80, 300));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel panelDatos = new JPanel(new GridLayout(10, 2, 80, 3));
        panelDatos.setBackground(new Color(207, 218, 230));
        panelDatos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(57, 93, 129)),
                "Datos de la Cita",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Roboto", Font.BOLD, 14),
                new Color(57, 93, 129)
        ));

        txtDNIPaciente = new JTextField();
        lblMuestraNomPaciente = new JLabel("-", SwingConstants.CENTER);
        cbxAtencionCita = new JComboBox<>();
        txtFechaCita = new JTextField();
        txtHoraInicio = new JTextField();
        txtHoraFin = new JTextField();
        cbxEspecialidad = new JComboBox<>();
        cbxDoctor = new JComboBox<>();
        lblMuestraConsultorio = new JLabel("-", SwingConstants.CENTER);

        btnCancelar = crearBoton("Cancelar");
        btnGuardar = crearBoton("Guardar");

        agregarCampo(panelDatos, "DNI del Paciente:", txtDNIPaciente);
        agregarCampo(panelDatos, "Paciente:", lblMuestraNomPaciente);
        agregarCampo(panelDatos, "Atención:", cbxAtencionCita);
        agregarCampo(panelDatos, "Fecha:", txtFechaCita);
        agregarCampo(panelDatos, "Hora Inicio:", txtHoraInicio);
        agregarCampo(panelDatos, "Hora Fin:", txtHoraFin);
        agregarCampo(panelDatos, "Especialidad:", cbxEspecialidad);
        agregarCampo(panelDatos, "Doctor:", cbxDoctor);
        agregarCampo(panelDatos, "Consultorio:", lblMuestraConsultorio);
        panelDatos.add(btnCancelar);
        panelDatos.add(btnGuardar);

        add(panelDatos, BorderLayout.CENTER);
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent componente) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Roboto", Font.PLAIN, 14));
        componente.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(label);
        panel.add(componente);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(57, 93, 129));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Roboto", Font.BOLD, 14));
        return boton;
    }
    
    private void cargarDatos() {
        // Cargar tipos de atención
        List<String> tiposAtencion = queryCita.obtenerTiposAtencion();
        cbxAtencionCita.removeAllItems();
        for (String tipo : tiposAtencion) {
            cbxAtencionCita.addItem(tipo);
        }
        
        // Cargar especialidades
        List<String> especialidades = queryCita.obtenerEspecialidades();
        cbxEspecialidad.removeAllItems();
        for (String especialidad : especialidades) {
            cbxEspecialidad.addItem(especialidad);
        }
        
        // Cargar doctores
        doctores = queryCita.obtenerDoctores();
        cbxDoctor.removeAllItems();
        for (Doctor doctor : doctores) {
            cbxDoctor.addItem(doctor.getNombre() + " " + doctor.getApellido());
        }
    }
    
    private void configurarEventos() {
        // Evento para buscar paciente cuando se pierde el foco del DNI
        txtDNIPaciente.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                buscarPaciente();
            }
        });
        
        // Evento para cambiar doctor cuando se selecciona especialidad
        cbxEspecialidad.addActionListener(e -> filtrarDoctoresPorEspecialidad());
        
        // Evento para mostrar consultorio cuando se selecciona doctor
        cbxDoctor.addActionListener(e -> mostrarConsultorio());
        
        // Evento del botón guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCita();
            }
        });
        
        // Evento del botón cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
    }
    
    private void buscarPaciente() {
        String dniText = txtDNIPaciente.getText().trim();
        if (!dniText.isEmpty()) {
            try {
                int dni = Integer.parseInt(dniText);
                Paciente paciente = queryCita.buscarPacientePorDni(dni);
                if (paciente != null) {
                    lblMuestraNomPaciente.setText(paciente.getNombre() + " " + paciente.getApellido());
                } else {
                    lblMuestraNomPaciente.setText("Paciente no encontrado");
                    JOptionPane.showMessageDialog(this, "Paciente no encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                lblMuestraNomPaciente.setText("-");
                JOptionPane.showMessageDialog(this, "DNI debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            lblMuestraNomPaciente.setText("-");
        }
    }
    
    private void filtrarDoctoresPorEspecialidad() {
        String especialidadSeleccionada = (String) cbxEspecialidad.getSelectedItem();
        if (especialidadSeleccionada != null) {
            cbxDoctor.removeAllItems();
            for (Doctor doctor : doctores) {
                if (doctor.getEspecialidad().equals(especialidadSeleccionada)) {
                    cbxDoctor.addItem(doctor.getNombre() + " " + doctor.getApellido());
                }
            }
        }
    }
    
    private void mostrarConsultorio() {
        int selectedIndex = cbxDoctor.getSelectedIndex();
        if (selectedIndex >= 0) {
            String especialidadSeleccionada = (String) cbxEspecialidad.getSelectedItem();
            List<Doctor> doctoresFiltrados = new ArrayList<>();
            
            for (Doctor doctor : doctores) {
                if (doctor.getEspecialidad().equals(especialidadSeleccionada)) {
                    doctoresFiltrados.add(doctor);
                }
            }
            
            if (selectedIndex < doctoresFiltrados.size()) {
                Doctor doctorSeleccionado = doctoresFiltrados.get(selectedIndex);
                String consultorio = queryCita.obtenerConsultorioPorDoctor(doctorSeleccionado.getDni());
                lblMuestraConsultorio.setText(consultorio);
            }
        }
    }
    
    private void actualizarCita() {
        try {
            // Validar campos
            if (txtDNIPaciente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el DNI del paciente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (lblMuestraNomPaciente.getText().equals("Paciente no encontrado") || lblMuestraNomPaciente.getText().equals("-")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un paciente válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener datos del formulario
            int dniPaciente = Integer.parseInt(txtDNIPaciente.getText().trim());
            
            // Obtener doctor seleccionado
            int selectedIndex = cbxDoctor.getSelectedIndex();
            String especialidadSeleccionada = (String) cbxEspecialidad.getSelectedItem();
            List<Doctor> doctoresFiltrados = new ArrayList<>();
            
            for (Doctor doctor : doctores) {
                if (doctor.getEspecialidad().equals(especialidadSeleccionada)) {
                    doctoresFiltrados.add(doctor);
                }
            }
            
            if (selectedIndex < 0 || selectedIndex >= doctoresFiltrados.size()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un doctor", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Doctor doctorSeleccionado = doctoresFiltrados.get(selectedIndex);
            
            // Crear objeto Cita para actualizar
            Cita cita = new Cita();
            cita.setDniRecep(1); // Por ahora DNI fijo del recepcionista
            cita.setCodCita("CITA" + System.currentTimeMillis()); // Generar código único
            cita.setCodHorario(1); // Por ahora horario fijo
            cita.setDniPct(dniPaciente);
            cita.setDniDoc(doctorSeleccionado.getDni());
            
            // Parsear fecha y horas
            LocalDate fecha = LocalDate.parse(txtFechaCita.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime horaInicio = LocalTime.parse(txtHoraInicio.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime horaFin = LocalTime.parse(txtHoraFin.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            
            cita.setFechaCita(fecha);
            cita.setHoraInicio(horaInicio);
            cita.setHoraFin(horaFin);
            cita.setIdEstadoCita(1); // Estado activo
            cita.setIdTipoAtencion(cbxAtencionCita.getSelectedIndex() + 1); // Asumir que los IDs empiezan en 1
            
            // Establecer fecha de reprogramación
            cita.setFechaReprogra(LocalDate.now());
            
            // Actualizar en la base de datos
            queryCita.actualizar(cita);
            
            // Limpiar formulario después de actualizar
            limpiarFormulario();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "DNI debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtDNIPaciente.setText("");
        lblMuestraNomPaciente.setText("-");
        cbxAtencionCita.setSelectedIndex(0);
        txtFechaCita.setText("");
        txtHoraInicio.setText("");
        txtHoraFin.setText("");
        cbxEspecialidad.setSelectedIndex(0);
        lblMuestraConsultorio.setText("-");
        cargarDatos(); // Recargar datos
    }
    
    public void cargarCita(Cita cita) {
        this.citaActual = cita;
        if (cita != null) {
            txtDNIPaciente.setText(String.valueOf(cita.getDniPct()));
            txtFechaCita.setText(cita.getFechaCita().toString());
            txtHoraInicio.setText(cita.getHoraInicio().toString());
            txtHoraFin.setText(cita.getHoraFin().toString());
            // Aquí se podrían cargar más datos según sea necesario
        }
    }
}
