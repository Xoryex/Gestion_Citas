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

    private pnlTblDoctoresMasCitas consltDoctoresMasCitas = new pnlTblDoctoresMasCitas();
    private pnlTblPacientesFrecuentes consltPacientesFrecuentes = new pnlTblPacientesFrecuentes();
    private pnlTblResumenCitasEstado consltResumenCitasEstado = new pnlTblResumenCitasEstado();
    private pnlTblConsultoriosMasUtilizados consltConsultoriosMasUtilizados =   new pnlTblConsultoriosMasUtilizados();  
    private pnlTblHorariosMasOcupados consltHorariosMasOcupados = new pnlTblHorariosMasOcupados();
    private pnlTblEspecialidadesMasSolicitadas consltEspecialidadesMasSolicitadas = new pnlTblEspecialidadesMasSolicitadas();   



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
                    case "Doctores con más citas":
                        cargarDatosDoctoresMasCitas(conn, filtro);
                        break;
                    case "Pacientes frecuentes":
                        cargarDatosPacientesFrecuentes(conn, filtro);
                        break;  
                    case "Resumen de citas por estado":
                        cargarDatosResumenCitasEstado(conn, filtro);
                        break;
                    case "Consultorios más utilizados": 
                        cargarDatosConsultoriosMasUtilizados(conn, filtro);
                        break;
                    case "Horarios más ocupados":
                        cargarDatosHorariosMasOcupados(conn, filtro);
                        break;
                    case "Especialidades más solicitadas":  
                        cargarDatosEspecialidadesMasSolicitadas(conn, filtro);
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
        String sql = "{CALL PA_CRUD_ListarCitasConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltCita.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosRecepcionista(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarRecepcionistaConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltRecepcionista.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosDoctor(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarDoctorConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltDoctor.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosPaciente(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarPacienteConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltPaciente.cargarDatos(rs);
            rs.close();
        }
    }

    private void cargarDatosHorario(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarHorarioConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltHorario.cargarDatos(rs);
            rs.close();
        }
    }

        // 1. Doctores con más citas
    private void cargarDatosDoctoresMasCitas(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarDoctoresMasCitasConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltDoctoresMasCitas.cargarDatos(rs);
            rs.close();
        }
    }

    // 2. Pacientes frecuentes
    private void cargarDatosPacientesFrecuentes(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarPacientesFrecuentesConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltPacientesFrecuentes.cargarDatos(rs);
            rs.close();
        }
    }

    // 3. Resumen de citas por estado
    private void cargarDatosResumenCitasEstado(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarResumenCitasPorEstadoConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltResumenCitasEstado.cargarDatos(rs);
            rs.close();
        }
    }

    // 4. Consultorios más utilizados
    private void cargarDatosConsultoriosMasUtilizados(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarConsultoriosMasUtilizadosConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltConsultoriosMasUtilizados.cargarDatos(rs);
            rs.close();
        }
    }

    // 5. Horarios más ocupados
    private void cargarDatosHorariosMasOcupados(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarHorariosMasOcupadosConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltHorariosMasOcupados.cargarDatos(rs);
            rs.close();
        }
    }

    // 6. Especialidades más solicitadas
    private void cargarDatosEspecialidadesMasSolicitadas(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarEspecialidadesMasSolicitadasConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltEspecialidadesMasSolicitadas.cargarDatos(rs);
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