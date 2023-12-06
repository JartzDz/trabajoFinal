package Modelo;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class DuenioMascota extends Persona implements Serializable {
    private ArrayList<Mascota> listaMascotas ;
    public DuenioMascota(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia) {
        super(cedula, nombre, direccion, telefono, correo, contrasenia);
        this.listaMascotas = new ArrayList<>();
    }

    public ArrayList<Mascota> getListaMascotas() {
        return listaMascotas;
    }

    public void setListaMascotas(ArrayList<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    public void agregarMascota(int ID, int edad, String nombreMascota, String sexo, String raza, String color, String duenio,
                               Image foto, Image fotoCarnet, boolean vacunas, boolean desparacitaciones, boolean esterilizacion,
                               boolean otrasCirugias){
        Mascota nuevaMascota=new Mascota(ID,edad, nombreMascota, sexo,raza,color, duenio, fotoCarnet, vacunas, desparacitaciones, esterilizacion, otrasCirugias);
        listaMascotas.add(nuevaMascota);
    }
    public int buscarMascota(int ID){
        for(Mascota i:listaMascotas){
            if(i.getID()==ID){
                return listaMascotas.indexOf(i);
            }
        }
        return -1;
    }
    public ArrayList<Mascota> buscarMascotaArray(int ID){
        ArrayList<Mascota> resultados=new ArrayList<>();
        for(Mascota i:listaMascotas){
            if(i.getID()==ID){
                resultados.add(i);
            }
        }
        return resultados;
    }

    public void eliminarMascota(int indice){

        listaMascotas.remove(indice);
    }

    public void modificarMascota(int indice,String nombre,String raza, String duenio){
        Mascota nuevaMascota=listaMascotas.get(indice);
        nuevaMascota.setNombreMascota(nombre);
        nuevaMascota.setRaza(raza);
        nuevaMascota.setDuenio(duenio);
    }
    public ArrayList<Mascota> mostrarMascotas(){
        return listaMascotas;
    }
}
