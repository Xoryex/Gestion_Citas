package views.panelesinicio;

import views.herramientasconsultareporte.*;
import java.awt.*;
import javax.swing.*;

import utils.Conexion;

import java.awt.event.*;
import java.sql.*;

public class PanelConsulta extends JPanel {
    // Paneles específicos para cada tipo de consulta
    private pnlTblCita consltCita = new pnlTblCita();
    private pnlTblRecepcionista consltRecepcionista = new pnlTblRecepcionista();
    private pnlTblDoctor consltDoctor = new pnlTblDoctor();
    private pnlTblPaciente consltPaciente = new pnlTblPaciente();
    private pnlTblHorario consltHorario = new pnlTblHorario();

    private Connection conn = Conexion.getConnection();

    // Layout para cambiar entre paneles
    private CardLayout tablaConsult;
    private JPanel pnlVentanasConsultas;

    // Panel de cabecera
    private JPanel pnlCabeceraConsult;
    // Componentes de cabecera
    private JLabel lblConsltPor;
    private JComboBox<String> cbxConsulta;
    private JLabel lblBuscarConsult;
    private JTextField txtBuscarConsult;
    private JButton btnBuscar;
    private JButton btnLimpiar;


    public PanelConsulta() {
     
        // Configurar layout principal
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Crear cabecera
        crearCabecera();
        
        // Crear panel de contenido
        crearPanelContenido();
        
        // Configurar eventos
        configurarEventos();
        
        // Cargar datos iniciales
        cargarDatosIniciales();
    }

    private void crearCabecera() {
        pnlCabeceraConsult = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCabeceraConsult.setBackground(new Color(240, 240, 240));

        // Componentes
        lblConsltPor = new JLabel("Consultar por:");
        lblConsltPor.setFont(new Font("Arial", Font.PLAIN, 12));

        cbxConsulta = new JComboBox<>(new String[] {
            "Cita", "Recepcionista", "Doctor", "Paciente", "Horario"
        });
        cbxConsulta.setPreferredSize(new Dimension(150, 25));

        lblBuscarConsult = new JLabel("Buscar:");
        lblBuscarConsult.setFont(new Font("Arial", Font.PLAIN, 12));

        txtBuscarConsult = new JTextField(20);
        txtBuscarConsult.setPreferredSize(new Dimension(250, 25));

        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        // Estilo de botones
        estilizarBoton(btnBuscar, new Color(70, 130, 180));
        estilizarBoton(btnLimpiar, new Color(220, 20, 60));

        // Agregar componentes al panel
        pnlCabeceraConsult.add(lblConsltPor);
        pnlCabeceraConsult.add(cbxConsulta);
        pnlCabeceraConsult.add(lblBuscarConsult);
        pnlCabeceraConsult.add(txtBuscarConsult);
        pnlCabeceraConsult.add(btnBuscar);
        pnlCabeceraConsult.add(btnLimpiar);

        add(pnlCabeceraConsult, BorderLayout.NORTH);
    }

    private void crearPanelContenido() {
        // Panel con CardLayout para las tablas
        tablaConsult = new CardLayout();
        pnlVentanasConsultas = new JPanel(tablaConsult);
        pnlVentanasConsultas.setBackground(Color.WHITE);

        // Agregar paneles al CardLayout
        pnlVentanasConsultas.add(consltCita, "Cita");
        pnlVentanasConsultas.add(consltRecepcionista, "Recepcionista");
        pnlVentanasConsultas.add(consltDoctor, "Doctor");
        pnlVentanasConsultas.add(consltPaciente, "Paciente");
        pnlVentanasConsultas.add(consltHorario, "Horario");

        add(pnlVentanasConsultas, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        // Evento para cambiar tabla según selección del ComboBox
        cbxConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) cbxConsulta.getSelectedItem();
                if (seleccion != null) {
                    tablaConsult.show(pnlVentanasConsultas, seleccion);
                    cargarDatosTabla(seleccion, ""); // Cargar todos los datos
                    limpiarBusqueda();
                }
            }
        });

        // Evento para botón buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });

        // Evento para botón limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarBusqueda();
                String seleccion = (String) cbxConsulta.getSelectedItem();
                if (seleccion != null) {
                    cargarDatosTabla(seleccion, ""); // Cargar todos los datos
                }
            }
        });

        // Evento para buscar al presionar Enter en el campo de texto
        txtBuscarConsult.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarBusqueda();
                }
            }
        });
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 11));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void cargarDatosIniciales() {
        // Mostrar tabla de citas por defecto
        tablaConsult.show(pnlVentanasConsultas, "Cita");
        cargarDatosTabla("Cita", "");
    }

    private void realizarBusqueda() {
        String textoBusqueda = txtBuscarConsult.getText().trim();
        String tipoConsulta = (String) cbxConsulta.getSelectedItem();
        
        if (tipoConsulta != null) {
            cargarDatosTabla(tipoConsulta, textoBusqueda);
        }
    }

    private void cargarDatosTabla(String tipoTabla, String filtro) {
        try {
            
            if (conn != null) {
                switch (tipoTabla) {
                    case "Cita":
                        cargarDatosCita(conn, filtro);
                        break;
                    case "Recepcionista":
                        cargarDatosRecepcionista(conn, filtro);
                        break;
                    case "Doctor":
                        cargarDatosDoctor(conn, filtro);
                        break;
                    case "Paciente":
                        cargarDatosPaciente(conn, filtro);
                        break;
                    case "Horario":
                        cargarDatosHorario(conn, filtro);
                        break;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosCita(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL sp_ConsultarCitas(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltCita.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosRecepcionista(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL sp_ConsultarRecepcionistas(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltRecepcionista.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosDoctor(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL sp_ConsultarDoctores(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltDoctor.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosPaciente(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL sp_ConsultarPacientes(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltPaciente.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosHorario(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL sp_ConsultarHorarios(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltHorario.cargarDatos(rs);
            rs.close();
        }
    }

    // Método público para limpiar búsqueda
    public void limpiarBusqueda() {
        txtBuscarConsult.setText("");
    }

    // Getters útiles
    public String getTextoBusqueda() {
        return txtBuscarConsult.getText().trim();
    }

    public String getTipoConsultaSeleccionado() {
        return (String) cbxConsulta.getSelectedItem();
    }

    public JComboBox<String> getCbxConsulta() {
        return cbxConsulta;
    }

    public JTextField getTxtBuscarConsult() {
        return txtBuscarConsult;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}