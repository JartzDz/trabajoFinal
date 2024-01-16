package Modelo;

import java.io.*;
import java.util.ArrayList;

public class GestorEstablecimiento {
    public ArrayList<Establecimiento> establecimientos;
    public GestorEstablecimiento() {
        establecimientos = new ArrayList<>();
    }
    public ArrayList<Establecimiento> getEstablecimiento() {
        return establecimientos;
    }

    public void setEstablecimientos(ArrayList<Persona> usuarios) {
        this.establecimientos = (usuarios != null) ? new ArrayList<>(establecimientos) : new ArrayList<>();
    }
    public void agregarEstablecimiento(String RUC, String nombre, String direccion, String telefono, String correo, String CIDuenio, String tipoEst) {
        establecimientos.add(new Establecimiento(RUC, nombre, telefono, direccion, correo, CIDuenio, tipoEst));
    }
    public void agregarEstablecimientoCGA(String RUC, String nombre, String direccion, String telefono, String correo, String CIDuenio, String tipoEst,String tipoEvaluacion) {
        establecimientos.add(new Establecimiento(RUC, nombre, telefono, direccion, correo, CIDuenio, tipoEst,tipoEvaluacion));
    }

    public boolean existeEstablecimiento(String ruc) {

        for (Establecimiento establecimiento : establecimientos) {
            if (establecimiento.getRuc().equals(ruc)) {
                return true;
            }
        }

        return false; // No existe un establecimiento con el mismo RUC
    }

    public ArrayList<Establecimiento>  buscarEstablecimientosDuenio(String cedula){
        ArrayList<Establecimiento> establecimientosDuenio = new ArrayList<>();
        for (Establecimiento establecimiento : establecimientos) {
            if (establecimiento.getCIRepresentante().equals(cedula)) {
                establecimientosDuenio.add(establecimiento);
            }
        }
        return establecimientosDuenio;
    }

    public static int buscarEstablecimiento(String RUC, ArrayList<Establecimiento> establecimientos){
        int contador=0;
        for (Establecimiento establecimiento : establecimientos) {
            if (establecimiento.getRuc().equals(RUC)) {
                return contador;
            }
            contador++;
        }
        return -1;
    }
    public int buscarEst(String criterio) {
        for (Establecimiento establecimiento : establecimientos) {
            if (establecimiento.getRuc().equals(criterio) || establecimiento.getNombreEst().equalsIgnoreCase(criterio)) {
                return establecimientos.indexOf(establecimiento);
            }
        }
        return -1;
    }
    public ArrayList<Establecimiento> mostrarEstablecimientos(){
        return establecimientos;
    }


    public void eliminarEstablecimiento(int indice){
        establecimientos.remove(indice);
    }

    // Método para serializar la lista de usuarios
    public void guardarEstablecimientos() {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("Establecimientos.bin"))) {
            salida.writeObject(establecimientos);
            System.out.println("La lista de establecimientos se ha serializado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al serializar la lista de establecimientos: " + e.getMessage());
        }
    }

    public void modificarEstablecimiento(String RUC,String nombre,String direccion,String telefono,String correo,String CIDuenio,String tipoEst,int indice){
        // Verificar si el índice es válido
        if (indice >= 0 && indice < establecimientos.size()) {
            establecimientos.get(indice).setRuc(RUC);
            establecimientos.get(indice).setNombreEst(nombre);
            establecimientos.get(indice).setDireccion(direccion);
            establecimientos.get(indice).setTelefono(telefono);
            establecimientos.get(indice).setCorreo(correo);
            establecimientos.get(indice).setCIRepresentante(CIDuenio);
            establecimientos.get(indice).setTipoEstablecimiento(tipoEst);
            System.out.println("Establecimiento modificado correctamente.");
        } else {
            System.out.println("Índice no válido. No se puede modificar el establecimiento.");
        }
    }


    // Método para deserializar la lista de usuarios
    @SuppressWarnings("unchecked")
    public void recuperarEstablecimientos() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("Establecimientos.bin"))) {
            establecimientos = (ArrayList<Establecimiento>) entrada.readObject();
            System.out.println("La lista de establecimientos se ha deserializado correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar la lista de establecimientos: " + e.getMessage());
        }
    }
}
