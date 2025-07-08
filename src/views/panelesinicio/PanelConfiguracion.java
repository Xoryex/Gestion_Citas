package views.panelesinicio;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import querys.QueryUser;
import static querys.QueryUser.usuario_actual;

import views.Init;

public class PanelConfiguracion extends JPanel {
    private QueryUser queryuser= new QueryUser();

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
        txtTelefConf.setText(String.valueOf(usuario_actual.getTlf()));

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

        eventos();

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

        txtContrasenaNuevaConf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a caracteres alfanuméricos y longitud máxima de 20 caracteres
                if (txtConfirmarContrasenaConf.getText().length() >= 20 || evt.getKeyChar() == ' ') {
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

        btnEliminarUsuario.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtContrasenaActual.getText().equals(usuario_actual.getContraseña())) {
                    // Mostrar mensaje de error
                    JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    
                } else {
                    queryuser.Eliminar(usuario_actual.getDni());

                    Window ventana = SwingUtilities.getWindowAncestor(btnEliminarUsuario);
                    new Init();
                    ventana.dispose();
                }

            }

        });

        btnAceptarConf.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!(txtContrasenaActual.getText().equals(usuario_actual.getContraseña()) && txtContrasenaActual.getText().length()!=0) ){
                    JOptionPane.showMessageDialog(null,"Contraseña actual ingresado no es igual a tu contraseña");
                    txtContrasenaActual.setText("");
                }else if (!(txtContrasenaNuevaConf.getText().equals(txtConfirmarContrasenaConf.getText()) && (txtContrasenaNuevaConf.getText().length()!=0 || txtConfirmarContrasenaConf.getText().length()!=0))){
                    JOptionPane.showMessageDialog(null,"Contraseñas no coinciden");
                    txtContrasenaNuevaConf.setText("");
                    txtConfirmarContrasenaConf.setText("");
                }else{
                    usuario_actual.setNombre(txtNombreConf.getText());
                    usuario_actual.setApellido(txtApellidoConf.getText());
                    usuario_actual.setTlf(Integer.parseInt(txtTelefConf.getText()));
                    if(txtContrasenaNuevaConf.getText().length()!=0){
                        usuario_actual.setContraseña(txtContrasenaNuevaConf.getText());
                    }
                
                    queryuser.actualizar(usuario_actual);



                }
            txtApellidoConf.setText(usuario_actual.getApellido());
            txtNombreConf.setText(usuario_actual.getNombre());
            txtTelefConf.setText(String.valueOf(usuario_actual.getTlf()));
            }


        });

    }
}
