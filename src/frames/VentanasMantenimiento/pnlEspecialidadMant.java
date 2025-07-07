package VentanasMantenimiento;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlEspecialidadMant extends JPanel {
    private JTable tblEspecialidad;
    private DefaultTableModel modelEspecialidad;
    
    public pnlEspecialidadMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Especialidades"));
        
        String[] columnas = {"Código", "Especialidad"};
        modelEspecialidad = new DefaultTableModel(columnas, 0);
        tblEspecialidad = new JTable(modelEspecialidad);
        
        JScrollPane scrollPane = new JScrollPane(tblEspecialidad);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblEspecialidad;
    }
    
    public DefaultTableModel getModelo() {
        return modelEspecialidad;
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