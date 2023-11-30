package Modelo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GestorUsuario {
    private ArrayList<Persona> usuarios;
    public GestorUsuario() {
        usuarios = new ArrayList<>();
    }
    public ArrayList<Persona> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Persona> usuarios) {
        this.usuarios = (usuarios != null) ? new ArrayList<>(usuarios) : new ArrayList<>();
    }

    public void agregarUsuario(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia, int tipoUsuario){
        if(tipoUsuario==0){
            usuarios.add(new DuenioEstablecimiento(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
        if(tipoUsuario==3){
            usuarios.add(new Administrador(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
        if(tipoUsuario==1){
            usuarios.add(new DuenioMascota(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
        if(tipoUsuario==2){
            usuarios.add(new PersonalCGA(cedula, nombre, direccion, telefono, correo, contrasenia));
        }
    }

    public int buscarUsuario(String cedula){
        int contador=0;
        for (Persona persona : usuarios) {
            if (persona.getCedula().equals(cedula)) {
                return contador;
            }
            contador++;
        }
        return -1;
    }



    public void eliminarUsuario(int indice){
        usuarios.remove(indice);
    }

    // Método para serializar la lista de usuarios
    public void guardarUsuarios() {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("Usuarios.bin"))) {
            salida.writeObject(usuarios);
            System.out.println("La lista de usuarios se ha serializado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al serializar la lista de usuarios: " + e.getMessage());
        }
    }

    public void modificarUsuario(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia, int indice){
        // Verificar si el índice es válido
        if (indice >= 0 && indice < usuarios.size()) {
            usuarios.get(indice).setCedula(cedula);
            usuarios.get(indice).setNombre(nombre);
            usuarios.get(indice).setDireccion(direccion);
            usuarios.get(indice).setTelefono(telefono);
            usuarios.get(indice).setCorreo(correo);
            usuarios.get(indice).setContrasenia(contrasenia);
            System.out.println("Usuario modificado correctamente.");
        } else {
            System.out.println("Índice no válido. No se puede modificar el usuario.");
        }
    }

    public boolean validarCedulaUnica(String cedula) {
        for (Persona usuarioExistente : usuarios) {
            if (usuarioExistente.getCedula().equals(cedula)) {
                JOptionPane.showMessageDialog(null, "La cédula ya está registrada. Ingrese una cédula única.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public boolean validarTelefonoUnico(String telefono) {
        for (Persona usuarioExistente : usuarios) {
            if (usuarioExistente.getTelefono().equals(telefono)) {
                JOptionPane.showMessageDialog(null, "El teléfono ya está registrado. Ingrese un teléfono único.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public boolean validarCorreoUnico(String correo) {
        for (Persona usuarioExistente : usuarios) {
            if (usuarioExistente.getCorreo().equals(correo)) {
                return false;
            }
        }
        return true;
    }
    // Método para deserializar la lista de usuarios
    @SuppressWarnings("unchecked")
    public void recuperarUsuarios() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("Usuarios.bin"))) {
            usuarios = (ArrayList<Persona>) entrada.readObject();
            System.out.println("La lista de usuarios se ha deserializado correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar la lista de usuarios: " + e.getMessage());
        }
    }

}
