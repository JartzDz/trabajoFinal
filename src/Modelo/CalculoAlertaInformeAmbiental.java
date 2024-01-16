package Modelo;

import com.toedter.calendar.JDateChooser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class CalculoAlertaInformeAmbiental implements Serializable {



    public void calcularAlertasInformeAmbiental(String correoEstablecimiento1, String FechaAprobacion, String periodo) {
        System.out.println("Calculando alertas de informe ambiental de cumplimiento");
        LocalDate fechaResolucion1 = LocalDate.parse((FechaAprobacion));

        String asunto = "Alerta de Evaluación Ambiental";

        System.out.println(fechaResolucion1);
        System.out.println(correoEstablecimiento1);

        if (fechaResolucion1 != null) {
            LocalDate fechaLimiteAlerta1 = calcularFechaLimiteAlerta(FechaAprobacion,periodo);
            LocalDate hoy = LocalDate.now();
            System.out.println(fechaLimiteAlerta1);
            System.out.println(hoy);


            if (hoy.isEqual(fechaLimiteAlerta1.minusMonths(2)) || hoy.isAfter(fechaLimiteAlerta1.minusMonths(2))) {
                String mensaje = "El periodo evaluado ha finalizado. Fecha límite: " + calcularFechaLimiteAlerta(FechaAprobacion,periodo);
                System.out.println("Enviando alerta de informe ambiental de cumplimiento");
                enviarCorreo(asunto, mensaje, correoEstablecimiento1);
                System.out.println("Alerta de informe ambiental de cumplimiento enviada");
            }

        }
    }

    private LocalDate calcularFechaLimiteAlerta(String fechaResolucion,String periodoEvaluado) {
        LocalDate fechaLimiteAlerta;
        LocalDate fechaResolucion1 = LocalDate.parse((fechaResolucion));

        if (periodoEvaluado != null && !periodoEvaluado.isEmpty()) {
            // Manejar el formato "dd/MM/yyyy"
            LocalDate fechaInicioPeriodo = LocalDate.parse(periodoEvaluado, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            fechaLimiteAlerta = fechaInicioPeriodo.plusYears(2).minusMonths(2);
        } else {
            fechaLimiteAlerta = fechaResolucion1.plusYears(1);
        }

        return fechaLimiteAlerta;
    }

    public void enviarCorreo(String asunto, String mensaje, String destinatario) {
        System.out.println("Empezando a enviar el correo");
        System.out.println(destinatario);
        final String remitente = "designjartz@gmail.com";
        final String password = "qpwwrokprrzpgofd";

        Properties propiedades = new Properties();;
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            Message mensajeCorreo = new MimeMessage(sesion);
            mensajeCorreo.setFrom(new InternetAddress(remitente));
            mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensajeCorreo.setSubject(asunto);
            mensajeCorreo.setText(mensaje);

            Transport.send(mensajeCorreo);

            // Mensaje de depuración
            System.out.println("Correo enviado a: " + destinatario);

            System.out.println("Correo enviado, Alerta enviada por correo correctamente.");        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            System.out.println("Error de autenticación"+ "La autenticación ha fallado. Verifica tus credenciales.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error"+ "Error al enviar la alerta por correo.");
        }
    }
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
    public String getFecha(JDateChooser jd){
        if (jd.getDate()!=null){
            return Formato.format(jd.getDate());
        }else{
            return null;
        }
    }
}
