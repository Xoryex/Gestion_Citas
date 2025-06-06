package src.views;

// Importaciones optimizadas
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.querys.QueryUser;

/**
 * Ventana principal de inicio de sesión
 */
public class Init extends JFrame {

    private JLabel Contraseña;
    private JButton btninicio;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JPasswordField txtcontraseña;
    private JLabel txtregistrar;
    private JTextField txtusuario;



    // Constructor
    public Init() {
        // Componentes de la interfaz
        jLabel3 = new JLabel();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        txtcontraseña = new JPasswordField();
        Contraseña = new JLabel();
        txtusuario = new JTextField();
        btninicio = new JButton();
        jLabel1 = new JLabel();
        jLabel4 = new JLabel();
        txtregistrar = new JLabel();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();

        // Configuración inicial de componentes
        jLabel3.setText("jLabel3");

        // Configuración de la ventana principal
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("INICIO");
        setResizable(false);
        

        // Panel principal
        jPanel2.setBackground(new Color(255, 255, 255));

        // Etiqueta de usuario
        jLabel2.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        jLabel2.setText("Dni del Usuario");

        // Campo de contraseña
        txtcontraseña.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtcontraseña.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Etiqueta de contraseña
        Contraseña.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        Contraseña.setText("Contraseña");

        // Campo de usuario
        txtusuario.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtusuario.setHorizontalAlignment(JTextField.LEFT);
        txtusuario.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        // Botón de inicio de sesión
        btninicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btninicio.setText("Iniciar Sesion");
        btninicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btninicioActionPerformed(evt);
            }
        });

        // Título principal
        jLabel1.setFont(new Font("Swis721 WGL4 BT", Font.BOLD, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("LOGIN");
        jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta de registro
        jLabel4.setText("Aun no tienes un usuario?");

        // Enlace de registro
        txtregistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtregistrar.setForeground(new Color(102, 102, 255));
        txtregistrar.setHorizontalAlignment(SwingConstants.CENTER);
        txtregistrar.setText("Registrar");
        txtregistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtregistrar.addMouseListener(new MouseAdapter() {
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
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(txtusuario, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(Contraseña))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(txtcontraseña, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(btninicio, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(txtregistrar, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        
        // Configuración vertical del layout
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txtusuario, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Contraseña)
                .addGap(0, 0, 0)
                .addComponent(txtcontraseña, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btninicio, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtregistrar))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        // Layout principal de la ventana
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

   

    // Método para manejar evento en campo de usuario

    // Método para manejar clic en enlace de registro
    private void txtregistrarMouseClicked(MouseEvent evt) {
        Registro_Usuario registro= new Registro_Usuario();
        registro.setVisible(true);
        registro.setLocationRelativeTo(null);
        dispose();
    }

    // Método para manejar clic en botón de inicio
    private void btninicioActionPerformed(ActionEvent evt) {
            QueryUser.Verificarusuario(txtusuario.getText(),String.valueOf(txtcontraseña.getPassword()));
    
    }

    
}