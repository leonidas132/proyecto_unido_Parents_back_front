package com.protalento.implementacion;

import com.protalento.entidadJdbc.Categorias;
import com.protalento.interfacesJdbc.ICategorias;

import java.util.List;

public class ImplCategorias implements ICategorias {
    @Override
    public Categorias buscarID(Long ID) {
        return null;
    }

    @Override
    public boolean insertar(Categorias categorias) {
        return false;
    }

    @Override
    public boolean modificar(Categorias categorias) {
        return false;
    }

    @Override
    public boolean eliminar(Categorias categorias) {
        return false;
    }

    @Override
    public List<Categorias> listar() {
        return null;
    }

}
