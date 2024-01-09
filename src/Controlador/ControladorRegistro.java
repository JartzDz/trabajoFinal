package Controlador;

import Modelo.GestorUsuario;
import Vista.InterfazRegistro;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorRegistro extends MouseAdapter implements ActionListener, KeyListener {
    InterfazRegistro vista;
    GestorUsuario modeloUsuario;
    ControladorAdministrador controladorAdministrador;
    ControladorLogin controladorLogin;

    public ControladorRegistro(InterfazRegistro vista, GestorUsuario modelo, ControladorAdministrador controladorAdministrador,ControladorLogin controladorLogin) {
        this.vista = vista;
        this.modeloUsuario = modelo;
        this.controladorAdministrador = controladorAdministrador;
        this.controladorLogin = controladorLogin;
        vista.btnRegresar.addMouseListener(this);
        vista.btnRegistrarme.addActionListener(this);
        vista.chkMostrarContra.addActionListener(this);
        vista.txtCedula.addKeyListener(this);
        vista.txtNombres.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtDireccion.addKeyListener(this);
    }
    public void setControladorLogin(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
    }

    public void mostrarInterfaz() {
        vista.setUndecorated(true);
        vista.setTitle("LOGIN");
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setResizable(false);
        vista.setSize(630, 740);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
    public void agregar() {
        String ID = vista.txtCedula.getText();
        String nombreUsuario = vista.txtNombres.getText();
        String tel = vista.txtTelefono.getText();
        String direccion = vista.txtDireccion.getText();
        String correo = vista.txtCorreo.getText();
        String clave = vista.txtContra.getText();
        String claveRep = vista.txtContraRep.getText();

        if (!nombreUsuario.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !ID.isEmpty()) {
            if(validarCedula(ID)) {
                if (validarCorreo(correo)) {
                    if (modeloUsuario.validarCedulaUnica(ID)) {
                        if (modeloUsuario.validarTelefonoUnico(tel)) {
                            if (modeloUsuario.validarCorreoUnico(correo)) {
                                if (validarContrasenias()) {
                                    int tipoUsuario = 1;
                                    modeloUsuario.agregarUsuario(ID, nombreUsuario, direccion, tel, correo, clave, tipoUsuario);
                                    modeloUsuario.guardarUsuarios();
                                    EnviarCorreoWorker worker = new EnviarCorreoWorker(correo, ID, clave, nombreUsuario);
                                    worker.execute();
                                    JOptionPane.showMessageDialog(null, "Usuario creado con éxito. Las credenciales fueron enviadas al usuario");
                                    limpiar();
                                } else JOptionPane.showMessageDialog(null, "Las contraseñas deben ser iguales", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "El correo ya está registrado. Ingrese un correo único.", "Error", JOptionPane.ERROR_MESSAGE);
                                vista.txtCorreo.setText("");vista.txtCorreo.requestFocus();
                                }
                            } else {
                            JOptionPane.showMessageDialog(null, "El teléfono ya está registrado. Ingrese un teléfono único.", "Error", JOptionPane.ERROR_MESSAGE);
                            vista.txtTelefono.setText("");vista.txtTelefono.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La cédula ya está registrada. Ingrese una cédula única.", "Error", JOptionPane.ERROR_MESSAGE);
                        vista.txtCedula.setText("");vista.txtCedula.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Correo INCORRECTO.", "Error", JOptionPane.ERROR_MESSAGE);
                    vista.txtCorreo.setText("");vista.txtCorreo.requestFocus();

                }
            }else {
                JOptionPane.showMessageDialog(null, "Cédula INCORRECTA.", "Error", JOptionPane.ERROR_MESSAGE);
                vista.txtCedula.setText("");vista.txtCedula.requestFocus();
            }
        }else  JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);

    }


    public void limpiar(){
        vista.txtCedula.setText("");
        vista.txtNombres.setText("");
        vista.txtTelefono.setText("");
        vista.txtDireccion.setText("");
        vista.txtCorreo.setText("");
        vista.txtContra.setText("");
        vista.txtContraRep.setText("");
    }

    public void ocultarYMostrarVentanaLogin() {
        vista.dispose();
        controladorLogin.mostrarInterfaz();
    }

    public void mostrarContrasenia(){
        if(vista.chkMostrarContra.isSelected()){
            vista.txtContra.setEchoChar((char)0);
            vista.txtContraRep.setEchoChar((char)0);
        }
        else {
            vista.txtContra.setEchoChar('•');
            vista.txtContraRep.setEchoChar('•');
        }
    }

    private void enviarCorreo(String destino, String ID, String clave,String Nombre) {
        final String remitente = "designjartz@gmail.com";
        final String password = "qpwwrokprrzpgofd";

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");

        Session sesion = Session.getInstance(propiedades,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remitente, password);
                    }
                });

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            mensaje.setSubject("Bienvenido");
            mensaje.setText("¡Hola " + Nombre + "!\n\nTu cuenta ha sido creada en la aplicación.\n\nUsuario: " + ID + "\nContraseña: " + clave);

            Transport.send(mensaje);

            System.out.println("Correo enviado con éxito");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public class EnviarCorreoWorker extends SwingWorker<Void, Void> {
        private String destino;
        private String ID;
        private String clave;
        private String nombre;

        public EnviarCorreoWorker(String destino, String ID, String clave, String nombre) {
            this.destino = destino;
            this.ID = ID;
            this.clave = clave;
            this.nombre = nombre;
        }

        @Override
        protected Void doInBackground() {
            final String remitente = "designjartz@gmail.com";
            final String password = "qpwwrokprrzpgofd";

            Properties propiedades = new Properties();
            propiedades.put("mail.smtp.auth", "true");
            propiedades.put("mail.smtp.starttls.enable", "true");
            propiedades.put("mail.smtp.host", "smtp.gmail.com");
            propiedades.put("mail.smtp.port", "587");

            Session sesion = Session.getInstance(propiedades,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(remitente, password);
                        }
                    });

            try {
                Message mensaje = new MimeMessage(sesion);
                mensaje.setFrom(new InternetAddress(remitente));
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
                mensaje.setSubject("Bienvenido");
                mensaje.setText("¡Hola " + nombre + "!\n\nTu cuenta ha sido creada en la aplicación.\n\nUsuario: " + ID + "\nContraseña: " + clave);

                Transport.send(mensaje);

                System.out.println("Correo enviado con éxito");
            } catch (MessagingException e) {
                e.printStackTrace();  // Manejar de manera adecuada el error, por ejemplo, puedes llamar a setException en el SwingWorker.
            }

            return null;
        }
    }
    //validaciones

    public boolean validarContrasenias() {
        char[] passwordChar1 = vista.txtContra.getPassword();
        String contra1 = new String(passwordChar1);

        char[] passwordChar2 = vista.txtContraRep.getPassword();
        String contra2 = new String(passwordChar2);

        return contra1.equals(contra2);
    }




    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()==vista.txtCedula || e.getSource()==vista.txtTelefono ){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombres  || e.getSource()==vista.txtDireccion){
            if(Character.isLetter(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ) {
                e.setKeyChar(Character.toUpperCase(c));

            }else{
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static boolean validarCorreo(String correo) {
        String patronCorreo = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(patronCorreo);
        Matcher matcher = pattern.matcher(correo);


        return matcher.matches();
    }

    public static boolean validarCedula(String cedula) {
        // Verificar que la cédula tenga 10 dígitos
        if (cedula == null  || cedula.length() != 10) {
            return false;
        }
        try {
            // Verificar que la cédula contenga solo dígitos
            Long.parseLong(cedula);
            // Extraer el décimo dígito de la cédula
            int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
            // Aplicar el algoritmo de validación para cédulas en Ecuador
            int suma = 0;
            int coeficientes[] = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            for (int i = 0; i < coeficientes.length; i++) {
                int digito = Integer.parseInt(cedula.substring(i, i + 1));
                digito *= coeficientes[i];
                if (digito > 9) {
                    digito -= 9;
                }
                suma += digito;
            }
            int resultado = 10 - (suma % 10);
            // Verificar si el resultado coincide con el décimo dígito de la cédula
            return (resultado == digitoVerificador || (resultado == 10 && digitoVerificador == 0));
        } catch (NumberFormatException e) {
            // Si la cédula no es un número válido
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       if(e.getSource()==vista.btnRegresar) {
           limpiar();
           ocultarYMostrarVentanaLogin();
       }
    }
    public void mouseEntered(MouseEvent e) {
        vista.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        vista.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnRegistrarme)agregar();
        if(e.getSource()==vista.chkMostrarContra)mostrarContrasenia();
    }
}
