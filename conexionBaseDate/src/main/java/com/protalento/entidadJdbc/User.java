package com.protalento.entidadJdbc;

import com.protalento.excepcion.PatronExcepcion;
import com.protalento.utilidades.Patrones;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private String correo;
    private String clave;
    private LocalDate fechaCreacion;
    private LocalDateTime fechaUltimoAcceso;
    private Byte intentosFallidos;

    public User() {
    }

    public User(String correo, String clave, LocalDate fechaCreacion, LocalDateTime fechaUltimoAcceso, Byte intentosFallidos) throws PatronExcepcion {
        super();
        setCorreo(correo);
        setClave( clave);
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
        this.intentosFallidos = intentosFallidos;
    }

    public Byte getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(Byte intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) throws PatronExcepcion {
        if(Patrones.esCorreo(correo)){
            this.correo = correo;
        }
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) throws PatronExcepcion {
        if(Patrones.esClave(clave)){
            this.clave = clave;
        }
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    @Override
    public String toString() {
        return "\n User " +
                "correo: " + correo + '\'' +
                " clave: " + clave + '\'' +
                " fechaCreacion: " + fechaCreacion +
                " fechaUltimoAcceso: " + fechaUltimoAcceso +
                " intentosFallidos: " + intentosFallidos ;
    }
}
