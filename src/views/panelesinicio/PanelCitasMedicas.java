package views.panelesinicio;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import querys.QueryCita;
import views.herramientascitas.*;

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
                pnlAgregarCita.cargarDatos();
                cardLayoutDatosCita.show(pnlContenidoCentral, "Agregar");
            }
        });

        btnReprogramarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutDatosCita.show(pnlContenidoCentral, "Reprogramar");
            }
        }); 
        
        

        btnFinalizarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    
                    int fila = TablaCitasPendientesReprogramadas.tablaCitas.getSelectedRow(); // Obtiene la fila seleccionada
                    if (fila != -1) {
                        Object valor = TablaCitasPendientesReprogramadas.tablaCitas.getValueAt(fila, 0); // Columna 0 = primer elemento
                        String idCita = valor.toString(); // Convierte el valor a String
                        queryCita.finalizarCita(idCita);}
                }
        });

        txtBuscarCita.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                TablaCitasPendientesReprogramadas.modelo.setRowCount(0);
                ArrayList<String[]> datos = queryCita.seleccionarConFiltro(txtBuscarCita.getText());
                for (String[] fila : datos) {
                    TablaCitasPendientesReprogramadas.modelo.addRow(fila);
                }
            }
        });
        
    }
}