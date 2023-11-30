import Controlador.*;
import Modelo.GestorEstablecimiento;
import Modelo.GestorUsuario;
import Modelo.GestorMascotas;
import Vista.*;

public class Main {
    public static void main(String[] args) {
        InterfazMascotas interfazMascotas = new InterfazMascotas();
        GestorMascotas gestorMascotas = new GestorMascotas();
        ControladorMascotas controladorMascotas = new ControladorMascotas(gestorMascotas, interfazMascotas);

        GestorUsuario gestorUsuario = new GestorUsuario();
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios();
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuario, interfazUsuarios);
        InterfazLogin interfazLogin = new InterfazLogin();
        InterfazAdmin interfazAdmin = new InterfazAdmin();

        InterfazEstablecimientos interfazEstablecimientos = new InterfazEstablecimientos();
        GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();
        ControladorEstablecimientos controladorEstablecimientos = new ControladorEstablecimientos(gestorEstablecimiento, interfazEstablecimientos);

        ControladorAdministrador controladorAdministrador = new ControladorAdministrador(interfazAdmin, gestorUsuario, controladorMascotas, controladorUsuarios, controladorEstablecimientos);

        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorUsuario, controladorMascotas, controladorUsuarios, controladorEstablecimientos, controladorAdministrador);
    }
}
