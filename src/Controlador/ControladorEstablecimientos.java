package Controlador;

import Modelo.*;
import Vista.InterfazEstablecimientos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ControladorEstablecimientos extends MouseAdapter implements ActionListener,KeyListener, FocusListener {
    private GestorEstablecimiento modeloEstablecimiento;
    private GestorUsuario modeloPropietarios;
    private InterfazEstablecimientos vistaEstablecimiento;
    DefaultTableModel modeloTabla;
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
        vista.txtCIProp.addActionListener(this);
        vista.txtRUC.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtBuscar.addKeyListener(this);
        vista.txtDireccion.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        String[] columnas = {"RUC", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "PROPIETARIO"};
        modeloTabla = new DefaultTableModel(null, columnas);
        activarBotones();
    }

    public void mostrarInterfazEstablecimiento() {
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
        vistaEstablecimiento.txtCIProp.setText("");
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
        String CIPropietario = vistaEstablecimiento.txtCIProp.getText();
        String tipoEstablecimiento = String.valueOf(vistaEstablecimiento.cboTipoEst.getSelectedIndex());
        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !CIPropietario.isEmpty() && !RUC.isEmpty()) {
            if (modeloPropietarios.validarCedulaUnica(CIPropietario) && modeloPropietarios.validarTelefonoUnico(tel) && modeloPropietarios.validarCorreoUnico(correo)) {
                int buscarPropietario = modeloPropietarios.buscarUsuario(CIPropietario);
                if(buscarPropietario!=-1){
                    if (modeloPropietarios.getUsuarios().get(buscarPropietario) instanceof DuenioEstablecimiento) {
                        modeloEstablecimiento.agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, Integer.parseInt(tipoEstablecimiento));
                        ((DuenioEstablecimiento) modeloPropietarios.getUsuarios().get(buscarPropietario)).agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, Integer.parseInt(tipoEstablecimiento));
                        modeloEstablecimiento.guardarEstablecimientos();
                        //enviarCorreo(correo, ID, clave, nombreUsuario);
                        JOptionPane.showMessageDialog(null, "Establecimiento creado con éxito. Las credenciales fueron enviadas al Propietario");
                    }else{
                        JOptionPane.showMessageDialog(null, "La cédula ingresada no pertenece a un Propietario", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "El Propietario NO Existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
                limpiar();
            }else{
                JOptionPane.showMessageDialog(null, "Cédula, Teléfono o Correo Inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarEstablecimiento(){
        String RUC = vistaEstablecimiento.txtBuscar.getText();
        int indice= modeloEstablecimiento.buscarEstablecimiento(RUC);
        if(indice!=-1){
            JOptionPane.showMessageDialog(null, "Establecimiento Encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Establecimiento No Encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            }
            modeloTabla.setRowCount(0);
            for (Establecimiento p : modeloEstablecimiento.getEstablecimiento()) {
                Object[] fila = {p.getRuc(), p.getNombreEst(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getCIRepresentante()};
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
        int indice = modeloEstablecimiento.buscarEstablecimiento(cedulaBuscada);
        if (indice != -1) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("RUC");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("PROPIETARIO");
            }
            modeloTabla.setRowCount(0);
            Object[] fila = {modeloEstablecimiento.getEstablecimiento().get(indice).getRuc(), modeloEstablecimiento.getEstablecimiento().get(indice).getNombreEst(), modeloEstablecimiento.getEstablecimiento().get(indice).getDireccion(), modeloEstablecimiento.getEstablecimiento().get(indice).getTelefono(), modeloEstablecimiento.getEstablecimiento().get(indice).getCorreo(), modeloEstablecimiento.getEstablecimiento().get(indice).getCIRepresentante()};
            modeloTabla.addRow(fila);
            vistaEstablecimiento.tablaEstablecimientos.setModel(modeloTabla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el establecimiento con ese RUC", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        if(e.getSource()==vistaEstablecimiento.btnModificar){} //modificarUsuario();
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
