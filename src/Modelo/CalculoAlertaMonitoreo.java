package Modelo;

public class CalculoAlertaMonitoreo {
    private String oficioAprobacion;

    private String tipoMonitoreo;
    private String CodigoProyecto;
    private String Nresolucion;
    private String FechaAprobacion;

    public CalculoAlertaMonitoreo(String oficioAprobacion, String tipoMonitoreo, String codigoProyecto, String nresolucion, String fechaAprobacion) {
        this.oficioAprobacion = oficioAprobacion;
        this.tipoMonitoreo = tipoMonitoreo;
        this.CodigoProyecto = codigoProyecto;
        this.Nresolucion = nresolucion;
        this.FechaAprobacion = fechaAprobacion;
    }

    public String getOficioAprobacion() {
        return oficioAprobacion;
    }

    public void setOficioAprobacion(String oficioAprobacion) {
        this.oficioAprobacion = oficioAprobacion;
    }

    public String getTipoMonitoreo() {
        return tipoMonitoreo;
    }

    public void setTipoMonitoreo(String tipoMonitoreo) {
        this.tipoMonitoreo = tipoMonitoreo;
    }

    public String getCodigoProyecto() {
        return CodigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        CodigoProyecto = codigoProyecto;
    }

    public String getNresolucion() {
        return Nresolucion;
    }

    public void setNresolucion(String nresolucion) {
        Nresolucion = nresolucion;
    }

    public String getFechaAprobacion() {
        return FechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        FechaAprobacion = fechaAprobacion;
    }
}
