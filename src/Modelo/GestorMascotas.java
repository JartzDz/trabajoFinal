package Modelo;

import java.util.ArrayList;

public class GestorMascotas {

    ArrayList<Mascota> listaMascotas;

    public GestorMascotas() {
        this.listaMascotas = new ArrayList<Mascota>();
    }

    public void agregarMascota(int ID, String nombre, String raza, String duenio){
        Mascota nuevaMascota=new Mascota(ID,nombre,raza,duenio);
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
