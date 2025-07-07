package PanelesInicio;
import java.awt.*;
import javax.swing.*;

public class PanelInicio extends JPanel {
    public PanelInicio() {
        setLayout(new BorderLayout());

        // Título
        JLabel lblTituloInicio = new JLabel("Bienvenido, estas son las citas de hoy:", SwingConstants.CENTER);
        lblTituloInicio.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTituloInicio, BorderLayout.NORTH);

        // Tabla con datos
        String[] columnas = {"ID", "Paciente", "Fecha", "Hora", "Estado"};
        Object[][] datos = {
            {"1", "Juan Pérez", "2025-07-06", "09:00", "Pendiente"},
            {"2", "Ana López", "2025-07-07", "10:30", "Confirmada"}
        };
        JTable tablaInicio = new JTable(datos, columnas);
        JScrollPane scrollTabla = new JScrollPane(tablaInicio);
        add(scrollTabla, BorderLayout.CENTER);
    }
}
