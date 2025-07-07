package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.querys.QueryUser;


public class Init extends JFrame {
    QueryUser queryuser = new QueryUser();
    // Componentes de la interfaz
    JPanel jPanel2 = new JPanel();
    JLabel jLabel2 = new JLabel("Dni del Usuario");
    JPasswordField txtcontraseña = new JPasswordField();
    JLabel jLabel3 = new JLabel("Contraseña");
    JTextField txtdni = new JTextField();
    JButton btninicio = new JButton("Iniciar Sesion");
    JLabel jLabel1 = new JLabel("LOGIN");
    JLabel jLabel4 = new JLabel("Aun no tienes un usuario?");
    JLabel lblregistrar = new JLabel("Registrar");
    JSeparator jSeparator1 = new JSeparator();
    JSeparator jSeparator2 = new JSeparator();

    public Init() {
        // Configuración de la ventana principal
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("INICIO");
        setResizable(false);
        setSize(400, 360);
        setLocationRelativeTo(null);
        
        // Configurar panel principal con layout nulo (absolute positioning)
        jPanel2.setLayout(null);
        jPanel2.setBackground(Color.WHITE);
        add(jPanel2, BorderLayout.CENTER);

        // Configurar posición y tamaño de cada componente
        jLabel1.setBounds(130, 30, 140, 30);
        jLabel2.setBounds(80, 80, 150, 20);
        txtdni.setBounds(80, 100, 220, 25);
        jSeparator1.setBounds(80, 125, 220, 2);
        jLabel3.setBounds(80, 140, 150, 20);
        txtcontraseña.setBounds(80, 160, 220, 25);
        jSeparator2.setBounds(80, 185, 220, 2);
        btninicio.setBounds(140, 210, 120, 30);
        jLabel4.setBounds(80, 270, 160, 20);
        lblregistrar.setBounds(240, 270, 70, 20);

        // Configurar estilos de los componentes (manteniendo los originales)
        jLabel1.setFont(new Font("Swis721 WGL4 BT", Font.BOLD, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        jLabel2.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        jLabel3.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        
        txtdni.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtdni.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        txtcontraseña.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtcontraseña.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        btninicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblregistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblregistrar.setForeground(new Color(102, 102, 255));
        lblregistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Añadir componentes al panel
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel2.add(txtdni);
        jPanel2.add(jSeparator1);
        jPanel2.add(jLabel3);
        jPanel2.add(txtcontraseña);
        jPanel2.add(jSeparator2);
        jPanel2.add(btninicio);
        jPanel2.add(jLabel4);
        jPanel2.add(lblregistrar);

        // Event listeners (manteniendo los originales)

        txtdni.addKeyListener( new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btninicio.doClick();
                }
            }
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar()) || txtdni.getText().length() >= 8) {
                    evt.consume();
                }
            }
        });

        txtcontraseña.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btninicio.doClick();
                }
            }
            public void keyTyped(KeyEvent evt) {
                if (txtcontraseña.getPassword().length >= 20 || evt.getKeyChar() == ' ') {
                    evt.consume();
                }
            }
        });

        btninicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int dni = Integer.parseInt(txtdni.getText());
                String contraseña = new String(txtcontraseña.getPassword());
                if(queryuser.IniciarSesion(dni, contraseña)){
                    new APLICACION();
                    dispose();
                }
            }
        });

        lblregistrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                new Registro_Usuario();
                dispose();
            }
        });

        setVisible(true);
    }
}