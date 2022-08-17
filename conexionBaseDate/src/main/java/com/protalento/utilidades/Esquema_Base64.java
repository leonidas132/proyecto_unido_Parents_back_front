package com.protalento.utilidades;

import com.protalento.enumerados.Base_64;


import java.util.Base64;

public final class Esquema_Base64 {

    // para que no puedan instanciar la clase

    private Esquema_Base64(){

    }
    /**
     * Codificar o Decodificar en Base 64
     * @param  cadena
     * @param base_64
     * @return cadena*/
    public static String codificar(String cadena, Base_64 base_64){
        switch (base_64){
            case CODIFICAR:
                /*
                * encodeToString()
                * Codifica la matriz de bytes especificada en una cadena mediante el
                *  esquema de codificación Base64. Este método primero codifica
                *  todos los bytes de entrada en una matriz
                *  de bytes codificados en base64 y luego construye una nueva c
                * adena utilizando la matriz de bytes */
                return Base64.getEncoder().encodeToString(cadena.getBytes());

                /*
                * getBytes()
                *  Codifica esta cadena en una secuencia
                * de bytes usando el conjunto de caracteres predeterminado
                *  de la plataforma, almacenando el resultado en una nueva matriz de bytes.*/

            case DECODIFICAR:
              return new String(Base64.getDecoder().decode(cadena));

        }
        return null;
    }

    public static void main(String[] args) {
        String cadenaCodificada = codificar("develoment",Base_64.CODIFICAR);
        System.out.println(cadenaCodificada);
        String cadenaDecodificada = codificar(cadenaCodificada,Base_64.DECODIFICAR);
        System.out.println(cadenaDecodificada);
    }
}
