package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Establecimiento implements Serializable {
    private String ruc,tipoEstablecimiento,telefono,nombreEst,direccion,correo,CIRepresentante;


    public Establecimiento(String ruc, String tipoEstablecimiento, String telefono, String nombreEst, String direccion, String correo, String CIRepresentante) {
        this.ruc = ruc;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.telefono = telefono;
        this.nombreEst = nombreEst;
        this.direccion = direccion;
        this.correo = correo;
        this.CIRepresentante = CIRepresentante;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreEst() {
        return nombreEst;
    }

    public void setNombreEst(String nombreEst) {
        this.nombreEst = nombreEst;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCIRepresentante() {
        return CIRepresentante;
    }

    public void setCIRepresentante(String CIRepresentante) {
        this.CIRepresentante = CIRepresentante;
    }
}
