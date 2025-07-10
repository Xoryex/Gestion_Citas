package views.panelesinicio;

import views.herramientascitas.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import querys.QueryCita;

/**
 * Panel principal para la gestión de Citas Médicas.
 */
public class PanelCitasMedicas extends JPanel {
    private QueryCita queryCita = new QueryCita();
    private pnlDatosCita pnlAgregarCita = new pnlDatosCita();
    private pnlModificarDatosCita pnlReprogramarCita = new pnlModificarDatosCita();
    private pnlTablaCitas TablaCitasPendientesReprogramadas = new pnlTablaCitas();

    private CardLayout cardLayoutDatosCita;

    private JPanel pnlCabeceraCitasMedicas;
    private JPanel pnlContenidoCentral;

    private JTextField txtBuscarCita;
    private JButton btnIniciarCita;
    private JButton btnFinalizarCita;
    private JButton btnAgregarCita;
    private JButton btnReprogramarCita;
    private JButton btnAnularCita;
    public JButton btnVerTablaCita;
    private JButton btnVerCitasReprogramadas;



    /**
     * Constructor del PanelCitasMedicas.
     */
    public PanelCitasMedicas() {
        initComponents();
        configurarEventos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        pnlCabeceraCitasMedicas = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblBuscarCita = new JLabel("Buscar:");
        txtBuscarCita = new JTextField(15);
        btnIniciarCita = new JButton("Iniciar Cita");
        btnFinalizarCita = new JButton("Finalizar Cita");

        btnAgregarCita = new JButton("Agregar");
        btnReprogramarCita = new JButton("Reprogramar");
        btnAnularCita = new JButton("Anular");
        btnVerTablaCita = new JButton("Ver Tabla");
        btnVerCitasReprogramadas = new JButton(" Ver Citas Reprogramadas");

        pnlCabeceraCitasMedicas.add(lblBuscarCita);
        pnlCabeceraCitasMedicas.add(txtBuscarCita);
        pnlCabeceraCitasMedicas.add(btnAgregarCita);
        pnlCabeceraCitasMedicas.add(btnReprogramarCita);
        pnlCabeceraCitasMedicas.add(btnAnularCita);
        pnlCabeceraCitasMedicas.add(btnIniciarCita);
        pnlCabeceraCitasMedicas.add(btnFinalizarCita);
        pnlCabeceraCitasMedicas.add(btnVerTablaCita);
        pnlCabeceraCitasMedicas.add(btnVerCitasReprogramadas);

        cardLayoutDatosCita = new CardLayout();
        pnlContenidoCentral = new JPanel(cardLayoutDatosCita);
        

        pnlContenidoCentral.add(pnlAgregarCita, "Agregar");
        pnlContenidoCentral.add(pnlReprogramarCita, "Reprogramar");
        pnlContenidoCentral.add(TablaCitasPendientesReprogramadas, "Ver Citas Pendientes Reprogramadas");



        
        add(pnlCabeceraCitasMedicas, BorderLayout.NORTH);
        add(pnlContenidoCentral, BorderLayout.CENTER);

        
    }

    private void configurarEventos() {
        btnVerTablaCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaCitasPendientesReprogramadas.modelo.setRowCount(0);
                
                ArrayList<String[]> datos = queryCita.CitasPendientesReprogramadas();
                for (String[] fila : datos) {
                    TablaCitasPendientesReprogramadas.modelo.addRow(fila);
                }
                cardLayoutDatosCita.show(pnlContenidoCentral, "Ver Citas Pendientes Reprogramadas");
            }
        });

        btnVerCitasReprogramadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablaCitasPendientesReprogramadas.modelo.setRowCount(0);
                
                ArrayList<String[]> datos = queryCita.CitasReprogramadas();
                for (String[] fila : datos) {
                    TablaCitasPendientesReprogramadas.modelo.addRow(fila);
                }
                cardLayoutDatosCita.show(pnlContenidoCentral, "Ver Citas Pendientes Reprogramadas");
                
            }
        });

        btnAgregarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutDatosCita.show(pnlContenidoCentral, "Agregar");
            }
        });

        btnReprogramarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutDatosCita.show(pnlContenidoCentral, "Reprogramar");
            }
        });
        
        
        
    }
}