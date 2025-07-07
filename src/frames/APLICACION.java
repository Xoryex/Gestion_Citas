import PanelesInicio.PanelCitasMedicas;
import PanelesInicio.PanelConfiguracion;
import PanelesInicio.PanelConsulta;
import PanelesInicio.PanelInicio;
import PanelesInicio.PanelMantenimiento;
import PanelesInicio.PanelReporte;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class APLICACION extends JFrame {
    private JLabel lblNomRecepc;
    private JLabel lblFuncion;
    private JLabel lblNombre;
    private JTabbedPane tbdpnInicio;

    public APLICACION() {
        setTitle("GESTION DE CITAS");
        setSize(1220, 663);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL CABECERA DEL FRAME
        JPanel pnlCabecera = new JPanel(new BorderLayout());
        pnlCabecera.setPreferredSize(new Dimension(1220, 100)); // Cambiado de 794 a 1220
        pnlCabecera.setBackground(new Color(41, 75, 99));
        pnlCabecera.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Etiquetas en cabecera
        lblNomRecepc = new JLabel("CORPOMEDIC");
        lblNomRecepc.setFont(new Font("Arial", Font.BOLD, 24));
        lblNomRecepc.setForeground(Color.WHITE);

        lblFuncion = new JLabel("Gestion de Citas Medicas");
        lblFuncion.setFont(new Font("Arial", Font.PLAIN, 16));
        lblFuncion.setForeground(Color.WHITE);

        lblNombre = new JLabel("AMYLING RACHEL AZANG VILCHEZ");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);

        // Panel interno para agrupar los labels de la izquierda
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(41, 75, 99));
        panelIzquierdo.add(lblNomRecepc);
        panelIzquierdo.add(lblFuncion);

        pnlCabecera.add(panelIzquierdo, BorderLayout.WEST);
        pnlCabecera.add(lblNombre, BorderLayout.EAST);

        add(pnlCabecera, BorderLayout.NORTH);

        // PANEL RELLENO DEL FRAME
        JPanel pnlRelleno = new JPanel(new BorderLayout());

        // TABBED PANE
        tbdpnInicio = new JTabbedPane();
        tbdpnInicio.addTab("INICIO", new PanelInicio());
        tbdpnInicio.addTab("CITAS MEDICAS", new PanelCitasMedicas());
        tbdpnInicio.addTab("CONFIGURACIÓN", new PanelConfiguracion());
        tbdpnInicio.addTab("MANTENIMIENTO", new PanelMantenimiento());
        tbdpnInicio.addTab("CONSULTAS", new PanelConsulta());
        tbdpnInicio.addTab("REPORTE", new PanelReporte());

        pnlRelleno.add(tbdpnInicio, BorderLayout.CENTER);
        add(pnlRelleno, BorderLayout.CENTER);
    }
}