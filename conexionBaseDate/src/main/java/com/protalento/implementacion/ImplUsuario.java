package com.protalento.implementacion;

import com.protalento.conexion.Conexion;
import com.protalento.entidadJdbc.User;
import com.protalento.excepcion.PatronExcepcion;
import com.protalento.interfacesJdbc.IUsuario;
import com.protalento.utilidades.Fechas;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImplUsuario implements IUsuario {

    //Log4j es una biblioteca de código abierto desarrollada en
    // Java por la Apache Software Foundation que permite a
    // los desarrolladores de software escribir mensajes de registro,
    // cuyo propósito es dejar constancia de una determinada transacción en tiempo de ejecución.
    private static Logger logger = LogManager.getLogger();
    private PreparedStatement preparedStatementBuscarId;
    private PreparedStatement preparedStatementInsertar;
    private PreparedStatement preparedStatementModificar;
    private PreparedStatement preparedStatementEliminar;
    private PreparedStatement preparedStatementListar;
    private PreparedStatement preparedStatementBuscarCorreoClave;
    private PreparedStatement preparedStatementActualizarIntentosFallidos;
    private PreparedStatement preparedStatementActualizarFechaUltimoAcceso;
    private Conexion conexion;

    public ImplUsuario() {
       conexion = new Conexion();
    }


    @Override
    public User buscarID(String correo) {
        User user = null;
        String sql = "select aes_decrypt(clave,?) as clave,fechaCreacion,fechaUltimoAcceso,intentosFallidos from usuarios where correo=?";
        try {
            if (null== preparedStatementBuscarId) {
                preparedStatementBuscarId = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementBuscarId.setString(1, conexion.getLlave());
            preparedStatementBuscarId.setString(2,correo);

            ResultSet resultSet = preparedStatementBuscarId.executeQuery();
            if(resultSet.next()) {
                user = new User();
                user.setCorreo(correo);
                user.setClave(resultSet.getString("clave"));
                user.setFechaCreacion(Fechas.getLocalDate(resultSet.getString("fechaCreacion")));
                user.setFechaUltimoAcceso(Fechas.getLocalDateTime(resultSet.getString("fechaUltimoAcceso")));
                user.setIntentosFallidos(resultSet.getByte("intentosFallidos"));

            }
            logger.debug(preparedStatementBuscarId);
            logger.info(user);
        } catch (SQLException e) {
            logger.error(e);
        } catch (PatronExcepcion e) {
           logger.error(e);
        }
        return user;
    }

    @Override
    public boolean insertar(User user) {
        String sql = "insert into usuarios(correo,clave,fechaCreacion,fechaUltimoAcceso) values(?,aes_encrypt(?,?),?,?)";
        try {
            if(null==preparedStatementInsertar) {
                preparedStatementInsertar = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementInsertar.setString(1,user.getCorreo());
            preparedStatementInsertar.setString(2,user.getClave());
            preparedStatementInsertar.setString(3, conexion.getLlave());
            preparedStatementInsertar.setString(4,Fechas.getString(LocalDate.now()));
            preparedStatementInsertar.setString(5,Fechas.getString(LocalDateTime.now()));

            logger.debug(preparedStatementInsertar);
            logger.info(user);
            return preparedStatementInsertar.executeUpdate()==1;

        } catch (SQLException e) {
           logger.error(e);
        }
        return false;
    }

    @Override
    public boolean modificar(User user) {
        String sql = "update usuarios set clave=aes_encrypt(?,?),fechaCreacion =? where correo = ?";
        try {
            if(null == preparedStatementModificar){
                preparedStatementModificar = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementModificar.setString(1,user.getClave());
            preparedStatementModificar.setString(2,conexion.getLlave());
            preparedStatementModificar.setString(3,Fechas.getString(user.getFechaCreacion()));
            preparedStatementModificar.setString(4, user.getCorreo());

            logger.debug(preparedStatementModificar);
            logger.info(user);
            return preparedStatementModificar.executeUpdate() == 1;
        } catch (SQLException e) {
           logger.error(e);
        }
        return false;
    }

    @Override
    public boolean eliminar(User user) {
        String sql = "delete from usuarios where correo = ?";
        try {
            if (null == preparedStatementEliminar) {
                preparedStatementEliminar = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementEliminar.setString(1, user.getCorreo());

            logger.debug(preparedStatementEliminar);
            logger.info(user);
            return preparedStatementEliminar.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public List<User> listar() {
        List<User> lista=new ArrayList<>();
        String sql = "select correo, aes_decrypt(clave,?) as clave,fechaCreacion,fechaUltimoAcceso,intentosFallidos from usuarios";
        try {
            if(null == preparedStatementListar){
                preparedStatementListar = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementListar.setString(1,conexion.getLlave());
            ResultSet resultSet = preparedStatementListar.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setCorreo(resultSet.getString("correo"));
                user.setClave(resultSet.getString("clave"));
                user.setFechaCreacion(Fechas.getLocalDate(resultSet.getString("fechaCreacion")));
                user.setFechaUltimoAcceso(Fechas.getLocalDateTime(resultSet.getString("fechaUltimoAcceso")));
                user.setIntentosFallidos(resultSet.getByte("intentosFallidos"));
                lista.add(user);
            }
            logger.debug(preparedStatementListar);
            logger.info(lista);
        } catch (SQLException e) {
            logger.error(e);
        } catch (PatronExcepcion e) {
           logger.error(e);
        }
        return lista;
    }

    @Override
    public User buscarPorCorreoClave(String correo, String clave) {
        User usuario = buscarID(correo);
        // si usuario diferente de null y usuario.getClave == clave entoces actualisamos
        //fecha ultimo acceso t retornamos el usuario
        if(null != usuario && usuario.getClave().equals(clave)){
            // nos aseguramos que actualize la informacion
            if(actualizarFechasUltimoAcceso(usuario)){
                usuario = buscarID(correo);
            }

            //de lo contrario si el usuario es diferente de null y si  susario.getClave es diferente de clave
            // entoces actualizamos los intentos fallidos y retornamos null
        } else if (null != usuario && !usuario.getClave().equals(clave)) {
            ActualizarIntentosFallidos(usuario);
        }

        return null;
    }

    @Override
    public boolean actualizarFechasUltimoAcceso(User user) {
        String sql = "update usuarios set fechaUltimoAcceso=? ,intentosFallidos=? where correo =?";
        try {
            if(null ==preparedStatementActualizarFechaUltimoAcceso){
                preparedStatementActualizarFechaUltimoAcceso = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementActualizarFechaUltimoAcceso.setString(1, Fechas.getString(LocalDateTime.now()));
            preparedStatementActualizarFechaUltimoAcceso.setByte(2,(byte)0);
            preparedStatementActualizarFechaUltimoAcceso.setString(3,user.getCorreo());
            logger.debug(preparedStatementActualizarFechaUltimoAcceso);
            logger.info(user);
            return preparedStatementActualizarFechaUltimoAcceso.executeUpdate()==1;
        } catch (SQLException e) {
           logger.error(e);
        }
      return false;
    }

    @Override
    public boolean ActualizarIntentosFallidos(User user) {
        String sql = "update usuarios set intentosFallidos = (intentosFallidos+1) where correo=?";
        try{
            if(null == preparedStatementActualizarIntentosFallidos){
                preparedStatementActualizarIntentosFallidos = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementActualizarIntentosFallidos.setString(1,user.getCorreo());
            logger.debug(preparedStatementActualizarIntentosFallidos);
            logger.info(user);
            return preparedStatementActualizarIntentosFallidos.executeUpdate() == 1;

        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }


    public static void main(String[] args){

        try {
            User user = new User("User232@somostodos.com","User3234",Fechas.getLocalDate("2022-09-11"), LocalDateTime.now(),(byte)0);



            IUsuario iUsuario= new ImplUsuario();
            System.out.println(user);
        //   iUsuario.insertar(user);

            // System.out.println(iUsuario.buscarPorCorreoClave("luismrocha132@gmail.com","Luis123"));
            // iUsuario.modificar(user);
            //  System.out.println(iUsuario.buscarID("luismrocha132@gmail.com"));//
            //  iUsuario.eliminar(user);
            System.out.println(iUsuario.buscarID("User132@gmail.com"));
            System.out.println(iUsuario.buscarID("User132@mail.com"));
            System.out.println("-----------------------------------------------------");

            iUsuario.buscarPorCorreoClave("User132@gmail.com","1234");
            iUsuario.buscarPorCorreoClave("User12@mail.cm","User1234");
            iUsuario.buscarPorCorreoClave("User132@gmail.com","User1234");
            System.out.println("----------------------------------------------------------");


            iUsuario.listar();


        } catch (PatronExcepcion e) {
            logger.error(e);
        }

    }


}
