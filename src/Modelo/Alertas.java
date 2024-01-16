package Modelo;


import java.io.Serializable;


public class Alertas implements Serializable {
    //Auditoria Ambiental
    private String txtPeriodo;
    private String txtFechaEmisionLicencia;
    private String txtFechaAprobacionTDRS;
    private   String  txtFechaAprobacionAA;
    private String txtNombreConsultor;
    private String txtNumPoliza;
    private String txtFechaEmiPoliza;
    private String txtFechaVenciPoliza;
    private String txtMontoPoliza;
    private String OficioAprobado;

    private String CodigoProyecto;
    private String nResolucion;
    private String PeriodoEvaluado;
    private String FechaAprobacion;


    public Alertas() {

    }

    //Alerta Informe Ambiental
    public Alertas( String codigoProyecto, String nResolucion, String periodoEvaluado, String fechaAprobacion) {

        this.CodigoProyecto = codigoProyecto;
        this.nResolucion = nResolucion;
        this.PeriodoEvaluado = periodoEvaluado;
        this.FechaAprobacion = fechaAprobacion;
    }



    public String getOficioAprobado() {
        return OficioAprobado;
    }

    public void setOficioAprobado(String oficioAprobado) {
        OficioAprobado = oficioAprobado;
    }

    public String getCodigoProyecto() {
        return CodigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        CodigoProyecto = codigoProyecto;
    }

    public String getnResolucion() {
        return nResolucion;
    }

    public void setnResolucion(String nResolucion) {
        this.nResolucion = nResolucion;
    }

    public String getPeriodoEvaluado() {
        return PeriodoEvaluado;
    }

    public void setPeriodoEvaluado(String periodoEvaluado) {
        PeriodoEvaluado = periodoEvaluado;
    }

    public String getFechaAprobacion() {
        return FechaAprobacion;
    }

    //Alerta Auditoria Ambiental
    public Alertas(String txtPeriodo, String txtFechaEmisionLicencia, String txtFechaAprobacionTDRS,
                   String txtFechaAprobacionAA, String txtNombreConsultor, String txtNumPoliza,
                   String txtFechaEmiPoliza, String txtFechaVenciPoliza, String txtMontoPoliza) {
        this.txtPeriodo = txtPeriodo;
        this.txtFechaEmisionLicencia = txtFechaEmisionLicencia;
        this.txtFechaAprobacionTDRS = txtFechaAprobacionTDRS;
        this.txtFechaAprobacionAA = txtFechaAprobacionAA;
        this.txtNombreConsultor = txtNombreConsultor;
        this.txtNumPoliza = txtNumPoliza;
        this.txtFechaEmiPoliza = txtFechaEmiPoliza;
        this.txtFechaVenciPoliza = txtFechaVenciPoliza;
        this.txtMontoPoliza = txtMontoPoliza;
    }



    public String getTxtFechaAprobacionAA() {
        return txtFechaAprobacionAA;
    }

    public String getTxtPeriodo() {
        return txtPeriodo;
    }

    public void setTxtPeriodo(String txtPeriodo) {
        this.txtPeriodo = txtPeriodo;
    }

    public String getTxtFechaEmisionLicencia() {
        return txtFechaEmisionLicencia;
    }



    public String getTxtFechaAprobacionTDRS() {
        return txtFechaAprobacionTDRS;
    }







    public String getTxtNombreConsultor() {
        return txtNombreConsultor;
    }

    public void setTxtNombreConsultor(String txtNombreConsultor) {
        this.txtNombreConsultor = txtNombreConsultor;
    }

    public String getTxtNumPoliza() {
        return txtNumPoliza;
    }

    public void setTxtNumPoliza(String txtNumPoliza) {
        this.txtNumPoliza = txtNumPoliza;
    }

    public String getTxtFechaEmiPoliza() {
        return txtFechaEmiPoliza;
    }



    public String getTxtFechaVenciPoliza() {
        return txtFechaVenciPoliza;
    }



    public String getTxtMontoPoliza() {
        return txtMontoPoliza;
    }

    public void setTxtMontoPoliza(String txtMontoPoliza) {
        this.txtMontoPoliza = txtMontoPoliza;
    }





}
