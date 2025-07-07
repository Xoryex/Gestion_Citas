package VentanasMantenimiento;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class pnlDoctorMant extends JPanel {
    private JTable tblDoctor;
    private DefaultTableModel modelDoctor;
    
    public pnlDoctorMant() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Mantenimiento de Doctores"));
        
        String[] columnas = {"DNI", "Nombre Completo", "Especialidad", "Codigo de Consultorio", "Correo", "Teléfono"};
        modelDoctor = new DefaultTableModel(columnas, 0);
        tblDoctor = new JTable(modelDoctor);
        
        JScrollPane scrollPane = new JScrollPane(tblDoctor);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTabla() {
        return tblDoctor;
    }
    
    public DefaultTableModel getModelo() {
        return modelDoctor;
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
