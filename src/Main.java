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
        

        GestorUsuario gestorUsuario = new GestorUsuario();
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios();
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuario, interfazUsuarios);
        InterfazLogin interfazLogin = new InterfazLogin();

        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorUsuario, controladorMascotas, controladorUsuarios);

    }
}
