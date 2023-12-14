import Controlador.*;
import Modelo.*;
import Vista.*;

public class Main {
    public static void main(String[] args) {

        // Modelos
        GestorMascotas gestorMascotas = new GestorMascotas();
        GestorUsuario gestorUsuario = new GestorUsuario();
        GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();

        // Vistas
        InterfazMascotas interfazMascotas = new InterfazMascotas();
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios();
        InterfazLogin interfazLogin = new InterfazLogin();
        InterfazAdmin interfazAdmin = new InterfazAdmin();
        InterfazEstablecimientos interfazEstablecimientos = new InterfazEstablecimientos();
        InterfazRegistro interfazRegistro = new InterfazRegistro();

        // Controladores
        ControladorMascotas controladorMascotas = new ControladorMascotas(gestorMascotas, interfazMascotas);

        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuario, interfazUsuarios);
        ControladorEstablecimientos controladorEstablecimientos = new ControladorEstablecimientos(gestorEstablecimiento, interfazEstablecimientos, gestorUsuario);

        ControladorAdministrador controladorAdministrador = new ControladorAdministrador(
                interfazAdmin, gestorUsuario, controladorMascotas, controladorUsuarios, controladorEstablecimientos, interfazLogin);

        ControladorRegistro controladorRegistro = new ControladorRegistro(interfazRegistro, gestorUsuario, controladorAdministrador, null);
        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorUsuario, controladorMascotas, controladorUsuarios, controladorEstablecimientos, controladorAdministrador, controladorRegistro);

        controladorRegistro.setControladorLogin(controladorLogin);

    }
}

