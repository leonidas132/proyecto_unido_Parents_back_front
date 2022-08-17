package com.protalento.conexion;

import com.protalento.enumerados.Base_64;
import com.protalento.utilidades.Esquema_Base64;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Conexion {
    // InputStream  representan un flujo de entrada de bytes

    // utilizamos un PATH ya que en un debido momento  necesitaremos un metodo que mepermita traer solo la clave encriptada
   private static final InputStream  PATH = Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"); //esto me permite acceder directamente y cargar el archivo properties
    private   Properties properties;
    public Conexion (){
      setProperties(new Properties());
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
        try {
            this.properties.load(PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  Connection conexiondb(){
        Connection connection = null;
        // se crea una instancia de la clase propertirs para acceder a sus metodos
        try {
           // properties.load(PATH); // DE ESTA MANERA CARGAMOS EL ARCHIVO
            String DRIVER = properties.getProperty("db_driver") ;
            String URL =properties.getProperty("db_url");
            String USER =properties.getProperty("db_user");
            String CLAVE=properties.getProperty("db_clave");

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USER,CLAVE);
            System.out.println("ok");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return connection;
    }

    public String getLlave(){
        return Esquema_Base64.codificar( properties.getProperty("db_llave"), Base_64.DECODIFICAR);
    }


    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        System.out.println(conexion.getLlave());
    }


}
