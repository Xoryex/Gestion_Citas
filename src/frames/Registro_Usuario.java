package frames;

import javax.swing.*;
import javax.swing.border.Border;

import models.Recepcionista;
import querys.QueryUser;

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

    // End of variables declaration

    /**
     * Creates new form NewJFrame
     */
    public Registro_Usuario() {

        super("Registro de Usuario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(500, 460);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Diseños();
        Oyentes();

        createHeader();
        createBody();
        createFooter();

        setVisible(true);
    }

    private void createHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 15, 10));
        header.add(jLabel1);
        add(header, BorderLayout.NORTH);
    }

    private void createBody() {
        // Panel principal del cuerpo
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel formPanel = createFormPanel();
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón centrado
        btnregistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadir componentes al cuerpo con espaciado
        body.add(Box.createVerticalGlue());
        body.add(formPanel);
        body.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio fijo
        body.add(btnregistrar);
        body.add(Box.createVerticalGlue());

        add(body, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Añadir pares label/campo de forma estructurada
        addFormField(formPanel, jLabel2, txtdni);
        addFormField(formPanel, jLabel4, txtnombre);
        addFormField(formPanel, jLabel5, txtapellido);
        addFormField(formPanel, jLabel6, txtcelular);
        addFormField(formPanel, jLabel7, txtcontraseña);
        addFormField(formPanel, jLabel3, txtconfirmarcontraseña);

        return formPanel;
    }

    private void addFormField(JPanel panel, JLabel label, JComponent field) {
        panel.add(label);
        panel.add(field);
    }

    private void createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        footer.setBackground(Color.WHITE); // Añadido para consistencia

        footer.add(jLabel8, BorderLayout.WEST);
        footer.add(jLabel9, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);
    }

    private void Diseños() {
        Font Lbl_Txt_Font = new Font("Roboto", 0, 14);
        Border lineBottonBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        jLabel1.setFont(new Font("Roboto Condensed Black", 1, 18));

        jLabel2.setFont(Lbl_Txt_Font);
        jLabel3.setFont(Lbl_Txt_Font);
        jLabel4.setFont(Lbl_Txt_Font);
        jLabel5.setFont(Lbl_Txt_Font);
        jLabel6.setFont(Lbl_Txt_Font);
        jLabel7.setFont(Lbl_Txt_Font);

        txtdni.setFont(Lbl_Txt_Font);
        txtdni.setBorder(lineBottonBorder);
        txtnombre.setFont(Lbl_Txt_Font);
        txtnombre.setBorder(lineBottonBorder);
        txtcelular.setFont(Lbl_Txt_Font);
        txtcelular.setBorder(lineBottonBorder);
        txtapellido.setFont(Lbl_Txt_Font);
        txtapellido.setBorder(lineBottonBorder);
        txtcontraseña.setFont(Lbl_Txt_Font);
        txtcontraseña.setBorder(lineBottonBorder);
        txtconfirmarcontraseña.setFont(Lbl_Txt_Font);
        txtconfirmarcontraseña.setBorder(lineBottonBorder);
        btnregistrar.setFont(new Font("Roboto", 1, 14));
        jLabel8.setFont(new Font("Roboto", 0, 12));
        jLabel9.setFont(new Font("Roboto", 1, 12));
        jLabel9.setForeground(new Color(51, 102, 255));
        jLabel9.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    private void Oyentes() {

        jLabel9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Aquí puedes agregar la lógica para iniciar sesión
                new Init();
                dispose(); // Cierra la ventana actual
            }
        });
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
                if (txtnombre.getText().length() >= 50 || Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                }
            }
        });

        txtcelular.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a números y longitud máxima de 9 caracteres
                if (!Character.isDigit(evt.getKeyChar())
                        || (txtcelular.getText().length() == 0 && evt.getKeyChar() != '9')
                        || txtcelular.getText().length() >= 9) {
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
                if (txtcontraseña.getPassword().length >= 20 || evt.getKeyChar() == ' ') {
                    evt.consume();
                }
            }
        });

        txtconfirmarcontraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                // Limitar a caracteres alfanuméricos y longitud máxima de 20 caracteres
                if (txtconfirmarcontraseña.getPassword().length >= 20 || evt.getKeyChar() == ' ') {
                    evt.consume();
                }
            }
        });

        btnregistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Aquí puedes agregar la lógica para registrar al usuario

                if (txtdni.getText().length() < 8 || txtnombre.getText().isEmpty() || txtapellido.getText().isEmpty()
                        || txtcelular.getText().length() < 9 || new String(txtcontraseña.getPassword()).isEmpty() || new String(txtconfirmarcontraseña.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                } else if (!new String(txtcontraseña.getPassword()).equals(new String(txtconfirmarcontraseña.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                    txtcontraseña.setText("");
                    txtconfirmarcontraseña.setText("");
                } else {
                    int dni, celular;
                    try {
                        dni = Integer.parseInt(txtdni.getText());
                        celular = Integer.parseInt(txtcelular.getText());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null,
                                "Por favor, ingresa un número válido en los campos de DNI y Celular.");
                        return;
                    }
                    String nombre = txtnombre.getText().trim();
                    String apellido = txtapellido.getText().trim();

                    String contraseña = new String(txtcontraseña.getPassword());

                    Recepcionista newuser = new Recepcionista(dni, nombre, apellido, celular, contraseña, false);
                    queryuser.Insetar(newuser);
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

    }

}
