package com.protalento.interfacesJdbc;

import java.util.List;

public interface DAOGenerico <E,K>{
    E buscarID(K k);
    boolean insertar(E e);
    boolean modificar(E e);
    boolean eliminar(E e);
    List<E> listar();



}
