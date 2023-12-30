package Modelo;

import java.io.File;
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

    public void agregarMascota(String ID, int edad, String nombreMascota, String sexo, String raza, String color,
                               String duenio, File fotoCarnet, boolean vacunas, boolean desparacitaciones, boolean esterilizacion,
                               boolean otrasCirugias) {

        String rutaFotoCarnet = "./src/vista/imagenes/" + fotoCarnet.getName();

        // Crear una nueva instancia de Mascota
        Mascota nuevaMascota = new Mascota(ID, edad, nombreMascota, sexo, raza, color, duenio, rutaFotoCarnet,
                vacunas, desparacitaciones, esterilizacion, otrasCirugias);

        // Agregar la nueva mascota a la lista o estructura de datos que estés usando en tu modelo
        listaMascotas.add(nuevaMascota);
    }
    public int buscarMascota(String criterio) {
        for (Mascota mascota : listaMascotas) {
            if (mascota.getID().equals(criterio) || mascota.getNombreMascota().equalsIgnoreCase(criterio) || mascota.getDuenio().equalsIgnoreCase(criterio)) {
                return listaMascotas.indexOf(mascota);
            }
        }
        return -1;
    }
    public ArrayList<Mascota> buscarMascotaArray(int ID){
        ArrayList<Mascota> resultados=new ArrayList<>();
        for(Mascota i:listaMascotas){
            if(i.getID().equals(ID)){
                resultados.add(i);
            }
        }
        return resultados;
    }

    public void eliminarMascota(int indice){

        listaMascotas.remove(indice);
    }

    public String modificarMascota(String idMascota, int edad, String nombreMascota, String sexo, String raza,
                                   String color, String duenio, File nuevaFotoCarnet, boolean vacunas, boolean desparacitaciones,
                                   boolean esterilizacion, boolean otrasCirugias, int indice) {

        Mascota mascotaExistente = listaMascotas.get(indice);

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
        mascotaExistente.setDesparasitaciones(desparacitaciones);
        mascotaExistente.setEsterilizacion(esterilizacion);
        mascotaExistente.setOtrasCirugias(otrasCirugias);

        // Devuelve la nueva ruta de la foto del carnet
        return nuevaRutaFotoCarnet;
    }
    public ArrayList<Mascota> mostrarMascotas(){
        return listaMascotas;
    }
}
