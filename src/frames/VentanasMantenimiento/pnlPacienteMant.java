package VentanasMantenimiento;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlPacienteMant extends JPanel {
    private JTable tblPaciente;
    private DefaultTableModel modelPaciente;
    
    public pnlPacienteMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Pacientes"));
        
        String[] columnas = {"DNI", "Nombre Comp.", "Teléfono", "Género", "Email", "Fecha de Nac.", 
                           "Dirección", "Ocupación", "Grupo Sanguín.", "Procedencia", "Estado Civil", 
                           "Grupo Étnico", "Centro de trab.", "Grado de Instru.", "Hijos"};
        modelPaciente = new DefaultTableModel(columnas, 0);
        tblPaciente = new JTable(modelPaciente);
        
        JScrollPane scrollPane = new JScrollPane(tblPaciente);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblPaciente;
    }
    
    public DefaultTableModel getModelo() {
        return modelPaciente;
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