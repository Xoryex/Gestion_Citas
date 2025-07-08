package views.panelesinicio;

import views.herramientascitas.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;;


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
        btnAgregarCita.addActionListener(e -> {
            AgregarDatosCita.show(pnlTablaCitasMedicas, "Agregar");
            SwingUtilities.updateComponentTreeUI(PanelCitasMedicas.this);
            PanelCitasMedicas.this.repaint();
        });

        // Acción del botón Reprogramar
        btnReprogramarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarDatosCita.show(pnlTablaCitasMedicas, "Reprogramar");
                SwingUtilities.updateComponentTreeUI(PanelCitasMedicas.this);
                PanelCitasMedicas.this.repaint();
            }
        });

        // Acción del botón ver la tabla
        btnVerTablaCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
                SwingUtilities.updateComponentTreeUI(pnlTablaCitasMedicas);
            }
        });
    }
}


