package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class DuenioEstablecimiento extends Persona implements Serializable {
    private ArrayList<Establecimiento> listaEstablecimiento;
    public DuenioEstablecimiento(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia) {
        super(cedula, nombre, direccion, telefono, correo, contrasenia);
        this.listaEstablecimiento = new ArrayList<>();
    }

    public ArrayList<Establecimiento> getListaEstablecimiento() {
        return listaEstablecimiento;
    }

    public void setListaEstablecimiento(ArrayList<Establecimiento> listaEstablecimiento) {
        this.listaEstablecimiento = listaEstablecimiento;
    }
}
