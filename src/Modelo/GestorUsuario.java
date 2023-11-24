package Modelo;

import java.util.ArrayList;

public class GestorUsuario {
    private ArrayList<Persona> usuarios;
    public GestorUsuario() {
        usuarios = new ArrayList<>();
    }

<<<<<<< Updated upstream
    public void agregarUsuario(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia){
=======
    public ArrayList<Persona> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Persona> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarUsuario(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia, String tipoUsuario){
        if(tipoUsuario.equalsIgnoreCase("PROPIETARIO ESTABLECIMIENTO")){
            usuarios.add(new DuenioEstablecimiento(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
        if(tipoUsuario.equalsIgnoreCase("ADMINISTRADOR")){
            usuarios.add(new Administrador(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
        if(tipoUsuario.equalsIgnoreCase("PROPIETARIO MASCOTA")){
            usuarios.add(new DuenioMascota(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
        if(tipoUsuario.equalsIgnoreCase("PERSONAL CGA")){
            usuarios.add(new PersonalCGA(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
    }
>>>>>>> Stashed changes

    }
}
