package Modelo;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Mascota implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ID,edad;
    private Image fotoCarnet;
    private String nombreMascota, sexo, raza, color, duenio;
    private String rutaFotoCarnet;
    private boolean vacunas, desparacitaciones, esterilizacion, otrasCirugias;

    public Mascota(int ID, int edad, String nombreMascota, String sexo, String raza, String color, String duenio,
                   Image fotoCarnet, boolean vacunas, boolean desparacitaciones, boolean esterilizacion,
                   boolean otrasCirugias) {
        this.ID = ID;
        this.edad = edad;
        this.nombreMascota = nombreMascota;
        this.sexo = sexo;
        this.raza = raza;
        this.color = color;
        this.duenio = duenio;
        this.fotoCarnet = fotoCarnet;
        this.vacunas = vacunas;
        this.desparacitaciones = desparacitaciones;
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

    public boolean isDesparacitaciones() {
        return desparacitaciones;
    }

    public void setDesparacitaciones(boolean desparacitaciones) {
        this.desparacitaciones = desparacitaciones;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public Image getFotoCarnet() {
        return fotoCarnet;
    }

    public void setFotoCarnet(Image fotoCarnet) {
        this.fotoCarnet = fotoCarnet;
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

