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
    // Paneles específicos para cada tipo de Reportea
    private pnlTblCita consltCita = new pnlTblCita();
    private pnlTblRecepcionista consltRecepcionista = new pnlTblRecepcionista();
    private pnlTblDoctor consltDoctor = new pnlTblDoctor();
    private pnlTblPaciente consltPaciente = new pnlTblPaciente();
    private pnlTblHorario consltHorario = new pnlTblHorario();
    private pnlTblDoctoresMasCitas consltDoctoresMasCitas = new pnlTblDoctoresMasCitas();
    private pnlTblPacientesFrecuentes consltPacientesFrecuentes = new pnlTblPacientesFrecuentes();
    private pnlTblResumenCitasEstado consltResumenCitasEstado = new pnlTblResumenCitasEstado();
    private pnlTblConsultoriosMasUtilizados consltReporteoriosMasUtilizados = new pnlTblConsultoriosMasUtilizados();
    private pnlTblHorariosMasOcupados consltHorariosMasOcupados = new pnlTblHorariosMasOcupados();
    private pnlTblEspecialidadesMasSolicitadas consltEspecialidadesMasSolicitadas = new pnlTblEspecialidadesMasSolicitadas();
    

    private Connection conn = Conexion.getConnection();

    // Layout para cambiar entre paneles
    private CardLayout tablaReporte;
    private JPanel pnlVentanasReporteas;

    // Cabecera y botones
    private JPanel pnlCabeceraReporte;
    private JPanel pnlBotonReporte;

    // Componentes de cabecera
    private JLabel lblConsultPorR;
    private JComboBox<String> cbxReportea;
    private JLabel lblBuscarReporte;
    private JTextField txtBuscarReporte;
    private JButton btnFiltroReportea;
    private JButton btnLimpiarBusquedaR;

    public PanelReporte() {
        setLayout(new BorderLayout());

        // 🔼 Cabecera
        pnlCabeceraReporte = new JPanel(new FlowLayout(FlowLayout.LEFT));

        lblConsultPorR = new JLabel("Reporte de:");
        cbxReportea = new JComboBox<>(new String[] {
            "Cita", "Recepcionista", "Doctor", "Paciente", "Horario", "Doctores Más Citas",
            "Pacientes Frecuentes", "Horarios Más Ocupados", "Resumen Citas Estado",
            "Especialidades Más Solicitadas", "Consultorios Más Utilizados"
        });
        cbxReportea.setPreferredSize(new Dimension(200, 23));

        lblBuscarReporte = new JLabel("Buscar:");
        txtBuscarReporte = new JTextField(10);
        txtBuscarReporte.setPreferredSize(new Dimension(300, 23));

        btnFiltroReportea = new JButton("Filtrar");
        btnLimpiarBusquedaR = new JButton("Limpiar");

        pnlCabeceraReporte.add(lblConsultPorR);
        pnlCabeceraReporte.add(cbxReportea);
        pnlCabeceraReporte.add(lblBuscarReporte);
        pnlCabeceraReporte.add(txtBuscarReporte);
        pnlCabeceraReporte.add(btnFiltroReportea);
        pnlCabeceraReporte.add(btnLimpiarBusquedaR);

        // 🔁 Panel con CardLayout
        tablaReporte = new CardLayout();
        pnlVentanasReporteas = new JPanel(tablaReporte);
        pnlVentanasReporteas.setBackground(Color.WHITE);

        pnlVentanasReporteas.add(consltCita, "Cita");
        pnlVentanasReporteas.add(consltRecepcionista, "Recepcionista");
        pnlVentanasReporteas.add(consltDoctor, "Doctor");
        pnlVentanasReporteas.add(consltPaciente, "Paciente");
        pnlVentanasReporteas.add(consltHorario, "Horario");

        // Agregar nuevos paneles de reportes
        pnlVentanasReporteas.add(consltDoctoresMasCitas, "Doctores Más Citas");
        pnlVentanasReporteas.add(consltPacientesFrecuentes, "Pacientes Frecuentes");
        pnlVentanasReporteas.add(consltHorariosMasOcupados, "Horarios Más Ocupados");
        pnlVentanasReporteas.add(consltResumenCitasEstado, "Resumen Citas Estado");
        pnlVentanasReporteas.add(consltEspecialidadesMasSolicitadas, "Especialidades Más Solicitadas");
        pnlVentanasReporteas.add(consltReporteoriosMasUtilizados, "Consultorios Más Utilizados");



        // 👇 Panel botones adicionales
        pnlBotonReporte = new JPanel(new FlowLayout());
        JButton btnExportar = new JButton("Exportar");
        JButton btnImprimir = new JButton("Imprimir");

        pnlBotonReporte.add(btnExportar);
        pnlBotonReporte.add(btnImprimir);

        // ➕ Añadir todo al panel principal
        add(pnlCabeceraReporte, BorderLayout.NORTH);
        add(pnlBotonReporte, BorderLayout.SOUTH);
        add(pnlVentanasReporteas, BorderLayout.CENTER);

        // 🔁 Eventos
        cbxReportea.addActionListener(e -> {
            String seleccion = (String) cbxReportea.getSelectedItem();
            if (seleccion != null) {
                tablaReporte.show(pnlVentanasReporteas, seleccion);
                SwingUtilities.updateComponentTreeUI(pnlVentanasReporteas);
                pnlVentanasReporteas.repaint();
                limpiarBusqueda();
                cargarDatosTabla(seleccion, "");
            }
        });

        btnFiltroReportea.addActionListener(e -> realizarBusqueda());

        btnLimpiarBusquedaR.addActionListener(e -> {
            limpiarBusqueda();
            String seleccion = (String) cbxReportea.getSelectedItem();
            if (seleccion != null) {
                cargarDatosTabla(seleccion, "");
            }
        });

        txtBuscarReporte.addKeyListener(new KeyAdapter() {
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
                exportarTabla(tabla, getTipoReporteaSeleccionado());
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
        tablaReporte.show(pnlVentanasReporteas, "Cita");
        cargarDatosTabla("Cita", "");
    }

    private void realizarBusqueda() {
        String textoBusqueda = txtBuscarReporte.getText().trim();
        String tipoReportea = (String) cbxReportea.getSelectedItem();

        if (tipoReportea != null) {
            cargarDatosTabla(tipoReportea, textoBusqueda);
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
                        cargarDatosReporteoriosMasUtilizados(conn, filtro);
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

    // 4. Reporteorios más utilizados
    private void cargarDatosReporteoriosMasUtilizados(Connection conn, String filtro) throws SQLException {
        String sql = "{CALL PA_CRUD_ListarConsultoriosMasUtilizadosConFiltro(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, filtro);
            ResultSet rs = cs.executeQuery();
            consltReporteoriosMasUtilizados.cargarDatos(rs);
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
        String tipo = (String) cbxReportea.getSelectedItem();

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
                return consltReporteoriosMasUtilizados.getTabla();
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
        return txtBuscarReporte.getText().trim();
    }

    public String getTipoReporteaSeleccionado() {
        return (String) cbxReportea.getSelectedItem();
    }

    public void limpiarBusqueda() {
        txtBuscarReporte.setText("");
    }
}
