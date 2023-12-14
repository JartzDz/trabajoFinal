package Controlador;

import Modelo.*;
import Vista.InterfazEstablecimientos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorEstablecimientos extends MouseAdapter implements ActionListener,KeyListener, FocusListener {
    private GestorEstablecimiento modeloEstablecimiento;
    private GestorUsuario modeloPropietarios;
    private InterfazEstablecimientos vistaEstablecimiento;
    DefaultTableModel modeloTabla;
    private String usuario="";
    public ControladorEstablecimientos (GestorEstablecimiento modelo, InterfazEstablecimientos vista, GestorUsuario modeloPropietarios){
        modeloEstablecimiento=modelo;
        vistaEstablecimiento=vista;
        this.modeloPropietarios = modeloPropietarios;
        modeloEstablecimiento.recuperarEstablecimientos();
        vista.btnBuscar.addActionListener(this);
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnMostrar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnBuscar.setFocusable(false);
        vista.btnRegresar.addMouseListener(this);
        vista.cboCIProp.addActionListener(this);
        vista.txtRUC.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtBuscar.addKeyListener(this);
        vista.txtDireccion.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        modeloPropietarios.recuperarUsuarios();
        String[] columnas = {"RUC", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "PROPIETARIO", "TIPO"};
        modeloTabla = new DefaultTableModel(null, columnas);
        activarBotones();
    }

    public boolean esPropietario(){
        int indice = modeloPropietarios.buscarUsuario(usuario);
        if(modeloPropietarios.getUsuarios().get(indice) instanceof Administrador){
            return false;
        }
        return true;
    }

    public void cargarCombo() {
        if (esPropietario()) {
            vistaEstablecimiento.cboCIProp.addItem(usuario);
        } else {
            for (Persona p : modeloPropietarios.getUsuarios()) {
                if (p instanceof DuenioEstablecimiento) {
                    vistaEstablecimiento.cboCIProp.addItem(p.getCedula() + "-" + p.getNombre());
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

        int itemCount = vistaEstablecimiento.cboCIProp.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            Object item = vistaEstablecimiento.cboCIProp.getItemAt(i);
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


    public void mostrarInterfazEstablecimiento() {
        vistaEstablecimiento.cboCIProp.removeAllItems();
        cargarCombo();
        vistaEstablecimiento.setUndecorated(true);
        vistaEstablecimiento.setTitle("ESTABLECIMIENTO");
        vistaEstablecimiento.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaEstablecimiento.setLocationRelativeTo(null);
        vistaEstablecimiento.setResizable(true);
        vistaEstablecimiento.setVisible(true);
    }
    public void limpiar(){
        vistaEstablecimiento.txtRUC.setText("");
        vistaEstablecimiento.txtNombre.setText("");
        vistaEstablecimiento.txtTelefono.setText("");
        vistaEstablecimiento.txtDireccion.setText("");
        vistaEstablecimiento.txtCorreo.setText("");
    }
    public void activarBotones(){
        if(modeloEstablecimiento.getEstablecimiento().isEmpty()){
            vistaEstablecimiento.txtBuscar.setEnabled(false);
            vistaEstablecimiento.btnMostrar.setEnabled(false);
            vistaEstablecimiento.btnBuscar.setEnabled(false);
        }else{
            vistaEstablecimiento.txtBuscar.setEnabled(true);
            vistaEstablecimiento.btnMostrar.setEnabled(true);
            vistaEstablecimiento.btnBuscar.setEnabled(true);
        }
    }

    public void agregar() {
        String RUC = vistaEstablecimiento.txtRUC.getText();
        String nombreEstablecimiento = vistaEstablecimiento.txtNombre.getText();
        String tel = vistaEstablecimiento.txtTelefono.getText();
        String direccion = vistaEstablecimiento.txtDireccion.getText();
        String correo = vistaEstablecimiento.txtCorreo.getText();
        String CIPropietario = vistaEstablecimiento.cboCIProp.getSelectedItem().toString();
        String tipoEstablecimiento = String.valueOf(vistaEstablecimiento.cboTipoEst.getSelectedIndex());
        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !CIPropietario.isEmpty() && !RUC.isEmpty()) {
            int buscarPropietario = modeloPropietarios.buscarUsuario(CIPropietario);
            if (validarCorreo(correo)) {
                modeloEstablecimiento.agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, Integer.parseInt(tipoEstablecimiento));
                ((DuenioEstablecimiento) modeloPropietarios.getUsuarios().get(buscarPropietario)).agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, Integer.parseInt(tipoEstablecimiento));
                modeloEstablecimiento.guardarEstablecimientos();
                //enviarCorreo(correo, ID, clave, nombreUsuario);
                JOptionPane.showMessageDialog(null, "Establecimiento creado con éxito. Las credenciales fueron enviadas al Propietario");
                limpiar();
            }else{
                JOptionPane.showMessageDialog(null, "Correo Inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*public void buscarEstablecimiento(){
        String RUC = vistaEstablecimiento.txtBuscar.getText();
        int indice= modeloEstablecimiento.buscarEstablecimiento(RUC);
        if(indice!=-1){
            JOptionPane.showMessageDialog(null, "Establecimiento Encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Establecimiento No Encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    public void eliminarTabla(){
        int fila=vistaEstablecimiento.tablaEstablecimientos.getSelectedRow();
        String valor= (String) vistaEstablecimiento.tablaEstablecimientos.getValueAt(fila,0);
        modeloEstablecimiento.eliminarEstablecimiento(fila);
        modeloEstablecimiento.guardarEstablecimientos();
        mostrarEstablecimiento();
    }

    public void mostrarEstablecimiento() {
        if (!modeloEstablecimiento.getEstablecimiento().isEmpty()) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("RUC");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("PROPIETARIO");
                modeloTabla.addColumn("TIPO");
            }
            modeloTabla.setRowCount(0);
            ArrayList<Establecimiento> listaEstablecimiento;
            if(esPropietario()){
                listaEstablecimiento = modeloEstablecimiento.buscarEstablecimientosDuenio(usuario);
            }else{
                listaEstablecimiento = modeloEstablecimiento.getEstablecimiento();
            }
            for (Establecimiento p : listaEstablecimiento) {
                Object[] fila = {p.getRuc(), p.getNombreEst(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getCIRepresentante(), p.getTipoEstablecimiento()};
                modeloTabla.addRow(fila);
            }
            vistaEstablecimiento.tablaEstablecimientos.setModel(modeloTabla);
            vistaEstablecimiento.btnMostrar.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen establecimientos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTabla.setRowCount(0);
            vistaEstablecimiento.btnMostrar.setEnabled(false);
        }
    }

    public void cargarEstablecimiento() {
        String cedulaBuscada = vistaEstablecimiento.txtBuscar.getText();
        int indice = 0;
        if(esPropietario()){
            indice = GestorEstablecimiento.buscarEstablecimiento(cedulaBuscada,modeloEstablecimiento.buscarEstablecimientosDuenio(usuario));
        }else{
            indice = GestorEstablecimiento.buscarEstablecimiento(cedulaBuscada, modeloEstablecimiento.getEstablecimiento());
        }
        if (indice != -1) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("RUC");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("PROPIETARIO");
                modeloTabla.addColumn("TIPO");
            }
            modeloTabla.setRowCount(0);
            Object[] fila = {modeloEstablecimiento.getEstablecimiento().get(indice).getRuc(), modeloEstablecimiento.getEstablecimiento().get(indice).getNombreEst(),
                    modeloEstablecimiento.getEstablecimiento().get(indice).getDireccion(), modeloEstablecimiento.getEstablecimiento().get(indice).getTelefono(), modeloEstablecimiento.getEstablecimiento().get(indice).getCorreo(),
                    modeloEstablecimiento.getEstablecimiento().get(indice).getCIRepresentante(), modeloEstablecimiento.getEstablecimiento().get(indice).getTipoEstablecimiento()};
            modeloTabla.addRow(fila);
            vistaEstablecimiento.tablaEstablecimientos.setModel(modeloTabla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el establecimiento con ese RUC", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarEstablecimiento() {
        String RUC = vistaEstablecimiento.txtRUC.getText();
        String nombreEstablecimiento = vistaEstablecimiento.txtNombre.getText();
        String tel = vistaEstablecimiento.txtTelefono.getText();
        String direccion = vistaEstablecimiento.txtDireccion.getText();
        String correo = vistaEstablecimiento.txtCorreo.getText();
        String CIPropietario = vistaEstablecimiento.cboCIProp.getSelectedItem().toString();
        String tipoEstablecimiento = String.valueOf(vistaEstablecimiento.cboTipoEst.getSelectedIndex());

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
                        limpiar();
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

    public static boolean validarCorreo(String correo) {
        String patronCorreo = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(patronCorreo);
        Matcher matcher = pattern.matcher(correo);


        return matcher.matches();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==vistaEstablecimiento.btnRegresar) {
            limpiar();
            vistaEstablecimiento.dispose();
        }
    }
    public void mouseEntered(MouseEvent e) {
        vistaEstablecimiento.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        vistaEstablecimiento.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaEstablecimiento.btnAgregar) agregar();
        if(e.getSource()==vistaEstablecimiento.btnMostrar) mostrarEstablecimiento();
        if(e.getSource()==vistaEstablecimiento.btnEliminar)eliminarTabla();
        if(e.getSource()==vistaEstablecimiento.btnBuscar)cargarEstablecimiento();
        if(e.getSource()==vistaEstablecimiento.btnModificar) modificarEstablecimiento();
    }

    @Override
    public void focusGained(FocusEvent e) {
        vistaEstablecimiento.txtBuscar.setText("");
        vistaEstablecimiento.txtBuscar.setForeground(Color.BLACK);
        vistaEstablecimiento.btnBuscar.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
        vistaEstablecimiento.txtBuscar.setForeground(Color.GRAY);
        vistaEstablecimiento.txtBuscar.setText("Ingrese el ID del Usuario");
        vistaEstablecimiento.btnBuscar.setEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(e.getSource()==vistaEstablecimiento.txtRUC){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vistaEstablecimiento.txtTelefono){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vistaEstablecimiento.txtNombre || e.getSource()==vistaEstablecimiento.txtDireccion){
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
