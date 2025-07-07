package HerramientasCitas;

import java.awt.*;
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
        cbxAtencionCita = new JComboBox<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" });
        txtFechaCita = new JTextField();
        txtHoraInicio = new JTextField();
        txtHoraFin = new JTextField();
        cbxEspecialidad = new JComboBox<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" });
        cbxDoctor = new JComboBox<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" });
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
}
