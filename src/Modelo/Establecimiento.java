package Modelo;

import java.util.ArrayList;

public class Establecimiento {
    private ArrayList<String> telefono = new ArrayList<>();
    private String ruc, nombreRepresentante, tipoEstablecimiento;
    private int coordenadas;

    public Establecimiento(ArrayList<String> telefono, String ruc, String nombreRepresentante, String tipoEstablecimiento, int coordenadas) {
        this.telefono = telefono;
        this.ruc = ruc;
        this.nombreRepresentante = nombreRepresentante;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.coordenadas = coordenadas;
    }

    public ArrayList<String> getTelefono() {
        return telefono;
    }

    public void setTelefono(ArrayList<String> telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public int getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(int coordenadas) {
        this.coordenadas = coordenadas;
    }
}
