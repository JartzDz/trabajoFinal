package Controlador;

import Modelo.GestorUsuario;
import Modelo.Mascota;
import Modelo.Persona;
import Vista.InterfazUsuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
        vista.btnBuscar.setFocusable(false);
        vista.txtBuscar.setEnabled(false);
        String[] columnas = {"CEDULA", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "CONTRASEÑA"};
        modeloTabla = new DefaultTableModel(null, columnas);
        vista.btnMostrarUsuarios.setEnabled(false);
        vista.btnBuscar.setEnabled(false);
    }

    public void mostrarInterfazUsuarios() {
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

    public void agregar(){
            String ID = vistaUsuario.txtID.getText();
            String nombreUsuario= vistaUsuario.txtNombre.getText();
            String tel = vistaUsuario.txtTelefono.getText();
            String direccion = vistaUsuario.txtDireccion.getText();
            String correo = vistaUsuario.txtCorreo.getText();
            String clave = vistaUsuario.txtClave.getText();
            String tipoUsuario= String.valueOf(vistaUsuario.cbTipoUsuario.getSelectedIndex());

            if (!nombreUsuario.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !ID.isEmpty()) {
                if(validarCedula(ID)){
                    if(validarCorreo(correo)){
                        modeloUsuario.agregarUsuario(ID,nombreUsuario, direccion, tel,correo,clave,tipoUsuario);
                        vistaUsuario.btnMostrarUsuarios.setEnabled(true);
                        vistaUsuario.btnBuscar.setEnabled(true);
                        vistaUsuario.txtBuscar.setEnabled(true);
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese correo válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese cédula válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    public void buscarUsuario(){
        String cedula = vistaUsuario.txtBuscar.getText();
        int indice= modeloUsuario.buscarUsuario(cedula);
        if(indice!=-1){
            JOptionPane.showMessageDialog(null, "Persona Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Persona No Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void eliminarTabla(){
        int fila=vistaUsuario.tablaUsuarios.getSelectedRow();
        //String valor= (String) vista.tablaPersonas.getValueAt(fila,0);
        modeloUsuario.eliminarUsuario(fila);
    }

    public void cargarUsuario() {
        int valor = modeloUsuario.buscarUsuario((vistaUsuario.txtBuscar.getText()));

        if (!modeloUsuario.getUsuarios().isEmpty()) {
            if (valor != -1) {
                String data[][] = {};
                String[] col = {"CEDULA", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "CONTRASEÑA"};
                modeloTabla = new DefaultTableModel(data, col);
                vistaUsuario.tablaUsuarios.setModel(modeloTabla);
                modeloTabla.insertRow(modeloTabla.getRowCount(), new Object[]{});
                modeloTabla.setValueAt(modeloUsuario.getUsuarios().get(valor).getCedula(), modeloTabla.getRowCount() - 1, 0);
                modeloTabla.setValueAt(modeloUsuario.getUsuarios().get(valor).getNombre(), modeloTabla.getRowCount() - 1, 1);
                modeloTabla.setValueAt(modeloUsuario.getUsuarios().get(valor).getDireccion(), modeloTabla.getRowCount() - 1, 2);
                modeloTabla.setValueAt(modeloUsuario.getUsuarios().get(valor).getTelefono(), modeloTabla.getRowCount() - 1, 3);
                modeloTabla.setValueAt(modeloUsuario.getUsuarios().get(valor).getCorreo(), modeloTabla.getRowCount() - 1, 4);
                modeloTabla.setValueAt(modeloUsuario.getUsuarios().get(valor).getContrasenia(), modeloTabla.getRowCount() - 1, 5);
                vistaUsuario.btnMostrarUsuarios.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la persona con esa Cédula", "Error", JOptionPane.ERROR_MESSAGE);
                vistaUsuario.btnMostrarUsuarios.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han ingresado usuarios", "Error", JOptionPane.ERROR_MESSAGE);
            vistaUsuario.btnMostrarUsuarios.setEnabled(false);
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
        // Patrón para validar direcciones de correo electrónico
        String patronCorreo = "^[a-zA-Z0-9+&*-]+(?:\\.[a-zA-Z0-9+&-]+)@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compila la expresión regular
        Pattern pattern = Pattern.compile(patronCorreo);

        // Crea un objeto Matcher que comparará la cadena proporcionada con el patrón
        Matcher matcher = pattern.matcher(correo);

        // Devuelve true si la cadena coincide con el patrón, de lo contrario, false
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
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== vistaUsuario.btnAgregar) agregar();
        if(e.getSource() == vistaUsuario.btnMostrarUsuarios) mostrarUsuarios();
        if(e.getSource()==vistaUsuario.btnEliminar)eliminarTabla();
        if(e.getSource()==vistaUsuario.btnBuscar)cargarUsuario();
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
