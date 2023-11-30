package Controlador;

import Modelo.GestorUsuario;
import Vista.InterfazAdmin;
import Vista.InterfazLogin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorAdministrador extends MouseAdapter implements ActionListener {
    InterfazAdmin vista;
    GestorUsuario modelo;
    ControladorMascotas controladorMascotas;
    ControladorUsuarios controladorUsuarios;
    ControladorEstablecimientos controladorEstablecimientos;

    public ControladorAdministrador(InterfazAdmin vista, GestorUsuario modelo, ControladorMascotas controladorMascotas, ControladorUsuarios controladorUsuarios, ControladorEstablecimientos controladorEstablecimientos, InterfazLogin interfazLogin) {
        this.vista = vista;
        this.modelo = modelo;
        this.controladorMascotas = controladorMascotas;
        this.controladorUsuarios = controladorUsuarios;
        this.controladorEstablecimientos = controladorEstablecimientos;
        vista.btnEstablecimientos.addActionListener(this);
        vista.btnMascotas.addActionListener(this);
        vista.btnUsuarios.addActionListener(this);
        vista.btnPersonalCGA.addActionListener(this);
        vista.btnSalir.addActionListener(this);
        vista.btnSalir.addMouseListener(this);
        vista.btnEstablecimientos.addMouseListener(this);
        vista.btnUsuarios.addMouseListener(this);
        vista.btnPersonalCGA.addMouseListener(this);
        vista.btnMascotas.addMouseListener(this);
        vista.setUndecorated(true);
    }

    public void mostrarInterfaz(){
        vista.setTitle("ADMINISTRADOR");
        vista.setSize(680,600);
        vista.setLocationRelativeTo(null);
        vista.setResizable(true);
        vista.setVisible(true);
    }

    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);

        if (e.getSource() == vista.btnMascotas) {
            vista.btnMascotas.setBackground(bg);
            vista.btnMascotas.setForeground(fg);
            vista.btnMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnUsuarios) {
            vista.btnUsuarios.setBackground(bg);
            vista.btnUsuarios.setForeground(fg);
            vista.btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnEstablecimientos) {
            vista.btnEstablecimientos.setBackground(bg);
            vista.btnEstablecimientos.setForeground(fg);
            vista.btnEstablecimientos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnPersonalCGA) {
            vista.btnPersonalCGA.setBackground(bg);
            vista.btnPersonalCGA.setForeground(fg);
            vista.btnPersonalCGA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnSalir) {
            vista.btnSalir.setBackground(bg);
            vista.btnSalir.setForeground(fg);
            vista.btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }
    public void mouseExited(MouseEvent e) {

        Color bg2 = new Color(229, 236, 186);
        Color fg2 = new Color(0, 0, 0);

        if (e.getSource() == vista.btnMascotas) {
            vista.btnMascotas.setBackground(bg2);
            vista.btnMascotas.setForeground(fg2);
            vista.btnMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnUsuarios) {
            vista.btnUsuarios.setBackground(bg2);
            vista.btnUsuarios.setForeground(fg2);
            vista.btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnEstablecimientos) {
            vista.btnEstablecimientos.setBackground(bg2);
            vista.btnEstablecimientos.setForeground(fg2);
            vista.btnEstablecimientos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnPersonalCGA) {
            vista.btnPersonalCGA.setBackground(bg2);
            vista.btnPersonalCGA.setForeground(fg2);
            vista.btnPersonalCGA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnSalir) {
            vista.btnSalir.setBackground(bg2);
            vista.btnSalir.setForeground(fg2);
            vista.btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnPersonalCGA || e.getSource()==vista.btnUsuarios)controladorUsuarios.mostrarInterfazUsuarios();
        if(e.getSource()==vista.btnMascotas)controladorMascotas.mostrarInterfazMascotas();
        if(e.getSource()==vista.btnEstablecimientos)controladorEstablecimientos.mostrarInterfazEstablecimiento();
        if(e.getSource()==vista.btnSalir){
            Color bg2 = new Color(229, 236, 186);
            Color fg2 = new Color(0, 0, 0);
            vista.btnSalir.setBackground(bg2);
            vista.btnSalir.setForeground(fg2);
            vista.dispose();

        }

    }

}
