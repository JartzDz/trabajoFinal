package Controlador;

import Modelo.GestorUsuario;
import Modelo.Persona;
import Vista.InterfazUsuarios;
import javax.mail.*;
import javax.mail.PasswordAuthentication;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorUsuarios extends MouseAdapter implements ActionListener, KeyListener, FocusListener {
    private GestorUsuario modeloUsuario;
    private InterfazUsuarios vistaUsuario;
    DefaultTableModel modeloTabla;
    public ControladorUsuarios(GestorUsuario modelo, InterfazUsuarios vista){
        modeloUsuario = modelo;
        vistaUsuario = vista;
        modeloUsuario.recuperarUsuarios();
        vistaUsuario.btnAgregar.addActionListener(this);
        vistaUsuario.btnBuscar.addActionListener(this);
        vistaUsuario.btnMostrarUsuarios.addActionListener(this);
        vistaUsuario.btnEliminar.addActionListener(this);
        vistaUsuario.btnModificar.addActionListener(this);
        vista.txtID.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtBuscar.addKeyListener(this);
        vista.txtDireccion.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txtClave.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        String[] columnas = {"CEDULA", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "CONTRASEÑA"};
        modeloTabla = new DefaultTableModel(null, columnas);
        vistaUsuario.btnBuscar.setFocusable(false);
        vistaUsuario.btnRegresar.addMouseListener(this);
        vistaUsuario.btnMostrarUsuarios.addMouseListener(this);
        vistaUsuario.btnAgregar.addMouseListener(this);
        vistaUsuario.btnModificar.addMouseListener(this);
        vistaUsuario.btnEliminar.addMouseListener(this);
        vistaUsuario.btnBuscar.addMouseListener(this);

        activarBotones();
    }
    public void activarBotones(){
        if(modeloUsuario.getUsuarios().isEmpty()){
            vistaUsuario.txtBuscar.setEnabled(false);
            vistaUsuario.btnMostrarUsuarios.setEnabled(false);
            vistaUsuario.btnBuscar.setEnabled(false);
        }else{
            vistaUsuario.txtBuscar.setEnabled(true);
            vistaUsuario.btnMostrarUsuarios.setEnabled(true);
            vistaUsuario.btnBuscar.setEnabled(true);
        }
    }
    public void mostrarInterfazUsuarios() {
        vistaUsuario.setUndecorated(true);
        vistaUsuario.setTitle("USUARIOS");
        vistaUsuario.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaUsuario.setLocationRelativeTo(null);
        vistaUsuario.setResizable(true);
        vistaUsuario.setVisible(true);
    }

    public void limpiar(){
        vistaUsuario.txtID.setText("");
        vistaUsuario.txtNombre.setText("");
        vistaUsuario.txtTelefono.setText("");
        vistaUsuario.txtDireccion.setText("");
        vistaUsuario.txtCorreo.setText("");
        vistaUsuario.txtClave.setText("");
    }

    public void agregar() {
        String ID = vistaUsuario.txtID.getText();
        String nombreUsuario = vistaUsuario.txtNombre.getText();
        String tel = vistaUsuario.txtTelefono.getText();
        String direccion = vistaUsuario.txtDireccion.getText();
        String correo = vistaUsuario.txtCorreo.getText();
        String clave = vistaUsuario.txtClave.getText();
        String tipoUsuario = String.valueOf(vistaUsuario.cbTipoUsuario.getSelectedIndex());

        if (!nombreUsuario.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !ID.isEmpty()) {
            if(validarCedula(ID)) {
                if(validarCorreo(correo)) {
                    if(modeloUsuario.validarCedulaUnica(ID)) {
                        if (modeloUsuario.validarCorreoUnico(correo)) {
                            if (modeloUsuario.validarTelefonoUnico(tel)) {
                                if (modeloUsuario.validarCedulaUnica(ID) && modeloUsuario.validarTelefonoUnico(tel) && modeloUsuario.validarCorreoUnico(correo)) {
                                    modeloUsuario.agregarUsuario(ID, nombreUsuario, direccion, tel, correo, clave, Integer.parseInt(tipoUsuario));
                                    modeloUsuario.guardarUsuarios();
                                    enviarCorreo(correo, ID, clave, nombreUsuario);
                                    JOptionPane.showMessageDialog(null, "Usuario creado con éxito. Las credenciales fueron enviadas al usuario");
                                    limpiar();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El teléfono ya está registrado. Ingrese un teléfono único.", "Error", JOptionPane.ERROR_MESSAGE);
                                vistaUsuario.txtTelefono.setText("");
                                vistaUsuario.txtTelefono.requestFocus();
                            }
                        }else {
                                JOptionPane.showMessageDialog(null, "El correo ya está registrado. Ingrese un correo único.", "Error", JOptionPane.ERROR_MESSAGE);
                                vistaUsuario.txtCorreo.setText("");vistaUsuario.txtCorreo.requestFocus();
                            }
                    }else {
                        JOptionPane.showMessageDialog(null, "La cédula ya está registrada. Ingrese una cédula única.", "Error", JOptionPane.ERROR_MESSAGE);
                        vistaUsuario.txtID.setText("");vistaUsuario.txtID.requestFocus();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Correo INCORRECTO.", "Error", JOptionPane.ERROR_MESSAGE);
                    vistaUsuario.txtCorreo.setText("");
                    vistaUsuario.txtCorreo.requestFocus();
                }
            }else {
                JOptionPane.showMessageDialog(null, "Cédula INCORRECTA.", "Error", JOptionPane.ERROR_MESSAGE);
                vistaUsuario.txtID.setText("");
                vistaUsuario.txtID.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void eliminarTabla(){
        int fila=vistaUsuario.tablaUsuarios.getSelectedRow();
        String valor= (String) vistaUsuario.tablaUsuarios.getValueAt(fila,0);
        modeloUsuario.eliminarUsuario(fila);
        modeloUsuario.guardarUsuarios();
        mostrarUsuarios();
    }
    private void enviarCorreo(String destino, String ID, String clave,String Nombre) {
        final String remitente = "designjartz@gmail.com";
        final String password = "fuuiorpabfajsasz";

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
    public void cargarUsuario() {
        String cedulaBuscada = vistaUsuario.txtBuscar.getText();
        int indice = modeloUsuario.buscarUsuario(cedulaBuscada);

        System.out.println(cedulaBuscada);
        if (indice != -1) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("CEDULA");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("CONTRASEÑA");
            }
            modeloTabla.setRowCount(0);
            Object[] fila = {modeloUsuario.getUsuarios().get(indice).getCedula(), modeloUsuario.getUsuarios().get(indice).getNombre(), modeloUsuario.getUsuarios().get(indice).getDireccion(), modeloUsuario.getUsuarios().get(indice).getTelefono(), modeloUsuario.getUsuarios().get(indice).getCorreo(), modeloUsuario.getUsuarios().get(indice).getContrasenia()};
            modeloTabla.addRow(fila);
            vistaUsuario.tablaUsuarios.setModel(modeloTabla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la persona con esa Cédula", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void modificarUsuario() {
        String ID = vistaUsuario.txtID.getText();
        String nombreUsuario = vistaUsuario.txtNombre.getText();
        String tel = vistaUsuario.txtTelefono.getText();
        String direccion = vistaUsuario.txtDireccion.getText();
        String correo = vistaUsuario.txtCorreo.getText();
        String clave = vistaUsuario.txtClave.getText();
        String tipoUsuario = String.valueOf(vistaUsuario.cbTipoUsuario.getSelectedIndex());

        if (!nombreUsuario.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !ID.isEmpty()) {
            if (validarCedula(ID)) {
                if (validarCorreo(correo)) {
                    // Obtener el índice del usuario a modificar
                    int indice = modeloUsuario.buscarUsuario(ID);
                    // Verificar si el índice es válido
                    if (indice != -1) {
                        // Llama al método modificarUsuario en el modelo
                        modeloUsuario.modificarUsuario(ID, nombreUsuario, direccion, tel, correo, clave, indice);
                        modeloUsuario.guardarUsuarios();
                        JOptionPane.showMessageDialog(null, "Usuario modificado con éxito.");
                        limpiar();
                        mostrarUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el usuario con esa Cédula", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese correo válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese cédula válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarUsuarios() {
        if (!modeloUsuario.getUsuarios().isEmpty()) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("CEDULA");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("CONTRASEÑA");
            }
            modeloTabla.setRowCount(0);
            for (Persona p : modeloUsuario.getUsuarios()) {
                Object[] fila = {p.getCedula(), p.getNombre(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getContrasenia()};
                modeloTabla.addRow(fila);
            }
            vistaUsuario.tablaUsuarios.setModel(modeloTabla);
            vistaUsuario.btnMostrarUsuarios.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen usuarios ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTabla.setRowCount(0);
            vistaUsuario.btnMostrarUsuarios.setEnabled(false);
        }
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
        if(e.getSource()==vistaUsuario.btnRegresar) {
            limpiar();
            vistaUsuario.dispose();
        }
    }
    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);

        if (e.getSource() == vistaUsuario.btnAgregar) {
            vistaUsuario.btnAgregar.setBackground(bg);
            vistaUsuario.btnAgregar.setForeground(fg);
            vistaUsuario.btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vistaUsuario.btnModificar) {
            vistaUsuario.btnModificar.setBackground(bg);
            vistaUsuario.btnModificar.setForeground(fg);
            vistaUsuario.btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnMostrarUsuarios){
            vistaUsuario.btnMostrarUsuarios.setBackground(bg);
            vistaUsuario.btnMostrarUsuarios.setForeground(fg);
            vistaUsuario.btnMostrarUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnEliminar){
            vistaUsuario.btnEliminar.setBackground(bg);
            vistaUsuario.btnEliminar.setForeground(fg);
            vistaUsuario.btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnBuscar){
            vistaUsuario.btnBuscar.setBackground(bg);
            vistaUsuario.btnBuscar.setForeground(fg);
            vistaUsuario.btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnRegresar){
            vistaUsuario.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

    }
    public void mouseExited(MouseEvent e) {

        Color bg2 = new Color(229, 236, 186);
        Color fg2 = new Color(0, 0, 0);

        if (e.getSource() == vistaUsuario.btnAgregar) {
            vistaUsuario.btnAgregar.setBackground(bg2);
            vistaUsuario.btnAgregar.setForeground(fg2);
            vistaUsuario.btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vistaUsuario.btnModificar) {
            vistaUsuario.btnModificar.setBackground(bg2);
            vistaUsuario.btnModificar.setForeground(fg2);
            vistaUsuario.btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnMostrarUsuarios){
            vistaUsuario.btnMostrarUsuarios.setBackground(bg2);
            vistaUsuario.btnMostrarUsuarios.setForeground(fg2);
            vistaUsuario.btnMostrarUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnEliminar){
            vistaUsuario.btnEliminar.setBackground(bg2);
            vistaUsuario.btnEliminar.setForeground(fg2);
            vistaUsuario.btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaUsuario.btnBuscar){
            vistaUsuario.btnBuscar.setBackground(bg2);
            vistaUsuario.btnBuscar.setForeground(fg2);
            vistaUsuario.btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource()==vistaUsuario.btnRegresar){
            vistaUsuario.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaUsuario.btnAgregar) agregar();
        if(e.getSource()==vistaUsuario.btnMostrarUsuarios) mostrarUsuarios();
        if(e.getSource()==vistaUsuario.btnEliminar)eliminarTabla();
        if(e.getSource()==vistaUsuario.btnBuscar)cargarUsuario();
        if(e.getSource()==vistaUsuario.btnModificar) modificarUsuario();
    }

    @Override
    public void focusGained(FocusEvent e) {
        vistaUsuario.txtBuscar.setText("");
        vistaUsuario.txtBuscar.setForeground(Color.BLACK);
        vistaUsuario.btnBuscar.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
        vistaUsuario.txtBuscar.setForeground(Color.GRAY);
        vistaUsuario.txtBuscar.setText("Ingrese el ID del Usuario");
        vistaUsuario.btnBuscar.setEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()==vistaUsuario.txtID){
            validarCedula(vistaUsuario.txtID.getText());
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vistaUsuario.txtTelefono){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vistaUsuario.txtNombre || e.getSource()==vistaUsuario.txtDireccion){
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
}
