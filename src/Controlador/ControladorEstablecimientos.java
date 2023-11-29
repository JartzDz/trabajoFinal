package Controlador;

import Modelo.GestorEstablecimiento;
import Vista.InterfazEstablecimientos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ControladorEstablecimientos extends MouseAdapter implements ActionListener {
    private GestorEstablecimiento modeloEstablecimiento;
    private InterfazEstablecimientos vistaEstablecimiento;
    DefaultTableModel modeloTabla;
    public ControladorEstablecimientos (GestorEstablecimiento modelo, InterfazEstablecimientos vista){
        modeloEstablecimiento=modelo;
        vistaEstablecimiento=vista;
        modeloEstablecimiento.recuperarEstablecimientos();
        vista.btnBuscar.addActionListener(this);
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnMostrar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnBuscar.setFocusable(false);

    }
    public void mostrarInterfazEstablecimiento() {
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
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
