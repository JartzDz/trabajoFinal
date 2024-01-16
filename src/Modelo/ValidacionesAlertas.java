package Modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesAlertas {
    public boolean validarFecha(String fechaStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // No permite fechas inválidas (por ejemplo, 32/01/2024)

        try {
            Date fecha = sdf.parse(fechaStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean validarCorreo(String correo) {
        String patronCorreo = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(patronCorreo);
        Matcher matcher = pattern.matcher(correo);


        return matcher.matches();
    }

    public boolean validarNombre(String nombre) {
        // La expresión regular permite letras y espacios, pero no al principio o al final.
        String regex = "^[a-zA-Z]+( [a-zA-Z]+)*$";

        return nombre.matches(regex);
    }

    public boolean esCadenaNumerica(String cadena) {
        // La expresión regular permite solo dígitos.
        String regex = "\\d+";

        return cadena.matches(regex);
    }

    public boolean esMontoValido(String monto) {
        // La expresión regular permite un número opcionalmente seguido por un punto decimal y más números.
        String regex = "^\\$?\\d+(\\.\\d+)?$";

        return monto.matches(regex);
    }

    public boolean esAnioValido(String anio) {
        // La expresión regular permite solo dígitos y exactamente cuatro dígitos.
        String regex = "^\\d{4}$";

        if (anio.matches(regex)) {
            int anioNumerico = Integer.parseInt(anio);
            // Puedes agregar más validaciones según tus requisitos, como verificar si es un año realista.
            return anioNumerico >= 1900 && anioNumerico <= 2100; // Por ejemplo, limitado a años entre 1900 y 2100.
        }

        return false;
    }

    public boolean validarFechas(String fechaCreacionString, String fechaVencimientoString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parsear las fechas
            Date fechaCreacion = sdf.parse(fechaCreacionString);
            Date fechaVencimiento = sdf.parse(fechaVencimientoString);

            // Validar que la fecha de vencimiento no sea mayor que la fecha de creación
            return !fechaVencimiento.before(fechaCreacion);

        } catch (ParseException e) {
            System.out.println("Error al parsear las fechas: " + e.getMessage());
            return false; // En caso de error, considerar la validación como no exitosa
        }
    }

}



