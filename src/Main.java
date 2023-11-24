import Controlador.ControladorLogin;
import Controlador.ControladorMascotas;
import Modelo.GestorUsuario;
import Modelo.GestorMascotas;
import Vista.InterfazLogin;
import Vista.InterfazMascotas;

public class Main {
    public static void main(String[] args) {
        InterfazLogin interfazLogin = new InterfazLogin();
        GestorUsuario gestorLogin = new GestorUsuario();
        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorLogin);

        InterfazMascotas interfazMascotas = new InterfazMascotas();
        GestorMascotas gestorMascotas = new GestorMascotas();
        ControladorMascotas controladorMascotas = new ControladorMascotas(gestorMascotas, interfazMascotas);

        controladorLogin.setControladorMascotas(controladorMascotas);
    }
}
