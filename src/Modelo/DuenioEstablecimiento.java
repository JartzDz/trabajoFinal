package Modelo;

import java.util.ArrayList;

public class DuenioEstablecimiento extends Persona{
    private ArrayList<Establecimiento> listaEstablecimiento = new ArrayList<>();
    public DuenioEstablecimiento(String cedula, String nombre, String direccion, String telefono, String correo, String contrasenia, ArrayList<Establecimiento> listaEstablecimiento) {
        super(cedula, nombre, direccion, telefono, correo, contrasenia);
        this.listaEstablecimiento = listaEstablecimiento;
    }

    public ArrayList<Establecimiento> getListaEstablecimiento() {
        return listaEstablecimiento;
    }

    public void setListaEstablecimiento(ArrayList<Establecimiento> listaEstablecimiento) {
        this.listaEstablecimiento = listaEstablecimiento;
    }
}
