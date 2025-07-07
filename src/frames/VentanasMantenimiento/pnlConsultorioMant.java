package VentanasMantenimiento;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlConsultorioMant extends JPanel {
    private JTable tblConsultorio;
    private DefaultTableModel modelConsultorio;
    
    public pnlConsultorioMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Consultorios"));
        
        String[] columnas = {"ID", "Nombre del Consultorio"};
        modelConsultorio = new DefaultTableModel(columnas, 0);
        tblConsultorio = new JTable(modelConsultorio);
        
        JScrollPane scrollPane = new JScrollPane(tblConsultorio);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblConsultorio;
    }
    
    public DefaultTableModel getModelo() {
        return modelConsultorio;
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