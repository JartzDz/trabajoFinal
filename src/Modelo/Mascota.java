package Modelo;

import java.util.ArrayList;

public class Mascota {
    private int ID;
    private String nombreMascota;
    private String raza;
    private String duenio;
    public Mascota(int ID, String nombreMascota, String raza, String duenio) {
        this.ID = ID;
        this.nombreMascota = nombreMascota;
        this.raza = raza;
        this.duenio = duenio;
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

