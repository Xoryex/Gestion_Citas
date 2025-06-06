package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Registro_Usuario extends JFrame {

    // Variables de componentes
    private JLabel Contraseña;
    private JLabel Contraseña1;
    private JButton btnregistrar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator6;
    private JSeparator jSeparator7;
    private JPasswordField txtcontraseña;
    private JTextField txtdni;
    private JLabel txtiniciar;
    private JTextField txtnombre;
    private JTextField txttelefono;
    private JPasswordField txtverificarcontraseña;

    /**
     * Constructor que inicializa los componentes
     */
    public Registro_Usuario() {
        initComponents();
    }

    private void initComponents() {
        // Componentes de la interfaz
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        txtcontraseña = new JPasswordField();
        Contraseña = new JLabel();
        btnregistrar = new JButton();
        jLabel1 = new JLabel();
        jLabel4 = new JLabel();
        txtiniciar = new JLabel();
        jLabel3 = new JLabel();
        txtdni = new JTextField();
        txtnombre = new JTextField();
        txtverificarcontraseña = new JPasswordField();
        Contraseña1 = new JLabel();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        jSeparator3 = new JSeparator();
        jSeparator6 = new JSeparator();
        jLabel5 = new JLabel();
        jSeparator7 = new JSeparator();
        txttelefono = new JTextField();

        // Configuración básica de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Usuario");
        setLocationByPlatform(true);
        setResizable(false);

        // Panel principal con fondo blanco
        jPanel2.setBackground(new Color(255, 255, 255));

        // Configuración de etiquetas
        jLabel2.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        jLabel2.setText("Nombre Completo");

        jLabel1.setFont(new Font("Swis721 WGL4 BT", Font.BOLD, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Registro de Usuario");
        jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jLabel3.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        jLabel3.setText("Dni del usuario");

        jLabel4.setText("Ya tienes un usuario?");

        jLabel5.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        jLabel5.setText("Número de teléfono");

        // Configuración de campos de texto
        txtdni.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtdni.setHorizontalAlignment(JTextField.LEFT);
        txtdni.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtdni.addActionListener(this::txtdniActionPerformed);

        txtnombre.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtnombre.setHorizontalAlignment(JTextField.LEFT);
        txtnombre.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtnombre.addActionListener(this::txtnombreActionPerformed);

        txttelefono.setFont(new Font("Roboto", Font.PLAIN, 13));
        txttelefono.setHorizontalAlignment(JTextField.LEFT);
        txttelefono.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txttelefono.addActionListener(this::txttelefonoActionPerformed);

        // Configuración de campos de contraseña
        Contraseña.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        Contraseña.setText("Contraseña");

        txtcontraseña.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtcontraseña.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Contraseña1.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        Contraseña1.setText("Confirmar Contraseña");

        txtverificarcontraseña.setFont(new Font("Roboto", Font.PLAIN, 13));
        txtverificarcontraseña.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Botón de registro
        btnregistrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnregistrar.setText("Registrar Usuario");
        btnregistrar.addActionListener(this::btnregistrarActionPerformed);

        // Enlace para iniciar sesión
        txtiniciar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtiniciar.setForeground(new Color(102, 102, 255));
        txtiniciar.setHorizontalAlignment(SwingConstants.CENTER);
        txtiniciar.setText("Iniciar Sesión");
        txtiniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtiniciar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                txtiniciarMouseClicked(evt);
            }
        });

        // Separadores
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        jSeparator3 = new JSeparator();
        jSeparator6 = new JSeparator();
        jSeparator7 = new JSeparator();

        // Configuración del layout del panel principal
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(txtdni, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(txtnombre, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel5)
                .addGap(38, 38, 38)
                .addComponent(txttelefono, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jSeparator7, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(Contraseña)
                .addGap(101, 101, 101)
                .addComponent(txtcontraseña, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(Contraseña1, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(txtverificarcontraseña, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(btnregistrar))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
                .addGap(242, 242, 242)
                .addComponent(txtiniciar, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        );
        
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)

                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                
                        .addGap(32, 32, 32)

                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(txtdni, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))

                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)

                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(txtnombre, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))

                .addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)

                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txttelefono, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator7, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(Contraseña)
                    .addComponent(txtcontraseña, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(Contraseña1)
                    .addComponent(txtverificarcontraseña, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnregistrar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtiniciar))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        // Configuración del layout principal
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    // Métodos para manejar eventos
    
    private void txttelefonoActionPerformed(ActionEvent evt) {
    }

    private void txtnombreActionPerformed(ActionEvent evt) {
    }

    private void txtdniActionPerformed(ActionEvent evt) {
    }

    private void txtiniciarMouseClicked(MouseEvent evt) {
        Init init = new Init();
        init.setVisible(true);
        init.setLocationRelativeTo(null);
        dispose();
    }

    private void btnregistrarActionPerformed(ActionEvent evt) {
    }

    /**
     * Método principal para ejecutar la aplicación
     */
    public static void main(String args[]) {
        // Configurar el look and feel Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Crear y mostrar la ventana
        EventQueue.invokeLater(() -> {
            new Registro_Usuario().setVisible(true);
        });
    }
}