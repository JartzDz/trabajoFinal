import Controlador.ControladorLogin;
import Controlador.ControladorMascotas;
import Controlador.ControladorUsuarios;
import Modelo.GestorUsuario;
import Modelo.GestorMascotas;
import Vista.InterfazLogin;
import Vista.InterfazMascotas;
import Vista.InterfazUsuarios;

public class Main {
    public static void main(String[] args) {

        InterfazMascotas interfazMascotas = new InterfazMascotas();
        GestorMascotas gestorMascotas = new GestorMascotas();
        ControladorMascotas controladorMascotas = new ControladorMascotas(gestorMascotas, interfazMascotas);

        GestorUsuario gestorLogin = new GestorUsuario();
        gestorLogin.agregarUsuario("1", "dsd", "das", "fsdf", "ADMINISTRADOR", "1", "ADMINISTRADOR");
        gestorLogin.guardarUsuarios();
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios();
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorLogin, interfazUsuarios);

        InterfazLogin interfazLogin = new InterfazLogin();

        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorLogin, controladorMascotas, controladorUsuarios);

    }
}
