package com.protalento.interfacesJdbc;

import com.protalento.entidadJdbc.User;
import com.protalento.interfacesJdbc.DAOGenerico;


// estas interface IUsuario se utiliza para no modificar el DAOGenerico
public interface IUsuario extends DAOGenerico<User,String> {
    // esta interface se utiliza con el fin y en caso de que necesitemos metodos adicionales, de esta manera
    // evitamos de que tengamos que modificar DAOGenerico ejemplo

    User buscarPorCorreoClave(String correo, String clave); // de esta manera exigimos a la implementacion importar el nuevo metodo

    boolean actualizarFechasUltimoAcceso(User user);
    boolean ActualizarIntentosFallidos(User user);

}
