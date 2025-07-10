package views.herramientascitas;

import models.*;
import querys.QueryCita;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class pnlDatosCita extends JPanel {

    private JTextField txtDNIPaciente;
    private JLabel lblMuestraNomPaciente;
    private JComboBox<String> cbxAtencionCita;
    private JSpinner spnFechaHoraIncCIta;
    private JComboBox<String> cbxEspecialidad;
    private JComboBox<String> cbxDoctor;
    private JLabel lblMuestraConsultorio;
    private JButton btnGuardar;
    
    private QueryCita queryCita = new QueryCita();
    private ArrayList<Doctor> doctores ;
    private ArrayList<Especialidad> especialidades ;
    private Paciente paciente;
    private ArrayList<TipoAtencion> atenciones;


    public pnlDatosCita() {
        Componentes();
        oyentes();
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
        spnFechaHoraIncCIta = new JSpinner(new SpinnerDateModel(new Date(), new Date(), null, Calendar.DAY_OF_MONTH));
        spnFechaHoraIncCIta.setEditor(new JSpinner.DateEditor(spnFechaHoraIncCIta, "dd/MM/yyyy HH:mm"));
        ((SpinnerDateModel)spnFechaHoraIncCIta.getModel()).setCalendarField(Calendar.MINUTE);

        cbxEspecialidad = new JComboBox<>();
        cbxDoctor = new JComboBox<>();
        lblMuestraConsultorio = new JLabel("-");
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
        btnGuardar.setFont(fuente);

        lblMuestraNomPaciente.setHorizontalAlignment(SwingConstants.CENTER);
        lblMuestraConsultorio.setHorizontalAlignment(SwingConstants.CENTER);

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

        pnlFormulario.add(btnGuardar);

        add(pnlFormulario, BorderLayout.CENTER);
    }
    

    private void oyentes(){
        cbxEspecialidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDoctores();
            }
        });
        spnFechaHoraIncCIta.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cargarDoctores();
            }
        });
        txtDNIPaciente.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar()) || txtDNIPaciente.getText().length() >= 8) {
                    evt.consume();
                }
            }
            public void keyReleased(KeyEvent evt) {
                if(txtDNIPaciente.getText().length() == 8){
                    paciente = queryCita.getPaciente(Integer.parseInt(txtDNIPaciente.getText()));
                    if(paciente != null){
                        lblMuestraNomPaciente.setText(paciente.getNombre() + " " + paciente.getApellido());
                    }else if (txtDNIPaciente.getText().length() < 8){
                        lblMuestraNomPaciente.setText("Dni incompleto");
                    }else{
                        lblMuestraNomPaciente.setText("Paciente no encontrado");
                    }
                }
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                queryCita.Insetar(
                
            }
        });
    }




    public void cargarDatos(){
        txtDNIPaciente.setText("");
        lblMuestraNomPaciente.setText("-");
        cbxAtencionCita.setSelectedIndex(0);
        spnFechaHoraIncCIta.setValue(new Date());
        cbxEspecialidad.removeAllItems();
        cbxDoctor.removeAllItems();

        atenciones = queryCita.getTiposAtencion();
        for(TipoAtencion atencion : atenciones){
            cbxAtencionCita.addItem(atencion.getNombreTipoAtencion());
        }

        especialidades = queryCita.getEspecialidadesActivas();
        for(Especialidad especialidad : especialidades){
            cbxEspecialidad.addItem(especialidad.getNombreEspecialidad());
        }
        
    }

    private void cargarDoctores(){
        cbxDoctor.removeAllItems();
        
        for(Especialidad especialidad : especialidades){
            if(especialidad.getNombreEspecialidad().equals(cbxEspecialidad.getSelectedItem())){
                doctores = queryCita.getDoctoresFiltrados(especialidad,new java.sql.Date(((java.util.Date)spnFechaHoraIncCIta.getValue()).getTime()));
                break;
            }
        }

        for(Doctor doctor : doctores){
            cbxDoctor.addItem(doctor.getNombre());
        }

}

// Método auxiliar para obtener la fecha del spinner

    
}

