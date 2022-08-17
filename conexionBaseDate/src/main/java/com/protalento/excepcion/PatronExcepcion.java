package com.protalento.excepcion;

import com.protalento.enumerados.ERRORES_PATRON;

public class PatronExcepcion extends Exception{
    private static final long serialVersionUID=1L;

    private ERRORES_PATRON errores_patron;

    public PatronExcepcion(ERRORES_PATRON errores_patron){
        super();
        this.errores_patron= errores_patron;
    }
    public PatronExcepcion(String mensaje){
        super(mensaje);
    }
    @Override
    public String getMessage() {
        switch (errores_patron){
            case CORREO:
                return errores_patron.getMensaje();

            case CLAVE:
                return errores_patron.getMensaje();
        }
        return super.getMessage();
    }
}
