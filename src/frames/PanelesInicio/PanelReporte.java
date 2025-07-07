package PanelesInicio;

import HerramientasConsultaReporte.*;
import java.awt.*;
import javax.swing.*;

public class PanelReporte extends JPanel {
    // Paneles específicos para cada tipo de consulta
    private pnlTblCita consltCita = new pnlTblCita();
    private pnlTblRecepcionista consltRecepcionista = new pnlTblRecepcionista();
    private pnlTblDoctor consltDoctor = new pnlTblDoctor();
    private pnlTblPaciente consltPaciente = new pnlTblPaciente();
    private pnlTblHorario consltHorario = new pnlTblHorario();

    // Layout para cambiar entre paneles
    private CardLayout tablaConsult;
    private JPanel pnlVentanasConsultas;

    // Cabecera y botones
    private JPanel pnlCabeceraConsult;
    private JPanel pnlBotonConsult;

    // Componentes de cabecera
    private JLabel lblConsltPor;
    private JComboBox<String> cbxConsulta;
    private JLabel lblFechaInicioConsult;
    private JSpinner spnFechaInicioConsult;
    private JLabel lblBuscarConsult;
    private JTextField txtBuscarConsult;
    private JButton btnFiltroConsulta;
    private JComboBox<String> cbxOrdenConsult;

    public PanelReporte() {
        // ⚙️ Establecer layout principal
        setLayout(new BorderLayout());

        // 🔼 Cabecera
        pnlCabeceraConsult = new JPanel(new FlowLayout(FlowLayout.LEFT));

        lblConsltPor = new JLabel("Reporde de:");
        cbxConsulta = new JComboBox<>(new String[] {
            "Cita", "Recepcionista", "Doctor", "Paciente", "Horario"
        });
        cbxConsulta.setPreferredSize(new Dimension(125, 23));

        lblFechaInicioConsult = new JLabel("Fecha inicio:");
        spnFechaInicioConsult = new JSpinner(new SpinnerDateModel());
        spnFechaInicioConsult.setPreferredSize(new Dimension(113, 23));

        lblBuscarConsult = new JLabel("Buscar:");
        txtBuscarConsult = new JTextField(10);
        txtBuscarConsult.setPreferredSize(new Dimension(300, 23));

        btnFiltroConsulta = new JButton("Filtrar");

        cbxOrdenConsult = new JComboBox<>(new String[] { "Ascendente", "Descendente" });
        cbxOrdenConsult.setPreferredSize(new Dimension(120, 23));

        pnlCabeceraConsult.add(lblConsltPor);
        pnlCabeceraConsult.add(cbxConsulta);
        pnlCabeceraConsult.add(lblFechaInicioConsult);
        pnlCabeceraConsult.add(spnFechaInicioConsult);
        pnlCabeceraConsult.add(lblBuscarConsult);
        pnlCabeceraConsult.add(txtBuscarConsult);
        pnlCabeceraConsult.add(btnFiltroConsulta);
        pnlCabeceraConsult.add(cbxOrdenConsult);

        // 🔁 Panel con CardLayout
        tablaConsult = new CardLayout();
        pnlVentanasConsultas = new JPanel(tablaConsult);
        pnlVentanasConsultas.add(consltCita, "Cita");
        pnlVentanasConsultas.add(consltRecepcionista, "Recepcionista");
        pnlVentanasConsultas.add(consltDoctor, "Doctor");
        pnlVentanasConsultas.add(consltPaciente, "Paciente");
        pnlVentanasConsultas.add(consltHorario, "Horario");

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

        // 🎯 Manejador de evento para el ComboBox
        cbxConsulta.addActionListener(e -> {
            String seleccion = (String) cbxConsulta.getSelectedItem();
            if (seleccion != null) {
                tablaConsult.show(pnlVentanasConsultas, seleccion);
                // Actualizar interfaz para evitar problemas de renderizado
                SwingUtilities.updateComponentTreeUI(this);
                this.repaint();
            }
        });

        // Agregar funcionalidad al botón filtrar
        btnFiltroConsulta.addActionListener(e -> {
            String textoBusqueda = txtBuscarConsult.getText().trim();
            if (!textoBusqueda.isEmpty()) {
                // Aquí puedes agregar la lógica de filtrado
                JOptionPane.showMessageDialog(this, 
                    "Buscando: " + textoBusqueda, 
                    "Filtro", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Mostrar por defecto
        tablaConsult.show(pnlVentanasConsultas, "Cita");
    }

    // Método para obtener el texto de búsqueda
    public String getTextoBusqueda() {
        return txtBuscarConsult.getText().trim();
    }

    // Método para obtener el tipo de consulta seleccionado
    public String getTipoConsultaSeleccionado() {
        return (String) cbxConsulta.getSelectedItem();
    }

    // Método para limpiar búsqueda
    public void limpiarBusqueda() {
        txtBuscarConsult.setText("");
    }
}