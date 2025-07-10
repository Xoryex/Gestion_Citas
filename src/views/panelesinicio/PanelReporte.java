package views.panelesinicio;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import utils.Conexion;
import views.herramientasconsultareporte.*;

public class PanelReporte extends JPanel {
    // Paneles específicos para cada tipo de consulta
    private pnlTblCita consltCita = new pnlTblCita();
    private pnlTblRecepcionista consltRecepcionista = new pnlTblRecepcionista();
    private pnlTblDoctor consltDoctor = new pnlTblDoctor();
    private pnlTblPaciente consltPaciente = new pnlTblPaciente();
    private pnlTblHorario consltHorario = new pnlTblHorario();
    private pnlTblDoctoresMasCitas consltDoctoresMasCitas = new pnlTblDoctoresMasCitas();
    private pnlTblPacientesFrecuentes consltPacientesFrecuentes = new pnlTblPacientesFrecuentes();
    private pnlTblResumenCitasEstado consltResumenCitasEstado = new pnlTblResumenCitasEstado();
    private pnlTblConsultoriosMasUtilizados consltConsultoriosMasUtilizados = new pnlTblConsultoriosMasUtilizados();
    private pnlTblHorariosMasOcupados consltHorariosMasOcupados = new pnlTblHorariosMasOcupados();
    private pnlTblEspecialidadesMasSolicitadas consltEspecialidadesMasSolicitadas  = new pnlTblEspecialidadesMasSolicitadas();
    


    private Connection conn = Conexion.getConnection();

    // Layout para cambiar entre paneles
    private CardLayout tablaConsult;
    private JPanel pnlVentanasConsultas;

    // Cabecera y botones
    private JPanel pnlCabeceraConsult;
    private JPanel pnlBotonConsult;

    // Componentes de cabecera
    private JLabel lblConsltPor;
    private JComboBox<String> cbxConsulta;
    private JLabel lblBuscarConsult;
    private JTextField txtBuscarConsult;
    private JButton btnFiltroConsulta;
    private JButton btnLimpiarBusqueda;

