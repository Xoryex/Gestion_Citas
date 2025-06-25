package src.frames;

import javax.swing.*;

import src.models.User;
import src.querys.QueryUser;

import java.awt.*;
import java.awt.event.*;

public class Registro_Usuario extends JFrame {
    QueryUser queryuser = new QueryUser();
    // Variables declaration - do not modify                     
    JLabel jLabel1 = new JLabel("REGISTRARSE");
    JTextField txtdni = new JTextField();
    JTextField txtnombre = new JTextField();
    JTextField txtcelular = new JTextField();
    JTextField txtapellido = new JTextField();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel2 = new JLabel("Dni del Usuario");
    JLabel jLabel3 = new JLabel("Confirmar Contraseña");
    JLabel jLabel4 = new JLabel("Nombres");
    JLabel jLabel5 = new JLabel("Apellidos");
    JLabel jLabel6 = new JLabel("Celular");
    JLabel jLabel7 = new JLabel("Contraseña");
    JPasswordField txtcontraseña = new JPasswordField();
    JPasswordField txtconfirmarcontraseña = new JPasswordField();
    JLabel jLabel8 = new JLabel("Ya tienes un usuario?");
    JLabel jLabel9 = new JLabel("Iniciar Sesion");
    JButton btnregistrar = new JButton("REGISTRARSE");
    JSeparator jSeparator1 = new JSeparator();
    JSeparator jSeparator2 = new JSeparator();
    JSeparator jSeparator3 = new JSeparator();
    JSeparator jSeparator4 = new JSeparator();
    JSeparator jSeparator5 = new JSeparator();
    JSeparator jSeparator6 = new JSeparator();
    // End of variables declaration   




