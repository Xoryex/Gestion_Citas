package views.herramientascitas;

import models.Cita;
import querys.QueryCita;
import java.awt.*;
import java.time.LocalTime;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlTablaCitas extends JPanel {

    private JTable tablaCitas;
    private JScrollPane scrollTabla;
    private DefaultTableModel modelo;
    private QueryCita queryCita;

    public pnlTablaCitas() {
        queryCita = new QueryCita();
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(226, 232, 238));

        String[] columnas = {
            "ID Cita", "DNI Paciente", "Nombre Paciente", "Consultorio", "Doctor",
            "Hora Inicio", "Hora Fin", "Atención", "Estado", "Recepcionista"
        };

        modelo = new DefaultTableModel(null, columnas) {
            private final Class<?>[] tipos = {
                String.class, String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class, String.class
            };

            private final boolean[] editable = new boolean[10];

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return editable[columnIndex];
            }
        };

        tablaCitas = new JTable(modelo);
        tablaCitas.getTableHeader().setReorderingAllowed(false);
        tablaCitas.getTableHeader().setResizingAllowed(true);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollTabla = new JScrollPane(tablaCitas);
        add(scrollTabla, BorderLayout.CENTER);
    }
    
    public void cargarDatos() {
        // Limpiar tabla
        modelo.setRowCount(0);
        
        // Obtener datos de la base de datos
        String[][] datos = queryCita.seleccionar();
        
        // Agregar filas al modelo
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }
    
    public void buscarCitas(String filtro) {
        // Limpiar tabla
        modelo.setRowCount(0);
        
        // Obtener datos filtrados de la base de datos
        String[][] datos = queryCita.seleccionarConFiltro(filtro);
        
        // Agregar filas al modelo
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }
    
    public Cita getCitaSeleccionada() {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Obtener datos de la fila seleccionada
            String idCita = (String) modelo.getValueAt(filaSeleccionada, 0);
            String dniPaciente = (String) modelo.getValueAt(filaSeleccionada, 1);
            String nombrePaciente = (String) modelo.getValueAt(filaSeleccionada, 2);
            String consultorio = (String) modelo.getValueAt(filaSeleccionada, 3);
            String nombreDoctor = (String) modelo.getValueAt(filaSeleccionada, 4);
            String horaInicio = (String) modelo.getValueAt(filaSeleccionada, 5);
            String horaFin = (String) modelo.getValueAt(filaSeleccionada, 6);
            String atencion = (String) modelo.getValueAt(filaSeleccionada, 7);
            String estado = (String) modelo.getValueAt(filaSeleccionada, 8);
            String nombreRecepcionista = (String) modelo.getValueAt(filaSeleccionada, 9);
            
            // Crear objeto Cita con los datos disponibles
            Cita cita = new Cita();
            cita.setCodCita(idCita);
            cita.setDniPct(Integer.parseInt(dniPaciente));
            cita.setNombrePaciente(nombrePaciente);
            cita.setConsultorio(consultorio);
            cita.setNombreDoctor(nombreDoctor);
            cita.setEstadoCita(estado);
            cita.setTipoAtencion(atencion);
            cita.setNombreRecepcionista(nombreRecepcionista);
            
            // Parsear horas
            try {
                cita.setHoraInicio(LocalTime.parse(horaInicio));
                cita.setHoraFin(LocalTime.parse(horaFin));
            } catch (Exception e) {
                // En caso de error al parsear las horas, usar valores por defecto
                cita.setHoraInicio(LocalTime.now());
                cita.setHoraFin(LocalTime.now().plusHours(1));
            }
            
            return cita;
        }
        return null;
    }
    
    public String getIdCitaSeleccionada() {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            return (String) modelo.getValueAt(filaSeleccionada, 0);
        }
        return null;
    }
    
    public void actualizarTabla() {
        cargarDatos();
    }
}
