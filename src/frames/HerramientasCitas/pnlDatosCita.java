package HerramientasCitas;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class pnlDatosCita extends JPanel {

    private JTextField txtDNIPaciente;
    private JLabel lblMuestraNomPaciente;
    private JComboBox<String> cbxAtencionCita;
    private JSpinner spnFechaHoraIncCIta;
    private JComboBox<String> cbxEspecialidad;
    private JComboBox<String> cbxDoctor;
    private JLabel lblMuestraConsultorio;
    private JButton btnCancelar;
    private JButton btnGuardar;

    public pnlDatosCita() {
        Componentes();
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
        cbxAtencionCita = new JComboBox<>(new String[]{"Consulta", "Emergencia", "Control"});
        spnFechaHoraIncCIta = new JSpinner(new SpinnerDateModel());
        cbxEspecialidad = new JComboBox<>(new String[]{"Pediatría", "Cardiología", "Dermatología"});
        cbxDoctor = new JComboBox<>(new String[]{"Dr. Juan", "Dra. Ana", "Dr. Luis"});
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
}