    /**
     * Creates new form NewJFrame
     */
    public Registro_Usuario() {
        // Inicializar componentes
        
        
        super("Registro de Usuario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(500, 460);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(jPanel1, BorderLayout.CENTER);

        jPanel1.setBackground(new Color(255, 255, 255));

        //Agregamos fuentes y bordes a los componentes
        jLabel1.setFont(new Font("Roboto Condensed Black", 1, 18)); 
        txtdni.setFont(new Font("Roboto", 0, 14)); 
        txtdni.setBorder(null);
        txtnombre.setFont(new Font("Roboto", 0, 14)); 
        txtnombre.setBorder(null);
        txtcelular.setFont(new Font("Roboto", 0, 14)); 
        txtcelular.setBorder(null);
        txtapellido.setFont(new Font("Roboto", 0, 14)); 
        txtapellido.setBorder(null);
        jLabel2.setFont(new Font("Roboto", 0, 14)); 
        jLabel3.setFont(new Font("Roboto", 0, 14));
        jLabel4.setFont(new Font("Roboto", 0, 14)); 
        jLabel5.setFont(new Font("Roboto", 0, 14)); 
        jLabel6.setFont(new Font("Roboto", 0, 14)); 
        jLabel7.setFont(new Font("Roboto", 0, 14)); 
        txtcontraseña.setFont(new Font("Roboto", 0, 14)); 
        txtcontraseña.setBorder(null);
        txtconfirmarcontraseña.setFont(new Font("Roboto", 0, 14)); 
        txtconfirmarcontraseña.setBorder(null);
        


        jLabel8.setFont(new Font("Roboto", 0, 12)); 
        jLabel9.setFont(new Font("Roboto", 1, 12)); 
        jLabel9.setForeground(new Color(51, 102, 255));
        jLabel9.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnregistrar.setFont(new Font("Roboto", 1, 14));
        btnregistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                new Init();
                dispose(); // Cierra la ventana actual
            }

        });

        txtdni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a números y longitud máxima de 8 caracteres
                if (!Character.isDigit(evt.getKeyChar()) || txtdni.getText().length() >= 8) {
                    evt.consume();
                }
            }
        });

        txtnombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a letras y espacios, y longitud máxima de 30 caracteres
                if (txtnombre.getText().length() >= 50|| Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        txtcelular.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a números y longitud máxima de 9 caracteres
                if (!Character.isDigit(evt.getKeyChar()) || txtcelular.getText().length() >= 9) {
                    evt.consume();
                }
            }
        });

        txtapellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a letras y espacios, y longitud máxima de 30 caracteres
                if (txtapellido.getText().length() >= 50 || Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        txtcontraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a caracteres alfanuméricos y longitud máxima de 20 caracteres
                if (txtcontraseña.getPassword().length >= 20 || evt.getKeyChar() == ' ' ) {
                    evt.consume();
                }
            }
        });


        txtconfirmarcontraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a caracteres alfanuméricos y longitud máxima de 20 caracteres
                if (txtconfirmarcontraseña.getPassword().length >= 20 || evt.getKeyChar() == ' ' ) {
                    evt.consume();
                }
            }
        });



        btnregistrar.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Aquí puedes agregar la lógica para registrar al usuario
                String dni = txtdni.getText();
                String nombre = txtnombre.getText().trim();
                String apellido = txtapellido.getText().trim();
                String celular = txtcelular.getText();
                String contraseña = new String(txtcontraseña.getPassword());
                String confirmarContraseña = new String(txtconfirmarcontraseña.getPassword());

                if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || celular.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()||dni.length() < 8 || celular.length() < 9) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                } else if (!contraseña.equals(confirmarContraseña)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                    txtcontraseña.setText("");
                    txtconfirmarcontraseña.setText("");
                } else {
                    User usuario = new User(dni, nombre, apellido, celular, contraseña, false);
                    queryuser.Insetar(usuario);
                    // Limpiar campos después del registro
                    txtdni.setText("");
                    txtnombre.setText("");
                    txtapellido.setText("");
                    txtcelular.setText("");
                    txtcontraseña.setText("");
                    txtconfirmarcontraseña.setText("");
                }
        
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        // 3. Constantes para dimensiones (ajusta estos valores según necesites)
        final int FIELD_WIDTH = 173;
        final int LEFT_MARGIN = 73;
        final int LABEL_GAP = 49;
        final int TOP_MARGIN = 23;
        final int ROW_GAP = 12;
        final int SECTION_GAP = 32;

        // 4. Configuración horizontal completa
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(LEFT_MARGIN)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    // Título centrado
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117)
                        .addComponent(jLabel1))
                    
                    // Campos de formulario
                    // DNI
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(LABEL_GAP+50)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtdni, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)
                            .addComponent(jSeparator1, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)))
                    
                    // Nombre
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(LABEL_GAP+85)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtnombre, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)
                            .addComponent(jSeparator2, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)))
                    
                    // Apellido
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(LABEL_GAP+86)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtapellido, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)
                            .addComponent(jSeparator3, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)))
                    
                    // Celular
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(LABEL_GAP+99)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtcelular, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)
                            .addComponent(jSeparator4, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)))
                    
                    // Contraseña
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(LABEL_GAP+70)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtcontraseña, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)
                            .addComponent(jSeparator5, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)))
                    
                    // Confirmar Contraseña
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(LABEL_GAP+1)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtconfirmarcontraseña, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)
                            .addComponent(jSeparator6, FIELD_WIDTH, FIELD_WIDTH, FIELD_WIDTH)))
                    
                    // Botón centrado
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(110)
                        .addComponent(btnregistrar)))
                )

            // Footer (jLabel8 y jLabel9)
            .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(12)
                    .addComponent(jLabel8)
                    .addGap(270)
                    .addComponent(jLabel9))
        );

        // 5. Configuración vertical completa
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createSequentialGroup()
                .addGap(TOP_MARGIN)
                .addComponent(jLabel1)
                .addGap(45)
                
                // DNI
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdni))
                .addComponent(jSeparator1)
                .addGap(ROW_GAP)
                
                // Nombre
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtnombre))
                .addComponent(jSeparator2)
                .addGap(ROW_GAP)
                
                // Apellido
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtapellido))
                .addComponent(jSeparator3)
                .addGap(ROW_GAP)
                
                // Celular
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtcelular))
                .addComponent(jSeparator4)
                .addGap(SECTION_GAP)
                
                // Contraseña
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtcontraseña))
                .addComponent(jSeparator5)
                .addGap(ROW_GAP)
                
                // Confirmar Contraseña
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtconfirmarcontraseña))
                .addComponent(jSeparator6)
                .addGap(SECTION_GAP)
                
                // Botón
                .addComponent(btnregistrar)
                .addGap(56)
                
                // Footer
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                );

        //hacer visible la ventana
        setVisible(true);
    }
                     
   
}
