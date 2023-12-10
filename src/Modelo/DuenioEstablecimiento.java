package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class DuenioEstablecimiento extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Establecimiento> listaEstablecimiento;
    public DuenioEstablecimiento(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia) {
        super(cedula, nombre, direccion, telefono, correo, contrasenia);
        this.listaEstablecimiento = new ArrayList<>();
    }

    public void agregarEstablecimiento(String RUC,String nombre,String direccion,String telefono,String correo,String CIDuenio,int tipoEst){
        if(tipoEst == 0){
            listaEstablecimiento.add(new Establecimiento(RUC,nombre,telefono,direccion,correo,CIDuenio, "CENTRO DE ATENCIÓN MÉDICO VETERINARIA"));
        }
        if(tipoEst == 1){
            listaEstablecimiento.add(new Establecimiento(RUC,nombre,telefono,direccion,correo,CIDuenio, "CENTRO DE MANEJO"));
        }
    }

    public void eliminarEstablecimiento(int indiceEstablecimiento){
        listaEstablecimiento.remove(indiceEstablecimiento);
    }

    public void modificarEstablecimiento(String RUC,String nombre,String direccion,String telefono,String correo,String CIDuenio,String tipoEst,int indice){
        // Verificar si el índice es válido
        if (indice >= 0 && indice < listaEstablecimiento.size()) {
            listaEstablecimiento.get(indice).setRuc(RUC);
            listaEstablecimiento.get(indice).setNombreEst(nombre);
            listaEstablecimiento.get(indice).setDireccion(direccion);
            listaEstablecimiento.get(indice).setTelefono(telefono);
            listaEstablecimiento.get(indice).setCorreo(correo);
            listaEstablecimiento.get(indice).setCIRepresentante(CIDuenio);
            listaEstablecimiento.get(indice).setTipoEstablecimiento(tipoEst);
            System.out.println("Establecimiento modificado correctamente.");
        } else {
            System.out.println("Índice no válido. No se puede modificar el establecimiento.");
        }
    }

    public static int buscarEstablecimiento(String RUC, ArrayList<Establecimiento> listaEstablecimiento){
        int contador=0;
        for (Establecimiento establecimiento : listaEstablecimiento) {
            if (establecimiento.getRuc().equals(RUC)) {
                return contador;
            }
            contador++;
        }
        return -1;
    }

    public ArrayList<Establecimiento> getListaEstablecimiento() {
        return listaEstablecimiento;
    }

    public void setListaEstablecimiento(ArrayList<Establecimiento> listaEstablecimiento) {
        this.listaEstablecimiento = listaEstablecimiento;
    }
}
