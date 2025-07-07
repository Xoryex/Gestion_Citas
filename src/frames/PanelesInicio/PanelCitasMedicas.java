package PanelesInicio;
import HerramientasCitas.pnlDatosCita;
import HerramientasCitas.pnlModificarDatosCita;
import HerramientasCitas.pnlTablaCitas;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class PanelCitasMedicas extends JPanel {
    //agregar
    pnlDatosCita AgregarCita = new pnlDatosCita();

    CardLayout AgregarDatosCita;
    //modificar/reprogramar
    pnlModificarDatosCita Reprogramar = new pnlModificarDatosCita();

    //Ver Tablas de citas
    pnlTablaCitas VerCitas = new pnlTablaCitas();

    //Paneles
    private JPanel pnlCabeceraCitasMedicas;
    private JPanel pnlTablaCitasMedicas;

    public PanelCitasMedicas() {
        setLayout(new BorderLayout());

        pnlCabeceraCitasMedicas = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblBuscarCita = new JLabel("Buscar:");
        JTextField txtBuscarCita = new JTextField(15);

        JButton btnBuscarCita = new JButton("Buscar");
        JButton btnAgregarCita = new JButton("Agregar");
        JButton btnReprogramarCita = new JButton("Reprogramar");
        JButton btnAnularCita = new JButton("Anular");
        JButton btnVerTablaCita = new JButton("Ver Tabla");

        pnlCabeceraCitasMedicas.add(lblBuscarCita);
        pnlCabeceraCitasMedicas.add(txtBuscarCita);
        pnlCabeceraCitasMedicas.add(btnBuscarCita);
        pnlCabeceraCitasMedicas.add(btnAgregarCita);
        pnlCabeceraCitasMedicas.add(btnReprogramarCita);
        pnlCabeceraCitasMedicas.add(btnAnularCita);
        pnlCabeceraCitasMedicas.add(btnVerTablaCita);

        // Cambia el layout a CardLayout
        pnlTablaCitasMedicas = new JPanel(new CardLayout());

        add(pnlCabeceraCitasMedicas, BorderLayout.NORTH);
        add(pnlTablaCitasMedicas, BorderLayout.CENTER);

        // Inicializa el CardLayout después de crear el panel
        AgregarDatosCita = (CardLayout) pnlTablaCitasMedicas.getLayout();

        // Agrega los paneles al CardLayout solo una vez
        pnlTablaCitasMedicas.add(AgregarCita, "Agregar");
        pnlTablaCitasMedicas.add(Reprogramar, "Reprogramar");
        pnlTablaCitasMedicas.add(VerCitas, "Ver Tabla");

        // Mostrar la tabla de citas al iniciar
        AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");

        // Acción del botón Agregar
        btnAgregarCita.addActionListener(evt -> {
            AgregarDatosCita.show(pnlTablaCitasMedicas, "Agregar");
            SwingUtilities.updateComponentTreeUI(this);
            this.repaint();
        });

        // Acción del botón Reprogramar
        btnReprogramarCita.addActionListener(evt -> {
            AgregarDatosCita.show(pnlTablaCitasMedicas, "Reprogramar");
            SwingUtilities.updateComponentTreeUI(this);
            this.repaint();
        });

        // Acción del botón ver la tabla
        btnVerTablaCita.addActionListener(evt -> {
            AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
            SwingUtilities.updateComponentTreeUI(this);
            this.repaint();
        });
    }
}


