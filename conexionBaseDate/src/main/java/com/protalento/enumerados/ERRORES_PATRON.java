package com.protalento.enumerados;

public enum ERRORES_PATRON {
    CORREO("Debe ingresar un formato permitido 'usuario@dominio.ext'"),
    CLAVE("La clave debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula. NO puede tener otros símbolos.");

    private String mensaje;
    ERRORES_PATRON(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
