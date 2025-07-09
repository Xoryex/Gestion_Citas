package views.panelesinicio;

import views.herramientascitas.*;
//import querys.QueryCita;
import java.awt.*;
import javax.swing.*;
//import java.awt.event.*;


public class PanelCitasMedicas extends JPanel {

    pnlDatosCita AgregarCita = new pnlDatosCita();
    CardLayout AgregarDatosCita;
   
    pnlModificarDatosCita Reprogramar = new pnlModificarDatosCita();

    pnlTablaCitas VerCitas = new pnlTablaCitas();

    private JPanel pnlCabeceraCitasMedicas;
    private JPanel pnlTablaCitasMedicas;
    
    private JTextField txtBuscarCita;
    private JButton btnBuscarCita;
    private JButton btnAgregarCita;
    private JButton btnReprogramarCita;
    private JButton btnAnularCita;
    private JButton btnVerTablaCita;
    
    //private QueryCita queryCita = new QueryCita();




    public PanelCitasMedicas() {
        initComponents();
        //configurarEventos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());

        pnlCabeceraCitasMedicas = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblBuscarCita = new JLabel("Buscar:");
        txtBuscarCita = new JTextField(15);

        btnBuscarCita = new JButton("Buscar");
        btnAgregarCita = new JButton("Agregar");
        btnReprogramarCita = new JButton("Reprogramar");
        btnAnularCita = new JButton("Anular");
        btnVerTablaCita = new JButton("Ver Tabla");

        pnlCabeceraCitasMedicas.add(lblBuscarCita);
        pnlCabeceraCitasMedicas.add(txtBuscarCita);
        pnlCabeceraCitasMedicas.add(btnBuscarCita);
        pnlCabeceraCitasMedicas.add(btnAgregarCita);
        pnlCabeceraCitasMedicas.add(btnReprogramarCita);
        pnlCabeceraCitasMedicas.add(btnAnularCita);
        pnlCabeceraCitasMedicas.add(btnVerTablaCita);

        pnlTablaCitasMedicas = new JPanel(new CardLayout());

        add(pnlCabeceraCitasMedicas, BorderLayout.NORTH);
        add(pnlTablaCitasMedicas, BorderLayout.CENTER);

        AgregarDatosCita = (CardLayout) pnlTablaCitasMedicas.getLayout();

        pnlTablaCitasMedicas.add(AgregarCita, "Agregar");
        pnlTablaCitasMedicas.add(Reprogramar, "Reprogramar");
        pnlTablaCitasMedicas.add(VerCitas, "Ver Tabla");

        AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
    }
}

    /* 
    private void configurarEventos() {
    }
    
    

}
*/

