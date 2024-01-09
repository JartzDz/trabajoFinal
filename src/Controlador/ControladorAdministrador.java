package Controlador;

import Modelo.*;
import Vista.InterfazLogin;
import Vista.InterfazPrincipalAdmin;


import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorAdministrador extends MouseAdapter implements ActionListener, KeyListener,FocusListener  {
    InterfazPrincipalAdmin vista;
    GestorUsuario modeloUsuarios;
    InterfazLogin interfazLogin;
    ControladorMascotas controladorMascotas;
    ControladorUsuarios controladorUsuarios;
    ControladorEstablecimientos controladorEstablecimientos;
    GestorEstablecimiento modeloEstablecimiento;
    GestorMascotas modeloMascota;
    HashSet<String> idSet;
    DefaultTableModel modeloTablaEst;
    DefaultTableModel modeloTablaUsuarios;
    DefaultTableModel modeloTablaMascotas;
    File dirImagen;
    String rutaDestino;
    String usuario="";
    CardLayout cardLayout;
    JPanel panelCards;

    public ControladorAdministrador(InterfazPrincipalAdmin vista, GestorUsuario modeloUsuarios, ControladorMascotas controladorMascotas, ControladorUsuarios controladorUsuarios, ControladorEstablecimientos controladorEstablecimientos, InterfazLogin interfazLogin, GestorMascotas modeloMascota, GestorEstablecimiento modeloEstablecimiento) {
        this.vista = vista;
        this.modeloUsuarios = modeloUsuarios;
        this.controladorMascotas = controladorMascotas;
        this.controladorUsuarios = controladorUsuarios;
        this.modeloMascota = modeloMascota;
        this.controladorEstablecimientos = controladorEstablecimientos;
        this.modeloEstablecimiento=modeloEstablecimiento;
        this.interfazLogin = interfazLogin;
        cardLayout = new CardLayout();
        vista.btnGestionEst.addMouseListener(this);
        vista.btnSalir.addMouseListener(this);
        vista.btnGestionUsuarios.addMouseListener(this);
        vista.btnGestionCGA.addMouseListener(this);
        vista.btnGestionMascotas.addMouseListener(this);
        vista.btnGestionMascotas.setOpaque(true);
        vista.btnSalir.setOpaque(true);
        vista.btnGestionEst.setOpaque(true);
        vista.btnGestionCGA.setOpaque(true);
        vista.btnGestionUsuarios.setOpaque(true);
        vista.panelPrincipal.setLayout(cardLayout);
        // Se agrega los paneles al panel principal
        vista.panelPrincipal.add(vista.panelUsuarios, "Usuarios");
        vista.panelPrincipal.add(vista.panelMascotas, "Mascotas");
        vista.panelPrincipal.add(vista.panelEstablecimientos, "Establecimientos");
        vista.panelPrincipal.add(vista.panelCGA, "CGA");
        vista.panelPrincipal.add(vista.panelBienvenida, "Bienvenida");

        cardLayout.show(vista.panelPrincipal, "Bienvenida");
        modeloMascota.recuperarMascotas();
        //Usuarios
        modeloUsuarios.recuperarUsuarios();
        vista.btnAgregar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnMostrarUsuarios.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.txtID.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtBuscar.addKeyListener(this);
        vista.txtDireccion.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txtClave.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        String[] columnasUsuarios = {"CEDULA", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "CONTRASEÑA"};
        modeloTablaUsuarios = new DefaultTableModel(null, columnasUsuarios);
        vista.btnBuscar.setFocusable(false);
        vista.btnRegresar.addMouseListener(this);
        vista.btnMostrarUsuarios.addMouseListener(this);
        vista.btnAgregar.addMouseListener(this);
        vista.btnModificar.addMouseListener(this);
        vista.btnEliminar.addMouseListener(this);
        vista.btnBuscar.addMouseListener(this);
        //Mascotas
        vista.btnAgregarMascota.addActionListener(this);
        vista.btnEliminarMascota.addActionListener(this);
        vista.btnModificarMascota.addActionListener(this);
        vista.btnMostrarMascotas.addActionListener(this);
        vista.btnBuscarMascota.addActionListener(this);
        vista.txtIDMascota.addKeyListener(this);
        vista.txtDuenio.addKeyListener(this);
        vista.txtBuscarMascota.addKeyListener(this);
        vista.txtRaza.addKeyListener(this);
        vista.txtNombreMascota.addKeyListener(this);
        vista.txtColor.addKeyListener(this);
        vista.txtBuscarMascota.addFocusListener(this);
        vista.btnBuscarMascota.setFocusable(false);
        vista.txtBuscarMascota.setEnabled(false);

        String[] columnasMascotas = {"ID", "NOMBRE", "RAZA", "DUEÑO","EDAD","COLOR","SEXO","VACUNAS","DESPARASITACIONES","ESTERILIZACION","OTRAS CIRUGIAS"};
        modeloTablaMascotas = new DefaultTableModel(null, columnasMascotas);
        vista.btnMostrarMascotas.setEnabled(false);
        vista.btnBuscarMascota.setEnabled(false);
        vista.btnSubirFotoCarnet.addActionListener(this);
        vista.btnRegresar.addMouseListener(this);
        vista.btnRegresar2.addMouseListener(this);
        vista.btnRegresar3.addMouseListener(this);
        vista.btnRegresar4.addMouseListener(this);

        vista.btnModificarMascota.addMouseListener(this);
        vista.btnBuscarMascota.addMouseListener(this);
        vista.btnAgregarMascota.addMouseListener(this);
        vista.btnEliminarMascota.addMouseListener(this);
        vista.btnMostrarMascotas.addMouseListener(this);
        vista.btnSubirFotoCarnet.addMouseListener(this);
        vista.setUndecorated(true);
        activarBotones();

        //Establecimientos
        modeloEstablecimiento.recuperarEstablecimientos();
        vista.btnBuscarEst.addActionListener(this);
        vista.btnAgregarEst.addActionListener(this);
        vista.btnEliminarEst.addActionListener(this);
        vista.btnMostrarEst.addActionListener(this);
        vista.btnModificarEst.addActionListener(this);
        vista.btnBuscarEst.setFocusable(false);
        vista.cboCIProp.addActionListener(this);
        vista.txtRUC.addKeyListener(this);
        vista.txtTelfEst.addKeyListener(this);
        vista.txtBuscarEst.addKeyListener(this);
        vista.txtDireccionEst.addKeyListener(this);
        vista.txtNombreEst.addKeyListener(this);
        vista.txtCorreoEst.addKeyListener(this);
        vista.txtBuscarEst.addFocusListener(this);
        vista.cboTipoEst.addActionListener(this);
        vista.cboSubTipoEst.addActionListener(this);
        vista.cboSubTipoEst.setEnabled(false);
        modeloUsuarios.recuperarUsuarios();
        vista.btnAgregarEst.addMouseListener(this);
        vista.btnEliminarEst.addMouseListener(this);
        vista.btnMostrarEst.addMouseListener(this);
        vista.btnModificarEst.addMouseListener(this);
        String[] columnasEst = {"RUC", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "PROPIETARIO", "TIPO"};
        modeloTablaEst = new DefaultTableModel(null, columnasEst);
        activarBotonesEst();
        actualizarSubtipo();

    }
    public void mostrarInterfaz(){
        cargarCombo();
        vista.setTitle("ADMINISTRADOR");
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setLocationRelativeTo(null);
        vista.setResizable(true);
        vista.setVisible(true);
    }

    //funciones Usuario
    public void activarBotonesUsuario(){
        if(modeloUsuarios.getUsuarios().isEmpty()){
            vista.txtBuscar.setEnabled(false);
            vista.btnMostrarUsuarios.setEnabled(false);
            vista.btnBuscar.setEnabled(false);
        }else{
            vista.txtBuscar.setEnabled(true);
            vista.btnMostrarUsuarios.setEnabled(true);
            vista.btnBuscar.setEnabled(true);
        }
    }

    public void limpiarUsuarios(){
        vista.txtID.setText("");
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtDireccion.setText("");
        vista.txtCorreo.setText("");
        vista.txtClave.setText("");
    }

    public void agregarUsuarios() {
        String ID = vista.txtID.getText();
        String nombreUsuario = vista.txtNombre.getText();
        String tel = vista.txtTelefono.getText();
        String direccion = vista.txtDireccion.getText();
        String correo = vista.txtCorreo.getText();
        String clave = vista.txtClave.getText();
        String tipoUsuario = String.valueOf(vista.cbTipoUsuario.getSelectedIndex());

        if (!nombreUsuario.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !ID.isEmpty()) {
            if(validarCedula(ID)) {
                if(validarCorreo(correo)) {
                    if(modeloUsuarios.validarCedulaUnica(ID)) {
                        if (modeloUsuarios.validarCorreoUnico(correo)) {
                            if (modeloUsuarios.validarTelefonoUnico(tel)) {
                                if (modeloUsuarios.validarCedulaUnica(ID) && modeloUsuarios.validarTelefonoUnico(tel) && modeloUsuarios.validarCorreoUnico(correo)) {
                                    modeloUsuarios.agregarUsuario(ID, nombreUsuario, direccion, tel, correo, clave, Integer.parseInt(tipoUsuario));
                                    modeloUsuarios.guardarUsuarios();
                                    EnviarCorreoWorker worker = new EnviarCorreoWorker(correo, ID, clave, nombreUsuario);
                                    worker.execute();
                                    JOptionPane.showMessageDialog(null, "Usuario creado con éxito. Las credenciales fueron enviadas al usuario");
                                    mostrarUsuarios();
                                    limpiarUsuarios();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El teléfono ya está registrado. Ingrese un teléfono único.", "Error", JOptionPane.ERROR_MESSAGE);
                                vista.txtTelefono.setText("");
                                vista.txtTelefono.requestFocus();
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "El correo ya está registrado. Ingrese un correo único.", "Error", JOptionPane.ERROR_MESSAGE);
                            vista.txtCorreo.setText("");vista.txtCorreo.requestFocus();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "La cédula ya está registrada. Ingrese una cédula única.", "Error", JOptionPane.ERROR_MESSAGE);
                        vista.txtID.setText("");vista.txtID.requestFocus();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Correo INCORRECTO.", "Error", JOptionPane.ERROR_MESSAGE);
                    vista.txtCorreo.setText("");
                    vista.txtCorreo.requestFocus();
                }
            }else {
                JOptionPane.showMessageDialog(null, "Cédula INCORRECTA.", "Error", JOptionPane.ERROR_MESSAGE);
                vista.txtID.setText("");
                vista.txtID.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarTablaUsuarios() {
        int fila = vista.tablaUsuarios.getSelectedRow();

        if (fila == -1) {
            // No hay fila seleccionada, muestra un mensaje de error
            JOptionPane.showMessageDialog(vista, "Seleccione la fila que desea eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String valor = (String) vista.tablaUsuarios.getValueAt(fila, 0);
            modeloUsuarios.eliminarUsuario(fila);
            modeloUsuarios.guardarUsuarios();
            mostrarUsuarios();
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

    public void cargarUsuario() {
        String cedulaBuscada = vista.txtBuscar.getText();
        int indice = modeloUsuarios.buscarUsuario(cedulaBuscada);

        System.out.println(cedulaBuscada);
        if (indice != -1) {
            if (modeloTablaUsuarios.getColumnCount() == 0) {
                modeloTablaUsuarios.addColumn("CEDULA");
                modeloTablaUsuarios.addColumn("NOMBRE");
                modeloTablaUsuarios.addColumn("DIRECCION");
                modeloTablaUsuarios.addColumn("TELEFONO");
                modeloTablaUsuarios.addColumn("CORREO");
                modeloTablaUsuarios.addColumn("CONTRASEÑA");
            }
            modeloTablaUsuarios.setRowCount(0);
            Object[] fila = {modeloUsuarios.getUsuarios().get(indice).getCedula(), modeloUsuarios.getUsuarios().get(indice).getNombre(), modeloUsuarios.getUsuarios().get(indice).getDireccion(), modeloUsuarios.getUsuarios().get(indice).getTelefono(), modeloUsuarios.getUsuarios().get(indice).getCorreo(), modeloUsuarios.getUsuarios().get(indice).getContrasenia()};
            modeloTablaUsuarios.addRow(fila);
            vista.tablaUsuarios.setModel(modeloTablaUsuarios);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la persona con esa Cédula", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void modificarUsuario() {
        String ID = vista.txtID.getText();
        String nombreUsuario = vista.txtNombre.getText();
        String tel = vista.txtTelefono.getText();
        String direccion = vista.txtDireccion.getText();
        String correo = vista.txtCorreo.getText();
        String clave = vista.txtClave.getText();
        String tipoUsuario = String.valueOf(vista.cbTipoUsuario.getSelectedIndex());

        if (!nombreUsuario.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !ID.isEmpty()) {
            if (validarCedula(ID)) {
                if (validarCorreo(correo)) {
                    // Obtener el índice del usuario a modificar
                    int indice = modeloUsuarios.buscarUsuario(ID);
                    // Verificar si el índice es válido
                    if (indice != -1) {
                        // Llama al método modificarUsuario en el modelo
                        modeloUsuarios.modificarUsuario(ID, nombreUsuario, direccion, tel, correo, clave, indice);
                        modeloUsuarios.guardarUsuarios();
                        JOptionPane.showMessageDialog(null, "Usuario modificado con éxito.");
                        limpiarUsuarios();
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
        if (!modeloUsuarios.getUsuarios().isEmpty()) {
            if (modeloTablaUsuarios.getColumnCount() == 0) {
                modeloTablaUsuarios.addColumn("CEDULA");
                modeloTablaUsuarios.addColumn("NOMBRE");
                modeloTablaUsuarios.addColumn("DIRECCION");
                modeloTablaUsuarios.addColumn("TELEFONO");
                modeloTablaUsuarios.addColumn("CORREO");
                modeloTablaUsuarios.addColumn("CONTRASEÑA");
            }
            modeloTablaUsuarios.setRowCount(0);
            for (Persona p : modeloUsuarios.getUsuarios()) {
                Object[] fila = {p.getCedula(), p.getNombre(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getContrasenia()};
                modeloTablaUsuarios.addRow(fila);
            }
            vista.tablaUsuarios.setModel(modeloTablaUsuarios);
            vista.btnMostrarUsuarios.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen usuarios ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTablaUsuarios.setRowCount(0);
            vista.btnMostrarUsuarios.setEnabled(false);
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


    //funciones Mascota
    public void activarBotones(){
        if(modeloMascota.getListaMascotas().isEmpty()){
            vista.txtBuscarMascota.setEnabled(false);
            vista.btnMostrarMascotas.setEnabled(false);
            vista.btnBuscarMascota.setEnabled(false);
        }else{
            vista.txtBuscarMascota.setEnabled(true);
            vista.btnMostrarMascotas.setEnabled(true);
            vista.btnBuscarMascota.setEnabled(true);
        }
    }

    public void limpiar(){
        vista.txtIDMascota.setText("");
        vista.txtNombreMascota.setText("");
        vista.txtRaza.setText("");
        vista.txtDuenio.setText("");
        vista.txtBuscarMascota.setText("");
        vista.txtColor.setText("");
        vista.spnEdad.setValue(0);
        vista.lblImagen.setIcon(null);
        DefaultTableModel modelo = (DefaultTableModel)  vista.tablaEstablecimientos.getModel();
        modelo.setRowCount(0);
        vista.chkCirugias.setSelected(false);
        vista.chkDesparacitaciones.setSelected(false);
        vista.chkEsterilizacion.setSelected(false);
        vista.chkVacunas.setSelected(false);

    }

    public void agregar() {
        try {
            String idMascota = vista.txtIDMascota.getText();
            String nombreMascota = vista.txtNombreMascota.getText();
            String raza = vista.txtRaza.getText();
            String duenio = vista.txtDuenio.getText();
            int edad = (int) vista.spnEdad.getValue();
            String sexo = vista.cboSexo.getSelectedItem().toString();
            System.out.println(sexo);
            String color = vista.txtColor.getText();
            boolean vacunas = vista.chkVacunas.isSelected();
            boolean esterilizacion = vista.chkEsterilizacion.isSelected();
            boolean desparacitaciones = vista.chkDesparacitaciones.isSelected();
            boolean otrasCirugias = vista.chkCirugias.isSelected();

            if (!nombreMascota.isEmpty() && !raza.isEmpty() && !duenio.isEmpty() && !color.isEmpty()) {
                if (ControladorUsuarios.validarCedula(duenio)) {
                    // Verificar si la imagen en lblImagen no es null
                    if (vista.lblImagen.getIcon() == null) {
                        JOptionPane.showMessageDialog(null, "Por favor, seleccione una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    guardarImagen(dirImagen);
                    modeloMascota.agregarMascota(idMascota, edad, nombreMascota, sexo, raza, color, duenio, dirImagen, vacunas, desparacitaciones, esterilizacion, otrasCirugias);
                    modeloMascota.guardarMascotas();
                    mostrarMascotas();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Cédula del propietario INCORRECTA.", "Error", JOptionPane.ERROR_MESSAGE);
                    vista.txtDuenio.setText("");
                    vista.txtDuenio.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void generarYMostrarID() {
        String idMascota = generarID();
        vista.txtIDMascota.setText(idMascota);
    }
    private String generarID() {

        char letra = (char) ('A' + new Random().nextInt(26));
        String digitos = generarDigitosUnicos();
        return letra + digitos;
    }


    private String generarDigitosUnicos() {
        Random random = new Random();
        StringBuilder digitos = new StringBuilder();

        while (digitos.length() < 4) {
            int digito = random.nextInt(10);
            if (!digitos.toString().contains(String.valueOf(digito))) {
                digitos.append(digito);
            }
        }

        return digitos.toString();
    }


    public void eliminarTabla() {
        // Verifica si hay una fila seleccionada
        int fila = vista.tablaEstablecimientos.getSelectedRow();
        if (fila != -1) {
            String idMascota = (String) vista.tablaEstablecimientos.getValueAt(fila, 0);
            try {
                int pos = modeloMascota.buscarMascota(idMascota);
                modeloMascota.eliminarMascota(pos);
                modeloMascota.guardarMascotas();

                // Remueve la fila seleccionada del modelo de la tabla
                modeloTablaMascotas.removeRow(fila);

                JOptionPane.showMessageDialog(null, "Mascota eliminada con éxito.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error al convertir el ID a entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila antes de eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    public void buscarMascota(){
        String valor = vista.txtBuscarMascota.getText();
        int indice= modeloMascota.buscarMascota(valor);
        if(indice!=-1){
            JOptionPane.showMessageDialog(null, "Mascota Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Mascota No Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void guardarImagen(File archivo) {
        try {
            String dest = "./src/vista/imagenes/" + archivo.getName();
            Path destino = Paths.get(dest);
            String origen = archivo.getPath();
            Path org = Paths.get(origen);

            Files.copy(org, destino, StandardCopyOption.REPLACE_EXISTING);
            rutaDestino = archivo.getName();
        } catch (IOException ex) {
            System.out.println("No se guardó la imagen" + ex.getMessage());
        }
    }


    public void cargarMascota() {
        String textoID = vista.txtBuscarMascota.getText();

        if (!textoID.isEmpty()) {
            try {
                int indice = modeloMascota.buscarMascota(textoID);

                if (indice != -1) {
                    // Limpiar el modelo de la tabla antes de agregar columnas y filas
                    modeloTablaMascotas.setRowCount(0);

                    // Verificar si las columnas ya han sido agregadas al modelo de la tabla
                    if (modeloTablaMascotas.getColumnCount() == 0) {
                        modeloTablaMascotas.addColumn("ID");
                        modeloTablaMascotas.addColumn("NOMBRE");
                        modeloTablaMascotas.addColumn("RAZA");
                        modeloTablaMascotas.addColumn("DUEÑO");
                        modeloTablaMascotas.addColumn("EDAD");
                        modeloTablaMascotas.addColumn("COLOR");
                        modeloTablaMascotas.addColumn("SEXO");
                        modeloTablaMascotas.addColumn("VACUNAS");
                        modeloTablaMascotas.addColumn("DESPARASITACIONES");
                        modeloTablaMascotas.addColumn("ESTERILIZACION");
                        modeloTablaMascotas.addColumn("OTRAS CIRUGIAS");
                    }

                    Mascota mascota = modeloMascota.mostrarMascotas().get(indice);

                    Object[] fila = {
                            mascota.getID(),
                            mascota.getNombreMascota(),
                            mascota.getRaza(),
                            mascota.getDuenio(),
                            mascota.getEdad(),
                            mascota.getColor(),
                            mascota.getSexo(),
                            mascota.isVacunas(),
                            mascota.isDesparasitaciones(),
                            mascota.isEsterilizacion(),
                            mascota.isOtrasCirugias()
                    };

                    modeloTablaMascotas.addRow(fila);
                    vista.tablaMascotas.setModel(modeloTablaMascotas);

                    // Cargar la imagen de la mascota en lblImagenMascota
                    String rutaImagen = mascota.getRutaFotoCarnet();
                    System.out.println("Ruta de la imagen: " + rutaImagen);
                    cargarImagenMascota(rutaImagen);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la mascota con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar mascota", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void cargarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (archivo != null) {
                String origen = archivo.getPath();
                Path org = Paths.get(origen);

                try {
                    Image image = ImageIO.read(archivo);
                    Image imagenEscalada = image.getScaledInstance(vista.lblImagen.getWidth(), vista.lblImagen.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(imagenEscalada);
                    vista.lblImagen.setIcon(icon);
                    dirImagen = archivo;
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una Imagen");
        }
    }


    // Método para cargar la imagen de la mascota en lblImagen
    private void cargarImagenMascota(String rutaImagen) {
        try {
            if (rutaImagen != null) {
                File file = new File(rutaImagen);

                // Verificar si el archivo existe
                if (file.exists()) {
                    Image image = ImageIO.read(file);
                    Image imagenEscalada = image.getScaledInstance(vista.lblImagen.getWidth(), vista.lblImagen.getHeight(), Image.SCALE_SMOOTH);
                    vista.lblImagen.setIcon(new ImageIcon(imagenEscalada));
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo de imagen no existe: " + rutaImagen, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La ruta de la imagen es nula", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen de la mascota", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarMascota() {
        String textoID = vista.txtIDMascota.getText();
        String nombreMascota = vista.txtNombreMascota.getText();
        String raza = vista.txtRaza.getText();
        String duenio = vista.txtDuenio.getText();
        int edad = (int) vista.spnEdad.getValue();
        String sexo = vista.cboSexo.getSelectedItem().toString();
        String color = vista.txtColor.getText();

        boolean vacunas = vista.chkVacunas.isSelected();
        boolean esterilizacion = vista.chkEsterilizacion.isSelected();
        boolean desparacitaciones = vista.chkDesparacitaciones.isSelected();
        boolean otrasCirugias = vista.chkCirugias.isSelected();

        if (!textoID.isEmpty() && !nombreMascota.isEmpty() && !raza.isEmpty() && !duenio.isEmpty() && !color.isEmpty()) {
            try {
                int indice = modeloMascota.buscarMascota(textoID);

                if (indice != -1) {
                    guardarImagen(dirImagen);
                    String nuevaRutaFotoCarnet = modeloMascota.modificarMascota(textoID, edad, nombreMascota, sexo, raza, color, duenio, dirImagen, vacunas, desparacitaciones, esterilizacion, otrasCirugias, indice);

                    // Guardas las mascotas actualizadas en el modelo
                    modeloMascota.guardarMascotas();

                    // Cargas la nueva imagen en la vista
                    cargarImagenMascota(nuevaRutaFotoCarnet);

                    JOptionPane.showMessageDialog(null, "Mascota modificada con éxito.");
                    limpiar();
                    mostrarMascotas();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la mascota con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarMascotas() {
        // Limpia el modelo de la tabla antes de agregar columnas y filas
        modeloTablaMascotas.setRowCount(0);

        if (!modeloMascota.mostrarMascotas().isEmpty()) {
            // Verifica si las columnas ya han sido agregadas al modelo de la tabla
            if (modeloTablaMascotas.getColumnCount() == 0) {
                modeloTablaMascotas.addColumn("ID");
                modeloTablaMascotas.addColumn("NOMBRE");
                modeloTablaMascotas.addColumn("RAZA");
                modeloTablaMascotas.addColumn("EDAD");
                modeloTablaMascotas.addColumn("DUEÑO");
                modeloTablaMascotas.addColumn("SEXO");
                modeloTablaMascotas.addColumn("COLOR");
                modeloTablaMascotas.addColumn("VACUNAS");
                modeloTablaMascotas.addColumn("DESPARASITACIONES");
                modeloTablaMascotas.addColumn("ESTERILIZACION");
                modeloTablaMascotas.addColumn("OTRAS CIRUGIAS");
            }

            for (Mascota mascota : modeloMascota.mostrarMascotas()) {

                Object[] fila = {
                        mascota.getID(),
                        mascota.getNombreMascota(),
                        mascota.getRaza(),
                        mascota.getDuenio(),
                        mascota.getEdad(),
                        mascota.getColor(),
                        mascota.getSexo(),
                        mascota.isVacunas(),
                        mascota.isDesparasitaciones(),
                        mascota.isEsterilizacion(),
                        mascota.isOtrasCirugias()
                };
                modeloTablaMascotas.addRow(fila);
            }

            vista.tablaMascotas.setModel(modeloTablaMascotas);


        } else {
            JOptionPane.showMessageDialog(null, "No existen mascotas ingresadas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Establecimientos

    private void actualizarSubtipo() {
        // Limpiar el modelo del JComboBox secundario
        DefaultComboBoxModel<String> modeloSubTipo = new DefaultComboBoxModel<>();
        vista.cboSubTipoEst.setModel(modeloSubTipo);

        // Obtener la selección del JComboBox principal (Tipo)
        String seleccionTipo = vista.cboTipoEst.getSelectedItem().toString();

        // Agregar elementos al JComboBox secundario (Subtipo) según la selección en el JComboBox principal
        if ("CENTRO DE ATENCIÓN MÉDICO VETERINARIA".equals(seleccionTipo)) {
            modeloSubTipo.addElement("MEDICINA VETERINARIA A DOMICILIO");
            modeloSubTipo.addElement("CONSULTORIOS VETERINARIOS");
            modeloSubTipo.addElement("CLINICAS VETERINARIAS");
            modeloSubTipo.addElement("HOSPITALES VETERINARIOS");
            modeloSubTipo.addElement("U. VETERINARIAS MOVILES");
            modeloSubTipo.addElement("SERVICIOS DE REHABILITACION");
            modeloSubTipo.addElement("CAMPAÑAS DE ESTERILIZACION");
            modeloSubTipo.addElement("CENTROS DE ESTERILIZACION");
        } else if ("CENTRO DE MANEJO".equals(seleccionTipo)) {
            modeloSubTipo.addElement("CENTRO DE CRIANZA");
            modeloSubTipo.addElement("TIENDAS DE MASCOTAS");
            modeloSubTipo.addElement("CENTROS DE ESTETICA ANIMAL");
            modeloSubTipo.addElement("HOTELES Y ALOJAMIENTO");
            modeloSubTipo.addElement("ALBERGUES");
            modeloSubTipo.addElement("CENTROS DE ADIESTRAMIENTO");
            modeloSubTipo.addElement("ESTABLECIMIENTOS PARA ESPECTACULOS");
            modeloSubTipo.addElement("CENTROS DE INVESTIGACION");
            modeloSubTipo.addElement("CENTROS DE CUARENTENA");
            modeloSubTipo.addElement("OTROS");
        }
        vista.cboSubTipoEst.setEnabled(true);
    }

    public boolean esPropietario(){
        int indice = modeloUsuarios.buscarUsuario(usuario);
        if(modeloUsuarios.getUsuarios().get(indice) instanceof Administrador){
            return false;
        }
        return true;
    }

    public void cargarCombo() {
        if (!esPropietario()) {
            for (Persona p : modeloUsuarios.getUsuarios()) {
                if (p instanceof DuenioEstablecimiento) {
                    vista.cboCIProp.addItem(p.getCedula() + "-" + p.getNombre());
                }
            }

            ArrayList<String> cedulas = separarCedula();
            for (String cedula : cedulas) {
                System.out.println(cedula);
            }
        }

    }

    public ArrayList<String> separarCedula() {
        ArrayList<String> cedulas = new ArrayList<>();

        int itemCount = vista.cboCIProp.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            Object item = vista.cboCIProp.getItemAt(i);
            String combo = item.toString();
            String[] partes = combo.split("-");

            if (partes.length > 0) {
                String cedula = partes[0].trim();
                cedulas.add(cedula);
            } else {
                System.out.println("Error al separar cédula y nombre para: " + combo);
            }
        }

        return cedulas;
    }
    public void limpiarEst(){
        vista.txtRUC.setText("");
        vista.txtNombreEst.setText("");
        vista.txtTelfEst.setText("");
        vista.txtDireccionEst.setText("");
        vista.txtCorreoEst.setText("");
    }
    public void activarBotonesEst(){
        if(modeloEstablecimiento.getEstablecimiento().isEmpty()){
            vista.txtBuscarEst.setEnabled(false);
            vista.btnMostrarEst.setEnabled(false);
            vista.btnBuscarEst.setEnabled(false);
        }else{
            vista.txtBuscarEst.setEnabled(true);
            vista.btnMostrarEst.setEnabled(true);
            vista.btnBuscarEst.setEnabled(true);
        }
    }

    public void agregarEst() {
        String RUC = vista.txtRUC.getText();
        String nombreEstablecimiento = vista.txtNombreEst.getText();
        String tel = vista.txtTelfEst.getText();
        String direccion = vista.txtDireccionEst.getText();
        String correo = vista.txtCorreoEst.getText();
        String CIPropietario = vista.cboCIProp.getSelectedItem().toString();
        String tipoEstablecimiento = String.valueOf(vista.cboSubTipoEst.getSelectedItem());

        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !RUC.isEmpty()) {
            // Validar RUC antes de agregar el establecimiento
            System.out.println("Valor del RUC: " + RUC);
            if (validarRUC(RUC)) {
                if (validarCorreo(correo)) {
                    modeloEstablecimiento.agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, tipoEstablecimiento);
                    modeloEstablecimiento.guardarEstablecimientos();
                    // enviarCorreo(correo, ID, clave, nombreUsuario);
                    JOptionPane.showMessageDialog(null, "Establecimiento creado con éxito. Las credenciales fueron enviadas al Propietario");
                    mostrarEstablecimiento();
                    limpiarEst();
                } else {
                    JOptionPane.showMessageDialog(null, "Correo Inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    vista.txtCorreoEst.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "RUC Inválido", "Error", JOptionPane.ERROR_MESSAGE);
                vista.txtRUC.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarTablaEst() {
        try {
            int fila = vista.tablaEstablecimientos.getSelectedRow();

            if (fila == -1) {
                // No hay fila seleccionada, muestra un mensaje de error
                throw new Exception("Seleccione la fila que desea eliminar.");
            }

            String valor = (String) vista.tablaEstablecimientos.getValueAt(fila, 0);
            modeloEstablecimiento.eliminarEstablecimiento(fila);
            modeloEstablecimiento.guardarEstablecimientos();
            mostrarEstablecimiento();


            JOptionPane.showMessageDialog(vista, "Registro eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Capturar la excepción y mostrar mensaje de error
            JOptionPane.showMessageDialog(vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void mostrarEstablecimiento() {
        if (!modeloEstablecimiento.getEstablecimiento().isEmpty()) {
            if (modeloTablaEst.getColumnCount() == 0) {
                modeloTablaEst.addColumn("RUC");
                modeloTablaEst.addColumn("NOMBRE");
                modeloTablaEst.addColumn("DIRECCION");
                modeloTablaEst.addColumn("TELEFONO");
                modeloTablaEst.addColumn("CORREO");
                modeloTablaEst.addColumn("PROPIETARIO");
                modeloTablaEst.addColumn("TIPO");
            }
            modeloTablaEst.setRowCount(0);
            ArrayList<Establecimiento> listaEstablecimiento;
            if(esPropietario()){
                listaEstablecimiento = modeloEstablecimiento.buscarEstablecimientosDuenio(usuario);
            }else{
                listaEstablecimiento = modeloEstablecimiento.getEstablecimiento();
            }
            for (Establecimiento p : listaEstablecimiento) {
                Object[] fila = {p.getRuc(), p.getNombreEst(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getCIRepresentante(), p.getTipoEstablecimiento()};
                modeloTablaEst.addRow(fila);
            }
            vista.tablaEstablecimientos.setModel(modeloTablaEst);
            vista.btnMostrarEst.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen establecimientos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTablaEst.setRowCount(0);
            vista.btnMostrarEst.setEnabled(false);
        }
    }

    public void cargarEst() {
        String textoID = vista.txtBuscarEst.getText();

        if (!textoID.isEmpty()) {
            try {
                int indice = modeloEstablecimiento.buscarEst(textoID);

                if (indice != -1) {
                    // Limpiar el modelo de la tabla antes de agregar columnas y filas
                    modeloTablaEst.setRowCount(0);

                    // Verificar si las columnas ya han sido agregadas al modelo de la tabla
                    if (modeloTablaEst.getColumnCount() == 0) {
                        modeloTablaEst.addColumn("RUC");
                        modeloTablaEst.addColumn("NOMBRE");
                        modeloTablaEst.addColumn("DIRECCION");
                        modeloTablaEst.addColumn("TELEFONO");
                        modeloTablaEst.addColumn("CORREO");
                        modeloTablaEst.addColumn("PROPIETARIO");
                        modeloTablaEst.addColumn("TIPO");
                    }

                    Object[] fila = {
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getRuc(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getNombreEst(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getDireccion(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getTelefono(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getCorreo(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getCIRepresentante(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getTipoEstablecimiento(),
                    };

                    modeloTablaEst.addRow(fila);
                    vista.tablaEstablecimientos.setModel(modeloTablaEst);

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el establecimiento", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un RUC válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un RUC o NOMBRE para buscar establecimiento", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void modificarEstablecimiento() {
        String RUC = vista.txtRUC.getText();
        String nombreEstablecimiento = vista.txtNombreEst.getText();
        String tel = vista.txtTelfEst.getText();
        String direccion = vista.txtDireccionEst.getText();
        String correo = vista.txtCorreoEst.getText();
        String CIPropietario = vista.cboCIProp.getSelectedItem().toString();
        String tipoEstablecimiento = String.valueOf(vista.cboSubTipoEst.getSelectedIndex());

        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !CIPropietario.isEmpty() && !RUC.isEmpty()) {
            if (validarCorreo(correo)) {
                // Obtener el índice del usuario a modificar
                int indice = GestorEstablecimiento.buscarEstablecimiento(RUC, modeloEstablecimiento.getEstablecimiento());
                // Verificar si el índice es válido
                if (indice != -1) {
                    // Llama al método modificarUsuario en el modelo
                    modeloEstablecimiento.modificarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, tipoEstablecimiento, indice);
                    modeloEstablecimiento.guardarEstablecimientos();
                    JOptionPane.showMessageDialog(null, "Establecimiento modificado con éxito.");
                    limpiarEst();
                    mostrarEstablecimiento();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el establecimiento con ese RUC", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese correo válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final int NUMERO_PROVINCIAS = 24;
    private static final int[] COEFICIENTES = {4, 3, 2, 7, 6, 5, 4, 3, 2};
    private static final int CONSTANTE = 11;

    public static boolean validarRUC(String ruc) {
        // Verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_PROVINCIAS
        int prov = Integer.parseInt(ruc.substring(0, 2));

        if (!(prov > 0 && prov <= NUMERO_PROVINCIAS)) {
            System.out.println("Error: RUC ingresado incorrecto");
            return false;
        }

        // Verifica que el último dígito del RUC sea válido
        int[] digitos = new int[10];
        int suma = 0;

        // Asignamos el string a un array
        for (int i = 0; i < digitos.length; i++) {
            digitos[i] = Integer.parseInt(String.valueOf(ruc.charAt(i)));
        }

        for (int i = 0; i < digitos.length - 1; i++) {
            digitos[i] = digitos[i] * COEFICIENTES[i];
            suma += digitos[i];
        }


        int aux = suma % CONSTANTE;
        int resp = CONSTANTE - aux;

        resp = (resp == 10) ? 0 : resp;

        if (resp == digitos[9]) {
            return true;
        } else {
            System.out.println("Error: RUC ingresado incorrecto");
            return false;
        }
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public void mouseExited(MouseEvent e) {
        Color bg2 = new Color(4, 148, 156);
        Color fg2 = new Color(255, 255, 255);
        Color bg3 = new Color(229, 236, 186);
        Color fg3 = new Color(0, 0, 0);

        if (e.getSource() == vista.btnAgregarMascota) {
            vista.btnAgregarMascota.setBackground(bg3);
            vista.btnAgregarMascota.setForeground(fg3);
            vista.btnAgregarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificarMascota) {
            vista.btnModificarMascota.setBackground(bg3);
            vista.btnModificarMascota.setForeground(fg3);
            vista.btnModificarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarMascotas){
            vista.btnMostrarMascotas.setBackground(bg3);
            vista.btnMostrarMascotas.setForeground(fg3);
            vista.btnMostrarMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminarMascota){
            vista.btnEliminarMascota.setBackground(bg3);
            vista.btnEliminarMascota.setForeground(fg3);
            vista.btnEliminarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscarMascota){
            vista.btnBuscarMascota.setBackground(bg3);
            vista.btnBuscarMascota.setForeground(fg3);
            vista.btnBuscarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnSubirFotoCarnet){
            vista.btnSubirFotoCarnet.setBackground(bg3);
            vista.btnSubirFotoCarnet.setForeground(fg3);
            vista.btnSubirFotoCarnet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == vista.btnAgregar) {
            vista.btnAgregar.setBackground(bg3);
            vista.btnAgregar.setForeground(fg3);
            vista.btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificar) {
            vista.btnModificar.setBackground(bg3);
            vista.btnModificar.setForeground(fg3);
            vista.btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarUsuarios){
            vista.btnMostrarUsuarios.setBackground(bg3);
            vista.btnMostrarUsuarios.setForeground(fg3);
            vista.btnMostrarUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminar){
            vista.btnEliminar.setBackground(bg3);
            vista.btnEliminar.setForeground(fg3);
            vista.btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscar){
            vista.btnBuscar.setBackground(bg3);
            vista.btnBuscar.setForeground(fg3);
            vista.btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == vista.btnAgregarEst) {
            vista.btnAgregarEst.setBackground(bg3);
            vista.btnAgregarEst.setForeground(fg3);
            vista.btnAgregarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificarEst) {
            vista.btnModificarEst.setBackground(bg3);
            vista.btnModificarEst.setForeground(fg3);
            vista.btnModificarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarEst){
            vista.btnMostrarEst.setBackground(bg3);
            vista.btnMostrarEst.setForeground(fg3);
            vista.btnMostrarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminarEst){
            vista.btnEliminarEst.setBackground(bg3);
            vista.btnEliminarEst.setForeground(fg3);
            vista.btnEliminarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscarEst){
            vista.btnBuscarEst.setBackground(bg3);
            vista.btnBuscarEst.setForeground(fg3);
            vista.btnBuscarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if(e.getSource()==vista.btnRegresar){
            vista.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if(e.getSource()==vista.btnRegresar || e.getSource() == vista.btnRegresar2 ||e.getSource() == vista.btnRegresar3 ||e.getSource() == vista.btnRegresar4){
            vista.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            vista.btnRegresar2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            vista.btnRegresar3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            vista.btnRegresar4.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }

        if (e.getSource() == vista.btnGestionMascotas) {
            vista.btnGestionMascotas.setBackground(bg2);
            vista.btnGestionMascotas.setForeground(fg2);
            vista.btnGestionMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnGestionUsuarios) {
            vista.btnGestionUsuarios.setBackground(bg2);
            vista.btnGestionUsuarios.setForeground(fg2);
            vista.btnGestionUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnGestionEst) {
            vista.btnGestionEst.setBackground(bg2);
            vista.btnGestionEst.setForeground(fg2);
            vista.btnGestionEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnGestionCGA) {
            vista.btnGestionCGA.setBackground(bg2);
            vista.btnGestionCGA.setForeground(fg2);
            vista.btnGestionCGA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnSalir) {
            vista.btnSalir.setBackground(bg2);
            vista.btnSalir.setForeground(fg2);
            vista.btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.btnGestionCGA || e.getSource() == vista.btnGestionUsuarios) {
            limpiarUsuarios();
            cardLayout.show(vista.panelPrincipal, "Usuarios");
            interfazLogin.setVisible(false);
        }
        if (e.getSource() == vista.btnGestionMascotas) {
            limpiar();
            generarYMostrarID();
            cardLayout.show(vista.panelPrincipal, "Mascotas");
            interfazLogin.setVisible(false);
        }
        if (e.getSource() == vista.btnGestionEst) {

            cardLayout.show(vista.panelPrincipal, "Establecimientos");
            interfazLogin.setVisible(false);
        }

        if(e.getSource()==vista.btnSalir){
            Color bg2 = new Color(229, 236, 186);
            Color fg2 = new Color(0, 0, 0);
            vista.btnSalir.setBackground(bg2);
            vista.btnSalir.setForeground(fg2);
            System.exit(0);
        }
        if(e.getSource()==vista.btnRegresar ||e.getSource()==vista.btnRegresar2 ||e.getSource()==vista.btnRegresar3 ||e.getSource()==vista.btnRegresar4){
            limpiar();
            cardLayout.show(vista.panelPrincipal, "Bienvenida");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnAgregarMascota)  {
            agregar();
            generarYMostrarID();
        }
        if(e.getSource()==vista.btnMostrarMascotas) {
            mostrarMascotas();
        }
        if(e.getSource()==vista.btnEliminarMascota){
            eliminarTabla();
        }
        if(e.getSource()==vista.btnBuscarMascota) {
            cargarMascota();
        }
        if(e.getSource()==vista.btnModificarMascota){
            modificarMascota();
        }
        if(e.getSource()==vista.btnSubirFotoCarnet) {
            cargarImagen();
        }
        if(e.getSource()==vista.btnAgregar) agregarUsuarios();
        if(e.getSource()==vista.btnMostrarUsuarios) mostrarUsuarios();
        if(e.getSource()==vista.btnEliminar) eliminarTablaUsuarios();
        if(e.getSource()==vista.btnBuscar)cargarUsuario();
        if(e.getSource()==vista.btnModificar) modificarUsuario();

        if(e.getSource()==vista.btnAgregarEst) agregarEst();
        if(e.getSource()==vista.btnMostrarEst) mostrarEstablecimiento();
        if(e.getSource()==vista.btnEliminarEst)eliminarTablaEst();
        if(e.getSource()==vista.btnBuscarEst)cargarEst();
        if(e.getSource()==vista.btnModificarEst) modificarEstablecimiento();
        if (e.getSource() == vista.cboTipoEst) {
            actualizarSubtipo();
        }
    }



    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);
        Color bg4 = new Color(162, 197, 121);
        Color fg5 = new Color(0,0,0);

        if (e.getSource() == vista.btnGestionMascotas) {
            vista.btnGestionMascotas.setBackground(bg4);
            vista.btnGestionMascotas.setForeground(fg5);
            vista.btnGestionMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnSalir) {
            vista.btnSalir.setBackground(bg4);
            vista.btnSalir.setForeground(fg5);
            vista.btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnGestionEst) {
            vista.btnGestionEst.setBackground(bg4);
            vista.btnGestionEst.setForeground(fg5);
            vista.btnGestionEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnGestionCGA) {
            vista.btnGestionCGA.setBackground(bg4);
            vista.btnGestionCGA.setForeground(fg5);
            vista.btnGestionCGA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnGestionUsuarios) {
            vista.btnGestionUsuarios.setBackground(bg4);
            vista.btnGestionUsuarios.setForeground(fg5);
            vista.btnGestionUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == vista.btnAgregarMascota) {
            vista.btnAgregarMascota.setBackground(bg);
            vista.btnAgregarMascota.setForeground(fg);
            vista.btnAgregarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificarMascota) {
            vista.btnModificarMascota.setBackground(bg);
            vista.btnModificarMascota.setForeground(fg);
            vista.btnModificarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarMascotas){
            vista.btnMostrarMascotas.setBackground(bg);
            vista.btnMostrarMascotas.setForeground(fg);
            vista.btnMostrarMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminarMascota){
            vista.btnEliminarMascota.setBackground(bg);
            vista.btnEliminarMascota.setForeground(fg);
            vista.btnEliminarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscarMascota){
            vista.btnBuscarMascota.setBackground(bg);
            vista.btnBuscarMascota.setForeground(fg);
            vista.btnBuscarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnSubirFotoCarnet){
            vista.btnSubirFotoCarnet.setBackground(bg);
            vista.btnSubirFotoCarnet.setForeground(fg);
            vista.btnSubirFotoCarnet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnAgregar) {
            vista.btnAgregar.setBackground(bg);
            vista.btnAgregar.setForeground(fg);
            vista.btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificar) {
            vista.btnModificar.setBackground(bg);
            vista.btnModificar.setForeground(fg);
            vista.btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarUsuarios){
            vista.btnMostrarUsuarios.setBackground(bg);
            vista.btnMostrarUsuarios.setForeground(fg);
            vista.btnMostrarUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminar){
            vista.btnEliminar.setBackground(bg);
            vista.btnEliminar.setForeground(fg);
            vista.btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscar){
            vista.btnBuscar.setBackground(bg);
            vista.btnBuscar.setForeground(fg);
            vista.btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnAgregarEst) {
            vista.btnAgregarEst.setBackground(bg);
            vista.btnAgregarEst.setForeground(fg);
            vista.btnAgregarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificarEst) {
            vista.btnModificarEst.setBackground(bg);
            vista.btnModificarEst.setForeground(fg);
            vista.btnModificarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarEst){
            vista.btnMostrarEst.setBackground(bg);
            vista.btnMostrarEst.setForeground(fg);
            vista.btnMostrarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminarEst){
            vista.btnEliminarEst.setBackground(bg);
            vista.btnEliminarEst.setForeground(fg);
            vista.btnEliminarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscarEst){
            vista.btnBuscarEst.setBackground(bg);
            vista.btnBuscarEst.setForeground(fg);
            vista.btnBuscarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if(e.getSource() == vista.btnRegresar || e.getSource() == vista.btnRegresar2 ||e.getSource() == vista.btnRegresar3 ||e.getSource() == vista.btnRegresar4){
            vista.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            vista.btnRegresar2.setCursor(new Cursor(Cursor.HAND_CURSOR));
            vista.btnRegresar3.setCursor(new Cursor(Cursor.HAND_CURSOR));
            vista.btnRegresar4.setCursor(new Cursor(Cursor.HAND_CURSOR));

        }

    }

    //validaciones
    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()==vista.txtDuenio){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombreMascota  || e.getSource()==vista.txtRaza || e.getSource()==vista.txtColor ){
            if(Character.isLetter(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ) {
                e.setKeyChar(Character.toUpperCase(c));

            }else{
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
        if(e.getSource()==vista.txtBuscarMascota){
            if (Character.isLetter(c) || Character.isDigit(c)|| (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
                e.setKeyChar(Character.toUpperCase(c));
            }
        }
        if(e.getSource()==vista.txtID){
            validarCedula(vista.txtID.getText());
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vista.txtTelefono){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombre || e.getSource()==vista.txtDireccion){
            if(Character.isLetter(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ) {
                e.setKeyChar(Character.toUpperCase(c));

            }else{
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
        if(e.getSource()==vista.txtRUC){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vista.txtTelfEst){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombreEst || e.getSource()==vista.txtDireccionEst){
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


    @Override
    public void focusGained(FocusEvent e) {
        vista.txtBuscarMascota.setText("");
        vista.txtBuscarMascota.setForeground(Color.BLACK);
        vista.btnBuscarMascota.setEnabled(true);
        vista.txtBuscar.setText("");
        vista.txtBuscar.setForeground(Color.BLACK);
        vista.btnBuscar.setEnabled(true);
        vista.txtBuscarEst.setText("");
        vista.txtBuscarEst.setForeground(Color.BLACK);
        vista.btnBuscarEst.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
        vista.txtBuscarMascota.setForeground(Color.GRAY);
        vista.txtBuscarMascota.setText("Ingrese el ID,NOMBRE de la mascota o CEDULA del PROPIETARIO");
        vista.btnBuscarMascota.setEnabled(false);
        vista.txtBuscar.setForeground(Color.GRAY);
        vista.txtBuscar.setText("Ingrese el ID del Usuario");
        vista.btnBuscar.setEnabled(false);
        vista.txtBuscarEst.setForeground(Color.GRAY);
        vista.txtBuscarEst.setText("Ingrese el RUC del establecimiento");
        vista.btnBuscarEst.setEnabled(false);

    }



}



