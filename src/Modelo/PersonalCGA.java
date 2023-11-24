package Modelo;

import java.io.Serializable;

public class PersonalCGA extends Persona implements Serializable {
    public PersonalCGA(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia) {
        super(cedula, nombre, direccion, telefono, correo, contrasenia);
    }
}
