package Modelo;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GestorMascotas {

    private ArrayList<Mascota> ListaMascotas;
    public GestorMascotas() {
        ListaMascotas = new ArrayList<>();
    }
    public ArrayList<Mascota> getListaMascotas() {
        return ListaMascotas;
    }

    public void setListaMascotas(ArrayList<Mascota> listaMascotas) {
        this.ListaMascotas = (listaMascotas != null) ? new ArrayList<>(listaMascotas) : new ArrayList<>();
    }

    public void agregarMascota(int ID, int edad, String nombreMascota, String sexo, String raza, String color, String duenio,
                               Image fotoCarnet, boolean vacunas, boolean desparacitaciones, boolean esterilizacion,
                               boolean otrasCirugias){
        Mascota nuevaMascota=new Mascota(ID,edad, nombreMascota, sexo,raza,color, duenio, fotoCarnet, vacunas, desparacitaciones, esterilizacion, otrasCirugias);
        ListaMascotas.add(nuevaMascota);
    }
    public int buscarMascota(int ID){
        for(Mascota i: ListaMascotas){
            if(i.getID()==ID){
                return ListaMascotas.indexOf(i);
            }
        }
        return -1;
    }
    public ArrayList<Mascota> buscarMascotaArray(int ID){
        ArrayList<Mascota> resultados=new ArrayList<>();
        for(Mascota i: ListaMascotas){
            if(i.getID()==ID){
                resultados.add(i);
            }
        }
        return resultados;
    }
    public void eliminarMascota(int indice){
        ListaMascotas.remove(indice);
    }
    public void modificarMascota(int indice,String nombre,String raza, String duenio){
        Mascota nuevaMascota= ListaMascotas.get(indice);
        nuevaMascota.setNombreMascota(nombre);
        nuevaMascota.setRaza(raza);
        nuevaMascota.setDuenio(duenio);
    }

    public void modificarMascota(int ID, int edad, String nombreMascota, String sexo, String raza, String color, String duenio,
                                 Image fotoCarnet, boolean vacunas, boolean desparacitaciones, boolean esterilizacion,
                                 boolean otrasCirugias, int indice){
        // Verificar si el índice es válido
        if (indice >= 0 && indice < ListaMascotas.size()) {
            ListaMascotas.get(indice).setID(ID);
            ListaMascotas.get(indice).setNombreMascota(nombreMascota);
            ListaMascotas.get(indice).setSexo(sexo);
            ListaMascotas.get(indice).setRaza(raza);
            ListaMascotas.get(indice).setColor(color);
            ListaMascotas.get(indice).setDuenio(duenio);
            ListaMascotas.get(indice).setFotoCarnet(fotoCarnet);
            ListaMascotas.get(indice).setDesparacitaciones(desparacitaciones);
            ListaMascotas.get(indice).setEsterilizacion(esterilizacion);
            ListaMascotas.get(indice).setVacunas(vacunas);
            ListaMascotas.get(indice).setOtrasCirugias(otrasCirugias);


            System.out.println("Usuario modificado correctamente.");
        } else {
            System.out.println("Índice no válido. No se puede modificar el usuario.");
        }
    }

    public ArrayList<Mascota> mostrarMascotas(){
        return ListaMascotas;
    }
    public void guardarMascotas() {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("ListaMascotas.bin"))) {
            salida.writeObject(ListaMascotas);
            System.out.println("La lista de mascotas se ha serializado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al serializar la lista de mascotas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void recuperarMascotas() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("ListaMascotas.bin"))) {
            ListaMascotas = (ArrayList<Mascota>) entrada.readObject();
            System.out.println("La lista de mascotas se ha deserializado correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar la lista de mascotas: " + e.getMessage());
        }
    }
}
