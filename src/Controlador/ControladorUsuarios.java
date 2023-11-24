package Controlador;

import Modelo.GestorUsuario;
import Vista.InterfazUsuarios;

import javax.swing.*;

public class ControladorUsuarios {
    private GestorUsuario modeloUsuario;
    private InterfazUsuarios vistaUsuario;
    public ControladorUsuarios(GestorUsuario modelo, InterfazUsuarios vista){
        modeloUsuario = modelo;
        vistaUsuario = vista;
        modeloUsuario.recuperarUsuarios();
    }

    public void mostrarInterfazUsuarios() {
        vistaUsuario.setLocationRelativeTo(null);
        vistaUsuario.setResizable(false);
        vistaUsuario.setVisible(true);
        vistaUsuario.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
