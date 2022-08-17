package com.protalento.enumerados;



public enum Alertas {
    CERRAR_SESION("Ha cerrado correctamente la sesion"),USUARIO_BLOQUEADO("El usuario esta bloqueado"),CREDENCIALES_INCORRECTAS("las credenciales son incorectas");

    private String mensaje;

    Alertas(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
