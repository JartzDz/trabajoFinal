package Controlador;

import Modelo.Administrador;
import Modelo.DuenioEstablecimiento;
import Modelo.DuenioMascota;
import Modelo.GestorUsuario;
import Vista.InterfazLogin;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ControladorLogin extends MouseAdapter implements ActionListener, KeyListener {
    InterfazLogin vista;
    GestorUsuario modelo;
    ControladorMascotas controladorMascotas;
    ControladorUsuarios controladorUsuarios;
    ControladorEstablecimientos controladorEstablecimientos;
    ControladorAdministrador controladorAdministrador;
    public ControladorLogin(InterfazLogin vista, GestorUsuario modelo, ControladorMascotas controladorMascotas, ControladorUsuarios controladorUsuarios,ControladorEstablecimientos controladorEstablecimientos,ControladorAdministrador controladorAdministrador) {
        this.vista = vista;
        this.modelo = modelo;
        this.controladorMascotas = controladorMascotas;
        this.controladorUsuarios = controladorUsuarios;
        this.controladorEstablecimientos = controladorEstablecimientos;
        this.controladorAdministrador = controladorAdministrador;
        mostrarInterfaz();
        vista.txtContra.setHorizontalAlignment(SwingConstants.LEFT);
        vista.btnIngresar.setBorder(new RoundedBorder(10));
        vista.btnSalir.setBorder(new RoundedBorder(10));
        vista.btnIngresar.addActionListener(this);
        vista.btnIngresar.addMouseListener(this);
        vista.chkMostrarContra.addActionListener(this);
        vista.btnSalir.addActionListener(this);
        vista.btnSalir.addMouseListener(this);

    }
    public void mostrarInterfaz(){
        vista.setUndecorated(true);
        vista.setTitle("LOGIN");
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setResizable(false);
        vista.setSize(700,500);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
    public void limpiar(){
        vista.txtUsuario.setText("");
        vista.txtContra.setText("");
    }

    public void validarIngreso() {
        String usuario = vista.txtUsuario.getText();
        char[] passwordChars = vista.txtContra.getPassword();
        String contra = new String(passwordChars);
        int indice = modelo.buscarUsuario(usuario);
        if (indice!=-1) {
            if(modelo.getUsuarios().get(indice).getContrasenia().equals(new String(passwordChars))){
                if(modelo.getUsuarios().get(indice) instanceof Administrador){
                    limpiar();
                    controladorAdministrador.mostrarInterfaz();

                }
                if(modelo.getUsuarios().get(indice) instanceof DuenioMascota){
                    limpiar();
                    controladorMascotas.mostrarInterfazMascotas();

                }
                if(modelo.getUsuarios().get(indice) instanceof DuenioEstablecimiento){
                    limpiar();
                    controladorEstablecimientos.mostrarInterfazEstablecimiento();

                }

            }else{
                JOptionPane.showMessageDialog(vista.contenedor, "CREDENCIALES INCORRECTAS");
                limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(vista.contenedor, "CREDENCIALES NO EXISTENTES");
            limpiar();
        }
    }
    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);

        if (e.getSource() == vista.btnIngresar) {
            vista.btnIngresar.setBackground(bg);
            vista.btnIngresar.setForeground(fg);
            vista.btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnSalir) {
            vista.btnSalir.setBackground(bg);
            vista.btnSalir.setForeground(fg);
            vista.btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }
    public void mostrarContrasenia(){
        if(vista.chkMostrarContra.isSelected()){
            vista.txtContra.setEchoChar((char)0);
        }
        else {
            vista.txtContra.setEchoChar('*');
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource()==vista.btnIngresar))validarIngreso();
        if((e.getSource()==vista.chkMostrarContra))mostrarContrasenia();
        if(e.getSource()==vista.btnSalir)System.exit(0);
    }

    public void mouseExited(MouseEvent e) {

        Color bg2 = new Color(229, 236, 186);
        Color fg2 = new Color(0, 0, 0);

        if (e.getSource() == vista.btnIngresar) {
            vista.btnIngresar.setBackground(bg2);
            vista.btnIngresar.setForeground(fg2);
        }
        if (e.getSource() == vista.btnSalir) {
            vista.btnSalir.setBackground(bg2);
            vista.btnSalir.setForeground(fg2);
        }
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
    static class RoundedBorder implements Border {

        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

}
