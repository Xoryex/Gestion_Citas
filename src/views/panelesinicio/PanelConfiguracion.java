package views.panelesinicio;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;

import static querys.QueryUser.usuario_actual;
import utils.*;
import views.APLICACION;
import views.Init;

public class PanelConfiguracion extends JPanel {
    private JPanel pnlCabeceraConf;
    private JPanel pnlBotonConf;

    // Declaración de labels y textfields
    private JLabel lblDNIConf;
    private JTextField txtDNIConf;
    private JLabel lblApellidoConf;
    private JTextField txtApellidoConf;
    private JLabel lblNombreConf;
    private JTextField txtNombreConf;
    private JLabel lblTelefConf;
    private JTextField txtTelefConf;
    private JLabel lblContrasenaActual;
    private JTextField txtContrasenaActual;
    private JLabel lblContrasenaNuevaConf;
    private JTextField txtContrasenaNuevaConf;
    private JLabel lblConfirmarContrasenaConf;
    private JTextField txtConfirmarContrasenaConf;

    // Declaración de botones
    private JButton btnCancelarConf;
    private JButton btnEliminarUsuario;
    private JButton btnAceptarConf;

    public PanelConfiguracion() {
        setLayout(new BorderLayout());

        // Panel cabecera con GridLayout 8 filas, 2 columnas
        pnlCabeceraConf = new JPanel(new GridLayout(8, 2, 5, 5));
        pnlCabeceraConf.setBorder(new EmptyBorder(50, 200, 0, 200)); // [top, left, bottom, right]

        lblDNIConf = new JLabel("DNI:");
        txtDNIConf = new JTextField();
        lblApellidoConf = new JLabel("Apellido:");
        txtApellidoConf = new JTextField();
        lblNombreConf = new JLabel("Nombre:");
        txtNombreConf = new JTextField();
        lblTelefConf = new JLabel("Teléfono:");
        txtTelefConf = new JTextField();
        lblContrasenaActual = new JLabel("Contraseña actual:");
        txtContrasenaActual = new JTextField();
        lblContrasenaNuevaConf = new JLabel("Contraseña nueva:");
        txtContrasenaNuevaConf = new JTextField();
        lblConfirmarContrasenaConf = new JLabel("Confirmar contraseña:");
        txtConfirmarContrasenaConf = new JTextField();

        txtDNIConf.setEnabled(false);
        txtDNIConf.setText(String.valueOf(usuario_actual.getDni()));
        txtApellidoConf.setText(usuario_actual.getApellido());
        txtNombreConf.setText(usuario_actual.getNombre());
        
        eventos();
        // Añadir componentes al panel cabecera
        pnlCabeceraConf.add(lblDNIConf);
        pnlCabeceraConf.add(txtDNIConf);
        pnlCabeceraConf.add(lblApellidoConf);
        pnlCabeceraConf.add(txtApellidoConf);
        pnlCabeceraConf.add(lblNombreConf);
        pnlCabeceraConf.add(txtNombreConf);
        pnlCabeceraConf.add(lblTelefConf);
        pnlCabeceraConf.add(txtTelefConf);
        pnlCabeceraConf.add(lblContrasenaActual);
        pnlCabeceraConf.add(txtContrasenaActual);
        pnlCabeceraConf.add(lblContrasenaNuevaConf);
        pnlCabeceraConf.add(txtContrasenaNuevaConf);
        pnlCabeceraConf.add(lblConfirmarContrasenaConf);
        pnlCabeceraConf.add(txtConfirmarContrasenaConf);

        // Panel de botones con FlowLayout
        pnlBotonConf = new JPanel(new FlowLayout());

        btnEliminarUsuario = new JButton("Eliminar Usuario");
        btnAceptarConf = new JButton("Aceptar");

        
        pnlBotonConf.add(btnEliminarUsuario);
        pnlBotonConf.add(btnAceptarConf);

        add(pnlCabeceraConf, BorderLayout.NORTH);
        add(pnlBotonConf, BorderLayout.CENTER);
    }

    public void eventos() {
        // Aquí puedes añadir los eventos de los botones
        

        

        txtNombreConf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a letras y espacios, y longitud máxima de 30 caracteres
                if (txtNombreConf.getText().length() >= 50 || Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        txtTelefConf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a números y longitud máxima de 9 caracteres
                if (!Character.isDigit(evt.getKeyChar())
                        || (txtTelefConf.getText().length() == 0 && evt.getKeyChar() != '9')
                        || txtTelefConf.getText().length() >= 9) {
                    evt.consume();
                }
            }
        });

        txtApellidoConf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a letras y espacios, y longitud máxima de 30 caracteres
                if (txtApellidoConf.getText().length() >= 50 || Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        txtContrasenaActual.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a caracteres alfanuméricos y longitud máxima de 20 caracteres
                if (txtContrasenaActual.getText().length() >= 20 || evt.getKeyChar() == ' ') {
                    evt.consume();
                }
            }
        });

        txtConfirmarContrasenaConf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a caracteres alfanuméricos y longitud máxima de 20 caracteres
                if (txtConfirmarContrasenaConf.getText().length() >= 20 || evt.getKeyChar() == ' ') {
                    evt.consume();
                }
            }
        });


        btnEliminarUsuario.addActionListener( new ActionListener() {
            
            @Override
            
            if(!txtContrasenaActual.getText().equals(usuario_actual.getContraseña())) {
                // Mostrar mensaje de error
                JOptionPane.showMessageDialog(this, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Eliminar usuario
                try {
                    CallableStatement cstm=Conexion.getConnection().prepareCall("{call  PA_CRUD_EliminarRecepcionista ? }");
                    cstm.setInt(1,usuario_actual.getDni());
                    cstm.executeUpdate();
                    

                } catch (SQLException e) {
                    // TODO: handle exception
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return ;
                }

                Window ventana = SwingUtilities.getWindowAncestor(this);
                new Init();
                ventana.dispose();
            }
        });

        btnAceptarConf.addActionListener(e -> {
            // Acción al aceptar cambios
        });

}
}
