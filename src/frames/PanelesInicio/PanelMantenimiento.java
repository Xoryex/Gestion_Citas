// Archivo: PanelesInicio/PanelMantenimiento.java
package PanelesInicio;

import javax.swing.*;

import VentanasMantenimiento.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelMantenimiento extends JPanel {
    
    // Componentes principales
    private JComboBox<String> cmbMantenimiento;
    private JPanel pnlContenedor;
    private CardLayout cardLayout;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    
    // Paneles individuales (instancias de las clases en VentanasMantenimiento)
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
        
        // Estilo de botones
        styleButton(btnAgregar, new Color(70, 130, 180));
        styleButton(btnActualizar, new Color(70, 130, 180));
        styleButton(btnEliminar, new Color(70, 130, 180));
        
        pnlSuperior.add(lblMantenimiento);
        pnlSuperior.add(cmbMantenimiento);
        pnlSuperior.add(btnAgregar);
        pnlSuperior.add(btnActualizar);
        pnlSuperior.add(btnEliminar);
        
        // Panel contenedor con CardLayout
        cardLayout = new CardLayout();
        pnlContenedor = new JPanel(cardLayout);
        pnlContenedor.setBackground(Color.WHITE);
        
        // Crear e inicializar paneles individuales
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
        // Instanciar todos los paneles de mantenimiento
        panelRecepcionista = new pnlRecepcionistaMant();
        panelConsultorio = new pnlConsultorioMant();
        panelDoctor = new pnlDoctorMant();
        panelEspecialidad = new pnlEspecialidadMant();
        panelHorario = new pnlHorarioMant();
        panelPaciente = new pnlPacienteMant();
        
        // Agregar paneles al CardLayout
        pnlContenedor.add(panelRecepcionista, "Recepcionista");
        pnlContenedor.add(panelConsultorio, "Consultorio");
        pnlContenedor.add(panelDoctor, "Doctor");
        pnlContenedor.add(panelEspecialidad, "Especialidad");
        pnlContenedor.add(panelHorario, "Horario");
        pnlContenedor.add(panelPaciente, "Paciente");
    }
    
    private void setupEventListeners() {
        // Evento para cambiar panel según selección del ComboBox
        cmbMantenimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) cmbMantenimiento.getSelectedItem();
                cardLayout.show(pnlContenedor, seleccion);
            }
        });
        
        // Eventos para botones (puedes personalizar según tu lógica)
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
    }
    
    // Métodos para manejo de botones (personalizar según tu lógica)
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

    
    // Método para cargar datos de ejemplo en todos los paneles
    public void agregarDatosEjemplo() {
        panelRecepcionista.agregarDatosEjemplo();
        panelConsultorio.agregarDatosEjemplo();
        panelDoctor.agregarDatosEjemplo();
        panelEspecialidad.agregarDatosEjemplo();
        panelHorario.agregarDatosEjemplo();
        panelPaciente.agregarDatosEjemplo();
    }
    
    // Getters para acceder a los paneles desde otras clases
    public pnlRecepcionistaMant getPanelRecepcionista() { return panelRecepcionista; }
    public pnlConsultorioMant getPanelConsultorio() { return panelConsultorio; }
    public pnlDoctorMant getPanelDoctor() { return panelDoctor; }
    public pnlEspecialidadMant getPanelEspecialidad() { return panelEspecialidad; }
    public pnlHorarioMant getPanelHorario() { return panelHorario; }
    public pnlPacienteMant getPanelPaciente() { return panelPaciente; }
    
    // Getter para el ComboBox
    public JComboBox<String> getCmbMantenimiento() { return cmbMantenimiento; }
    
    // Getters para los botones
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
}