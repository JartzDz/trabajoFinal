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

    public void agregarMascota(String ID, int edad, String nombreMascota, String sexo, String raza, String color,
                               String duenio, File fotoCarnet, boolean vacunas, boolean desparacitaciones, boolean esterilizacion,
                               boolean otrasCirugias) {

        String rutaFotoCarnet = "./src/vista/imagenes/" + fotoCarnet.getName();

        // Crear una nueva instancia de Mascota
        Mascota nuevaMascota = new Mascota(ID, edad, nombreMascota, sexo, raza, color, duenio, rutaFotoCarnet,
                vacunas, desparacitaciones, esterilizacion, otrasCirugias);

        // Agregar la nueva mascota a la lista o estructura de datos que estés usando en tu modelo
        ListaMascotas.add(nuevaMascota);
    }

    public int buscarMascota(String criterio) {
        for (Mascota mascota : ListaMascotas) {
            if (mascota.getID().equals(criterio) || mascota.getNombreMascota().equalsIgnoreCase(criterio) || mascota.getDuenio().equalsIgnoreCase(criterio)) {
                return ListaMascotas.indexOf(mascota);
            }
        }
        return -1;
    }
    public ArrayList<Mascota> buscarMascotaArray(int ID){
        ArrayList<Mascota> resultados=new ArrayList<>();
        for(Mascota i: ListaMascotas){
            if(i.getID().equals(ID)){
                resultados.add(i);
            }
        }
        return resultados;
    }
    public void eliminarMascota(int indice){
        ListaMascotas.remove(indice);

    }


    public String modificarMascota(String idMascota, int edad, String nombreMascota, String sexo, String raza,
                                   String color, String duenio, File nuevaFotoCarnet, boolean vacunas, boolean desparacitaciones,
                                   boolean esterilizacion, boolean otrasCirugias, int indice) {

        Mascota mascotaExistente = ListaMascotas.get(indice);

        // Ahora actualiza los atributos de la mascota existente
        mascotaExistente.setID(idMascota);
        mascotaExistente.setEdad(edad);
        mascotaExistente.setNombreMascota(nombreMascota);
        mascotaExistente.setSexo(sexo);
        mascotaExistente.setRaza(raza);
        mascotaExistente.setColor(color);
        mascotaExistente.setDuenio(duenio);

        // Actualiza la ruta de la foto del carnet
        String nuevaRutaFotoCarnet = "./src/vista/imagenes/" + nuevaFotoCarnet.getName();
        mascotaExistente.setRutaFotoCarnet(nuevaRutaFotoCarnet);
        mascotaExistente.setVacunas(vacunas);
        mascotaExistente.setDesparacitaciones(desparacitaciones);
        mascotaExistente.setEsterilizacion(esterilizacion);
        mascotaExistente.setOtrasCirugias(otrasCirugias);

        // Devuelve la nueva ruta de la foto del carnet
        return nuevaRutaFotoCarnet;
    }

    public ArrayList<Mascota> obtenerMascotasPorDuenio(String idDuenio) {
        ArrayList<Mascota> mascotasPorDuenio = new ArrayList<>();

        for (Mascota mascota : ListaMascotas) {
            if (mascota.getDuenio().equals(idDuenio)) {
                mascotasPorDuenio.add(mascota);
            }
        }

        return mascotasPorDuenio;
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
