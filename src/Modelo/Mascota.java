package Modelo;

import java.io.Serializable;

public class Mascota implements Serializable {
    private static final long serialVersionUID = 1L;
    private int edad;
    private String nombreMascota, sexo, raza, color, duenio,ID;
    private String rutaFotoCarnet;
    private boolean vacunas, desparasitaciones, esterilizacion, otrasCirugias;

    public Mascota(String ID, int edad, String nombreMascota, String sexo, String raza, String color, String duenio,
                   String rutaFotoCarnet, boolean vacunas, boolean desparasitaciones, boolean esterilizacion,
                   boolean otrasCirugias) {
        this.ID = ID;
        this.edad = edad;
        this.nombreMascota = nombreMascota;
        this.sexo = sexo;
        this.raza = raza;
        this.color = color;
        this.duenio = duenio;
        this.rutaFotoCarnet= rutaFotoCarnet;
        this.vacunas = vacunas;
        this.desparasitaciones = desparasitaciones;
        this.esterilizacion = esterilizacion;
        this.otrasCirugias = otrasCirugias;
    }
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getRutaFotoCarnet() {
        return rutaFotoCarnet;
    }

    public void setRutaFotoCarnet(String rutaFotoCarnet) {
        this.rutaFotoCarnet = rutaFotoCarnet;
    }

    public boolean isVacunas() {
        return vacunas;
    }

    public void setVacunas(boolean vacunas) {
        this.vacunas = vacunas;
    }

    public boolean isDesparasitaciones() {
        return desparasitaciones;
    }

    public void setDesparasitaciones(boolean desparacitaciones) {
        this.desparasitaciones = desparacitaciones;
    }

    public boolean isEsterilizacion() {
        return esterilizacion;
    }

    public void setEsterilizacion(boolean esterilizacion) {
        this.esterilizacion = esterilizacion;
    }

    public boolean isOtrasCirugias() {
        return otrasCirugias;
    }

    public void setOtrasCirugias(boolean otrasCirugias) {
        this.otrasCirugias = otrasCirugias;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDuenio() {
        return duenio;
    }

    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }



    @Override
    public String toString() {
        return "Mascota{" +
                "ID=" + ID +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", raza='" + raza + '\'' +
                ", duenio='" + duenio + '\'' +
                '}';
    }
}

