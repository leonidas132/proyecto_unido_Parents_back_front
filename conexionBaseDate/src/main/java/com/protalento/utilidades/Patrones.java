package com.protalento.utilidades;

import com.protalento.enumerados.ERRORES_PATRON;
import com.protalento.excepcion.PatronExcepcion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Patrones {

    private Patrones(){

    }
    /*
    * java.util.regex es un paquete de biblioteca de clases que usa
    * patrones personalizados por expresiones regulares para hacer coincidir cadenas.
    *  Incluye dos clases: Pattern y Matcher. El objeto Pattern es la representación
    *  de la expresión regular en la memoria después de la compilación., Por lo tanto,
    *  la cadena de expresión regular debe compilarse en un objeto Pattern,
    * y luego el objeto Pattern se usa para crear el objeto Matcher correspondiente.
    * El estado involucrado en la realización de coincidencias se mantiene en el objeto Matcher,
    *  y varios objetos Matcher pueden compartir el mismo objeto Pattern.
    * */




    /**
     * Verifica si tiene un patron de correo electronico
     *
     * @param correo
     * @return boolean
     * @throws PatronExcepcion
     */
    public static boolean esCorreo(String correo) throws PatronExcepcion {
        Pattern patron = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))");
       //me permite evaluar el correo recibido en base a la expreción
        Matcher comparador = patron.matcher(correo);
        if(!comparador.find()){
            throw new PatronExcepcion(ERRORES_PATRON.CORREO);
        }
        return true;

    }



    /**
     * Verifica si tiene un patron de clave
     *
     * @param clave
     * @return boolean
     * @throws PatronExcepcion
     */

    public static boolean esClave(String clave) throws PatronExcepcion {
        Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        //me permite evaluar el clave recibido en base a la expreción
        Matcher comparador = patron.matcher(clave);
        if(!comparador.find()){
            throw new PatronExcepcion(ERRORES_PATRON.CLAVE);
        }
        return true;

    }

    public static void main(String[] args) {
        try {
            System.out.println(esClave("Luism123"));
        } catch (PatronExcepcion e) {
           e.printStackTrace();
        }
    }
}