    public PanelReporte() {
        setLayout(new BorderLayout());

        // 🔼 Cabecera
        pnlCabeceraConsult = new JPanel(new FlowLayout(FlowLayout.LEFT));

        lblConsltPor = new JLabel("Reporte de:");
        cbxConsulta = new JComboBox<>(new String[] {
            "Cita", "Recepcionista", "Doctor", "Paciente", "Horario", "Doctores Más Citas",
            "Pacientes Frecuentes", "Horarios Más Ocupados", "Resumen Citas Estado",
            "Especialidades Más Solicitadas", "Consultorios Más Utilizados"
        });
        cbxConsulta.setPreferredSize(new Dimension(125, 23));

        lblBuscarConsult = new JLabel("Buscar:");
        txtBuscarConsult = new JTextField(10);
        txtBuscarConsult.setPreferredSize(new Dimension(300, 23));

        btnFiltroConsulta = new JButton("Filtrar");
        btnLimpiarBusqueda = new JButton("Limpiar");

        pnlCabeceraConsult.add(lblConsltPor);
        pnlCabeceraConsult.add(cbxConsulta);
        pnlCabeceraConsult.add(lblBuscarConsult);
        pnlCabeceraConsult.add(txtBuscarConsult);
        pnlCabeceraConsult.add(btnFiltroConsulta);
        pnlCabeceraConsult.add(btnLimpiarBusqueda);

        // 🔁 Panel con CardLayout
        tablaConsult = new CardLayout();
        pnlVentanasConsultas.add(consltCita, "Cita");
        pnlVentanasConsultas.add(consltRecepcionista, "Recepcionista");
        pnlVentanasConsultas.add(consltDoctor, "Doctor");
        pnlVentanasConsultas.add(consltPaciente, "Paciente");
        pnlVentanasConsultas.add(consltHorario, "Horario");
        
        // Agregar nuevos paneles de reportes
        pnlVentanasConsultas.add(consltDoctoresMasCitas, "Doctores Más Citas");
        pnlVentanasConsultas.add(consltPacientesFrecuentes, "Pacientes Frecuentes");
        pnlVentanasConsultas.add(consltHorariosMasOcupados, "Horarios Más Ocupados");
        pnlVentanasConsultas.add(consltResumenCitasEstado, "Resumen Citas Estado");
        pnlVentanasConsultas.add(consltEspecialidadesMasSolicitadas, "Especialidades Más Solicitadas");
        pnlVentanasConsultas.add(consltConsultoriosMasUtilizados, "Consultorios Más Utilizados");

        // 👇 Panel botones adicionales
        pnlBotonConsult = new JPanel(new FlowLayout());
        JButton btnExportar = new JButton("Exportar");
        JButton btnImprimir = new JButton("Imprimir");

        pnlBotonConsult.add(btnExportar);
        pnlBotonConsult.add(btnImprimir);

        // ➕ Añadir todo al panel principal
        add(pnlCabeceraConsult, BorderLayout.NORTH);
        add(pnlBotonConsult, BorderLayout.SOUTH);
        add(pnlVentanasConsultas, BorderLayout.CENTER);

        // 🔁 Eventos
        cbxConsulta.addActionListener(e -> {
            String seleccion = (String) cbxConsulta.getSelectedItem();
            if (seleccion != null) {
                tablaConsult.show(pnlVentanasConsultas, seleccion);
                SwingUtilities.updateComponentTreeUI(pnlVentanasConsultas);
                pnlVentanasConsultas.repaint();
                limpiarBusqueda();
                cargarDatosTabla(seleccion, "");
            }
        });

        btnFiltroConsulta.addActionListener(e -> realizarBusqueda());

        btnLimpiarBusqueda.addActionListener(e -> {
            limpiarBusqueda();
            String seleccion = (String) cbxConsulta.getSelectedItem();
            if (seleccion != null) {
                cargarDatosTabla(seleccion, "");
            }
        });

        txtBuscarConsult.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarBusqueda();
                }
            }
        });

        btnExportar.addActionListener(e -> {
            JTable tabla = obtenerTablaActiva();
            if (tabla != null) {
                exportarTabla(tabla, getTipoConsultaSeleccionado());
            } else {
                JOptionPane.showMessageDialog(null,
                    "No hay tabla para exportar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnImprimir.addActionListener(e -> {
            JTable tabla = obtenerTablaActiva();
            if (tabla != null) {
                try {
                    boolean completo = tabla.print();
                    if (!completo) {
                        JOptionPane.showMessageDialog(null,
                            "La impresión fue cancelada.",
                            "Impresión", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                        "Error al imprimir: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Mostrar por defecto
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
                    case "Doctores Más Citas":
                        cargarDatosDoctoresMasCitas(conn, filtro);
                        break;
                    case "Pacientes Frecuentes":
                        cargarDatosPacientesFrecuentes(conn, filtro);
                        break;
                    case "Horarios Más Ocupados":
                        cargarDatosHorariosMasOcupados(conn, filtro);
                        break;
                    case "Resumen Citas Estado":
                        cargarDatosResumenCitasEstado(conn, filtro);
                        break;
                    case "Especialidades Más Solicitadas":
                        cargarDatosEspecialidadesMasSolicitadas(conn, filtro);
                        break;
                    case "Consultorios Más Utilizados":
                        cargarDatosConsultoriosMasUtilizados(conn, filtro);
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




    private JTable obtenerTablaActiva() {
        String tipo = (String) cbxConsulta.getSelectedItem();

        switch (tipo) {
             case "Cita":
                return consltCita.getTabla();
            case "Recepcionista":
                return consltRecepcionista.getTabla();
            case "Doctor":
                return consltDoctor.getTabla();
            case "Paciente":
                return consltPaciente.getTabla();
            case "Horario":
                return consltHorario.getTabla();
            case "Doctores Más Citas":
                return consltDoctoresMasCitas.getTabla();
            case "Pacientes Frecuentes":
                return consltPacientesFrecuentes.getTabla();
            case "Horarios Más Ocupados":
                return consltHorariosMasOcupados.getTabla();
            case "Resumen Citas Estado":
                return consltResumenCitasEstado.getTabla();
            case "Especialidades Más Solicitadas":
                return consltEspecialidadesMasSolicitadas.getTabla();
            case "Consultorios Más Utilizados":
                return consltConsultoriosMasUtilizados.getTabla();
            default:
                return null;

        }
    }

    private void exportarTabla(JTable tabla, String nombreArchivo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(nombreArchivo + ".csv"));
        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (PrintWriter pw = new PrintWriter(archivo)) {
                TableModel model = tabla.getModel();

                for (int i = 0; i < model.getColumnCount(); i++) {
                    pw.print(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) pw.print(",");
                }
                pw.println();

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object valor = model.getValueAt(i, j);
                        pw.print(valor != null ? valor.toString() : "");
                        if (j < model.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                }

                JOptionPane.showMessageDialog(this,
                    "Exportado correctamente a:\n" + archivo.getAbsolutePath(),
                    "Exportación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al exportar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getTextoBusqueda() {
        return txtBuscarConsult.getText().trim();
    }

    public String getTipoConsultaSeleccionado() {
        return (String) cbxConsulta.getSelectedItem();
    }

    public void limpiarBusqueda() {
        txtBuscarConsult.setText("");
    }
}
