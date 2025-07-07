package PanelesInicio;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PanelConfiguracion extends JPanel {
    private JPanel pnlCabeceraConf;
    private JPanel pnlBotonConf;

    // Declaración de labels y textfields
    private JLabel lblDNIConf;
    private JTextField txtDNIConf;
    private JLabel lblApellidoConf;
    private JTextField txtApellidoConf;
    private JLabel lblNombreConf;
    private JTextField txtNombreConf;
    private JLabel lblTelefConf;
    private JTextField txtTelefConf;
    private JLabel lblContrasenaActual;
    private JTextField txtlContrasenaActual;
    private JLabel lblContrasenaNuevaConf;
    private JTextField txtContrasenaNuevaConf;
    private JLabel lblConfirmarContrasenaConf;
    private JTextField txtConfirmarContrasenaConf;

    // Declaración de botones
    private JButton btnCancelarConf;
    private JButton btnEliminarUsuario;
    private JButton btnAceptarConf;

    public PanelConfiguracion() {
        setLayout(new BorderLayout());

        // Panel cabecera con GridLayout 8 filas, 2 columnas
        pnlCabeceraConf = new JPanel(new GridLayout(8, 2, 5, 5));
        pnlCabeceraConf.setBorder(new EmptyBorder(50, 200, 0, 200)); // [top, left, bottom, right]

        lblDNIConf = new JLabel("DNI:");
        txtDNIConf = new JTextField();
        lblApellidoConf = new JLabel("Apellido:");
        txtApellidoConf = new JTextField();
        lblNombreConf = new JLabel("Nombre:");
        txtNombreConf = new JTextField();
        lblTelefConf = new JLabel("Teléfono:");
        txtTelefConf = new JTextField();
        lblContrasenaActual = new JLabel("Contraseña actual:");
        txtlContrasenaActual = new JTextField();
        lblContrasenaNuevaConf = new JLabel("Contraseña nueva:");
        txtContrasenaNuevaConf = new JTextField();
        lblConfirmarContrasenaConf = new JLabel("Confirmar contraseña:");
        txtConfirmarContrasenaConf = new JTextField();

        // Añadir componentes al panel cabecera
        pnlCabeceraConf.add(lblDNIConf);
        pnlCabeceraConf.add(txtDNIConf);
        pnlCabeceraConf.add(lblApellidoConf);
        pnlCabeceraConf.add(txtApellidoConf);
        pnlCabeceraConf.add(lblNombreConf);
        pnlCabeceraConf.add(txtNombreConf);
        pnlCabeceraConf.add(lblTelefConf);
        pnlCabeceraConf.add(txtTelefConf);
        pnlCabeceraConf.add(lblContrasenaActual);
        pnlCabeceraConf.add(txtlContrasenaActual);
        pnlCabeceraConf.add(lblContrasenaNuevaConf);
        pnlCabeceraConf.add(txtContrasenaNuevaConf);
        pnlCabeceraConf.add(lblConfirmarContrasenaConf);
        pnlCabeceraConf.add(txtConfirmarContrasenaConf);

        // Panel de botones con FlowLayout
        pnlBotonConf = new JPanel(new FlowLayout());

        btnCancelarConf = new JButton("Cancelar");
        btnEliminarUsuario = new JButton("Eliminar Usuario");
        btnAceptarConf = new JButton("Aceptar");

        pnlBotonConf.add(btnCancelarConf);
        pnlBotonConf.add(btnEliminarUsuario);
        pnlBotonConf.add(btnAceptarConf);

        add(pnlCabeceraConf, BorderLayout.NORTH);
        add(pnlBotonConf, BorderLayout.CENTER);
    }
}
