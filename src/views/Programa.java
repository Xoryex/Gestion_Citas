package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import static querys.QueryUser.usuario_actual;
import views.panelesinicio.*;

public class Programa extends JFrame {
    private JLabel lblNomRecepc;
    private JLabel lblFuncion;
    private JLabel lblNombre;

    private PanelConfiguracion panelconfiguracion=new PanelConfiguracion();

    private JTabbedPane tbdpnInicio;

    public Programa() {
        setTitle("GESTION DE CITAS");
        setSize(1220, 663);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL CABECERA DEL FRAME
        JPanel pnlCabecera = new JPanel(new BorderLayout());
        pnlCabecera.setPreferredSize(new Dimension(1220, 100));
        pnlCabecera.setBackground(new Color(41, 75, 99));
        pnlCabecera.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Etiquetas en cabecera
        lblNomRecepc = new JLabel("CORPOMEDIC");
        lblNomRecepc.setFont(new Font("Arial", Font.BOLD, 24));
        lblNomRecepc.setForeground(Color.WHITE);

        lblFuncion = new JLabel("Gestion de Citas Medicas");
        lblFuncion.setFont(new Font("Arial", Font.PLAIN, 16));
        lblFuncion.setForeground(Color.WHITE);

        lblNombre = new JLabel();
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);

        // Crear botón de cierre
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setPreferredSize(new Dimension(90, 30));

        // Acción para cerrar el programa
        btnCerrar.addActionListener(e -> System.exit(0));

        // Panel interno para agrupar los labels de la izquierda
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(41, 75, 99));
        panelIzquierdo.add(lblNomRecepc);
        panelIzquierdo.add(lblFuncion);

        // Panel derecho que contiene el nombre y el botón de cerrar
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelDerecho.setBackground(new Color(41, 75, 99));
        panelDerecho.add(lblNombre);
        panelDerecho.add(btnCerrar);

        pnlCabecera.add(panelIzquierdo, BorderLayout.WEST);
        pnlCabecera.add(panelDerecho, BorderLayout.EAST);

        add(pnlCabecera, BorderLayout.NORTH);

        lblNombre.setText(usuario_actual.getNombre() + " " + usuario_actual.getApellido());

        // PANEL RELLENO DEL FRAME
        JPanel pnlRelleno = new JPanel(new BorderLayout());

        // TABBED PANE
        tbdpnInicio = new JTabbedPane();
        tbdpnInicio.addTab("INICIO", new PanelInicio());
        //tbdpnInicio.addTab("CITAS MEDICAS", new PanelCitasMedicas());
        tbdpnInicio.addTab("MANTENIMIENTO", new PanelMantenimiento());
        tbdpnInicio.addTab("CONSULTAS", new PanelConsulta());
        tbdpnInicio.addTab("REPORTE", new PanelReporte());
        tbdpnInicio.addTab("CONFIGURACIÓN", new PanelConfiguracion());

        pnlRelleno.add(tbdpnInicio, BorderLayout.CENTER);
        add(pnlRelleno, BorderLayout.CENTER);

        setVisible(true);
    }
    
   
    private void oyentes(){
        tbdpnInicio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String index = tbdpnInicio.getTitleAt(tbdpnInicio.getSelectedIndex());
                switch (index) {
                    case "INICIO":
                        //
                        break;
                    case "CITAS MEDICAS":
                        //
                        break;
                    case "MANTENIMIENTO":
                        //
                        break;
                    case "CONSULTAS":
                        //
                        break;
                    case "REPORTE":
                        //
                        break;
                    case "CONFIGURACIÓN":
                        panelconfiguracion.actualizarDatos();
                        break;
                }
            }
        });
    }   
}
