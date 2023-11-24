package Modelo;

import java.io.Serializable;

public class Administrador extends Persona implements Serializable {
    public Administrador(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia) {
        super(cedula, nombre, direccion, telefono, correo, contrasenia);
    }
}
