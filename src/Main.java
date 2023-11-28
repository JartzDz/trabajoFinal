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
        String cedula = "1234567890"; // Reemplaza con la cédula deseada
        String nombre = "juli"; // Reemplaza con el nombre deseado
        String direccion = "Dirección Admin"; // Reemplaza con la dirección deseada
        String telefono = "123456789"; // Reemplaza con el teléfono deseado
        String correo = "admin@example.com"; // Reemplaza con el correo deseado
        String contrasenia = "admin123"; // Reemplaza con la contraseña deseada
        String tipoUsuario = "ADMINISTRADOR";



        GestorUsuario gestorUsuario = new GestorUsuario();
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios();
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuario, interfazUsuarios);
        InterfazLogin interfazLogin = new InterfazLogin();

        ControladorLogin controladorLogin = new ControladorLogin(interfazLogin, gestorUsuario, controladorMascotas, controladorUsuarios);

    }
}
