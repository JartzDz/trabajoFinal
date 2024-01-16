package Modelo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GestorAlertas {
    private ArrayList<Alertas> listaAlertas;


    public GestorAlertas(){
        listaAlertas =new ArrayList<>();

    }
    public ArrayList<Alertas> getListaAlertas() {
        return listaAlertas;
    }


    public void agregarAlertaAuditoriaAmbiental( String txtPeriodo,
                                                 String txtFechaEmisionLicencia, String txtFechaAprobacionTDRS,String  txtFechaAprobacionAA,String txtNombreConsultor,
                                                 String txtNumPoliza, String txtFechaEmiPoliza,String txtFechaVenciPoliza,String txtMontoPoliza){
        Alertas alertaAuditoriaAmbiental =new Alertas(txtPeriodo,txtFechaEmisionLicencia,txtFechaAprobacionTDRS,txtFechaAprobacionAA,txtNombreConsultor,txtNumPoliza,txtFechaEmiPoliza,txtFechaVenciPoliza,txtMontoPoliza);
        listaAlertas.add(alertaAuditoriaAmbiental);
    }
    public void agregarAlertaInformeAmbiental( String CodigoProyecto, String nResolucion, String PeriodoEvaluado, String FechaAprobacion){
        Alertas alertaInformeAmbiental=new Alertas(CodigoProyecto,nResolucion,PeriodoEvaluado,FechaAprobacion);
        listaAlertas.add(alertaInformeAmbiental);
    }
    public void agregarAlertaMonitoreo( String tipoMonitoreo, String codigoProyecto, String nresolucion, String fechaAprobacion){
        Alertas alertaMonitoreo=new Alertas(tipoMonitoreo,codigoProyecto,nresolucion,fechaAprobacion);
        listaAlertas.add(alertaMonitoreo);
    }
    public void guardarAlerta() {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("ListaAlerta.bin"))) {
            salida.writeObject(listaAlertas);
            System.out.println("La lista de Alertas de se ha serializado correctamente");
        } catch (IOException e) {
            System.out.println("Error al serializar la lista de AlertasAuditoriaAmbiental: " + e.getMessage());
        }
    }

    public void eliminarAlerta(int indice){

        listaAlertas.remove(indice);
        System.out.println("Alerta Eliminada");;
    }
    @SuppressWarnings("unchecked")

    public void recuperarAlertas() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("ListaAlerta.bin"))) {

            listaAlertas = (ArrayList<Alertas>) entrada.readObject();
            System.out.println("La lista de Alertas se ha deserializado correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar la lista de Alertas " + e.getMessage());
        }
    }




}
