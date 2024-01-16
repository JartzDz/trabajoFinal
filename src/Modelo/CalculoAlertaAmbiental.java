package Modelo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Properties;

public class CalculoAlertaAmbiental {
    Alertas alertas=new Alertas();
    public LocalDate calcularFechaLTDRs() {
        LocalDate fechaInicioAuditoria = obtenerFechaInicioAuditoria();
        int periodoAuditar = (Licencia2019()) ? 2 : 3;
        return fechaInicioAuditoria.plusYears(periodoAuditar).minusMonths(4);
    }

    public void enviarCorreoPrimeraAuditoria(String fechaPresentacionTDRs, String correoEstablecimiento) {
        String asunto = "Primera Auditoría";
        String mensaje = "Es tiempo de presentar TDRs para la primera auditoría\n. Plazo límite: " + fechaPresentacionTDRs;
        System.out.println("Antes de enviar el correo");
        enviarCorreo(asunto, mensaje, correoEstablecimiento);
        System.out.println("Después de enviar el correo");
    }

    public void enviarCorreoTDRs(String fechaPresentacionTDRs, String correoEstablecimiento) {
        String asunto = "Presentación de TDRs";
        String mensaje = "Se acerca la fecha para presentar TDRs. Plazo límite: " + fechaPresentacionTDRs;
        System.out.println("Antes de enviar el correo");
        enviarCorreo(asunto, mensaje, correoEstablecimiento);
        System.out.println("Después de enviar el correo");
    }

    public void calcularAlertaAA(String correoEstablecimiento) {
        LocalDate fechaLimiteTDRs = calcularFechaLTDRs();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaLTDRS = fechaLimiteTDRs.format(formateador);
        if (PrimeraAuditoria()) {
            LocalDate fechaPresentacionTDRs = obtenerFechaInicioAuditoria().plusYears(1);

            String fechaPTDRS = fechaPresentacionTDRs.format(formateador);

            enviarCorreoPrimeraAuditoria(fechaPTDRS, correoEstablecimiento);
        } else {
            if (PresentarTDRs(fechaLimiteTDRs)) {
                enviarCorreoTDRs(fechaLTDRS, correoEstablecimiento);
            }
        }

        verificarVPoliza(correoEstablecimiento);
    }

    public  boolean PrimeraAuditoria() {
        LocalDate fechaEmisionLicencia = LocalDate.parse((alertas.getTxtFechaEmisionLicencia()));
        return fechaEmisionLicencia != null && LocalDate.now().isAfter(fechaEmisionLicencia.plusYears(1));
    }

    public  boolean PresentarTDRs(LocalDate fechaLimiteTDRs) {
        LocalDate hoy = LocalDate.now();
        return hoy.isAfter(fechaLimiteTDRs) && hoy.isBefore(fechaLimiteTDRs.plus(3, ChronoUnit.MONTHS));
    }

    public  void verificarVPoliza(String correoEstablecimiento) {
        LocalDate fechaVencimientoPoliza = LocalDate.parse((alertas.getTxtFechaVenciPoliza()));

        if (fechaVencimientoPoliza != null && LocalDate.now().isAfter(fechaVencimientoPoliza.minusMonths(1))) {
            enviarNVencimientoPoliza(correoEstablecimiento);
        }
    }

    public  void enviarNVencimientoPoliza(String correoEstablecimiento) {
        String asunto = "Vencimiento de Póliza";
        String mensaje = "La póliza está por vencer. Renueve pronto.";
        System.out.println("Antes de enviar el correo");
        enviarCorreo(asunto, mensaje, correoEstablecimiento);
        System.out.println("Después de enviar el correo");
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

            System.out.println("Correo enviado, Alerta enviada por correo correctamente.");
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            System.out.println("Error de autenticación"+ "La autenticación ha fallado. Verifica tus credenciales.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error"+ "Error al enviar la alerta por correo.");
        }
    }
    private LocalDate obtenerFechaInicioAuditoria() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate fechaEmisionLicencia = LocalDate.parse((alertas.getTxtFechaEmisionLicencia()));

        if (fechaEmisionLicencia != null) {
            if (Licencia2019()) {
                return fechaEmisionLicencia.plusYears(2);
            } else {
                return fechaEmisionLicencia.plusYears(3);
            }
        } else {

            return LocalDate.now();
        }
    }



    public  boolean Licencia2019() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate fechaLimite = LocalDate.of(2019, Month.JUNE, 12);
        LocalDate fechaEmisionLicencia = LocalDate.parse((alertas.getTxtFechaEmisionLicencia()));

        return fechaEmisionLicencia != null && fechaEmisionLicencia.isBefore(fechaLimite);
    }
    SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");

}
