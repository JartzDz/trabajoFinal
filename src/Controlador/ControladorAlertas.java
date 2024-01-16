package Controlador;

import Modelo.*;
import Vista.InterfazAuditoriaAmbiental;
import Vista.InterfazInformeAmbiental;
import Vista.InterfazMonitoreo;
import Vista.InterfazPersonalCGA;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class ControladorAlertas extends MouseAdapter implements ActionListener, KeyListener, FocusListener {
    InterfazAuditoriaAmbiental interfazAuditoriaAmbiental;
    InterfazInformeAmbiental interfazInformeAmbiental;
    InterfazMonitoreo interfazMonitoreo;
    GestorAlertas gestorAlertas;
    ValidacionesAlertas validacionesAlertas;
    InterfazPersonalCGA interfazPersonalCGA;
    CalculoAlertaInformeAmbiental calculoAlertaInformeAmbiental;

    public ControladorAlertas(InterfazAuditoriaAmbiental interfazAuditoriaAmbiental, InterfazInformeAmbiental interfazInformeAmbiental, InterfazMonitoreo interfazMonitoreo, InterfazPersonalCGA interfazPersonalCGA) {
        this.interfazAuditoriaAmbiental = interfazAuditoriaAmbiental;
        this.interfazInformeAmbiental = interfazInformeAmbiental;
        this.interfazPersonalCGA=interfazPersonalCGA;
        this.interfazMonitoreo = interfazMonitoreo;
        calculoAlertaInformeAmbiental=new CalculoAlertaInformeAmbiental();
        gestorAlertas=new GestorAlertas();
        validacionesAlertas=new ValidacionesAlertas();
        this.interfazAuditoriaAmbiental.btnAgregar.addActionListener(this);
        this.interfazInformeAmbiental.CREARALERTAButton.addActionListener(this);
        gestorAlertas.recuperarAlertas();

    }

    public String getFecha(JDateChooser jd){
        SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
        return Formato.format(jd.getDate());

    }
    public void agregarAlertaAuditoriaAmbiental(){
        String fechaAprobacionAA=getFecha(interfazAuditoriaAmbiental.fecha3);
        String fechaAprobacionTDRS=getFecha(interfazAuditoriaAmbiental.fecha2);
        String correo=interfazAuditoriaAmbiental.txtCorreo.getText();
        String fechaEmiPoliza=getFecha(interfazAuditoriaAmbiental.fecha4);
        String fechaVenciPoliza=getFecha(interfazAuditoriaAmbiental.fecha5);
        String fechaEmisionLicencia=(getFecha(interfazAuditoriaAmbiental.fecha1));
        String montoPoliza=interfazAuditoriaAmbiental.txtMontoPoliza.getText();
        String nombreConsultor=interfazAuditoriaAmbiental.txtNombreConsultor.getText();
        String NumLicencia=interfazAuditoriaAmbiental.txtNumLicencia.getText();
        String numPoliza=interfazAuditoriaAmbiental.txtNumPoliza.getText();
        String periodo=interfazAuditoriaAmbiental.txtPeriodo.getText();
        if(!fechaAprobacionAA.isEmpty()||!fechaAprobacionTDRS.isEmpty()||!fechaEmiPoliza.isEmpty()||!correo.isEmpty()||!fechaVenciPoliza.isEmpty()||!fechaEmisionLicencia.isEmpty()||!montoPoliza.isEmpty()||!nombreConsultor.isEmpty()||!NumLicencia.isEmpty()||!numPoliza.isEmpty()){
            if(interfazAuditoriaAmbiental.checkBox1.isSelected()){
                if(interfazAuditoriaAmbiental.checkBox2.isSelected()){
                    if(validacionesAlertas.validarCorreo(correo)){
                        if(validacionesAlertas.esAnioValido(periodo)){
                            if(validacionesAlertas.esCadenaNumerica(NumLicencia)){
                                if(validacionesAlertas.esCadenaNumerica(numPoliza)){
                                    if(validacionesAlertas.validarFechas(fechaEmiPoliza,fechaVenciPoliza)){
                                        if(validacionesAlertas.esMontoValido(montoPoliza)){
                                            if(validacionesAlertas.validarNombre(nombreConsultor)){
                                                gestorAlertas.agregarAlertaAuditoriaAmbiental(periodo,fechaAprobacionTDRS,fechaEmisionLicencia,fechaAprobacionAA,nombreConsultor,numPoliza,fechaEmiPoliza,fechaVenciPoliza,montoPoliza);
                                                gestorAlertas.guardarAlerta();

                                                System.out.println(getFecha(interfazAuditoriaAmbiental.fecha1));
                                                interfazAuditoriaAmbiental.setVisible(false);
                                                interfazPersonalCGA.setVisible(true);
                                            }else {
                                                JOptionPane.showMessageDialog(null, "Nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);

                                            }
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(null, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);

                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null, "La fecha de vencimiento de la poliza no puede ser menor a la de emisión.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Numero de poliza Invalido, solo números.", "Error", JOptionPane.ERROR_MESSAGE);
                                    interfazAuditoriaAmbiental.txtNumPoliza.setText("");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Numero de Licencia Invalido, solo números.", "Error", JOptionPane.ERROR_MESSAGE);
                                interfazAuditoriaAmbiental.txtNumLicencia.setText("");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Año Invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                            interfazAuditoriaAmbiental.txtPeriodo.setText("");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Correo Invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                        interfazAuditoriaAmbiental.txtCorreo.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No cuenta con el oficio AA.", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }else{
                JOptionPane.showMessageDialog(null, "No cuenta con el oficio TDR.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    public void agregarAlertaInformeAmbiental(){
        String CodigoProyecto=interfazInformeAmbiental.CodigoProyecto.getText();
        String nResolucion=interfazInformeAmbiental.nResolucion.getText();
        String PeriodoEvaluado=interfazInformeAmbiental.PeriodoEvaluado.getText();
        String FechaAprobacion=getFecha(interfazInformeAmbiental.fecha);
        if(!CodigoProyecto.isEmpty()||!nResolucion.isEmpty()||!PeriodoEvaluado.isEmpty()||!FechaAprobacion.isEmpty()){
            if(validacionesAlertas.esCadenaNumerica(CodigoProyecto)){
                if(validacionesAlertas.esCadenaNumerica(nResolucion)){
                    if(validacionesAlertas.esAnioValido(PeriodoEvaluado)){
                        if(interfazInformeAmbiental.checkBox1.isSelected()){
                            gestorAlertas.agregarAlertaInformeAmbiental(CodigoProyecto,nResolucion,PeriodoEvaluado,FechaAprobacion);
                            gestorAlertas.guardarAlerta();
                            calculoAlertaInformeAmbiental.calcularAlertasInformeAmbiental(interfazAuditoriaAmbiental.txtCorreo.getText(),FechaAprobacion,PeriodoEvaluado);
                            interfazInformeAmbiental.setVisible(false);
                            interfazPersonalCGA.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(null,"No cuenta con el Oficio de Aprobación","ERROR",JOptionPane.ERROR_MESSAGE);

                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Periodo Inválido, ingrese un año correcto","ERROR",JOptionPane.ERROR_MESSAGE);

                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Número de resolución incorrecto solo ingresar números","ERROR",JOptionPane.ERROR_MESSAGE);

                }
            }else{
                JOptionPane.showMessageDialog(null,"Codigo Incorrecto solo ingresar números","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Error rellene todos los campos","ERROR",JOptionPane.ERROR_MESSAGE);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==interfazAuditoriaAmbiental.btnAgregar){

            agregarAlertaAuditoriaAmbiental();

        }
        if(e.getSource()==interfazInformeAmbiental.CREARALERTAButton){
            agregarAlertaInformeAmbiental();
        }
    }


    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
