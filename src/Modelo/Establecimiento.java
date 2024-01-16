package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Establecimiento implements Serializable {
    private String tipoEvaluacion;
    private String ruc,nombreEst,telefono,direccion,correo,CIRepresentante, tipoEstablecimiento;


    public Establecimiento(String ruc, String nombreEst, String telefono, String direccion, String correo, String CIRepresentante, String tipoEstablecimiento) {
        this.ruc = ruc;
        this.telefono = telefono;
        this.nombreEst = nombreEst;
        this.direccion = direccion;
        this.correo = correo;
        this.CIRepresentante = CIRepresentante;
        this.tipoEstablecimiento = tipoEstablecimiento;
    }
    public Establecimiento(String ruc, String nombreEst, String telefono, String direccion, String correo, String CIRepresentante, String tipoEstablecimiento, String tipoEvaluacion) {
        this.ruc = ruc;
        this.nombreEst = nombreEst;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.CIRepresentante = CIRepresentante;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.tipoEvaluacion = tipoEvaluacion;
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
