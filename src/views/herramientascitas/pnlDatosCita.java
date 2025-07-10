package views.herramientascitas;

import models.Cita;
import models.Doctor;
import models.Especialidad;
import models.Paciente;
import models.TipoAtencion;
import querys.QueryCita;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class pnlDatosCita extends JPanel {

    private JTextField txtDNIPaciente;
    private JLabel lblMuestraNomPaciente;
    private JComboBox<TipoAtencion> cbxAtencionCita;
    private JSpinner spnFechaHoraIncCIta;
    private JComboBox<Especialidad> cbxEspecialidad;
    private JComboBox<Doctor> cbxDoctor;
    private JLabel lblMuestraConsultorio;
    private JButton btnCancelar;
    private JButton btnGuardar;
    
    private QueryCita queryCita = new QueryCita();
    private List<Doctor> doctores = new ArrayList<>();

    public pnlDatosCita() {
        Componentes();
        cargarDatos();
        configurarEventos();
    }

    private void Componentes() {
        setBackground(new Color(207, 218, 230));
        setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));
        setLayout(new BorderLayout());

        JPanel pnlFormulario = new JPanel(new GridLayout(8, 2, 80, 5));
        pnlFormulario.setBackground(new Color(207, 218, 230));
        
        pnlFormulario.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(57, 93, 129)),
                "Datos de la Cita",
                SwingConstants.CENTER,
                TitledBorder.DEFAULT_POSITION,
                new Font("Roboto", Font.BOLD, 14),
                new Color(57, 93, 129)
            )
        );

        // Componentes
        txtDNIPaciente = new JTextField();
        lblMuestraNomPaciente = new JLabel("-");
        cbxAtencionCita = new JComboBox<>();
        spnFechaHoraIncCIta = new JSpinner(new SpinnerDateModel());
        cbxEspecialidad = new JComboBox<>();
        cbxDoctor = new JComboBox<>();
        lblMuestraConsultorio = new JLabel("-");
        btnCancelar = new JButton("Cancelar");
        btnGuardar = new JButton("Guardar");

        // Estilo
        Font fuente = new Font("Roboto", Font.PLAIN, 14);
        txtDNIPaciente.setFont(fuente);
        lblMuestraNomPaciente.setFont(fuente);
        cbxAtencionCita.setFont(fuente);
        spnFechaHoraIncCIta.setFont(fuente);
        cbxEspecialidad.setFont(fuente);
        cbxDoctor.setFont(fuente);
        lblMuestraConsultorio.setFont(fuente);
        btnCancelar.setFont(fuente);
        btnGuardar.setFont(fuente);

        lblMuestraNomPaciente.setHorizontalAlignment(SwingConstants.CENTER);
        lblMuestraConsultorio.setHorizontalAlignment(SwingConstants.CENTER);

        btnCancelar.setBackground(new Color(57, 93, 129));
        btnCancelar.setForeground(Color.WHITE);
        btnGuardar.setBackground(new Color(57, 93, 129));
        btnGuardar.setForeground(Color.WHITE);

        // Agregar al panel
        pnlFormulario.add(new JLabel("DNI del Paciente:", JLabel.RIGHT));
        pnlFormulario.add(txtDNIPaciente);

        pnlFormulario.add(new JLabel("Paciente:", JLabel.RIGHT));
        pnlFormulario.add(lblMuestraNomPaciente);

        pnlFormulario.add(new JLabel("Atención:", JLabel.RIGHT));
        pnlFormulario.add(cbxAtencionCita);

        pnlFormulario.add(new JLabel("Fecha y Hora de inicio:", JLabel.RIGHT));
        pnlFormulario.add(spnFechaHoraIncCIta);

        pnlFormulario.add(new JLabel("Especialidad:", JLabel.RIGHT));
        pnlFormulario.add(cbxEspecialidad);

        pnlFormulario.add(new JLabel("Doctor:", JLabel.RIGHT));
        pnlFormulario.add(cbxDoctor);

        pnlFormulario.add(new JLabel("Consultorio:", JLabel.RIGHT));
        pnlFormulario.add(lblMuestraConsultorio);

        pnlFormulario.add(btnCancelar);
        pnlFormulario.add(btnGuardar);

        add(pnlFormulario, BorderLayout.CENTER);
    }
    
    private void cargarDatos() {
        // Cargar tipos de atención
        ArrayList<TipoAtencion> tiposAtencion = queryCita.obtenerTiposAtencion();
        cbxAtencionCita.removeAllItems();
        for (TipoAtencion tipo : tiposAtencion) {
            cbxAtencionCita.addItem(tipo);
        }
        
        // Cargar especialidades
        ArrayList<Especialidad> especialidades = queryCita.obtenerEspecialidades();
        cbxEspecialidad.removeAllItems();
        for (Especialidad especialidad : especialidades) {
            cbxEspecialidad.addItem(especialidad.getNombreEspecialidad());
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
                guardarCita();
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
    
    private void guardarCita() {
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
            
            // Crear objeto Cita
            Cita cita = new Cita();
            cita.setDniRecep(1); // Por ahora DNI fijo del recepcionista
            cita.setCodCita(queryCita.generarCodigoCita()); // Generar código único
            cita.setCodHorario(1); // Por ahora horario fijo
            cita.setDniPct(dniPaciente);
            cita.setDniDoc(doctorSeleccionado.getDni());
            
            // Obtener fecha y hora del spinner
            Date fechaHora = (Date) spnFechaHoraIncCIta.getValue();
            LocalDate fecha = LocalDate.now(); // Por simplicidad, usar fecha actual
            
            // Convertir Date a LocalTime de forma segura
            @SuppressWarnings("deprecation")
            LocalTime horaInicio = LocalTime.of(fechaHora.getHours(), fechaHora.getMinutes());
            LocalTime horaFin = horaInicio.plusHours(1); // 1 hora de duración
            
            cita.setFechaCita(fecha);
            cita.setHoraInicio(horaInicio);
            cita.setHoraFin(horaFin);
            cita.setIdEstadoCita(1); // Estado activo
            
            // Obtener ID del tipo de atención seleccionado
            String tipoAtencionSeleccionado = (String) cbxAtencionCita.getSelectedItem();
            int idTipoAtencion = queryCita.obtenerIdTipoAtencionPorNombre(tipoAtencionSeleccionado);
            cita.setIdTipoAtencion(idTipoAtencion);
            
            // Guardar en la base de datos
            queryCita.Insetar(cita);
            
            // Limpiar formulario después de guardar
            limpiarFormulario();
            
            // Notificar al panel padre para actualizar la tabla
            Container parent = getParent();
            while (parent != null && !(parent instanceof JPanel && parent.getClass().getSimpleName().equals("PanelCitasMedicas"))) {
                parent = parent.getParent();
            }
            if (parent != null) {
                // Si encontramos el panel padre, podríamos llamar a un método para actualizar
                // Por ahora, solo mostramos mensaje de éxito
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "DNI debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtDNIPaciente.setText("");
        lblMuestraNomPaciente.setText("-");
        cbxAtencionCita.setSelectedIndex(0);
        spnFechaHoraIncCIta.setValue(new Date());
        cbxEspecialidad.setSelectedIndex(0);
        lblMuestraConsultorio.setText("-");
        cargarDatos(); // Recargar datos
    }
}

