package Modelo;

import java.io.*;
import java.util.ArrayList;

public class GestorEstablecimiento {
    private ArrayList<Establecimiento> establecimientos;
    public GestorEstablecimiento() {
        establecimientos = new ArrayList<>();
    }
    public ArrayList<Establecimiento> getEstablecimiento() {
        return establecimientos;
    }

    public void setEstablecimientos(ArrayList<Persona> usuarios) {
        this.establecimientos = (usuarios != null) ? new ArrayList<>(establecimientos) : new ArrayList<>();
    }
    public void agregarEstablecimiento(String RUC,String nombre,String direccion,String telefono,String correo,String CIDuenio,int tipoEst){
        if(tipoEst == 0){
            establecimientos.add(new Establecimiento(RUC,nombre,telefono,direccion,correo,CIDuenio, "CENTRO DE ATENCIÓN MÉDICO VETERINARIA"));
        }
        if(tipoEst == 1){
            establecimientos.add(new Establecimiento(RUC,nombre,telefono,direccion,correo,CIDuenio, "CENTRO DE MANEJO"));
        }
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
