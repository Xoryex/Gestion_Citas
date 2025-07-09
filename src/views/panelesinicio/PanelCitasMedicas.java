package views.panelesinicio;

import java.awt.*;
import javax.swing.*;
import querys.QueryCita;
import views.herramientascitas.*;


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
    
    // Componentes de búsqueda
    private JTextField txtBuscarCita;
    private JButton btnBuscarCita;
    private JButton btnAgregarCita;
    private JButton btnReprogramarCita;
    private JButton btnAnularCita;
    private JButton btnVerTablaCita;
    
    private QueryCita queryCita;

    public PanelCitasMedicas() {
        queryCita = new QueryCita();
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
    }
}

    /* 
    private void configurarEventos() {
        // Acción del botón Buscar
        btnBuscarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCitas();
            }
        });
        
        
        // Permitir buscar con Enter en el campo de texto
        txtBuscarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCitas();
            }
        });

        // Acción del botón Agregar
        btnAgregarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarDatosCita.show(pnlTablaCitasMedicas, "Agregar");
                SwingUtilities.updateComponentTreeUI(PanelCitasMedicas.this);
                PanelCitasMedicas.this.repaint();
            }
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
        
        // Acción del botón Anular
        btnAnularCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anularCita();
            }
        });

        // Acción del botón ver la tabla
        btnVerTablaCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
                VerCitas.actualizarTabla(); // Actualizar datos al mostrar la tabla
                SwingUtilities.updateComponentTreeUI(pnlTablaCitasMedicas);
            }
        });
    }
    
    private void buscarCitas() {
        String filtro = txtBuscarCita.getText().trim();
        if (!filtro.isEmpty()) {
            VerCitas.buscarCitas(filtro);
            AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un término de búsqueda", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void anularCita() {
        // Verificar que estemos en la vista de tabla
        AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
        
        // Obtener el ID de la cita seleccionada
        String idCitaSeleccionada = VerCitas.getIdCitaSeleccionada();
        
        if (idCitaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cita para anular", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea anular la cita seleccionada?\nID: " + idCitaSeleccionada,
            "Confirmar Anulación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            queryCita.anularCita(idCitaSeleccionada);
            VerCitas.actualizarTabla(); // Actualizar la tabla después de anular
        }
    }
    
    public void actualizarTablaCitas() {
        VerCitas.actualizarTabla();
    }
    
    public void mostrarPanelAgregar() {
        AgregarDatosCita.show(pnlTablaCitasMedicas, "Agregar");
    }
    
    public void mostrarPanelTabla() {
        AgregarDatosCita.show(pnlTablaCitasMedicas, "Ver Tabla");
        VerCitas.actualizarTabla();
    }
}
*/

