import Controlador.ControladorLogin;
import Controlador.ControladorMascotas;
import Modelo.GestorLogin;
import Modelo.GestorMascotas;
import Vista.InterfazLogin;
import Vista.InterfazMascotas;

public class Main {
    public static void main(String[] args) {
        InterfazLogin interfazLogin = new InterfazLogin();
        GestorLogin gestorLogin = new GestorLogin();
        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorLogin);

        InterfazMascotas interfazMascotas = new InterfazMascotas();
        GestorMascotas gestorMascotas = new GestorMascotas();
        ControladorMascotas controladorMascotas = new ControladorMascotas(gestorMascotas, interfazMascotas);

        controladorLogin.setControladorMascotas(controladorMascotas);
    }
}
