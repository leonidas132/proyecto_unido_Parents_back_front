package com.protalento.utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author Luis Nuñez
 * @version 1
 *  */

public final class Fechas {
    public static String PATRON_FECHA = "yyyy-MM-dd";
    public static String PATRON_FECHA_HORA = "yyyy-MM-dd HH:mm:ss";

    // para que no me puedan instanciar la clase
    private Fechas(){

    }
    /**
     * obtener LocalDate de String
     * @param fecha con patron yyyy-MM-dd
     * @return fecha LocalDate
     * */
    public static LocalDate getLocalDate(String fecha){
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern(PATRON_FECHA));
    }

    /**
     * obtener LocalDateTime de String
     * @param fecha con patron yyyy-MM-dd
     * @return fecha LocalDateTime
     * */
    public static LocalDateTime getLocalDateTime(String fecha){
       return LocalDateTime.parse(fecha,DateTimeFormatter.ofPattern(PATRON_FECHA_HORA));
    }

    /**
     * Obtener String de LocalDate
     *
     * @param fecha LocalDate
     * @return fecha String con patron yyyy-MM-dd
     */


    public static String getString(LocalDate fecha){
        return fecha.format(DateTimeFormatter.ofPattern(PATRON_FECHA));
    }

    /**
     * Obtener String de LocalDateTime
     *
     * @param fecha LocalDateTime
     * @return fecha String con patron yyyy-MM-dd HH:mm:ss
     */
    public static String getString(LocalDateTime fecha) {
        return fecha.format(DateTimeFormatter.ofPattern(PATRON_FECHA_HORA));
    }
}
