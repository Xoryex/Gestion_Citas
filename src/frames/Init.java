package src.frames;

// Importaciones optimizadas
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.querys.QueryUser;

/**
 * Ventana principal de inicio de sesión
 */
public class Init extends JFrame {
   QueryUser queryuser = new QueryUser();
    // Componentes de la interfaz
    private JLabel jLabel3;
    private JButton btninicio;
    private JLabel jLabel1;
    private JLabel jLabel2;
 
    private JLabel jLabel4;
    private JPanel jPanel2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JPasswordField txtcontraseña;
    private JLabel lblregistrar;
    private JTextField txtdni;



    // Constructor
    public Init() {
        
        // Componentes de la interfaz
        jPanel2 = new JPanel();
        jLabel2 = new JLabel("Dni del Usuario");
        txtcontraseña = new JPasswordField();
        jLabel3 = new JLabel("Contraseña");
        txtdni = new JTextField();
        btninicio = new JButton("Iniciar Sesion");
        jLabel1 = new JLabel("LOGIN");
        jLabel4 = new JLabel("Aun no tienes un usuario?");
        lblregistrar = new JLabel("Registrar");
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();

        // Configuración inicial de componentes


        // Configuración de la ventana principal
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("INICIO");
        setResizable(false);
        setSize(400,360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(jPanel2,BorderLayout.CENTER);

        // Panel principal
        jPanel2.setBackground(new Color(255, 255, 255));

        // Etiqueta de usuario
        jLabel2.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));

        // Campo de contraseña
        txtcontraseña.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtcontraseña.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Etiqueta de contraseña
        jLabel3.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));

        // Campo de usuario
        txtdni.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtdni.setHorizontalAlignment(JTextField.LEFT);
        txtdni.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        // Botón de inicio de sesión
        btninicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btninicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btninicioActionPerformed(evt);
            }
        });

        // Título principal
        jLabel1.setFont(new Font("Swis721 WGL4 BT", Font.BOLD, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Enlace de registro
        lblregistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblregistrar.setForeground(new Color(102, 102, 255));
        lblregistrar.setHorizontalAlignment(SwingConstants.CENTER);
        lblregistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblregistrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                txtregistrarMouseClicked(evt);
            }
        });

        // Layout del panel principal
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        
        // Configuración horizontal del layout
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(130)
                        .addComponent(jLabel1, 127, 127, 127))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80)
                        .addComponent(jLabel2, 150, 150, 150))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80)
                        .addComponent(txtdni, 220, 220, 220))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80)
                        .addComponent(jSeparator1, 220, 220, 220))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80)
                        .addComponent(txtcontraseña, 220, 220, 220))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80)
                        .addComponent(jSeparator2, 220, 220,220))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(130)
                        .addComponent(btninicio, 120, 120, 120))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(jLabel4, 158, 158, 158)
                        .addGap(122)
                        .addComponent(lblregistrar, 70, 70, 70)));
        
        // Configuración vertical del layout
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30)
                .addComponent(jLabel1)
                .addGap(32)
                .addComponent(jLabel2)
                .addComponent(txtdni)
                .addComponent(jSeparator1)
                .addGap(10)
                .addComponent(jLabel3)
                .addComponent(txtcontraseña)
                .addComponent(jSeparator2)
                .addGap(20)
                .addComponent(btninicio)
                .addGap(40)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblregistrar))
                .addGap(14)));
        // Layout principal de la ventana
        
        
        //hacer visible la ventana
        setVisible(true);
    }

   

    // Método para manejar evento en campo de usuario

    

    // Método para manejar clic en enlace de registro
    private void txtregistrarMouseClicked(MouseEvent evt) {
        new Registro_Usuario();
        dispose();
    }

    // Método para manejar clic en botón de inicio
    private void btninicioActionPerformed(ActionEvent evt) {
            String dni = txtdni.getText();
            String contraseña = new String(txtcontraseña.getPassword());
            queryuser.IniciarSesion(dni,contraseña);
    
    }

    
}