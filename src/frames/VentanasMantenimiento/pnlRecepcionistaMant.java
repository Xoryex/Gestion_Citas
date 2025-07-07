package VentanasMantenimiento;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlRecepcionistaMant extends JPanel {
    private JTable tblRecepcionista;
    private DefaultTableModel modelRecepcionista;
    
    public pnlRecepcionistaMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Recepcionistas"));
        
        String[] columnas = {"DNI", "Nombre", "Apellido", "Teléfono", "Admin"};
        modelRecepcionista = new DefaultTableModel(columnas, 0);
        tblRecepcionista = new JTable(modelRecepcionista);
        
        JScrollPane scrollPane = new JScrollPane(tblRecepcionista);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblRecepcionista;
    }
    
    public DefaultTableModel getModelo() {
        return modelRecepcionista;
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