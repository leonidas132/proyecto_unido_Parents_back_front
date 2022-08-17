package com.protalento.implementacion;

import com.protalento.conexion.Conexion;
import com.protalento.entidadJdbc.Categorias;
import com.protalento.interfacesJdbc.ICategorias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImplCategorias implements ICategorias {
    private static Logger logger = LogManager.getLogger();

    //Un  PreparedStatement es objeto que representa una instrucción SQL precompilada.
    //Una instrucción SQL se compila previamente y se almacena en un
    // objeto PreparedStatement. Este objeto se puede usar para ejecutar de
    // manera eficiente esta declaración varias veces.

    private PreparedStatement preparedStatementBuscarPorID;
    private PreparedStatement preparedStatementInsertar;
    private PreparedStatement preparedStatementEliminar;
    private PreparedStatement preparedStatementModificar;
    private PreparedStatement preparedStatementListar;
    private Conexion conexion;

    public ImplCategorias() {
       conexion = new Conexion();
    }

    @Override
    public Categorias buscarID(Long ID) {
        Categorias categorias = null;
        String sql = "select id,descripcion from categorias where id =?";
        try {
            //Crea un objeto PreparedStatement para enviar declaraciones SQL parametrizadas a la base de datos.
            //Una instrucción SQL con o sin parámetros IN se puede precompilar y almacenar en un objeto
            if(null == preparedStatementBuscarPorID){
                preparedStatementBuscarPorID = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementBuscarPorID.setLong(1,ID);
            ResultSet resultSet = preparedStatementBuscarPorID.executeQuery();
            if(resultSet.next()){
                categorias = new Categorias();
                categorias.setId(ID);
                categorias.setDescripcion(resultSet.getString("descripcion"));
            }
            logger.debug(preparedStatementBuscarPorID);
            logger.info(categorias);
        } catch (SQLException e) {
            logger.error(e);
        }
        return categorias;
    }

    @Override
    public boolean insertar(Categorias categorias) {
        boolean inserto = false;
        String sql = "insert into categorias (descripcion) values (?)";
        try {
            if(null == preparedStatementInsertar){
                preparedStatementInsertar = conexion.conexiondb().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            }
            preparedStatementInsertar.setString(1,categorias.getDescripcion());
            inserto = preparedStatementInsertar.executeUpdate() == 1;
            // obtener el autoincremental
            if(inserto){
                ResultSet resultSet= preparedStatementInsertar.getGeneratedKeys();
                if (resultSet.next()){
                    categorias.setId(resultSet.getLong(1));
                }
            }
            logger.debug(preparedStatementInsertar);
            logger.info(categorias);

        } catch (SQLException e) {
            logger.error(e);
        }

        return inserto;
    }

    @Override
    public boolean modificar(Categorias categorias) {
        String sql = "update categorias set descripcion = ? where id = ?";
        try {
            if (null == preparedStatementModificar) {
                preparedStatementModificar = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementModificar.setString(1, categorias.getDescripcion());
            preparedStatementModificar.setLong(2, categorias.getId());

            logger.debug(preparedStatementModificar);
            logger.info(categorias);

            return preparedStatementModificar.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean eliminar(Categorias categorias) {
        String sql = "delete from categorias where id = ?";
        try {
            if (null == preparedStatementEliminar) {
                preparedStatementEliminar = conexion.conexiondb().prepareStatement(sql);
            }
            preparedStatementEliminar.setLong(1, categorias.getId());

            logger.debug(preparedStatementEliminar);
            logger.info(categorias);

            return preparedStatementEliminar.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public List<Categorias> listar() {
        List<Categorias> categorias = new ArrayList<>();
        String sql = "select id, descripcion from categorias";
        try {
            if (null == preparedStatementListar) {
                preparedStatementListar = conexion.conexiondb().prepareStatement(sql);
            }

            ResultSet resultSet = preparedStatementListar.executeQuery();
            while (resultSet.next()) {
                Categorias categoria = new Categorias();
                categoria.setId(resultSet.getLong("id"));
                categoria.setDescripcion(resultSet.getString("descripcion"));
                // agrego el objeto a la lista
                categorias.add(categoria);
            }

            logger.debug(preparedStatementListar);
            logger.info(categorias);

        } catch (SQLException e) {
            logger.error(e);
        }

        return categorias;
    }

    public static void main(String[] args) {
        ICategorias icategorias = new ImplCategorias();
        Categorias categorias1 = new Categorias();
        categorias1.setDescripcion("vegetales");
    //    icategorias.insertar(categorias1);
        icategorias.buscarID(1l);
        icategorias.listar();
    }

}
