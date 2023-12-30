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
        InterfazPrincipalAdmin interfazAdmin = new InterfazPrincipalAdmin();
        InterfazEstablecimientos interfazEstablecimientos = new InterfazEstablecimientos();
        InterfazRegistro interfazRegistro = new InterfazRegistro();
        InterfazPersonalCGA interfazPersonalCGA = new InterfazPersonalCGA();
        // Controladores
        ControladorMascotas controladorMascotas = new ControladorMascotas(gestorMascotas, interfazMascotas,gestorUsuario);

        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuario, interfazUsuarios);
        ControladorEstablecimientos controladorEstablecimientos = new ControladorEstablecimientos(gestorEstablecimiento, interfazEstablecimientos, gestorUsuario);
        ControladorCGA controladorCGA = new ControladorCGA(interfazPersonalCGA,gestorEstablecimiento,gestorUsuario);
        ControladorAdministrador controladorAdministrador = new ControladorAdministrador(
                interfazAdmin, gestorUsuario, controladorMascotas, controladorUsuarios, controladorEstablecimientos, interfazLogin,gestorMascotas,gestorEstablecimiento);

        ControladorRegistro controladorRegistro = new ControladorRegistro(interfazRegistro, gestorUsuario, controladorAdministrador, null);
        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorUsuario, controladorMascotas, controladorUsuarios, controladorEstablecimientos, controladorAdministrador, controladorRegistro,controladorCGA);

        controladorRegistro.setControladorLogin(controladorLogin);

    }
}

