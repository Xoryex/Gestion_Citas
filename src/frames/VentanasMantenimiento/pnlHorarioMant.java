package VentanasMantenimiento;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlHorarioMant extends JPanel {
    private JTable tblHorario;
    private DefaultTableModel modelHorario;
    
    public pnlHorarioMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Horarios"));
        
        String[] columnas = {"ID Horario", "Día", "Hora Inicio", "Hora Fin", "Límite de Pacientes", "Estado del Horario", "Turno"};
        modelHorario = new DefaultTableModel(columnas, 0);
        tblHorario = new JTable(modelHorario);
        
        JScrollPane scrollPane = new JScrollPane(tblHorario);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblHorario;
    }
    
    public DefaultTableModel getModelo() {
        return modelHorario;
    }
    
    public void agregarDatosEjemplo() {

    }
    
    public void agregar() {

    }
    public void actualizar() {

    }
    public void eliminar() {
        
    }
}